package com.jgaap.distances;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;

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
	public double distance(EventSet es1, EventSet es2) {
		return distance(es1.getHistogram(), es2.getHistogram());
	}
	
	@Override
	public double distance(EventHistogram h1, EventHistogram h2) {
		Set<Event> events = new HashSet<Event>(h1.events());
		events.addAll(h2.events());
		double sum = 0.0;
		for(Event event : events) { 
			sum += Math.pow(Math.sqrt(h1.getRelativeFrequency(event))-Math.sqrt(h2.getRelativeFrequency(event)), 2);
		}
		return Math.sqrt(sum)*oneOverSqrtTwo;
	}
	
	@Override
	public double distance(List<Double> v1, List<Double> v2) {
		double sum = 0.0;
		for(int i = 0; i<v1.size(); i++) { 
			sum += Math.pow(Math.sqrt(v1.get(i))-Math.sqrt(v2.get(i)), 2);
		}
		return Math.sqrt(sum)*oneOverSqrtTwo;
	}

}
