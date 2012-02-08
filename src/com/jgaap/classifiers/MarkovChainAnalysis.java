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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

/**
 * @author Darren Vescovi
 * 
 */
public class MarkovChainAnalysis extends AnalysisDriver {

	private Map<EventSet, Map<Event, Map<Event, Double>>> knownProbabilityMatacies;

	@Override
	public String displayName() {

		return "Markov Chain Analysis";
	}

	@Override
	public boolean showInGUI() {

		return true;
	}

	@Override
	public String tooltipText() {

		return "First Order Markov Chain Analysis";
	}

	public void train(List<EventSet> known) {
		Iterator<EventSet> setIt = known.iterator();
		Map<Event, Map<Event, Double>> probMatrix = new HashMap<Event, Map<Event, Double>>();

		// loop throught the known and assign a probability to the unknown set
		// using
		// a transition probability matrix built from each known event set.
		while (setIt.hasNext()) {
			Map<Event, Map<Event, Double>> matrix = new HashMap<Event, Map<Event, Double>>();

			EventSet ev = setIt.next();

			// Iterate over events to create a matrix with
			// counts of each time a event pair sequence
			// appears.
			Iterator<Event> eventIt = ev.iterator();
			if (eventIt.hasNext()) {
				// get the first event
				Event e1 = eventIt.next();
				while (eventIt.hasNext()) {
					// get the next event
					Event e2 = eventIt.next();
					if (matrix.containsKey(e1)) {
						if (matrix.get(e1).containsKey(e2)) {
							// find out if the event sequence is already in the
							// matrix
							// if so increment the count by 1;
							double tmp = matrix.get(e1).get(e2).doubleValue();
							matrix.get(e1).remove(e2);
							matrix.get(e1).put(e2, Double.valueOf(tmp + 1));

						} else {
							// add the new sequence provided the first event is
							// already
							// in the matrix
							matrix.get(e1).put(e2, Double.valueOf(1));
						}
					} else {
						// add the new sequence to matrix
						matrix.put(e1, new HashMap<Event, Double>());
						matrix.get(e1).put(e2, Double.valueOf(1));
					}
					// reassign e1 to be e2
					e1 = e2;

				}
			}

			// TODO calculate the probabilities of each event pair sequence to
			// create the Markov chain.

			// Get the row totals i.e. total of the double values from the
			// second hashtable
			// then divide each row entry by its row total to obtain a valid
			// transition probability matrix

			for (Entry<Event, Map<Event, Double>> matrixEntry : matrix
					.entrySet()) {

				Event event = matrixEntry.getKey();

				probMatrix.put(event, new HashMap<Event, Double>());
				Set<Entry<Event, Double>> matrixCellEntrySet = matrixEntry
						.getValue().entrySet();
				double rowTotal = 0;
				for (Entry<Event, Double> matrixCellEntry : matrixCellEntrySet) {
					// Get the row totals.
					rowTotal += matrixCellEntry.getValue().doubleValue();
				}

				for (Entry<Event, Double> matrixCellEntry : matrixCellEntrySet) {
					// divide each row entry by the row total to obtain a valid
					// transition probability matrix.
					Event event2 = matrixCellEntry.getKey();

					double tmp = matrixCellEntry.getValue().doubleValue();
					// System.out.println(tmp.doubleValue());
					tmp = tmp / rowTotal;

					probMatrix.get(event).put(event2, Double.valueOf(tmp));

				}

			}
			knownProbabilityMatacies.put(ev, probMatrix);
		}
	}

	@Override
	public List<Pair<String, Double>> analyze(EventSet unknown) {

		List<Pair<String, Double>> results = new ArrayList<Pair<String, Double>>();

		for (Entry<EventSet, Map<Event, Map<Event, Double>>> entry : knownProbabilityMatacies.entrySet()) {
			// now assign probability to the unknown event set using the newly
			// constructed transition probability matrix obtained above
			Map<Event, Map<Event, Double>> probMatrix = entry.getValue();
			Iterator<Event> unknownIt = unknown.iterator();
			Event event1;
			double prob = 0;
			if (unknownIt.hasNext()) {
				event1 = unknownIt.next();

				while (unknownIt.hasNext()) {
					Event event2 = unknownIt.next();
					// System.out.println(event1.toString()+event2.toString());
					if (probMatrix.containsKey(event1)) {
						if (probMatrix.get(event1).containsKey(event2)) {

							// use the negative log(base e) sum since
							// multiplication will
							// almost always result in a probability of 0;\
							double tmp = probMatrix.get(event1).get(event2);
							// System.out.println(tmp);
							prob -= Math.log(tmp);
							// System.out.println(prob+"-----------");
						}
					}

					// set event1 equal to event2
					event1 = event2;

				}

			}

			// assign the probability to the current known document author
			results.add(new Pair<String, Double>(entry.getKey().getAuthor(), prob, 2));
		}
		// return the results
		Collections.sort(results);
		Collections.reverse(results);

		return results;
	}

}
