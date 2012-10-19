package com.jgaap.generics;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * FeatureVectorFactory provides methods for getting known and unknown feature matrices
 * for any analysis method that requires such information.
 * 
 * This class replaces KernelMethodMatrix
 * @author John Noecker Jr.
 *
 */
public class FeatureVectorFactory {
	/**
	 * Generate the absolute frequency vector matrices
	 * @param known The known event sets
	 * @param unknown The unknown event sets 
	 * @return Pair of feature vector matrices
	 */
	public static Pair<double[][], double[][]> getFeatures(List<EventSet> known, List<EventSet> unknown) {
		Set<Event> vocab = new HashSet<Event>();
		
		// Add all events from known documents to the vocab list.
		for(EventSet eventSet : known) {
			for(Event event : eventSet) {
				vocab.add(event);
			}
		}
		
		// Add all events from unknown documents to the vocab list.
		for(EventSet eventSet : unknown) {
			for(Event event : eventSet) {
				vocab.add(event);
			}
		}
		
		// Construct the known vector
		double[][] knownSet = new double[known.size()][vocab.size()];
		for(int i = 0; i < known.size(); i++) {
			EventHistogram histogram = new EventHistogram(known.get(i));
			int j = 0;
			for(Event event : vocab) {
				knownSet[i][j] = (double)histogram.getAbsoluteFrequency(event);
				j++;
			}
		}
		
		// Construct the unknown vector
		double[][] unknownSet = new double[unknown.size()][vocab.size()];
		for(int i = 0; i < unknown.size(); i++) {
			EventHistogram histogram = new EventHistogram(unknown.get(i));
			int j = 0;
			for(Event event : vocab) {
				unknownSet[i][j] = (double)histogram.getAbsoluteFrequency(event);
				j++;
			}
		}
		
		return new Pair<double[][], double[][]>(knownSet, unknownSet);
	}
	
	/**
	 * Generate the relative frequency vector matrices
	 * @param known The known event sets
	 * @param unknown The unknown event sets 
	 * @return Pair of feature vector matrices
	 */
	public static Pair<double[][], double[][]> getRelativeFeatures(List<EventSet> known, List<EventSet> unknown) {
		Set<Event> vocab = new HashSet<Event>();
		
		// Add all events from known documents to the vocab list.
		for(EventSet eventSet : known) {
			for(Event event : eventSet) {
				vocab.add(event);
			}
		}
		
		// Add all events from unknown documents to the vocab list.
		for(EventSet eventSet: unknown) {
			for(Event event : eventSet) {
				vocab.add(event);
			}
		}
		
		// Construct the known vector
		double[][] knownSet = new double[known.size()][vocab.size()];
		for(int i = 0; i < known.size(); i++) {
			EventHistogram histogram = new EventHistogram(known.get(i));
			int j = 0;
			for(Event event : vocab) {
				knownSet[i][j] = (double)histogram.getRelativeFrequency(event);
				j++;
			}
		}
		
		// Construct the unknown vector
		double[][] unknownSet = new double[unknown.size()][vocab.size()];
		for(int i = 0; i < unknown.size(); i++) {
			EventHistogram histogram = new EventHistogram(unknown.get(i));
			int j = 0;
			for(Event event : vocab) {
				unknownSet[i][j] = (double)histogram.getRelativeFrequency(event);
				j++;
			}
		}
		
		return new Pair<double[][], double[][]>(knownSet, unknownSet);
	}
	
	public static double[][] getRelativeFeatures(List<EventSet> eventSets, Set<Event> vocab){
		double[][] results = new double[eventSets.size()][vocab.size()];
		int i = 0;
		for(EventSet eventSet : eventSets) {
			EventHistogram histogram = new EventHistogram(eventSet);
			int j = 0;
			for(Event event : vocab) {
				results[i][j] = histogram.getRelativeFrequency(event);
				j++;
			}
			i++;
		}
		
		return results;
	}
	
	public static Pair<double[][], Set<Event>> getRelativeFeatures(List<EventSet> eventSets){
		Set<Event> vocab = new HashSet<Event>();
		for(EventSet eventSet : eventSets){
			for(Event event : eventSet)
			vocab.add(event);
		}
		double[][] resultsSet = getRelativeFeatures(eventSets, vocab);
		return new Pair<double[][], Set<Event>>(resultsSet, vocab);
	}
	
	/**
	 * Generate the normalized frequency vector matrices
	 * @param known The known event sets
	 * @param unknown The unknown event sets 
	 * @return Pair of feature vector matrices
	 */
	public static Pair<double[][], double[][]> getNormalizedFeatures(List<EventSet> known, List<EventSet> unknown) {
		Set<Event> vocab = new HashSet<Event>();
		
		// Add all events from known documents to the vocab list.
		for(EventSet eventSet : known) {
			for(Event event : eventSet) {
				vocab.add(event);
			}
		}
		
		// Add all events from unknown documents to the vocab list.
		for(EventSet eventSet: unknown) {
			for(Event event : eventSet) {
				vocab.add(event);
			}
		}
		
		// Construct the known vector
		double[][] knownSet = new double[known.size()][vocab.size()];
		for(int i = 0; i < known.size(); i++) {
			EventHistogram histogram = new EventHistogram(known.get(i));
			int j = 0;
			for(Event event : vocab) {
				knownSet[i][j] = (double)histogram.getNormalizedFrequency(event);
				j++;
			}
		}
		
		// Construct the unknown vector
		double[][] unknownSet = new double[unknown.size()][vocab.size()];
		for(int i = 0; i < unknown.size(); i++) {
			EventHistogram histogram = new EventHistogram(unknown.get(i));
			int j = 0;
			for(Event event : vocab) {
				unknownSet[i][j] = (double)histogram.getNormalizedFrequency(event);
				j++;
			}
		}
		
		return new Pair<double[][], double[][]>(knownSet, unknownSet);
	}
	
	public static double[][] getNormalizedFeatures(List<Document> documents, Set<Event> vocab){
		double[][] results = new double[documents.size()][vocab.size()];
		int i = 0;
		for(Document document : documents) {
			results[i]=getNormalizedFeatures(document, vocab);
			i++;
		}
		
		return results;
	}
	
	public static double[] getNormalizedFeatures(Document document, Set<Event> vocab){
		double[] result = new double[vocab.size()];
		EventMap eventMap = new EventMap(document);
		int i = 0;
		for(Event event : vocab) {
			result[i] = eventMap.normalizedFrequency(event);
			i++;
		}
		return result;
	}
	
	public static Pair<double[][], Set<Event>> getNormalizedFeatures(List<Document> documents){
		Set<Event> vocab = new HashSet<Event>();
		for(Document document : documents){
			for(EventSet eventSet : document.getEventSets().values())
				for(Event event : eventSet)
					vocab.add(event);
		}
		double[][] resultsSet = getNormalizedFeatures(documents, vocab);
		return new Pair<double[][], Set<Event>>(resultsSet, vocab);
	}
}
