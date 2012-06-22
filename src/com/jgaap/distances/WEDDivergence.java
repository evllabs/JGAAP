package com.jgaap.distances;
/**
 * WED Divergence
 * Weighted Euclidean Distance Divergence
 * d = sqrt( sum( wi * (xi - yi)^2 ))
 * 
 * if(xi == 0)
 * 	wi = 1
 * else
 * 	wi = xi
 * 
 * @author Adam Sargent
 * @version 1.0
 */

import java.util.HashSet;
import java.util.Set;

import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;

public class WEDDivergence extends DistanceFunction {

	@Override
	public String displayName() {
		return "WED Divergence";
	}

	@Override
	public String tooltipText() {
		return "Weighted Euclidean Distance (WED) Divergence";
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
		
		double distance = 0.0;
		
		for(Event event : events){
			distance += (unknownHistogram.getRelativeFrequency(event) == 0 ? 1 : unknownHistogram.getRelativeFrequency(event)) * (unknownHistogram.getRelativeFrequency(event) - knownHistogram.getRelativeFrequency(event)) * (unknownHistogram.getRelativeFrequency(event) - knownHistogram.getRelativeFrequency(event));  
		}
		
		return Math.sqrt(distance);
	}

}
