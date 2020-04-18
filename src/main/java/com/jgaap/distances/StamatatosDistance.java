package com.jgaap.distances;

import java.util.Set;

import com.google.common.collect.Sets;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.util.Event;
import com.jgaap.util.Histogram;

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
	public double distance(Histogram unknownHistogram, Histogram knownHistogram) {
		Set<Event> events = Sets.union(unknownHistogram.uniqueEvents(), knownHistogram.uniqueEvents());
		
		double distance = 0.0;
		
		for(Event event : events){
			double unknownValue = unknownHistogram.relativeFrequency(event);
			double knownValue = knownHistogram.relativeFrequency(event);
			distance += Math.pow(2*(unknownValue-knownValue)/(unknownValue+knownValue), 2);
		}
		
		return distance;
	}

}
