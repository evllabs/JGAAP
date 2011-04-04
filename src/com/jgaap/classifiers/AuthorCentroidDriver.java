// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
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
			}
			writer.close();
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
		EventHistogram unknownHist = new EventHistogram();
		for(Event event : unknown){
			unknownHist.add(event);
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
