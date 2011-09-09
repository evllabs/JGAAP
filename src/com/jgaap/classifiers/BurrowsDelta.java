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

	public BurrowsDelta() {
		addParams("centroid", "Centroid Model", "false", new String[] { "true",
				"false" }, false);
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

	/**
	 * Burrows Delta using Argamon's Formula Note this is sum(|(Xi - Yi) /
	 * sigma|) (Basically, a Manhattan Distance normalized by the standard
	 * deviation of a word across the known author list)
	 */
	public List<Pair<String, Double>> analyze(EventSet unknown,
			List<EventSet> known) {

		boolean useCentroid = "true".equalsIgnoreCase(getParameter("centroid"));

		List<Pair<String, Double>> results = new ArrayList<Pair<String, Double>>();
		Set<Event> allEvents = new HashSet<Event>();

		EventHistogram unknownHistogram = new EventHistogram();
		List<EventHistogram> knownHistograms = new ArrayList<EventHistogram>();

		Map<String, List<EventHistogram>> authorHistogramMap = new HashMap<String, List<EventHistogram>>();

		for (Event e : unknown) {
			unknownHistogram.add(e);
			allEvents.add(e);
		}
		for (EventSet es : known) {
			EventHistogram currentKnownHistogram = new EventHistogram();
			for (Event e : es) {
				currentKnownHistogram.add(e);
				allEvents.add(e);
			}
			if (useCentroid) {
				String author = es.getAuthor();
				List<EventHistogram> value = authorHistogramMap.get(author);
				if (value != null) {
					value.add(currentKnownHistogram);
				} else {
					List<EventHistogram> tmp = new ArrayList<EventHistogram>();
					tmp.add(currentKnownHistogram);
					authorHistogramMap.put(author, tmp);
				}
			} else {
				knownHistograms.add(currentKnownHistogram);
			}
		}

		List<Map<Event, Double>> centroids = new ArrayList<Map<Event, Double>>();

		if (useCentroid) {
			for (Entry<String, List<EventHistogram>> entry : authorHistogramMap
					.entrySet()) {
				centroids.add(Utils.makeNormalizedCentroid(entry.getValue()));
			}
		}

		double[] delta;
		if (useCentroid) {
			delta = new double[centroids.size()];
			for (int i = 0; i < centroids.size(); i++) {
				delta[i] = 0.0;
			}
		} else {
			delta = new double[known.size()];
			for (int i = 0; i < known.size(); i++) {
				delta[i] = 0.0;
			}
		}

		for (Event event : allEvents) {
			List<Double> knownValues = new ArrayList<Double>();
			if (useCentroid) {
				for (Map<Event, Double> centroid : centroids) {
					knownValues.add(centroid.get(event));
				}
			} else {
				for (EventHistogram hist : knownHistograms) {
					knownValues.add(hist.getNormalizedFrequency(event));
				}
			}
			double stddev = Utils.stddev(knownValues);
			for (int i = 0; i < delta.length; i++) {
				delta[i] = delta[i]
						+ Math.abs((unknownHistogram
								.getNormalizedFrequency(event) - knownValues
								.get(i))
								/ stddev);
			}
		}

		if (useCentroid) {
			int i = 0;
			for(Entry<String, List<EventHistogram>> entry : authorHistogramMap.entrySet()){
				results.add(new Pair<String, Double>(entry.getKey(), delta[i], 2));
				i++;
			}
		} else {
			for (int i = 0; i < known.size(); i++) {
				results.add(new Pair<String, Double>(known.get(i).getAuthor(),
						delta[i], 2));
			}
		}

		Collections.sort(results);
		return results;

	}

}
