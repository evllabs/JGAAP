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
import com.jgaap.generics.NeighborAnalysisDriver;
import com.jgaap.util.AbsoluteHistogram;
import com.jgaap.util.Document;
import com.jgaap.util.Histogram;
import com.jgaap.util.Pair;

/**
 * 
 * This Centroid Driver classifier uses Absolute values of event occurrences to build the centroids.
 * 
 * 
 * @author Michael Ryan
 * @since 7.0
 */
public class AbsoluteCentroidDriver extends NeighborAnalysisDriver {

	static private Logger logger = Logger.getLogger(AbsoluteCentroidDriver.class);

	private ImmutableMap<String, Histogram> knownCentroids;

	@Override
	public String displayName() {
		return "Absolute Centroid Driver" + getDistanceName();
	}

	@Override
	public String tooltipText() {
		return "Computes one centroid per author.\n"
				+ "Centroids are the frequency of events over all documents provided.\n";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public void train(List<Document> knowns) {
		Multimap<String, AbsoluteHistogram> knownHistograms = HashMultimap.create();
		for (Document known : knowns) {
			AbsoluteHistogram AbsoluteHistogram = new AbsoluteHistogram(known);
			knownHistograms.put(known.getAuthor(), AbsoluteHistogram);
		}
		ImmutableMap.Builder<String, Histogram> mapBuilder = ImmutableMap.builder();
		for (Entry<String, Collection<AbsoluteHistogram>> entry : knownHistograms.asMap().entrySet()) {
			mapBuilder.put(entry.getKey(), AbsoluteHistogram.centroid(entry.getValue()));
		}
		knownCentroids = mapBuilder.build();
	}

	@Override
	public List<Pair<String, Double>> analyze(Document unknown) throws AnalyzeException {
		Histogram unknownHistogram = new AbsoluteHistogram(unknown);
		List<Pair<String, Double>> result = new ArrayList<Pair<String, Double>>(knownCentroids.size());
		for (Entry<String, Histogram> knownEntry : knownCentroids.entrySet()) {
			try {
				double current = distance.distance(unknownHistogram, knownEntry.getValue());
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
