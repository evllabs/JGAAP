package com.jgaap.generics;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Methods for building and manipulating a Cross Entropy Dictionary. A Cross
 * Entropy Dictionary is a hashtable where the keys are Events and the values
 * are Cross Entropy Dictionary Nodes
 **/
public class EventGraph {

	Map<Event,Set<Event>> root ;

	public EventGraph() {
		root = new HashMap<Event, Set<Event>>();
	}

	public int find(EventSet eventSet) {
		int matchlength = 0;
		Set<Event> children = root.get(eventSet.eventAt(0));
		if(children!=null){
			for(int i = 1; i < eventSet.size(); i++){
				matchlength++;
				if(children.contains(eventSet.eventAt(i))){
					children = root.get(eventSet.eventAt(i));
				} else {
					break;
				}
			}
		}
		return matchlength;
	}

	public void add(EventSet eventSet) {
		for(int i = 0; i < eventSet.size(); i++){
			Set<Event> children = root.get(eventSet.eventAt(i));
			if(children == null){
				children = new HashSet<Event>();
			}
			if(i+1<eventSet.size())
				children.add(eventSet.eventAt(i+1));
			root.put(eventSet.eventAt(i), children);
		}
	}

	@Override
	public String toString() {
		return root.toString();
	}
}
