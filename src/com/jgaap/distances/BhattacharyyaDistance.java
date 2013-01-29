package com.jgaap.distances;

import java.util.HashSet;
import java.util.Set;

import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventMap;

/**
 * Bhattacharyya Distance
 * d = abs( log(  sum( sqrt(  abs(xi - yi)  ) ) / sum(xi) ) )
 * 
 * @author Adam Sargent
 * @version 1.0
 */

public class BhattacharyyaDistance extends DistanceFunction {

	@Override
	public String displayName() {
		return "Bhattacharyya Distance";
	}

	@Override
	public String tooltipText() {
		return "Bhattacharyya Distance";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public double distance(EventMap unknownEventMap, EventMap knownEventMap)
			throws DistanceCalculationException {

		Set<Event> events = new HashSet<Event>(unknownEventMap.uniqueEvents());
		events.addAll(knownEventMap.uniqueEvents());
		
		double distance = 0.0, sumNumer = 0.0, sumDenom = 0.0;

		for(Event event: events){
			sumNumer += Math.sqrt(Math.abs(unknownEventMap.relativeFrequency(event) - knownEventMap.relativeFrequency(event)));
			sumDenom += unknownEventMap.relativeFrequency(event);
		}
		distance = Math.abs(Math.log(sumNumer / sumDenom));
		return distance;
	}

}
