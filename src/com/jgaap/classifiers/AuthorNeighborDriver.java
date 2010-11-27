/**
 *   JGAAP -- Java Graphical Authorship Attribution Program
 *   Copyright (C) 2009 Patrick Juola
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation under version 3 of the License.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/
package com.jgaap.classifiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.NeighborAnalysisDriver;

/**
 * Assigns authorship labels by using a nearest-neighbor approach on a given
 * distance/divergence function.
 * 
 */
public class AuthorNeighborDriver extends NeighborAnalysisDriver {

	public String displayName() {
		return "Author Neighbor Driver"+getDistanceName();
	}

	public String tooltipText() {
		return " ";
	}

	public boolean showInGUI() {
		return false;
	}

	public DistanceFunction Dist;

	@Override
	public String analyze(EventSet unknown, List<EventSet> knowns) {

		String auth = "";
		String[] authorArray;
		double[] distArray;
		List<String> authors = new ArrayList<String>();
		List<Double> distances = new ArrayList<Double>();
		Map<String, List<Double>> authorMap = new HashMap<String, List<Double>>();

		for (EventSet known : knowns) {
			double current = Dist.distance(unknown, known);
			authors.add(known.getAuthor());
			distances.add(current);
			System.out.print(unknown.getDocumentName() + "(Unknown)");
			System.out.print(":");
			System.out.print(known.getDocumentName() + "(" + known.getAuthor()
					+ ")\t");
			System.out.println("Distance is " + current);
		}
		for (int i = 0; i < authors.size(); i++) {
			if(authorMap.containsKey(authors.get(i))){
				List<Double> distance = authorMap.get(authors.get(i));
				distance.add(distances.get(i));
			}else{
				List<Double> distance = new ArrayList<Double>();
				distance.add(distances.get(i));
				authorMap.put(authors.get(i), distance);
			}
		}
		authorArray=new String[authorMap.size()];
		distArray = new double[authorMap.size()];
		int k=0;
		for(Entry<String, List<Double>> entry : authorMap.entrySet()){
			int count = 0;
			double distance = 0;
			for(Double current : entry.getValue()){
				count++;
				distance+=current;
			}
			authorArray[k]=entry.getKey();
			distArray[k]=distance/count;
			k++;
		}

		// sort algorithm here;

		for (int i = 0; i < distArray.length - 1; i++) {
			for (int j = distArray.length - 1; j > i; j--) {
				if (distArray[j - 1] > distArray[j]) {
					double tmp = distArray[j - 1];
					distArray[j - 1] = distArray[j];
					distArray[j] = tmp;
					String tmpA = authorArray[j - 1];
					authorArray[j - 1] = authorArray[j];
					authorArray[j] = tmpA;
				}
			}
		}

		for (int i = 1; i <= distArray.length; i++) {
			auth = auth + "\n" + i + ". " + authorArray[i - 1] + " "
					+ distArray[i - 1];
		}

		auth = auth + "\n----------------------------------------\n";

		return auth;// +" "+min_distance;
	}

	public DistanceFunction getDistanceFunction() {
		return Dist;
	}

	public void setDistance(DistanceFunction Dist) {
		this.Dist = Dist;
	}

}
