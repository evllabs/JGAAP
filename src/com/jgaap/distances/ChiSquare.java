package com.jgaap.distances;

import java.util.HashSet;
import java.util.Set;

import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;

/**
 * Chi Square
 * d = sum( (xi - yi)^2/(xi + yi) )
 * 
 * @author Adam Sargent
 * @version 1.0
 */

public class ChiSquare extends DistanceFunction {

	@Override
	public String displayName() {
		return "Chi Square";
	}

	@Override
	public String tooltipText() {
		return "Chi Square";
	}

	@Override
	public boolean showInGUI() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double distance(EventSet unknownEventSet, EventSet knownEventSet)
			throws DistanceCalculationException {
		EventHistogram unknownHistogram = new EventHistogram(unknownEventSet);
		EventHistogram knownHistogram = new EventHistogram(knownEventSet);

		Set<Event> events = new HashSet<Event>();
		events.addAll(unknownHistogram.events());
		events.addAll(knownHistogram.events());
		
		double sum = 0.0, numer, denom;
		
		for(Event event: events){
			numer = (unknownHistogram.getRelativeFrequency(event) - knownHistogram.getRelativeFrequency(event)) * (unknownHistogram.getRelativeFrequency(event) - knownHistogram.getRelativeFrequency(event));
			denom = unknownHistogram.getRelativeFrequency(event) + knownHistogram.getRelativeFrequency(event);
			sum += numer / denom;
		}
		
		return sum;
	}

}
