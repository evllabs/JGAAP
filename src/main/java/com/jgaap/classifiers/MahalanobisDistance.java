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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.Float64Matrix;
import org.jscience.mathematics.vector.Matrix;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.jgaap.generics.AnalysisDriver;
import com.jgaap.util.Document;
import com.jgaap.util.Event;
import com.jgaap.util.EventMap;
import com.jgaap.util.Pair;

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

	private ImmutableSet<Event> events;
	private Matrix<Float64> inverseCovarianceMatrix;
	private ImmutableMap<Document,EventMap> knownHistograms;
	
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
		ImmutableSet.Builder<Event> eventsBuilder = ImmutableSet.builder();
		ImmutableMap.Builder<Document, EventMap> knownHistogramsBuilder = ImmutableMap.builder();
		List<EventMap> histograms = new ArrayList<EventMap>(knowns.size());
		for(Document known : knowns){
			EventMap histogram = new EventMap(known);
			eventsBuilder.addAll(histogram.uniqueEvents());
			histograms.add(histogram);
			knownHistogramsBuilder.put(known, histogram);
		}
		events = eventsBuilder.build();
		knownHistograms = knownHistogramsBuilder.build();
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
