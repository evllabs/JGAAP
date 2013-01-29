package com.jgaap.classifiers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.Document;
import com.jgaap.generics.EventMap;
import com.jgaap.generics.NeighborAnalysisDriver;
import com.jgaap.generics.Pair;

/**
 * 
 * This is a version of was had been the Frequency Centroid Driver This uses the
 * average relative frequency of events of a single author as a centroid
 * 
 * @author Michael Ryan
 * @since 5.0.2
 */
public class CentroidDriver extends NeighborAnalysisDriver {

	static private Logger logger = Logger.getLogger(CentroidDriver.class);

	private ImmutableMap<String, EventMap> knownCentroids;

	@Override
	public String displayName() {
		return "Centroid Driver" + getDistanceName();
	}

	@Override
	public String tooltipText() {
		return "Computes one centroid per Author.\n"
				+ "Centroids are the average relitive frequency of events over all docuents provided.\n"
				+ "i=1 to n \u03A3frequencyIn_i(event)/n";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public void train(List<Document> knowns) {
		Multimap<String, EventMap> knownHistograms = HashMultimap.create();
		for (Document known : knowns) {
			EventMap eventMap = new EventMap(known);
			knownHistograms.put(known.getAuthor(), eventMap);
		}
		ImmutableMap.Builder<String, EventMap> mapBuilder = ImmutableMap.builder();
		for (Entry<String, Collection<EventMap>> entry : knownHistograms.asMap().entrySet()) {
			mapBuilder.put(entry.getKey(), EventMap.centroid(entry.getValue()));
		}
		knownCentroids = mapBuilder.build();
	}

	@Override
	public List<Pair<String, Double>> analyze(Document unknown) throws AnalyzeException {
		EventMap unknownEventMap = new EventMap(unknown);
		List<Pair<String, Double>> result = new ArrayList<Pair<String, Double>>(knownCentroids.size());
		for (Entry<String, EventMap> knownEntry : knownCentroids.entrySet()) {
			try {
				double current = distance.distance(unknownEventMap, knownEntry.getValue());
				logger.debug(unknown.getTitle()+" ("+unknown.getFilePath()+")"+" -> "+knownEntry.getKey()+":"+current);
				result.add(new Pair<String, Double>(knownEntry.getKey(), current, 2));
			} catch (DistanceCalculationException e) {
				logger.fatal("Distance " + distance.displayName() + " failed", e);
				throw new AnalyzeException("Distance " + distance.displayName() + " failed");
			}
		}
		Collections.sort(result);
		return result;
	}

}
