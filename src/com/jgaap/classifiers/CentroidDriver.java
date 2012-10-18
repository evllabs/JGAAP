package com.jgaap.classifiers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.Document;
import com.jgaap.generics.EventMap;
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

	static private Logger logger = Logger.getLogger(CentroidDriver.class);
		
	private Map<String, EventMap> knownCentroids;
	
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
	public void train(List<Document> knowns){
		 Map<String, List<EventMap>> knownHistograms=new HashMap<String, List<EventMap>>();
		for(Document known : knowns){
			EventMap eventMap = new EventMap(known);
			List<EventMap> histograms = knownHistograms.get(known.getAuthor());
			if(histograms != null){
				histograms.add(eventMap);
			} else {
				histograms = new ArrayList<EventMap>();
				histograms.add(eventMap);
				knownHistograms.put(known.getAuthor(), histograms);
			}
		}
		knownCentroids = new HashMap<String, EventMap>(knownHistograms.size());
		for(Entry<String, List<EventMap>> entry : knownHistograms.entrySet()){
			knownCentroids.put(entry.getKey(), EventMap.centroid(entry.getValue()));
		}
		
	}
	
	@Override
	public List<Pair<String, Double>> analyze(Document unknown) throws AnalyzeException {
		EventMap unknownEventMap = new EventMap(unknown);
		List<Pair<String, Double>> result = new ArrayList<Pair<String,Double>>(knownCentroids.size());
		for(Entry<String, EventMap> knownEntry : knownCentroids.entrySet()){
			try {
				result.add(new Pair<String, Double>(knownEntry.getKey(), distance.distance(unknownEventMap, knownEntry.getValue()), 2));
			} catch (DistanceCalculationException e) {
				logger.fatal("Distance "+distance.displayName()+" failed", e);
				throw new AnalyzeException("Distance "+distance.displayName()+" failed");
			}			
		}
		Collections.sort(result);
		return result;
	}


}
