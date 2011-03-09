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
package com.jgaap.distances;

import java.util.Enumeration;
import java.util.Hashtable;

import com.jgaap.jgaapConstants;
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
	}

	public void build(EventSet e) {
		for (int i = 0; i < e.size(); i++) {
			Event start = e.eventAt(i);
			if (!root.isEventInLevel(start)) {
				insertAtRoot(start, e, i);
			} else {
				insertBelowRoot(start, e, i);
			}

		}
		root.key = null;
	}

	public int find(EventSet e) {
		int matchlength = 0;
		boolean matched = false;
		XEDictionaryNode node = root;
		while ((matchlength < e.size()) && !matched) {
			if (node.isEventInLevel(e.eventAt(matchlength))) {
				node = node.get(e.eventAt(matchlength));
				matchlength++;
			} else {
				matched = true;
			}
		}
		return matchlength;
	}

	private void insertAtRoot(Event start, EventSet e, int offset) {
		root.addEventToLevel(start);
		XEDictionaryNode node;
		node = root;
		int j = offset;
		while (j < e.size() - 1) {
			node = node.get(e.eventAt(j));
			j++;
			// System.out.println("Adding Event: " + e.eventAt(j));
			node.addEventToLevel(e.eventAt(j));
		}
	}

	private void insertBelowRoot(Event start, EventSet e, int offset) {
		XEDictionaryNode node;
		node = root;
		// System.out.println("Event at offset: " + e.eventAt(offset));
		node = node.get(e.eventAt(offset));
		int j = offset;
		boolean matches = true; // match the events up to a given level
		while (matches && (j < e.size() - 1)) {
			j++;
			if (node.isEventInLevel(e.eventAt(j))) {
				// System.out.println("Match at level: " + e.eventAt(j));
				node = node.get(e.eventAt(j));
			} else {
				matches = false;
			}
		}
		for (int i = j; i < e.size(); i++) {
			// System.out.println("Adding Event: " + e.eventAt(i));
			node.addEventToLevel(e.eventAt(i));
			node = node.get(e.eventAt(i));
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
	Hashtable<Event, XEDictionaryNode> child = new Hashtable<Event, XEDictionaryNode>();

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
		String t = new String();
		for (Enumeration<Event> en = child.keys(); en.hasMoreElements();) {
			t += en.nextElement();
		}
		return t;
	}

	XEDictionaryNode get(Event e) {
		return child.get(e);
	}

	boolean isEventInLevel(Event e) {
		return child.containsKey(e);
	}

	String printKey(XEDictionaryNode key) {
		return child.get(key).toString();
	}

	void setKey(Event key) {
		this.key = key;
	}

	@Override
	public String toString() {
		String t = new String();
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
	public String displayName() {
		return "JW Cross Entropy";
	}

	public String tooltipText() {
		return "Juola-Wyner Cross Entropy (Slower)";
	}

	public boolean showInGUI() {
		return true;
	}

	private int windowSize = 500;

	@Override
	public double divergence(EventSet e1, EventSet e2) {
		if (!jgaapConstants.globalParams.getParameter("windowSize").equals("")) {
			windowSize = Integer.parseInt(jgaapConstants.globalParams
					.getParameter("windowSize"));
		}
		return distance(e1, e2, windowSize);
	}

	public double distance(EventSet e1, EventSet e2, int windowSize) {

		double me = meanEntropy(e1, e2, windowSize);
		double hhat = (Math.log(1.0 * windowSize) / Math.log(2.0)) / me;

		return hhat;
	}

	public int getWindowSize() {
		return windowSize;
	}

	private double meanEntropy(EventSet e1, EventSet e2, int windowSize) {

		double totalEntropy = 0;
		int trials = 0;

		if (windowSize > e1.size() - 1) {
			windowSize = e1.size();
		}

		for (int j = 0; j <= e1.size() - windowSize; j++) {
			XEDictionary xed = new XEDictionary();
			EventSet dictionary;
			dictionary = window(e1, j, windowSize);
			xed.build(dictionary);

			for (int i = 0; i <= e2.size()-windowSize; i++) {
				totalEntropy += xed.find(window(e2,i,windowSize));
				trials++;
			}
		}
		return totalEntropy / trials;
	}

	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}

	private EventSet window(EventSet e1, int offset, int windowSize) {

		return e1.subset(offset, offset + windowSize);
	}

}
