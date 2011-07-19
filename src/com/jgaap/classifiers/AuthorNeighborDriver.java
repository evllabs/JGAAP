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
 *
 * @author Michael Ryan
 * @since 5.0.0
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
