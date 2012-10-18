package com.jgaap.distances;

import java.util.HashSet;
import java.util.Set;

import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventMap;

public class StamatatosDistance extends DistanceFunction {

	@Override
	public String displayName() {
		return "Stamatatos Distance";
	}

	@Override
	public String tooltipText() {
		return "Stamatatos Distance (2*(f(x)-g(x))/(f(x)+g(x)))^2";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public double distance(EventMap unknownEventMap, EventMap knownEventMap) {
		Set<Event> events = new HashSet<Event>(unknownEventMap.uniqueEvents());
		events.addAll(knownEventMap.uniqueEvents());
		
		double distance = 0.0;
		
		for(Event event : events){
			double unknownValue = unknownEventMap.relativeFrequency(event);
			double knownValue = knownEventMap.relativeFrequency(event);
			distance += Math.pow(2*(unknownValue-knownValue)/(unknownValue+knownValue), 2);
		}
		
		return distance;
	}

}
