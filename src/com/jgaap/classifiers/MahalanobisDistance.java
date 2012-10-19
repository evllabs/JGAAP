/*
 * JGAAP -- a graphical program for stylometric authorship attribution
 * Copyright (C) 2009,2011 by Patrick Juola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package com.jgaap.classifiers;

import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventMap;
import com.jgaap.generics.Pair;

import java.util.*;
import java.util.Map.Entry;

import org.jscience.mathematics.number.*;
import org.jscience.mathematics.vector.*;

/**
 * MahalanobisDistance class does the generalized squared interpoint distance.
 * This is the dissimilarity measure between two random vectors from the same
 * distribution. The random vectors are event histograms or the relative
 * frequencey of events. We use the sample covariance matrix composed of the
 * sample mean for each element in the vectors. Here the sample is all the known
 * eventsets.
 * 
 * 
 * @author Micahel Ryan
 * 
 */
public class MahalanobisDistance extends AnalysisDriver {

	private Set<Event> events;
	private Matrix<Float64> inverseCovarianceMatrix;
	private Map<Document,EventMap> knownHistograms;
	
	public String displayName() {
		return "Mahalanobis Distance";
	}

	public String tooltipText() {
		return "Generalized Squared Interpoint Distance";
	}

	public boolean showInGUI() {
		return true; 
	}

	public void train(List<Document> knowns){
		events = new HashSet<Event>();
		knownHistograms = new HashMap<Document, EventMap>();
		List<EventMap> histograms = new ArrayList<EventMap>(knowns.size());
		for(Document known : knowns){
			EventMap histogram = new EventMap(known);
			events.addAll(histogram.uniqueEvents());
			histograms.add(histogram);
			knownHistograms.put(known, histogram);
		}
		EventMap mu = EventMap.centroid(histograms);
		double[][] s = new double[events.size()][events.size()];
		int i = 0;
		for(Event x : events){
			int j =0;
			for(Event y : events){
				double tmp = 0;
				for(EventMap histogram : histograms){
					tmp += (histogram.relativeFrequency(x)-mu.relativeFrequency(x))*(histogram.relativeFrequency(y)-mu.relativeFrequency(y));
				}
				s[i][j] = tmp/(events.size()-1);
				if(i == j) {
					s[i][j] += 0.00001;
				}
				j++;
			}
			i++;
		}
		inverseCovarianceMatrix = Float64Matrix.valueOf(s).pseudoInverse();
	}
	
	@Override
	public List<Pair<String, Double>> analyze(Document unknown) {
		List<Pair<String, Double>> results = new ArrayList<Pair<String,Double>>();
		EventMap histogram = new EventMap(unknown);
		for(Entry<Document, EventMap> entry : knownHistograms.entrySet()){
			double[][] tmp = new double[events.size()][1];
			int i = 0;
			for(Event event : events){
				tmp[i][0] = histogram.relativeFrequency(event)-entry.getValue().relativeFrequency(event);
				i++;
			}
			Matrix<Float64> difference = Float64Matrix.valueOf(tmp);
			Matrix<Float64> radicand = difference.transpose().times(inverseCovarianceMatrix).times(difference);
			double result = radicand.get(0, 0).sqrt().doubleValue();
			results.add(new Pair<String, Double>(entry.getKey().getAuthor(), result, 2));
		}
		
		Collections.sort(results);

		return results;

	}

}
