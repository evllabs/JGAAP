package com.jgaap.generics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EventMap {

	private Map<Event, Node> histogram;

	public EventMap() {
		this(1001);
	}
	
	public EventMap(Document document) {
		this();
		for(EventSet eventSet : document.getEventSets().values()){
			add(eventSet);
		}
	}

	public EventMap(int size) {
		histogram = new HashMap<Event, EventMap.Node>(size);
	}

	public void add(EventSet eventSet) {
		int numEvents = eventSet.size();
		for (Event event : eventSet) {
			Node node = histogram.get(event);
			if (node == null) {
				node = new Node(1, numEvents);
				histogram.put(event, node);
			} else {
				node.increment();
			}
		}
	}

	public double relativeFrequency(Event event) {
		Node node = histogram.get(event);
		if (node == null) {
			return 0.0;
		} else {
			return node.relativeFrequency();
		}
	}
	
	public double normalizedFrequency(Event event) {
		return 100000 * relativeFrequency(event);
	}

	public long absoluteFrequency(Event event) {
		Node node = histogram.get(event);
		if (node == null) {
			return 0;
		} else {
			return node.occurrences;
		}
	}

	public Set<Event> uniqueEvents() {
		return histogram.keySet();
	}
	
	public static EventMap centroid(List<EventMap> eventMaps) {
		EventMap centroidEventMap = new EventMap();
		Set<Event> events = new HashSet<Event>();
		for (EventMap eventMap : eventMaps) {
			events.addAll(eventMap.histogram.keySet());
		}
		for (Event event : events) {
			List<Node> nodes = new ArrayList<Node>(eventMaps.size());
			for(EventMap eventMap : eventMaps) {
				Node node = eventMap.histogram.get(event);
				if(node != null){
					nodes.add(node);
				}
			}
			long occurrences = 0;
			long numEvents = 1;
			for (int i = 0; i < nodes.size(); i++) {
				long tmp = nodes.get(i).occurrences;
				for (int j = 0; j < nodes.size(); j++) {
					if (j != i) {
						tmp *= nodes.get(j).numEvents;
					}
				}
				occurrences += tmp;
				numEvents *= nodes.get(i).numEvents;
				
			}
			numEvents *= eventMaps.size();
			centroidEventMap.histogram.put(event, new Node(occurrences, numEvents));
		}
		return centroidEventMap;
	}

	private static class Node {
		long occurrences;
		long numEvents;

		Node(long occurrences, long numEvents) {
			this.occurrences = occurrences;
			this.numEvents = numEvents;
		}

		void increment() {
			this.occurrences++;
		}

		double relativeFrequency() {
			return occurrences / (double)numEvents;
		}
	}
}
