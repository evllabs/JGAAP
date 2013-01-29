package com.jgaap.distances;

import java.util.HashSet;
import java.util.Set;

import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventMap;

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
	public double distance(EventMap unknownEventMap, EventMap knownEventMap) {
		Set<Event> events = new HashSet<Event>(unknownEventMap.uniqueEvents());
		events.addAll(knownEventMap.uniqueEvents());
		double sum = 0.0;
		for(Event event : events) { 
			sum += Math.pow(Math.sqrt(unknownEventMap.relativeFrequency(event))-Math.sqrt(knownEventMap.relativeFrequency(event)), 2);
		}
		return Math.sqrt(sum)*oneOverSqrtTwo;
	}

}
