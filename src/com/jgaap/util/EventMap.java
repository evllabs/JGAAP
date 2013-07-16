package com.jgaap.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;

/**
 * An Immutable mapping of Events to their relative frequencies
 * 
 * @author Michael Ryan
 *
 */
public class EventMap implements Histogram {

	private final ImmutableMap<Event, Double> histogram;
	
	public EventMap(Document document) {
		this(document.getEventSets().values());
	}
	
	public EventMap(Iterable<EventSet> eventSets) {
		Builder<Event, Double> histogramBuilder = ImmutableMap.builder();
		for(EventSet eventSet : eventSets){
			double numEvents = eventSet.size();
			Multiset<Event> multiset = HashMultiset.create(eventSet); 
			for (Entry<Event> eventEntry : multiset.entrySet()) {
				histogramBuilder.put(eventEntry.getElement(), eventEntry.getCount()/numEvents);
			}
		}
		histogram = histogramBuilder.build();
	}
	
	public EventMap(EventSet eventSet) {
		this(Collections.singleton(eventSet));
	}
	
	public EventMap(Map<Event, Double> histogram){
		this.histogram = ImmutableMap.copyOf(histogram);
	}

	public double relativeFrequency(Event event) {
		Double frequency = histogram.get(event);
		if (frequency == null) {
			return 0.0;
		} else {
			return frequency.doubleValue();
		}
	}
		
	public double normalizedFrequency(Event event) {
		Double frequency = histogram.get(event);
		if (frequency == null) {
			return 0.0;
		} else {
			return frequency * 100000;
		}
	}
	
	public boolean contains(Event event) {
		return histogram.containsKey(event);
	}

	public Set<Event> uniqueEvents() {
		return histogram.keySet();
	}
	
	public static EventMap centroid(Collection<EventMap> eventMaps) {
		double count = eventMaps.size();
		Map<Event, Double> map = new HashMap<Event, Double>(10000);
		for (EventMap eventMap : eventMaps) {
			for(Map.Entry<Event, Double> entry : eventMap.histogram.entrySet()){
				Double current = map.get(entry.getKey());
				if(current == null){
					map.put(entry.getKey(), entry.getValue()/count);
				} else {
					map.put(entry.getKey(), current+entry.getValue()/count);
				}
			}
		}

		return new EventMap(map);
	}

	@Override
	public int absoluteFrequency(Event event) {
		throw new UnsupportedOperationException();
	}
}
