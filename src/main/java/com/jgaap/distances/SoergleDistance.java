package com.jgaap.distances;

import java.util.Set;

import com.google.common.collect.Sets;
import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.util.Event;
import com.jgaap.util.Histogram;

/**
 * Soergle Distance
 * d = sum( abs(xi - yi) ) / sum( max(xi, yi) )
 * 
 * @author Adam Sargent
 * @version 1.0
 */

public class SoergleDistance extends DistanceFunction {

	@Override
	public String displayName() {
		return "Soergle Distance";
	}

	@Override
	public String tooltipText() {
		return "Soergle Distance";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public double distance(Histogram unknownHistogram, Histogram knownHistogram)
			throws DistanceCalculationException {

		Set<Event> events = Sets.union(unknownHistogram.uniqueEvents(), knownHistogram.uniqueEvents());

		double distance = 0.0, sumNumer = 0.0, sumDenom = 0.0;
		
		for(Event event : events){
			double known = knownHistogram.relativeFrequency(event);
			double unknown = unknownHistogram.relativeFrequency(event);
			sumNumer += Math.abs(unknown - known);
			sumDenom += Math.max(unknown, known);
		}
		distance = sumNumer / sumDenom;
		
		return distance;
	}

}
