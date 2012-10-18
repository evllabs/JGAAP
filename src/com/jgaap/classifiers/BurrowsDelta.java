package com.jgaap.classifiers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.jgaap.backend.Utils;
import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventMap;
import com.jgaap.generics.Pair;

public class BurrowsDelta extends AnalysisDriver {

	private Set<Event> events;
	private Map<String,List<EventMap>> knownHistograms;
	private Map<String,EventMap> knownCentroids;
	private boolean useCentroid;
	private Map<Event, Double> eventStddev;

	public BurrowsDelta() {
		addParams("centroid", "Centroid Model", "false", new String[] { "true", "false" }, false);
	}

	@Override
	public String displayName() {
		return "Burrows Delta";
	}

	@Override
	public String tooltipText() {
		return "Burrow's Delta with Argamon's Formula";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	public void train(List<Document> knowns) {
		useCentroid = "true".equalsIgnoreCase(getParameter("centroid"));
		events = new HashSet<Event>();
		knownHistograms = new HashMap<String, List<EventMap>>();
		for (Document known : knowns) {
			EventMap eventMap = new EventMap(known);
			events.addAll(eventMap.uniqueEvents());
			List<EventMap> eventMaps = knownHistograms.get(known.getAuthor());
			if (eventMaps == null) {
				eventMaps = new ArrayList<EventMap>();
				knownHistograms.put(known.getAuthor(), eventMaps);
			}
			eventMaps.add(eventMap);
		}

		eventStddev = new HashMap<Event, Double>();

		if (useCentroid) {
			knownCentroids = new HashMap<String, EventMap>();
			for (Entry<String, List<EventMap>> entry : knownHistograms.entrySet()) {
				knownCentroids.put(entry.getKey(), EventMap.centroid(entry.getValue()));
			}
			for (Event event : events) {
				List<Double> sample = new ArrayList<Double>();
				for (EventMap histogram : knownCentroids.values()) {
					Double value = histogram.relativeFrequency(event);
					if (value == null) {
						sample.add(0.0);
					} else {
						sample.add(value);
					}
				}
				eventStddev.put(event, Utils.stddev(sample));
			}
		} else {
			for (Event event : events) {
				List<Double> sample = new ArrayList<Double>();
				for (List<EventMap> histograms : knownHistograms.values()) {
					for (EventMap histogram : histograms) {
						sample.add(histogram.relativeFrequency(event));
					}
				}
				eventStddev.put(event, Utils.stddev(sample));
			}
		}
	}

	/**
	 * Burrows Delta using Argamon's Formula Note this is sum(|(Xi - Yi) /
	 * sigma|) (Basically, a Manhattan Distance normalized by the standard
	 * deviation of a word across the known author list)
	 */
	public List<Pair<String, Double>> analyze(Document unknown) {
		List<Pair<String, Double>> results = new ArrayList<Pair<String, Double>>();
		EventMap unknownEventMap = new EventMap(unknown);
		if (useCentroid) {
			for (Entry<String, EventMap> entry : knownCentroids.entrySet()) {
				double delta = 0.0;
				for (Event event : events) {
					Double knownFrequency = entry.getValue().relativeFrequency(event);
					if (knownFrequency == null) {
						knownFrequency = 0.0;
					}
					delta += Math.abs((unknownEventMap.relativeFrequency(event) - knownFrequency) / eventStddev.get(event));
				}
				results.add(new Pair<String, Double>(entry.getKey(), delta,2));
			}
		} else {
			for (Entry<String, List<EventMap>> entry : knownHistograms.entrySet()) {
				for (EventMap histogram : entry.getValue()) {
					double delta = 0.0;
					for (Event event : events) {
						delta += Math.abs((unknownEventMap.relativeFrequency(event) - histogram.relativeFrequency(event)) / eventStddev.get(event));
					}
					results.add(new Pair<String, Double>(entry.getKey(), delta,2));
				}
			}
		}
		Collections.sort(results);
		return results;
	}
}
