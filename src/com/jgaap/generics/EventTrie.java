package com.jgaap.generics;

import java.util.HashMap;
import java.util.Map;

/**
 * Methods for building and manipulating a Cross Entropy Dictionary. A Cross
 * Entropy Dictionary is a hashtable where the keys are Events and the values
 * are Cross Entropy Dictionary Nodes
 **/
public class EventTrie {

	EventTrieNode root;

	public EventTrie() {
		root = new EventTrieNode();
		root.key = null;
	}

	public int find(EventSet eventSet) {
		int matchlength = 0;
		EventTrieNode node = root;
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
		EventTrieNode node = root;
		for (Event event : eventSet) {
			if (!node.isEventInLevel(event)) {
				node.addEventToLevel(event);
			}
			node = node.get(event);
			node.increment();
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
class EventTrieNode {
	Event key;
	Map<Event, EventTrieNode> child = new HashMap<Event, EventTrieNode>();
	int occurances = 0;

	EventTrieNode() {
		key = null;
	}

	EventTrieNode(Event key) {
		this.key = key;
	}

	void addEventToLevel(Event e) {
		EventTrieNode node = new EventTrieNode();
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

	EventTrieNode get(Event e) {
		return child.get(e);
	}

	boolean isEventInLevel(Event e) {
		return child.containsKey(e);
	}

	void setKey(Event key) {
		this.key = key;
	}

	void increment() {
		occurances++;
	}

	int getOccurances() {
		return occurances;
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
