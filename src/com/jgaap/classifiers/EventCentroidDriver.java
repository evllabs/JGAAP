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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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
 *
 * @author Michael Ryan
 * @since 5.0.0
 */

public class EventCentroidDriver extends NeighborAnalysisDriver {

	public String displayName() {
		return "Event Centroid Driver" + getDistanceName();
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
		Set<Event> events = new HashSet<Event>();
		Map<String, List<EventHistogram>> authorDocumentHistograms = new HashMap<String, List<EventHistogram>>();
		for(EventSet eventSet : known){
			EventHistogram eventHistogram = new EventHistogram();
			for(Event event : eventSet){
				eventHistogram.add(event);
				events.add(event);
			}
			if(authorDocumentHistograms.containsKey(eventSet.getAuthor())){
				authorDocumentHistograms.get(eventSet.getAuthor()).add(eventHistogram);
			}else {
				List<EventHistogram> tmp = new ArrayList<EventHistogram>();
				tmp.add(eventHistogram);
				authorDocumentHistograms.put(eventSet.getAuthor(), tmp);
			}
		}
		
		List<Event> orderedEvents = new ArrayList<Event>(events);
		
		try {
			Writer writer = new BufferedWriter(new FileWriter(new File(jgaapConstants.JGAAP_TMPDIR+"key.centroid")));
			for(Event event : orderedEvents){
				writer.write(event.getEvent());
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		List<EventSet> knownCentroids = new ArrayList<EventSet>();
		
		for(String author : authorDocumentHistograms.keySet()){
			try {
				Writer writer = new BufferedWriter(new FileWriter(new File(jgaapConstants.JGAAP_TMPDIR+author+".centroid")));
				List<EventHistogram> currentHistograms = authorDocumentHistograms.get(author);
				for(Event event : orderedEvents){
					double sum = 0;
					double totalEvents = 0;
					for(EventHistogram eventHistogram : currentHistograms){
						sum += eventHistogram.getAbsoluteFrequency(event);
						totalEvents += eventHistogram.getNTokens();
					}
					double frequency = ((double)Math.round(sum/currentHistograms.size())) / Math.round(totalEvents/currentHistograms.size());
					writer.write(frequency+"\n");
				}
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//TODO: 
//		for(String author : authorDocumentHistograms.keySet()){
//			EventSet current = new EventSet(author);
//			for(EventHistogram : ){
//				
//			}
//		}
		
		
//		Map<String, Map<Event, Integer>> authorHistograms = new HashMap<String, Map<Event, Integer>>();  
//		Map<String, Integer>  authorEventCounts = new HashMap<String, Integer>();
//		
//		for(EventSet eventSet : known){
//			for(Event event : eventSet){
//				if(authorHistograms.containsKey(eventSet.getAuthor())){
//					if(authorHistograms.get(eventSet.getAuthor()).containsKey(event)){
//						Integer tmp = authorHistograms.get(eventSet.getAuthor()).get(event);
//						tmp++;
//						authorHistograms.get(eventSet.getAuthor()).put(event, tmp);
//					} else {
//						Integer tmp = 1;
//						authorHistograms.get(eventSet.getAuthor()).put(event, tmp);
//					}
//					Integer tmp = authorEventCounts.get(eventSet.getAuthor());
//					tmp++;
//					authorEventCounts.put(eventSet.getAuthor(), tmp);
//				}else { 
//					Map<Event, Integer> tmp = new HashMap<Event, Integer>();
//					tmp.put(event, 1);
//					authorHistograms.put(eventSet.getAuthor(), tmp);
//					authorEventCounts.put(eventSet.getAuthor(), 1);
//				}
//				events.add(event);
//			}
//		}
//		
//		List<Event> orderedEvents = new ArrayList<Event>(events);
//		try {
//			Writer writer = new BufferedWriter(new FileWriter(new File(jgaapConstants.tmpDir()+"key.centroid")));
//			for(Event event : orderedEvents){
//				writer.write(event.getEvent()+"\n");
//			}
//			writer.flush();
//			writer.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		for(String author : authorHistograms.keySet()){
//			int i = 0;
//			try {
//				Writer writer = new BufferedWriter(new FileWriter(new File(jgaapConstants.tmpDir()+author+".centroid")));
//				Map<Event, Integer> current = authorHistograms.get(author);
//				double count = authorEventCounts.get(author);
//				for(Event event : orderedEvents){
//					if(current.containsKey(event)){
//						Double tmp = current.get(event)/count;
//						writer.write(tmp.toString() + "\n");
//						i++;
//					}else {
//						writer.write("0.0\n");
//						i++;
//					}
//				}
//				writer.flush();
//				writer.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println(i);
//		}
//		
		return results;
	}

}
