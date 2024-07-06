package com.jgaap.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Methods for building and manipulating a Cross Entropy Dictionary. A Cross
 * Entropy Dictionary is a hashtable where the keys are Events and the values
 * are Cross Entropy Dictionary Nodes
 * 
 * @author Michael Ryan
 * @author Juola
 **/
public class EventTrie {

	private Node root;

	public EventTrie() {
		root = new Node();
	}

	public int find(Iterable<Event> events) {
		int matchlength = 0;
		Node node = root;
		for (Event event : events) {
			if (node.isEventInLevel(event)) {
				matchlength++;
				node = node.get(event);
			} else {
				break;
			}
		}
		return matchlength;
	}

	public void add(Iterable<Event> events) {
		Node node = root;
		for (Event event : events) {
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
	
	/**
	 * Cross Entropy Dictionary Node Each node contains a single event and a
	 * hashtable containing Events as keys and Cross Entropy Dictionary Nodes as
	 * values to the keys. The hashtable can be of any size. When this node is
	 * placed in a tree structure, a generalized Trie is created
	 **/
	private class Node {
		private Event key;
		private Map<Event, Node> child = new HashMap<Event, Node>();

		Node() {
			key = null;
		}

		Node(Event key) {
			this.key = key;
		}

		void addEventToLevel(Event e) {
			Node node = new Node(e);
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

		Node get(Event e) {
			return child.get(e);
		}

		boolean isEventInLevel(Event e) {
			return child.containsKey(e);
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
}

