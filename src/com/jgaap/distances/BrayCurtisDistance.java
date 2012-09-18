package com.jgaap.distances;

import java.util.HashSet;
import java.util.Set;

import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;

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
	public double distance(EventSet unknownEventSet, EventSet knownEventSet)
			throws DistanceCalculationException {
		EventHistogram unknownHistogram = unknownEventSet.getHistogram();
		EventHistogram knownHistogram = knownEventSet.getHistogram();

		Set<Event> events = new HashSet<Event>();
		events.addAll(unknownHistogram.events());
		events.addAll(knownHistogram.events());
		
		double distance = 0.0, sumNumer = 0.0, sumDenom = 0.0;
		
		for(Event event: events){
			sumNumer += Math.abs(unknownHistogram.getRelativeFrequency(event) - knownHistogram.getRelativeFrequency(event));
			sumDenom += unknownHistogram.getRelativeFrequency(event) + knownHistogram.getRelativeFrequency(event);
		}
		distance = sumNumer / sumDenom;
		return distance;
	}

}
