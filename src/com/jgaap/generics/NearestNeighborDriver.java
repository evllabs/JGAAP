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
package com.jgaap.generics;

import java.util.HashMap;
import java.util.Vector;

import com.jgaap.jgaapConstants;

/**
 * Assigns authorship labels by using a nearest-neighbor approach on a given
 * distance/divergence function.
 * 
 */
public class NearestNeighborDriver extends AnalysisDriver {

	private boolean meanAuthorDistance = jgaapConstants.globalParams.getParameter("authorDistance").equals("true");
	
	public void setMeanAuthorDistance(boolean mean){
		this.meanAuthorDistance = mean;
	}

	public String displayName() {
		return "NearestNeighborDriver ";
	}

	public String tooltipText() {
		return " ";
	}

	public boolean showInGUI() {
		return false;
	}

	public DistanceFunction Dist;

	@Override
	public String analyze(EventSet unknown, Vector<EventSet> known) {
		//removed for detailed ranked report
		//double min_distance = Double.MAX_VALUE;
		String auth ="";
		HashMap<String, Vector<Double>> authorDistances = null;
		String [] authorArray = new String[known.size()];
		double [] distArray = new double[known.size()];

		if (meanAuthorDistance) {
			authorDistances = new HashMap<String, Vector<Double>>();
		}

		for (int i = 0; i < known.size(); i++) {
			double current = Dist.distance(unknown, known.elementAt(i));
			authorArray[i] = known.elementAt(i).getAuthor();
			distArray[i] = current;
			if (!meanAuthorDistance) {
				System.out.print(unknown.getDocumentName() + "(Unknown)");
				System.out.print(":");
				System.out.print(known.elementAt(i).getDocumentName() + "("
						+ known.elementAt(i).getAuthor() + ")\t");
				System.out.println("Distance is " + current);
				/*
				 * removed for detailed ranked output
				 * 
				 * if (current < min_distance) {
					min_distance = current;
					auth = known.elementAt(i).getAuthor();
				}*/
				
			} else {
				Vector<Double> tmp = authorDistances.get(known.elementAt(i)
						.getAuthor());
				if (tmp==null)
					tmp = new Vector<Double>();
				tmp.add(current);
				authorDistances.put(known.elementAt(i).getAuthor(), tmp);
			}
		}
		if (meanAuthorDistance) {
			for (String author : authorDistances.keySet()) {
				int k=0;
				Vector<Double> currentAuthorDistances = authorDistances
						.get(author);
				double average = 0;
				for (Double dist : currentAuthorDistances) {
					average += dist;
				}
				average /= currentAuthorDistances.size();
				authorArray[k]=author;
				distArray[k]=average;
				k++;
				/*
				 * removed for detailed rank report
				 * 
				if (average < min_distance) {
					min_distance = average;
					//auth = author;
				}
				*/
			}
		}
		
		//sort algorithm here;
		
		for(int i=0; i<distArray.length-1; i++){
			for(int j=distArray.length-1; j>i; j--){
				if(distArray[j-1]>distArray[j]){
					double tmp = distArray[j-1];
					distArray[j-1]=distArray[j];
					distArray[j]=tmp;
					String tmpA = authorArray[j-1];
					authorArray[j-1]= authorArray[j];
					authorArray[j]=tmpA;
				}
			}
		}
		
		/*auth="1."+authorArray[0]+" "+distArray[0]+
				" 2."+authorArray[1]+" "+distArray[1]+
				" 3."+authorArray[2]+" "+distArray[1];
				*/
		for(int i=1; i<=distArray.length; i++){
			auth = auth +"\n"+i+". "+ authorArray[i-1]+" "+distArray[i-1];
		}
		
		auth = auth +"\n----------------------------------------\n";
		
		if(meanAuthorDistance)
			auth = "\nAuthor Distance\n"+auth;
		
		return auth;//+" "+min_distance;
	}

	public DistanceFunction getDist() {
		return Dist;
	}

	public void setDist(DistanceFunction Dist) {
		this.Dist = Dist;
	}
}
