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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import com.jgaap.jgaapConstants;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.NeighborAnalysisDriver;
import com.jgaap.generics.Pair;

/**
 * Assigns authorship labels by using a nearest-neighbor approach on a given
 * distance/divergence function.
 * 
 */
public class AuthorCentroidDriver extends NeighborAnalysisDriver {

	public String displayName() {
		return "Author Centroid Driver" + getDistanceName();
	}

	public String tooltipText() {
		return " ";
	}

	public boolean showInGUI() {
		return false;
	}

	@Override
	public List<Pair<String, Double>> analyze(EventSet unknown, List<EventSet> known) {
		List<Pair<String, Double>> results = new ArrayList<Pair<String, Double>>();
		List<EventSet> knownCentroids = new ArrayList<EventSet>();
		Map<String, List<EventSet>> knownAuthors = new HashMap<String, List<EventSet>>();
		for (EventSet eventSet : known) {
			if (knownAuthors.containsKey(eventSet.getAuthor())) {
				knownAuthors.get(eventSet.getAuthor()).add(eventSet);
			} else {
				List<EventSet> tmp = new ArrayList<EventSet>();
				tmp.add(eventSet);
				knownAuthors.put(eventSet.getAuthor(), tmp);
			}
		}
		Set<Event> events = new HashSet<Event>();
		List<EventHistogram> histograms = new ArrayList<EventHistogram>();
		List<String> authors = new ArrayList<String>(knownAuthors.keySet());
		for (String author : authors) {
			EventSet centroid = new EventSet(author);
			// Writer writer = new BufferedWriter(new FileWriter(new
			// File(jgaapConstants.tmpDir()+ author + ".centroid")));
			double count = 0;
			EventHistogram hist = new EventHistogram();
			for (EventSet eventSet : knownAuthors.get(author)) {
				for (Event event : eventSet) {
					hist.add(event);
					events.add(event);
				}
				count++;
			}
			histograms.add(hist);
			for (Event event : hist) {
				// writer.write(event.getEvent()+"\t"+hist.getAbsoluteFrequency(event)/count+"\n");
				for (int i = 0; i < Math.round(hist.getAbsoluteFrequency(event) / count); i++) {
					centroid.addEvent(event);
				}
			}
			knownCentroids.add(centroid);
		}
		List<Event> orderedEvents = new ArrayList<Event>(events);

		try {
			Writer writer = new BufferedWriter(new FileWriter(new File(jgaapConstants.tmpDir()+ "key.centroid")));
			for (Event event : orderedEvents) {
				writer.write(event.getEvent() + "\n");
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		int j = 0;
		for (EventHistogram hist : histograms) {
			try {
				Writer writer = new BufferedWriter(new FileWriter(new File(jgaapConstants.tmpDir()+ authors.get(j)+".centroid")));
				for (Event event : orderedEvents) {
					writer.write(hist.getRelativeFrequency(event)+"\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			j++;
		}

		for (int i = 0; i < knownCentroids.size(); i++) {
			double current = distance.distance(unknown, knownCentroids.get(i));
			results.add(new Pair<String, Double>(knownCentroids.get(i).getAuthor(), current, 2));
			if (jgaapConstants.JGAAP_DEBUG_VERBOSITY) {
				System.out.print(unknown.getDocumentName() + "(Unknown)");
				System.out.print(":");
				System.out.print(knownCentroids.get(i).getDocumentName() + "("
						+ knownCentroids.get(i).getAuthor() + ")\t");
				System.out.println("Distance is " + current);
			}
		}
		Collections.sort(results);
		return results;
	}

}
