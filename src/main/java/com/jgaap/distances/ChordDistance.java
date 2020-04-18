package com.jgaap.distances;

import java.util.Set;

import com.google.common.collect.Sets;
import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.util.Event;
import com.jgaap.util.Histogram;

/**
 * Chord Distance
 * d = sqrt( 2 - 2 * (sum(xi * yi)/sqrt( sum(xi)^2 * sum(yi)^2 )) )
 * 
 * @author Adam Sargent
 * @version 1.0
 */

public class ChordDistance extends DistanceFunction {

	@Override
	public String displayName() {
		return "Chord Distance";
	}

	@Override
	public String tooltipText() {
		return "Chord Distance";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public double distance(Histogram unknownHistogram, Histogram knownHistogram)
			throws DistanceCalculationException {
		Set<Event> events = Sets.union(unknownHistogram.uniqueEvents(), knownHistogram.uniqueEvents());

		double distance = 0.0, sumNumer = 0.0, sumUnknown = 0.0, sumKnown = 0.0;
		
		for(Event event : events){
			sumNumer += unknownHistogram.relativeFrequency(event) * knownHistogram.relativeFrequency(event);
			sumUnknown += unknownHistogram.relativeFrequency(event);
			sumKnown += knownHistogram.relativeFrequency(event);
		}
		distance = Math.sqrt(2 - 2 * (sumNumer / Math.sqrt(sumUnknown * sumUnknown * sumKnown * sumKnown)));
		
		return distance;
	}

}
