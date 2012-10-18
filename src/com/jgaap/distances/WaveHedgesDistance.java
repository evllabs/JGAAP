package com.jgaap.distances;

import java.util.HashSet;
import java.util.Set;

import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventMap;

/**
 * Wave Hedges Distance
 * d = sum( 1 - min(xi, yi)/max(xi, yi) )
 * 
 * @author Adam Sargent
 * @version 1.0
 */

public class WaveHedgesDistance extends DistanceFunction {

	@Override
	public String displayName() {
		return "Wave Hedges Distance";
	}

	@Override
	public String tooltipText() {
		return "Wave Hedges Distance";
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
		
		double sum = 0.0;
		
		for(Event event : events){
			sum += 1 - Math.min( unknownEventMap.relativeFrequency(event), knownEventMap.relativeFrequency(event)) / Math.max(unknownEventMap.relativeFrequency(event), knownEventMap.relativeFrequency(event));
		}

		return sum;
	}

}
