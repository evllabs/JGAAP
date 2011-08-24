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

import java.util.*;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.NeighborAnalysisDriver;
import com.jgaap.generics.Pair;

/**
 * This class is designe for its side effects only and should never be added to
 * the main branch of jgaap
 * 
 *
 * @author Michael Ryan
 * @since 5.0.0
 */

public class FrequencyCentroidDriver extends NeighborAnalysisDriver {

	public String displayName() {
		return "Frequency Centroid Driver" + getDistanceName();
	}

	public String tooltipText() {
		return " ";
	}

	public boolean showInGUI() {
		return false;
	}

	@Override
	public List<Pair<String, Double>> analyze(EventSet unknown, List<EventSet> known) {

        System.out.println("Now analyzing: " + unknown.getDocumentName());

        List<Pair<String, Double>> results = new ArrayList<Pair<String,Double>>();
//        if(!jgaapConstants.globalObjects.containsKey("orderedEvents")) {
            Map<String, List<EventSet>> knownAuthors = new HashMap<String, List<EventSet>>();
            Set<Event> events = new HashSet<Event>();
            for (EventSet eventSet : known) {
                if (knownAuthors.containsKey(eventSet.getAuthor())) {
                    knownAuthors.get(eventSet.getAuthor()).add(eventSet);
                } else {
                    List<EventSet> tmp = new ArrayList<EventSet>();
                    tmp.add(eventSet);
                    knownAuthors.put(eventSet.getAuthor(), tmp);
                }
                for (Event event : eventSet) {
                    events.add(event);
                }
            }

            Map<String, Map<Event, Double>> authorFrequencies = new HashMap<String, Map<Event, Double>>();
            for (String author : knownAuthors.keySet()) {
                Map<Event, Double> authorHistogram = new HashMap<Event, Double>();
                for (EventSet eventSet : knownAuthors.get(author)) {
                    EventHistogram current = new EventHistogram();
                    for (Event event : eventSet) {
                        current.add(event);
                    }
                    for (Event event : current) {
                        if (authorHistogram.containsKey(event)) {
                            double tmp = authorHistogram.get(event);
                            tmp += current.getRelativeFrequency(event)
                                    / knownAuthors.get(author).size();
                            authorHistogram.put(event, tmp);
                        } else {
                            authorHistogram.put(event, current.getRelativeFrequency(event)
                                    / knownAuthors.get(author).size());
                        }
                    }
                }
                authorFrequencies.put(author, authorHistogram);
            }

            EventHistogram unknownHS = new EventHistogram();
            for(Event e: unknown) {
                unknownHS.add(e);
            }
            Vector<Double> unknownVector = new Vector<Double>();
            List<Event> orderedEvents = new ArrayList<Event>(events);
            //Writer writer = new BufferedWriter(new FileWriter(new File(jgaapConstants.tmpDir()
            //		+ "key.centroid")));
            for (Event event : orderedEvents) {
                //writer.write(event.getEvent() + "\n");
                unknownVector.add(unknownHS.getRelativeFrequency(event));
            }
            //writer.close();

            for (String author : authorFrequencies.keySet()) {
                //Writer writer = new BufferedWriter(new FileWriter(new File(jgaapConstants.tmpDir()
                //		+ author + ".centroid")));
                Vector<Double> authorCentroid = new Vector<Double>();
                for (Event event : orderedEvents) {
                    if (authorFrequencies.get(author).containsKey(event)) {
                        authorCentroid.add(authorFrequencies.get(author).get(event));
                    } else {
                        authorCentroid.add(0.0);
                    }
                }

                results.add(new Pair<String, Double>(author, distance.distance(unknownVector, authorCentroid), 2));
//                jgaapConstants.globalObjects.put("authorFrequencies", authorFrequencies);
//                jgaapConstants.globalObjects.put((author + "Vector"), authorCentroid);

            }

//            jgaapConstants.globalObjects.put("orderedEvents", (Object)orderedEvents);
//            jgaapConstants.globalObjects.put("authorFrequencies", (Object)authorFrequencies);
            Collections.sort(results);
            return results;
//        }

//        else {
//            List<Event> orderedEvents = (List<Event>)jgaapConstants.globalObjects.get("orderedEvents");
//            Vector<Double> unknownVector = new Vector<Double>();
//            EventHistogram unknownHS = new EventHistogram();
//            for(Event e : unknown) {
//                unknownHS.add(e);
//            }
//            for(Event event : orderedEvents) {
//                unknownVector.add(unknownHS.getRelativeFrequency(event));
//            }
//            Map<String, Map<Event, Double>> authorFrequencies = (Map<String, Map<Event, Double>>)jgaapConstants.globalObjects.get("authorFrequencies");
//
//            for (String author : authorFrequencies.keySet()) {
//                results.add(new Pair<String, Double>(author, distance.distance(unknownVector, (Vector<Double>)jgaapConstants.globalObjects.get(author + "Vector"))));
//            }
//
//            return results;
//        }
	}

}
