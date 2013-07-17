package com.jgaap.util;

import java.util.Iterator;
import java.util.Set;

import com.google.common.collect.ImmutableSetMultimap;

/**
 * Methods for building and manipulating a Cross Entropy Dictionary. A Cross
 * Entropy Dictionary is a hashtable where the keys are Events and the values
 * are Cross Entropy Dictionary Nodes
 * 
 * @author Michael Ryan
 * 
 **/
public class EventGraph {

	private ImmutableSetMultimap<Event, Event> root ;

	private EventGraph(ImmutableSetMultimap<Event, Event> root) {
		this.root = ImmutableSetMultimap.copyOf(root);
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
	
	static public Builder builder() {
		return new Builder();
	}

	static public class Builder {
		private ImmutableSetMultimap.Builder<Event, Event> builder = ImmutableSetMultimap.builder();
		
		public Builder add(Iterable<Event> events) {
			Iterator<Event> iterator = events.iterator();
			Event previous = iterator.next();
			while(iterator.hasNext()) {
				Event current = iterator.next();
				builder.put(previous, current);
				previous = current;
			}
			return this;
		}
		
		public Builder addAll(Iterable<EventSet> eventSets) {
			for(EventSet eventSet : eventSets) {
				add(eventSet);
			}
			return this;
		}
		
		public EventGraph build() {
			return new EventGraph(builder.build());
		}
		
	}

	@Override
	public String toString() {
		return root.toString();
	}
}
