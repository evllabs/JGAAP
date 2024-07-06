package com.jgaap.classifiers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.ValidationDriver;
import com.jgaap.util.AbsoluteHistogram;
import com.jgaap.util.Document;
import com.jgaap.util.Pair;

public class LeaveOneOutAbsoluteCentroidDriver extends ValidationDriver {

	private ImmutableMap<String, AbsoluteHistogram> knownCentroids;
	private ImmutableMultimap<String, Document> knownDocuments;
	private ImmutableMap<Document, AbsoluteHistogram> knownAbsoluteHistograms;
	
	@Override
	public String displayName() {
		return "Leave One Out Absolute Centroid"+this.getDistanceName();
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
		return true;
	}

	public void train(List<Document> knowns) {
		ImmutableMap.Builder<Document, AbsoluteHistogram> knownAbsoluteHistogramsBuilder = ImmutableMap.builder();
		ImmutableMultimap.Builder<String, Document> knownDocumentsBuilder = ImmutableMultimap.builder();
		Multimap<String, AbsoluteHistogram> knownHistograms = HashMultimap.create();
		for(Document known : knowns){
			AbsoluteHistogram AbsoluteHistogram = new AbsoluteHistogram(known);
			knownAbsoluteHistogramsBuilder.put(known, AbsoluteHistogram);
			knownHistograms.put(known.getAuthor(), AbsoluteHistogram);
			knownDocumentsBuilder.put(known.getAuthor(), known);
		}
		knownAbsoluteHistograms = knownAbsoluteHistogramsBuilder.build();
		knownDocuments = knownDocumentsBuilder.build();
		ImmutableMap.Builder<String, AbsoluteHistogram> knownCentoidsBuilder = ImmutableMap.builder();
		for(Map.Entry<String, Collection<AbsoluteHistogram>> entry : knownHistograms.asMap().entrySet()){
			knownCentoidsBuilder.put(entry.getKey(), AbsoluteHistogram.centroid(entry.getValue()));
		}
		knownCentroids = knownCentoidsBuilder.build();
	}

	@Override
	public List<Pair<String, Double>> analyze(Document known) throws AnalyzeException {
		List<Pair<String, Double>> results = new ArrayList<Pair<String, Double>>(knownCentroids.size());
		String currentAuthor = known.getAuthor();
		Collection<Document> authorDocuments = knownDocuments.get(currentAuthor);
		List<AbsoluteHistogram> currentAuthorHistograms = new ArrayList<AbsoluteHistogram>(authorDocuments.size());
		for (Document document : authorDocuments) {
			if (!document.equals(known))
				currentAuthorHistograms.add(knownAbsoluteHistograms.get(document));
		}
		AbsoluteHistogram currentAuthorCentroid = AbsoluteHistogram.centroid(currentAuthorHistograms);
		AbsoluteHistogram currentAbsoluteHistogram = knownAbsoluteHistograms.get(known);
		try {
			results.add(new Pair<String, Double>(currentAuthor,distance.distance(currentAbsoluteHistogram, currentAuthorCentroid), 2));
			for (Map.Entry<String, AbsoluteHistogram> entry : knownCentroids.entrySet()) {
				if (!entry.getKey().equals(currentAuthor)) {
					results.add(new Pair<String, Double>(entry.getKey(),distance.distance(currentAbsoluteHistogram, entry.getValue()), 2));
				}
			}
		} catch (DistanceCalculationException e) {
			throw new AnalyzeException("Distance Method "+distance.displayName()+" has failed");
		}
		Collections.sort(results);
		return results;
	}

}
