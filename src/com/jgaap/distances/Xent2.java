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
package com.jgaap.distances;

import java.util.HashMap;
import java.util.Map;

import com.jgaap.generics.DivergenceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;

/**
 * Methods for building and manipulating a Cross Entropy Dictionary. A Cross
 * Entropy Dictionary is a hashtable where the keys are Events and the values
 * are Cross Entropy Dictionary Nodes
 **/
class XEDictionary {

	XEDictionaryNode root;

	XEDictionary() {
		root = new XEDictionaryNode();
		root.key = null;
	}

	public int find(EventSet eventSet) {
		int matchlength = 0;
		XEDictionaryNode node = root;
		for (Event event : eventSet) {
			if (node.isEventInLevel(event)) {
				matchlength++;
				node = node.get(event);
			} else {
				break;
			}
		}
		return matchlength;
	}

	public void add(EventSet eventSet) {
		XEDictionaryNode node = root;
		for (Event event : eventSet) {
			if (!node.isEventInLevel(event)) {
				node.addEventToLevel(event);
			}
			node = node.get(event);
		}
	}

	@Override
	public String toString() {
		return root.toString();
	}
}

/**
 * Cross Entropy Dictionary Node Each node contains a single event and a
 * hashtable containing Events as keys and Cross Entropy Dictionary Nodes as
 * values to the keys. The hashtable can be of any size. When this node is
 * placed in a tree structure, a generalized Trie is created
 **/
class XEDictionaryNode {
	Event key;
	Map<Event, XEDictionaryNode> child = new HashMap<Event, XEDictionaryNode>();

	XEDictionaryNode() {
		key = null;
	}

	XEDictionaryNode(Event key) {
		this.key = key;
	}

	void addEventToLevel(Event e) {
		XEDictionaryNode node = new XEDictionaryNode();
		node.key = e;
		child.put(e, node);
	}

	/**
	 * Shows the events at this level of the tree. Used mainly for debugging
	 * purposes
	 **/
	String eventsAtThisLevel() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Event event : child.keySet()) {
			stringBuilder.append(event);
		}
		return stringBuilder.toString();
	}

	XEDictionaryNode get(Event e) {
		return child.get(e);
	}

	boolean isEventInLevel(Event e) {
		return child.containsKey(e);
	}

	void setKey(Event key) {
		this.key = key;
	}

	@Override
	public String toString() {
		String t = "";
		if (key != null) {
			t = key.toString();
		}
		if (child != null) {
			t += eventsAtThisLevel();
			t += child;
		}
		return t;
	}

}

public class Xent2 extends DivergenceFunction {

	public Xent2() {
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

	private int windowSize = 15;

	@Override
	public double divergence(EventSet e1, EventSet e2) {
		if (!getParameter("windowSize").equals("")) {
			windowSize = Integer.parseInt(getParameter("windowSize"));
		}
		return distance(e1, e2);
	}

	public double distance(EventSet e1, EventSet e2) {

		double me = meanEntropy(e1, e2, windowSize);
		double hhat = (Math.log(1.0 * windowSize) / Math.log(2.0)) / me;

		return hhat;
	}

	private double meanEntropy(EventSet e1, EventSet e2, int windowSize) {

		double totalEntropy = 0;
		int trials = 0;

		if (windowSize > e1.size() - 1) {
			windowSize = e1.size();
		}

		XEDictionary xed = new XEDictionary();

		for (int j = 0; j < e1.size() - windowSize; j++) {
			EventSet dictionary;
			dictionary = window(e1, j, windowSize);
			xed.add(dictionary);
		}
		for (int i = 0; i <= e2.size() - windowSize; i++) {
			totalEntropy += xed.find(window(e2, i, windowSize));
			trials++;
		}
		return totalEntropy / trials;
	}

	private EventSet window(EventSet e1, int offset, int windowSize) {
		return e1.subset(offset, offset + windowSize);
	}
}
