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
import java.util.Set;

import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.EventGraph;
import com.jgaap.generics.Pair;

public class ThinXent extends AnalysisDriver {

	private int windowSize;
	private Map<String, EventGraph> eventGraphs;
	private boolean authorModel;
	
	public ThinXent() {
		addParams("model", "Model", "Document", new String[]{"Document","Author"}, false);
		addParams("windowSize", "Window Size", "15", new String[] { "1", "2",
				"3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
				"14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
				"24", "25" }, true);
	}

	public String displayName() {
		return "Thin Cross Entropy";
	}

	public String tooltipText() {
		return "JW Cross Entropy using event transitions in place of trie routs";
	}

	public boolean showInGUI() {
		return true;
	}

	public double distance(EventGraph eventGraph, EventSet eventSet) {

		double me = meanEntropy(eventGraph, eventSet);
		double hhat = (Math.log(1.0 * windowSize) / Math.log(2.0)) / me;

		return hhat;
	}

	private double meanEntropy(EventGraph eventGraph, EventSet eventSet) {

		double totalEntropy = 0;
		int trials = 0;

		for (int i = 0; i < eventSet.size(); i++) {
			totalEntropy += eventGraph.find(window(eventSet, i, windowSize));
			trials++;
		}
		return totalEntropy / trials;
	}

	private EventSet window(EventSet e1, int offset, int windowSize) {
		return e1.subset(offset, offset + windowSize);
	}
	
	private String identifier(EventSet eventSet){
		return (authorModel? eventSet.getAuthor() : eventSet.getAuthor()+" -"+eventSet.getDocumentName());
	}

	@Override
	public void train(List<EventSet> knownEventSets) throws AnalyzeException {
		windowSize = Integer.parseInt(getParameter("windowSize"));
		authorModel = getParameter("model").equalsIgnoreCase("author");
		eventGraphs = new HashMap<String, EventGraph>();
		for(EventSet eventSet : knownEventSets){
			EventGraph eventGraph = eventGraphs.get(identifier(eventSet));
			if(eventGraph == null){
				eventGraph = new EventGraph();
				eventGraphs.put(identifier(eventSet), eventGraph);
			}
			eventGraph.add(eventSet);
//			for (int i = 0; i < eventSet.size(); i++) {
//				EventSet dictionary;
//				dictionary = window(eventSet, i, windowSize);
//				eventGraph.add(dictionary);
//			}
		}
	}

	@Override
	public List<Pair<String, Double>> analyze(EventSet unknownEventSet)
			throws AnalyzeException {
		Set<Entry<String,EventGraph>> entrySet = eventGraphs.entrySet();
		List<Pair<String, Double>> results = new ArrayList<Pair<String,Double>>(entrySet.size());
		for(Entry<String,EventGraph> entry : entrySet){
			results.add(new Pair<String, Double>(entry.getKey(), distance(entry.getValue(), unknownEventSet), 2));
		}
		Collections.sort(results);
		return results;
	}
}
