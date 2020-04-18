package com.jgaap.distances;

import java.util.Set;

import com.google.common.collect.Sets;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.util.Event;
import com.jgaap.util.Histogram;

public class HellingerDistance extends DistanceFunction {

	private static final double oneOverSqrtTwo = 1/Math.sqrt(2); 
	
	@Override
	public String displayName() {
		return "Hellinger Distance";
	}

	@Override
	public String tooltipText() {
		return "1/sqrt(2) * sqrt( sum( (sqrt(pi)-sqrt(qi))^2 ) )";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	
	@Override
	public double distance(Histogram unknownHistogram, Histogram knownHistogram) {
		Set<Event> events = Sets.union(unknownHistogram.uniqueEvents(), knownHistogram.uniqueEvents());
		double sum = 0.0;
		for(Event event : events) { 
			sum += Math.pow(Math.sqrt(unknownHistogram.relativeFrequency(event))-Math.sqrt(knownHistogram.relativeFrequency(event)), 2);
		}
		return Math.sqrt(sum)*oneOverSqrtTwo;
	}

}
