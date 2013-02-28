package com.jgaap.distances;

import java.util.Set;

import com.google.common.collect.Sets;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.util.Event;
import com.jgaap.util.EventMap;

/**
 * Bray Curtis Distance
 * d = sum( abs(xi - yi))/sum( xi + yi )
 * 
 * @author Adam Sargent
 * @version 1.0
 */

public class BrayCurtisDistance extends DistanceFunction {

	@Override
	public String displayName() {
		return "Bray Curtis Distance";
	}

	@Override
	public String tooltipText() {
		return "Bray Curtis Distance";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public double distance(EventMap unknownEventMap, EventMap knownEventMap) {

		Set<Event> events = Sets.union(unknownEventMap.uniqueEvents(), knownEventMap.uniqueEvents());
		
		double distance = 0.0, sumNumer = 0.0, sumDenom = 0.0;
		
		for(Event event: events){
			double known = knownEventMap.relativeFrequency(event);
			double unknown = unknownEventMap.relativeFrequency(event);
			sumNumer += Math.abs(unknown - known);
			sumDenom += unknown + known;
		}
		distance = sumNumer / sumDenom;
		return distance;
	}

}
