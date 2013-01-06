package com.jgaap.generics;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;

/**
 * An Immutable mapping of Events to their relative frequencies
 * 
 * @author Michael Ryan
 *
 */
public class EventMap {

	private final ImmutableMap<Event, Double> histogram;
	
	public EventMap(Document document) {
		this(document.getEventSets().values());
	}
	
	public EventMap(Iterable<EventSet> eventSets) {
		Builder<Event, Double> histogramBuilder = ImmutableMap.builder();
		for(EventSet eventSet : eventSets){
			double numEvents = eventSet.size();
			Multiset<Event> multiset = ImmutableMultiset.copyOf(eventSet); 
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
	
	public static EventMap centroid(List<EventMap> eventMaps) {
		ImmutableMultimap.Builder<Event, Double> multiMapBuilder = ImmutableMultimap.builder();
		for (EventMap eventMap : eventMaps) {
			for(Map.Entry<Event, Double> entry : eventMap.histogram.entrySet()){
				multiMapBuilder.put(entry.getKey(), entry.getValue());
			}
		}
		ImmutableMultimap<Event, Double> multimap = multiMapBuilder.build();
		
		double count = eventMaps.size();
		Builder<Event, Double> builder = ImmutableMap.builder();
		for (Map.Entry<Event, Collection<Double>> entry : multimap.asMap().entrySet()) {
			double value = 0.0;
			for(double current : entry.getValue()){
				value += current;
			}
			builder.put(entry.getKey(), value/count);
		}
		return new EventMap(builder.build());
	}
}
