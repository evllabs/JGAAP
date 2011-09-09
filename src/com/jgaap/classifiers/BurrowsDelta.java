package com.jgaap.classifiers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.jgaap.backend.Utils;
import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

public class BurrowsDelta extends AnalysisDriver {

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
	 * Burrows Delta using Argamon's Formula
	 * Note this is sum(|(Xi - Yi) / sigma|)
	 * (Basically, a Manhattan Distance normalized by the standard deviation
	 * of a word across the known author list)
	 */
    public List<Pair<String, Double>> analyze(EventSet unknown, List<EventSet> known) {
    	List<Pair<String, Double>> results = new ArrayList<Pair<String, Double>>();
    	Set<Event> allEvents = new HashSet<Event>();
    	
    	EventHistogram unknownHistogram = new EventHistogram();
    	List<EventHistogram> knownHistograms = new ArrayList<EventHistogram>();
    	
    	for(Event e : unknown) {
    		unknownHistogram.add(e);
    		allEvents.add(e);
    	}
    	for(EventSet es : known) {
    		EventHistogram currentKnownHistogram = new EventHistogram();
    		knownHistograms.add(currentKnownHistogram);
    		for(Event e : es) {
    			currentKnownHistogram.add(e);
    			allEvents.add(e);
    		}
    	}
    	
    	double[] delta = new double[known.size()];
    	for(int i = 0; i < known.size(); i++) {
    		delta[i] = 0.0;
    	}
    	for(Event event : allEvents) {
    		List<Double> knownValues = new ArrayList<Double>();
    		for(EventHistogram hist : knownHistograms) {
    			knownValues.add(hist.getNormalizedFrequency(event));
    		}
    		double stddev = Utils.stddev(knownValues);
    		for(int i = 0; i < delta.length; i++) {
    			delta[i] = delta[i] + Math.abs((unknownHistogram.getNormalizedFrequency(event) - knownValues.get(i))/ stddev);
    		}
    	}
    	
    	for(int i = 0; i < known.size(); i++) {
    		results.add(new Pair<String, Double>(known.get(i).getAuthor(), delta[i], 2));
    	}
    	
    	Collections.sort(results);
    	return results;
    	
    }

}
