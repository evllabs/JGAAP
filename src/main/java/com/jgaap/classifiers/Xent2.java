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
import com.jgaap.util.Document;
import com.jgaap.util.EventSet;
import com.jgaap.util.EventTrie;
import com.jgaap.util.Pair;

public class Xent2 extends AnalysisDriver {

	private int windowSize;
	private Map<String, EventTrie> eventTries;
	private boolean authorModel;
	
	public Xent2() {
		addParams("model", "Model", "Document", new String[]{"Document","Author"}, false);
		addParams("windowSize", "Window Size", "15", new String[] { "1", "2",
				"3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
				"14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
				"24", "25" }, false);
	}

	public String displayName() {
		return "JW Cross Entropy";
	}

	public String tooltipText() {
		return "Juola-Wyner Cross Entropy";
	}

	public boolean showInGUI() {
		return true;
	}

	public double distance(EventTrie eventTrie, Document document) {

		double me = meanEntropy(eventTrie, document);
		double hhat = (Math.log(1.0 * windowSize) / Math.log(2.0)) / me;

		return hhat;
	}

	private double meanEntropy(EventTrie eventTrie, Document document) {

		double totalEntropy = 0;
		int trials = 0;

		for(EventSet eventSet : document.getEventSets().values()){
			for (int i = 0; i < eventSet.size(); i++) {
				totalEntropy += eventTrie.find(window(eventSet, i, windowSize));
				trials++;
			}
		}
		return totalEntropy / trials;
	}

	private EventSet window(EventSet e1, int offset, int windowSize) {
		return e1.subset(offset, offset + windowSize);
	}
	
	private String identifier(Document document){
		return (authorModel? document.getAuthor() : document.getAuthor()+" -"+document.getFilePath());
	}

	@Override
	public void train(List<Document> knownDocuments) throws AnalyzeException {
		windowSize = getParameter("windowSize", 15);
		authorModel = getParameter("model").equalsIgnoreCase("author");
		eventTries = new HashMap<String, EventTrie>();
		for(Document document : knownDocuments){
			EventTrie eventTrie = eventTries.get(identifier(document));
			if(eventTrie == null){
				eventTrie = new EventTrie();
				eventTries.put(identifier(document), eventTrie);
			}
			for(EventSet eventSet : document.getEventSets().values()) {
				for (int i = 0; i < eventSet.size(); i++) {
					EventSet dictionary;
					dictionary = window(eventSet, i, windowSize);
					eventTrie.add(dictionary);
				}
			}
		}
	}

	@Override
	public List<Pair<String, Double>> analyze(Document unknownDocument)
			throws AnalyzeException {
		Set<Entry<String,EventTrie>> entrySet = eventTries.entrySet();
		List<Pair<String, Double>> results = new ArrayList<Pair<String,Double>>(entrySet.size());
		for(Entry<String,EventTrie> entry : entrySet){
			results.add(new Pair<String, Double>(entry.getKey(), distance(entry.getValue(), unknownDocument), 2));
		}
		Collections.sort(results);
		return results;
	}
}
