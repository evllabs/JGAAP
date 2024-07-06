package com.jgaap.distances;

import java.util.Set;

import com.google.common.collect.Sets;
import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.util.Event;
import com.jgaap.util.Histogram;

/**
 * Matusita Distance
 * d = sqrt( sum( (sqrt(xi) - sqrt(yi))^2 ) )
 * 
 * @author Adam Sargent
 * @version 1.0
 */

public class MatusitaDistance extends DistanceFunction {

	@Override
	public String displayName() {
		return "Matusita Distance";
	}

	@Override
	public String tooltipText() {
		return "Matusita Distance";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public double distance(Histogram unknownHistogram, Histogram knownHistogram)
			throws DistanceCalculationException {
		Set<Event> events = Sets.union(unknownHistogram.uniqueEvents(), knownHistogram.uniqueEvents());

		double distance = 0.0, sum = 0.0;
		
		for(Event event : events){
			sum += Math.pow(Math.sqrt(unknownHistogram.relativeFrequency(event)) - Math.sqrt(knownHistogram.relativeFrequency(event)), 2);
		}
		distance = Math.sqrt(sum);
		
		return distance;
	}

}
