package com.jgaap.classifiers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventMap;
import com.jgaap.generics.Pair;
import com.jgaap.generics.ValidationDriver;

public class LeaveOneOutCentroidDriver extends ValidationDriver {

	private Map<String, EventMap> knownCentroids;
	private Map<String, List<Document>> knownDocuments;
	private Set<Event> events;

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
		Map<String, List<EventMap>> knownHistograms = new HashMap<String, List<EventMap>>();
		events = new HashSet<Event>();
		knownDocuments = new HashMap<String, List<Document>>();
		for (Document known : knowns) {
			EventMap eventMap = new EventMap(known);
			events.addAll(eventMap.uniqueEvents());
			List<EventMap> histograms = knownHistograms.get(known.getAuthor());
			if (histograms != null) {
				histograms.add(eventMap);
			} else {
				histograms = new ArrayList<EventMap>();
				histograms.add(eventMap);
				knownHistograms.put(known.getAuthor(), histograms);
			}
			List<Document> documents = knownDocuments.get(known.getAuthor());
			if (documents != null) {
				documents.add(known);
			} else {
				documents = new ArrayList<Document>();
				documents.add(known);
				knownDocuments.put(known.getAuthor(), documents);
			}
		}
		knownCentroids = new HashMap<String, EventMap>(knownHistograms.size());
		for (Entry<String, List<EventMap>> entry : knownHistograms.entrySet()) {
			knownCentroids.put(entry.getKey(), EventMap.centroid(entry.getValue()));
		}
	}

	@Override
	public List<Pair<String, Double>> analyze(Document known) throws AnalyzeException {
		List<Pair<String, Double>> results = new ArrayList<Pair<String, Double>>();
		String currentAuthor = known.getAuthor();
		List<EventMap> currentAuthorHistograms = new ArrayList<EventMap>();
		for (Document document : knownDocuments.get(currentAuthor)) {
			if (!document.equals(known))
				currentAuthorHistograms.add(new EventMap(document));
		}
		EventMap currentAuthorCentroid = EventMap.centroid(currentAuthorHistograms);
		EventMap currentEventMap = new EventMap(known);
		try {
			results.add(new Pair<String, Double>(currentAuthor,distance.distance(currentEventMap, currentAuthorCentroid), 2));
			for (Entry<String, EventMap> entry : knownCentroids.entrySet()) {
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
