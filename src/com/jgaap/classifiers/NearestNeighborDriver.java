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

import java.util.List;

import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.NeighborAnalysisDriver;

/**
 * Assigns authorship labels by using a nearest-neighbor approach on a given
 * distance/divergence function.
 * 
 */
public class NearestNeighborDriver extends NeighborAnalysisDriver {

	public String displayName() {
		return "Nearest Neighbor Driver"+getDistanceName();
	}

	public String tooltipText() {
		return " ";
	}

	public boolean showInGUI() {
		return false;
	}

	public DistanceFunction distance;

	@Override
	public String analyze(EventSet unknown, List<EventSet> known) {
		// removed for detailed ranked report
		// double min_distance = Double.MAX_VALUE;
		String auth = "";
		String[] authorArray = new String[known.size()];
		double[] distArray = new double[known.size()];

		for (int i = 0; i < known.size(); i++) {
			double current = distance.distance(unknown, known.get(i));
			authorArray[i] = known.get(i).getAuthor();
			distArray[i] = current;
			System.out.print(unknown.getDocumentName() + "(Unknown)");
			System.out.print(":");
			System.out.print(known.get(i).getDocumentName() + "("
					+ known.get(i).getAuthor() + ")\t");
			System.out.println("Distance is " + current);
			/*
			 * removed for detailed ranked output
			 * 
			 * if (current < min_distance) { min_distance = current; auth =
			 * known.elementAt(i).getAuthor(); }
			 */

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

		/*
		 * auth="1."+authorArray[0]+" "+distArray[0]+
		 * " 2."+authorArray[1]+" "+distArray[1]+
		 * " 3."+authorArray[2]+" "+distArray[1];
		 */
		for (int i = 1; i <= distArray.length; i++) {
			auth = auth + "\n" + i + ". " + authorArray[i - 1] + " "
					+ distArray[i - 1];
		}

		auth = auth + "\n----------------------------------------\n";

		return auth;// +" "+min_distance;
	}

	public DistanceFunction getDistanceFunction() {
		return distance;
	}

	public void setDistance(DistanceFunction Dist) {
		this.distance = Dist;
	}

}
