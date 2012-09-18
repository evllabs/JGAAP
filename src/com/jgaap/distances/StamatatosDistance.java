package com.jgaap.distances;

import java.util.HashSet;
import java.util.Set;

import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;

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
	public double distance(EventSet es1, EventSet es2) {

		EventHistogram unknown = es1.getHistogram();
		EventHistogram known = es2.getHistogram();
		
		Set<Event> events = new HashSet<Event>();
		events.addAll(unknown.events());
		events.addAll(known.events());
		
		double distance = 0.0;
		
		for(Event event : events){
			double unknownValue = unknown.getRelativeFrequency(event);
			double knownValue = known.getRelativeFrequency(event);
			distance += Math.pow(2*(unknownValue-knownValue)/(unknownValue+knownValue), 2);
		}
		
		return distance;
	}

}
