package com.jgaap.util;

import java.util.*;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multiset;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Multiset.Entry;
import com.jgaap.generics.EventDriver;
import org.jetbrains.annotations.NotNull;

/**
 * 
 * @author Michael Ryan
 * @since 7.0
 */
public class AbsoluteHistogram implements Histogram {

	private final ImmutableMap<Event, Integer> histogram;
	private final ImmutableMap<EventDriver, Integer> totals;
	
	public AbsoluteHistogram(Document document) {
		Builder<Event, Integer> histogramBuilder = ImmutableMap.builder();
		Builder<EventDriver, Integer> totalsBuilder = ImmutableMap.builder();
		for(Map.Entry<EventDriver, EventSet> eventSetEntry : document.getEventSets().entrySet()){
			EventDriver eventDriver = eventSetEntry.getKey();
			EventSet eventSet = eventSetEntry.getValue();
			totalsBuilder.put(eventDriver, eventSet.size());
			Multiset<Event> multiset = HashMultiset.create(eventSet);
			for(Entry<Event> entry : multiset.entrySet()){
				histogramBuilder.put(entry.getElement(),entry.getCount());
			}
		}
		histogram = histogramBuilder.build();
		totals = totalsBuilder.build();
	}
	
	private AbsoluteHistogram(Map<Event,Integer> histogram, Map<EventDriver,Integer> totals) {
		this.histogram = ImmutableMap.copyOf(histogram);
		this.totals = ImmutableMap.copyOf(totals);
	}
	
	@Override
	public double getRelativeFrequency(Event event) {
		return getAbsoluteFrequency(event)/(double)totals.get(event.getEventDriver());
	}

	@Override
	public double getNormalizedFrequency(Event event) {
		return getRelativeFrequency(event)*100000;
	}

	@Override
	public int getAbsoluteFrequency(Event event) {
		Integer current = histogram.get(event);
		if(current == null){
			return 0;
		} 
		return current;
	}

	@Override
	public boolean contains(Event event) {
		return histogram.containsKey(event);
	}

	@Override
	public Set<Event> uniqueEvents() {
		return histogram.keySet();
	}
	
	public static AbsoluteHistogram centroid(Collection<AbsoluteHistogram> histograms) {
		Map<Event,Integer> tmpHistogram = new HashMap<Event, Integer>();
		Map<EventDriver, Integer> tmpTotals = new HashMap<EventDriver, Integer>();
		for(AbsoluteHistogram histogram : histograms) {
			for(Map.Entry<Event, Integer> entry : histogram.histogram.entrySet()) { 
				Integer current = tmpHistogram.get(entry.getKey());
				if(current == null){
					tmpHistogram.put(entry.getKey(), entry.getValue());
				} else {
					tmpHistogram.put(entry.getKey(), current + entry.getValue());
				}
			}
			for(Map.Entry<EventDriver, Integer> entry : histogram.totals.entrySet()) { 
				Integer current = tmpTotals.get(entry.getKey());
				if(current == null) {
					tmpTotals.put(entry.getKey(), entry.getValue());
				} else {
					tmpTotals.put(entry.getKey(), current + entry.getValue());
				}
			}
		}
		return new AbsoluteHistogram(tmpHistogram, tmpTotals);
	}

	@NotNull
	@Override
	public Iterator<Event> iterator() {
		return this.uniqueEvents().iterator();
	}
}
