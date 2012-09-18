package com.jgaap.distances;

import java.util.HashSet;
import java.util.Set;

import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;

/**
 * Matusita Distance
 * d = sqrt( sum( (sqrt(xi) - sqrt(yi))^2 ) )
 * 
 * @author Adam Sargent
 * @version 1.0
 */

public class MatusitaDistance extends DistanceFunction {

	@Override
	public String displayName() {
		return "Matusita Distance";
	}

	@Override
	public String tooltipText() {
		return "Matusita Distance";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public double distance(EventSet unknownEventSet, EventSet knownEventSet)
			throws DistanceCalculationException {
		EventHistogram unknownHistogram = unknownEventSet.getHistogram();
		EventHistogram knownHistogram = knownEventSet.getHistogram();

		Set<Event> events = new HashSet<Event>();
		events.addAll(unknownHistogram.events());
		events.addAll(knownHistogram.events());

		double distance = 0.0, sum = 0.0;
		
		for(Event event : events){
			sum += Math.pow(Math.sqrt(unknownHistogram.getRelativeFrequency(event)) - Math.sqrt(knownHistogram.getRelativeFrequency(event)), 2);
		}
		distance = Math.sqrt(sum);
		
		return distance;
	}

}
