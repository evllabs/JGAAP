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
import com.jgaap.util.Document;
import com.jgaap.util.EventMap;
import com.jgaap.util.Pair;

public class LeaveOneOutCentroidDriver extends ValidationDriver {

	private ImmutableMap<String, EventMap> knownCentroids;
	private ImmutableMultimap<String, Document> knownDocuments;
	private ImmutableMap<Document, EventMap> knownEventMaps;
	
	@Override
	public String displayName() {
		return "Leave One Out Centroid"+this.getDistanceName();
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
		ImmutableMap.Builder<Document, EventMap> knownEventMapsBuilder = ImmutableMap.builder();
		ImmutableMultimap.Builder<String, Document> knownDocumentsBuilder = ImmutableMultimap.builder();
		Multimap<String, EventMap> knownHistograms = HashMultimap.create();
		for(Document known : knowns){
			EventMap eventMap = new EventMap(known);
			knownEventMapsBuilder.put(known, eventMap);
			knownHistograms.put(known.getAuthor(), eventMap);
			knownDocumentsBuilder.put(known.getAuthor(), known);
		}
		knownEventMaps = knownEventMapsBuilder.build();
		knownDocuments = knownDocumentsBuilder.build();
		ImmutableMap.Builder<String, EventMap> knownCentoidsBuilder = ImmutableMap.builder();
		for(Map.Entry<String, Collection<EventMap>> entry : knownHistograms.asMap().entrySet()){
			knownCentoidsBuilder.put(entry.getKey(), EventMap.centroid(entry.getValue()));
		}
		knownCentroids = knownCentoidsBuilder.build();
	}

	@Override
	public List<Pair<String, Double>> analyze(Document known) throws AnalyzeException {
		List<Pair<String, Double>> results = new ArrayList<Pair<String, Double>>(knownCentroids.size());
		String currentAuthor = known.getAuthor();
		Collection<Document> authorDocuments = knownDocuments.get(currentAuthor);
		List<EventMap> currentAuthorHistograms = new ArrayList<EventMap>(authorDocuments.size());
		for (Document document : authorDocuments) {
			if (!document.equals(known))
				currentAuthorHistograms.add(knownEventMaps.get(document));
		}
		EventMap currentAuthorCentroid = EventMap.centroid(currentAuthorHistograms);
		EventMap currentEventMap = knownEventMaps.get(known);
		try {
			results.add(new Pair<String, Double>(currentAuthor,distance.distance(currentEventMap, currentAuthorCentroid), 2));
			for (Map.Entry<String, EventMap> entry : knownCentroids.entrySet()) {
				if (!entry.getKey().equals(currentAuthor)) {
					results.add(new Pair<String, Double>(entry.getKey(),distance.distance(currentEventMap, entry.getValue()), 2));
				}
			}
		} catch (DistanceCalculationException e) {
			throw new AnalyzeException("Distance Method "+distance.displayName()+" has failed");
		}
		Collections.sort(results);
		return results;
	}

}
