package com.jgaap.classifiers;

import java.util.ArrayList;
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
import com.jgaap.generics.NeighborAnalysisDriver;
import com.jgaap.generics.Pair;

public class LeaveOneOutCentroidDriver extends NeighborAnalysisDriver {

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

	@Override
	public List<Pair<String, Double>> analyze(EventSet unknown, List<EventSet> knowns) throws AnalyzeException {
		Map<String, List<EventHistogram>> knownHistograms = new HashMap<String, List<EventHistogram>>();
		Set<Event> events = new HashSet<Event>();
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
		}
		Map<String, Map<Event, Double>> knownCentroids = new HashMap<String, Map<Event,Double>>(knownHistograms.size());
		for(Entry<String, List<EventHistogram>> entry : knownHistograms.entrySet()){
			knownCentroids.put(entry.getKey(), Utils.makeRelativeCentroid(entry.getValue()));
		}
		for(Entry<String, List<EventHistogram>> entry : knownHistograms.entrySet()){
			for(EventHistogram currentHistogram : entry.getValue()){
				List<EventHistogram> currentHistograms = new ArrayList<EventHistogram>(entry.getValue().size());
				for(EventHistogram histogram : entry.getValue()){
					if(histogram != currentHistogram)
						currentHistograms.add(histogram);
				}
				Map<Event, Double> modelLessCurrent = Utils.makeRelativeCentroid(currentHistograms);
				List<Double> currentFeatureVector = generateFeatureVector(currentHistogram, events);
				for(Entry<String, Map<Event, Double>> centroidEntry : knownCentroids.entrySet()){
					List<Double> knownFeatureVector;
					if(centroidEntry.getKey().equalsIgnoreCase(entry.getKey())){
						knownFeatureVector = generateFeatureVector(modelLessCurrent, events);
					} else {
						knownFeatureVector = generateFeatureVector(centroidEntry.getValue(), events);
					}
					try {
						distance.distance(currentFeatureVector, knownFeatureVector);
					} catch (DistanceCalculationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		return null;
	}

	private List<Double> generateFeatureVector(Map<Event, Double> histogram, Set<Event> events){
		List<Double> featureVector = new ArrayList<Double>(events.size());
		Double zero = 0.0;
		for(Event event : events){
			Double current = histogram.get(event);
			if(event == null){
				featureVector.add(zero);
			} else {
				featureVector.add(current);
			}
		}
		return featureVector;
	}
	
	private List<Double> generateFeatureVector(EventHistogram histogram, Set<Event> events){
		List<Double> featureVector = new ArrayList<Double>(events.size());
		for(Event event : events){
			featureVector.add(histogram.getRelativeFrequency(event));
		}
		return featureVector;
	}
	
}
