// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
package com.jgaap.classifiers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jgaap.jgaapConstants;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.NeighborAnalysisDriver;
import com.jgaap.generics.Pair;

/**
 * Assigns authorship labels by using a nearest-neighbor approach on a given
 * distance/divergence function.
 * 
 */
public class AuthorNeighborDriver extends NeighborAnalysisDriver {

	public String displayName() {
		return "Author Neighbor Driver" + getDistanceName();
	}

	public String tooltipText() {
		return " ";
	}

	public boolean showInGUI() {
		return false;
	}

	@Override
	public List<Pair<String, Double>> analyze(EventSet unknown, List<EventSet> knowns) {

		List<String> authors = new ArrayList<String>();
		List<Double> distances = new ArrayList<Double>();
		Map<String, List<Double>> authorMap = new HashMap<String, List<Double>>();
		List<Pair<String, Double>> results = new ArrayList<Pair<String, Double>>();

		for (EventSet known : knowns) {
			double current = distance.distance(unknown, known);
			authors.add(known.getAuthor());
			distances.add(current);
			if (jgaapConstants.JGAAP_DEBUG_VERBOSITY) {
				System.out.print(unknown.getDocumentName() + "(Unknown)");
				System.out.print(":");
				System.out.print(known.getDocumentName() + "("
						+ known.getAuthor() + ")\t");
				System.out.println("Distance is " + current);
			}
		}
		for (int i = 0; i < authors.size(); i++) {
			if (authorMap.containsKey(authors.get(i))) {
				List<Double> distance = authorMap.get(authors.get(i));
				distance.add(distances.get(i));
			} else {
				List<Double> distance = new ArrayList<Double>();
				distance.add(distances.get(i));
				authorMap.put(authors.get(i), distance);
			}
		}
		for (Entry<String, List<Double>> entry : authorMap.entrySet()) {
			int count = 0;
			double distance = 0;
			for (Double current : entry.getValue()) {
				count++;
				distance += current;
			}
			results.add(new Pair<String, Double>(entry.getKey(), distance / count, 2));
		}
		Collections.sort(results);
		return results;
	}

}
