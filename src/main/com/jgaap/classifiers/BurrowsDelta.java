package com.jgaap.classifiers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.jgaap.backend.Utils;
import com.jgaap.generics.AnalysisDriver;
import com.jgaap.util.Document;
import com.jgaap.util.Event;
import com.jgaap.util.EventMap;
import com.jgaap.util.Pair;

public class BurrowsDelta extends AnalysisDriver {

	private ImmutableSet<Event> events;
	private ImmutableMultimap<String, EventMap> knownHistograms;
	private ImmutableMap<String,EventMap> knownCentroids;
	private boolean useCentroid;
	private ImmutableMap<Event, Double> eventStddev;

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
		ImmutableSet.Builder<Event> eventsBuilder = ImmutableSet.builder();
		ImmutableMultimap.Builder<String, EventMap> knownHistogramsBuilder = ImmutableMultimap.builder();
		for (Document known : knowns) {
			EventMap eventMap = new EventMap(known);
			eventsBuilder.addAll(eventMap.uniqueEvents());
			knownHistogramsBuilder.put(known.getAuthor(), eventMap);
		}
		events = eventsBuilder.build();
		knownHistograms = knownHistogramsBuilder.build();
		
		ImmutableMap.Builder<Event, Double> eventStddevBuilder = ImmutableMap.builder();

		if (useCentroid) {
			ImmutableMap.Builder<String, EventMap> knownCentroidsBuilder = ImmutableMap.builder();
			for (Entry<String, Collection<EventMap>> entry : knownHistograms.asMap().entrySet()) {
				knownCentroidsBuilder.put(entry.getKey(), EventMap.centroid(entry.getValue()));
			}
			knownCentroids = knownCentroidsBuilder.build();
			for (Event event : events) {
				List<Double> sample = new ArrayList<Double>();
				for (EventMap histogram : knownCentroids.values()) {
					sample.add(histogram.relativeFrequency(event));
				}
				eventStddevBuilder.put(event, Utils.stddev(sample));
			}
		} else {
			for (Event event : events) {
				List<Double> sample = new ArrayList<Double>();
				for (Collection<EventMap> histograms : knownHistograms.asMap().values()) {
					for (EventMap histogram : histograms) {
						sample.add(histogram.relativeFrequency(event));
					}
				}
				eventStddevBuilder.put(event, Utils.stddev(sample));
			}
		}
		eventStddev = eventStddevBuilder.build();
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
					double knownFrequency = entry.getValue().relativeFrequency(event);
					delta += Math.abs((unknownEventMap.relativeFrequency(event) - knownFrequency) / eventStddev.get(event));
				}
				results.add(new Pair<String, Double>(entry.getKey(), delta,2));
			}
		} else {
			for (Entry<String, Collection<EventMap>> entry : knownHistograms.asMap().entrySet()) {
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
