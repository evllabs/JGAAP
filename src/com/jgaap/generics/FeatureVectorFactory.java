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
			EventHistogram histogram = known.get(i).getHistogram();
			int j = 0;
			for(Event event : vocab) {
				knownSet[i][j] = (double)histogram.getAbsoluteFrequency(event);
				j++;
			}
		}
		
		// Construct the unknown vector
		double[][] unknownSet = new double[unknown.size()][vocab.size()];
		for(int i = 0; i < unknown.size(); i++) {
			EventHistogram histogram = unknown.get(i).getHistogram();
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
			EventHistogram histogram = known.get(i).getHistogram();
			int j = 0;
			for(Event event : vocab) {
				knownSet[i][j] = (double)histogram.getRelativeFrequency(event);
				j++;
			}
		}
		
		// Construct the unknown vector
		double[][] unknownSet = new double[unknown.size()][vocab.size()];
		for(int i = 0; i < unknown.size(); i++) {
			EventHistogram histogram = unknown.get(i).getHistogram();
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
			EventHistogram histogram = eventSet.getHistogram();
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
			vocab.addAll(eventSet.getHistogram().events());
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
			EventHistogram histogram = known.get(i).getHistogram();
			int j = 0;
			for(Event event : vocab) {
				knownSet[i][j] = (double)histogram.getNormalizedFrequency(event);
				j++;
			}
		}
		
		// Construct the unknown vector
		double[][] unknownSet = new double[unknown.size()][vocab.size()];
		for(int i = 0; i < unknown.size(); i++) {
			EventHistogram histogram = unknown.get(i).getHistogram();
			int j = 0;
			for(Event event : vocab) {
				unknownSet[i][j] = (double)histogram.getNormalizedFrequency(event);
				j++;
			}
		}
		
		return new Pair<double[][], double[][]>(knownSet, unknownSet);
	}
	
	public static double[][] getNormalizedFeatures(List<EventSet> eventSets, Set<Event> vocab){
		double[][] results = new double[eventSets.size()][vocab.size()];
		int i = 0;
		for(EventSet eventSet : eventSets) {
			results[i]=getNormalizedFeatures(eventSet, vocab);
			i++;
		}
		
		return results;
	}
	
	public static double[] getNormalizedFeatures(EventSet eventSet, Set<Event> vocab){
		double[] result = new double[vocab.size()];
		EventHistogram histogram = eventSet.getHistogram();
		int i = 0;
		for(Event event : vocab) {
			result[i] = histogram.getNormalizedFrequency(event);
			i++;
		}
		return result;
	}
	
	public static Pair<double[][], Set<Event>> getNormalizedFeatures(List<EventSet> eventSets){
		Set<Event> vocab = new HashSet<Event>();
		for(EventSet eventSet : eventSets){
			vocab.addAll(eventSet.getHistogram().events());
		}
		double[][] resultsSet = getNormalizedFeatures(eventSets, vocab);
		return new Pair<double[][], Set<Event>>(resultsSet, vocab);
	}
}
