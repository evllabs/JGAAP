package com.jgaap.distances;

import java.util.Set;

import com.google.common.collect.Sets;
import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.util.Event;
import com.jgaap.util.Histogram;

/**
 * Angular Separation Distance
 * d = 1 - ( sum( xi * yi ) / sqrt( sum(xi)^2 * sum( yi )^2 ) )
 * 
 * @author Adam Sargent
 * @version 1.0
 */

public class AngularSeparationDistance extends DistanceFunction {

	@Override
	public String displayName() {
		return "Angular Separation Distance";
	}

	@Override
	public String tooltipText() {
		return "Angular Separation Distance";
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
		distance = 1 - (sumNumer / Math.sqrt(sumUnknown * sumUnknown * sumKnown * sumKnown));
		return distance;
	}

}
