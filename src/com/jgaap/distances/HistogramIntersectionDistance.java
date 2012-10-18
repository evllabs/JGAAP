package com.jgaap.distances;

import java.util.HashSet;
import java.util.Set;

import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventMap;

/**
 * Histogram Intersection Distance
 * d = 1 - (sum( min(xi, yi) ) / min( sum(xi), sum(yi)))
 * 
 * @author Adam Sargent
 * @version 1.0
 */

public class HistogramIntersectionDistance extends DistanceFunction {

	@Override
	public String displayName() {
		return "Histogram Intersection Distance";
	}

	@Override
	public String tooltipText() {
		return "Histogram Intersection Distance";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public double distance(EventMap unknownEventMap, EventMap knownEventMap)
			throws DistanceCalculationException{

		Set<Event> events = new HashSet<Event>(unknownEventMap.uniqueEvents());
		events.addAll(knownEventMap.uniqueEvents());
		
		double distance = 0.0, sumNumer = 0.0, sumUnknown = 0.0, sumKnown = 0.0;

		for(Event event: events){
			sumNumer += Math.min(unknownEventMap.relativeFrequency(event), knownEventMap.relativeFrequency(event));
			sumUnknown += unknownEventMap.relativeFrequency(event);
			sumKnown += knownEventMap.relativeFrequency(event);
		}

		distance = 1 - (sumNumer / Math.min(sumUnknown, sumKnown));
		return distance;
	}

}
