package com.jgaap.classifiers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.jgaap.backend.Utils;
import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;
import com.jgaap.generics.ValidationDriver;

public class LeaveOneOutCentroidDriver extends ValidationDriver {

	private Map<String, Map<Event, Double>> knownCentroids;
	private Map<String, List<EventSet>> knownEventSets;
	private Set<Event> events;

	@Override
	public String displayName() {
		return "Leave One Out Centroid";
	}

	@Override
	public String tooltipText() {
		return "";
	}

	/**
	 * This is not for the gui because it goes against what that product does
	 */
	@Override
	public boolean showInGUI() {
		return false;
	}

	public void train(List<EventSet> knowns) {
		Map<String, List<EventHistogram>> knownHistograms = new HashMap<String, List<EventHistogram>>();
		events = new HashSet<Event>();
		knownEventSets = new HashMap<String, List<EventSet>>();
		for (EventSet known : knowns) {
			EventHistogram histogram = new EventHistogram();
			for (Event event : known) {
				events.add(event);
				histogram.add(event);
			}
			List<EventHistogram> histograms = knownHistograms.get(known.getAuthor());
			if (histograms != null) {
				histograms.add(histogram);
			} else {
				histograms = new ArrayList<EventHistogram>();
				histograms.add(histogram);
				knownHistograms.put(known.getAuthor(), histograms);
			}
			List<EventSet> eventSets = knownEventSets.get(known.getAuthor());
			if (eventSets != null) {
				eventSets.add(known);
			} else {
				eventSets = new ArrayList<EventSet>();
				eventSets.add(known);
				knownEventSets.put(known.getAuthor(), eventSets);
			}
		}
		Map<String, Map<Event, Double>> knownCentroids = new HashMap<String, Map<Event, Double>>(knownHistograms.size());
		for (Entry<String, List<EventHistogram>> entry : knownHistograms.entrySet()) {
			knownCentroids.put(entry.getKey(),Utils.makeRelativeCentroid(entry.getValue()));
		}
	}

	@Override
	public List<Pair<String, Double>> analyze(EventSet known)
			throws AnalyzeException {
		List<Pair<String, Double>> results = new ArrayList<Pair<String, Double>>();
		String currentAuthor = known.getAuthor();
		List<EventHistogram> currentAuthorHistograms = new ArrayList<EventHistogram>();
		for (EventSet eventSet : knownEventSets.get(currentAuthor)) {
			if (eventSet != known)
				currentAuthorHistograms.add(new EventHistogram(eventSet));
		}
		Map<Event, Double> currentAuthorCentroid = Utils.makeRelativeCentroid(currentAuthorHistograms);
		List<Double> currentAuthorFeatureVector = generateFeatureVector(currentAuthorCentroid, events);
		List<Double> currentFeatureVector = generateFeatureVector(new EventHistogram(known), events);
		try {
			results.add(new Pair<String, Double>(currentAuthor,distance.distance(currentFeatureVector,currentAuthorFeatureVector), 2));
			for (Entry<String, Map<Event, Double>> entry : knownCentroids.entrySet()) {
				if (!entry.getKey().equals(currentAuthor)) {
					results.add(new Pair<String, Double>(entry.getKey(),distance.distance(currentFeatureVector, generateFeatureVector(entry.getValue(), events)), 2));
				}
			}
		} catch (DistanceCalculationException e) {
			throw new AnalyzeException("Distance Method "+distance.displayName()+" has failed");
		}
		Collections.sort(results);
		return results;
	}

	private List<Double> generateFeatureVector(Map<Event, Double> histogram, Set<Event> events) {
		List<Double> featureVector = new ArrayList<Double>(events.size());
		Double zero = 0.0;
		for (Event event : events) {
			Double current = histogram.get(event);
			if (event == null) {
				featureVector.add(zero);
			} else {
				featureVector.add(current);
			}
		}
		return featureVector;
	}

	private List<Double> generateFeatureVector(EventHistogram histogram, Set<Event> events) {
		List<Double> featureVector = new ArrayList<Double>(events.size());
		for (Event event : events) {
			featureVector.add(histogram.getRelativeFrequency(event));
		}
		return featureVector;
	}

}
