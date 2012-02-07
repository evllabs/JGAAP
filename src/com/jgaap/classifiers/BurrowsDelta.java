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
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

public class BurrowsDelta extends AnalysisDriver {

	private Set<Event> events;
	private Map<String, List<EventHistogram>> knownHistograms;
	private Map<String, Map<Event, Double>> knownCentroids;
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

	public void train(List<EventSet> knowns) {
		useCentroid = "true".equalsIgnoreCase(getParameter("centroid"));
		events = new HashSet<Event>();
		knownHistograms = new HashMap<String, List<EventHistogram>>();
		for (EventSet known : knowns) {
			EventHistogram histogram = new EventHistogram(known);
			events.addAll(histogram.events());
			List<EventHistogram> histograms = knownHistograms.get(known.getAuthor());
			if (histograms == null) {
				histograms = new ArrayList<EventHistogram>();
				knownHistograms.put(known.getAuthor(), histograms);
			}
			histograms.add(histogram);
		}

		eventStddev = new HashMap<Event, Double>();

		if (useCentroid) {
			knownCentroids = new HashMap<String, Map<Event, Double>>();
			for (Entry<String, List<EventHistogram>> entry : knownHistograms.entrySet()) {
				knownCentroids.put(entry.getKey(), Utils.makeRelativeCentroid(entry.getValue()));
			}
			for (Event event : events) {
				List<Double> sample = new ArrayList<Double>();
				for (Map<Event, Double> histogram : knownCentroids.values()) {
					Double value = histogram.get(event);
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
				for (List<EventHistogram> histograms : knownHistograms.values()) {
					for (EventHistogram histogram : histograms) {
						sample.add(histogram.getRelativeFrequency(event));
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
	public List<Pair<String, Double>> analyze(EventSet unknown) {
		List<Pair<String, Double>> results = new ArrayList<Pair<String, Double>>();
		EventHistogram unknownHistogram = new EventHistogram(unknown);
		if (useCentroid) {
			for (Entry<String, Map<Event, Double>> entry : knownCentroids.entrySet()) {
				double delta = 0.0;
				for (Event event : events) {
					Double knownFrequency = entry.getValue().get(event);
					if (knownFrequency == null) {
						knownFrequency = 0.0;
					}
					delta += Math.abs((unknownHistogram.getRelativeFrequency(event) - knownFrequency) / eventStddev.get(event));
				}
				results.add(new Pair<String, Double>(entry.getKey(), delta));
			}
		} else {
			for (Entry<String, List<EventHistogram>> entry : knownHistograms.entrySet()) {
				for (EventHistogram histogram : entry.getValue()) {
					double delta = 0.0;
					for (Event event : events) {
						delta += Math.abs((unknownHistogram.getRelativeFrequency(event) - histogram.getRelativeFrequency(event)) / eventStddev.get(event));
					}
					results.add(new Pair<String, Double>(entry.getKey(), delta));
				}
			}
		}
		Collections.sort(results);
		return results;
	}
}
