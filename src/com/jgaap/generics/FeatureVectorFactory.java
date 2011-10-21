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
}
