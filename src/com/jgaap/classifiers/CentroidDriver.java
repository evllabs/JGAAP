package com.jgaap.classifiers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.NeighborAnalysisDriver;
import com.jgaap.generics.Pair;

/**
 * 
 * This is a version of was had been the Frequency Centroid Driver
 * This uses the average relative frequency of events of a single author as a centroid
 * 
 * @author Michael Ryan
 * @since 5.0.2
 */
public class CentroidDriver extends NeighborAnalysisDriver {

	@Override
	public String displayName() {
		return "Centroid Driver"+getDistanceName();
	}

	@Override
	public String tooltipText() {
		return "Computes one centroid per Author.\n" +
				"Centroids are the average relitive frequency of events over all docuents provided.\n" +
				"i=1 to n \u03A3frequencyIn_i(event)/n";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	
	@Override
	public List<Pair<String, Double>> analyze(EventSet unknown, List<EventSet> knowns) {
		Map<String, List<EventHistogram>>knownHistograms=new HashMap<String, List<EventHistogram>>();
		Set<Event> events = new HashSet<Event>();
		for(EventSet known : knowns){
			EventHistogram histogram = new EventHistogram();
			for(Event event : known){
				events.add(event);
				histogram.add(event);
			}
			List<EventHistogram> histograms = knownHistograms.get(known.getAuthor());
			if(histograms != null){
				histograms.add(histogram);
			} else {
				histograms = new ArrayList<EventHistogram>();
				histograms.add(histogram);
				knownHistograms.put(known.getAuthor(), histograms);
			}
		}
		EventHistogram unknownHistogram = new EventHistogram();
		for(Event event : unknown){
			events.add(event);
			unknownHistogram.add(event);
		}
		Vector<Double> unknownVector = new Vector<Double>(events.size());
		for(Event event : events){
			unknownVector.add(unknownHistogram.getRelativeFrequency(event));
		}
		List<Pair<String, Double>> result = new ArrayList<Pair<String,Double>>(knownHistograms.size());
		for(Entry<String, List<EventHistogram>> knownEntry : knownHistograms.entrySet()){
			Vector<Double> knownVector = new Vector<Double>(events.size());
			List<EventHistogram> currentKnownHistogram = knownEntry.getValue();
			for(Event event : events){
				double frequency = 0.0;
				double size = currentKnownHistogram.size();
				for(EventHistogram known : currentKnownHistogram){
					frequency += known.getRelativeFrequency(event)/size;
				}
				knownVector.add(frequency);
			}
			result.add(new Pair<String, Double>(knownEntry.getKey(), distance.distance(unknownVector, knownVector), 2));			
		}
		Collections.sort(result);
		return result;
	}


}
