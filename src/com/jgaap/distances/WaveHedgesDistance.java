package com.jgaap.distances;

import java.util.HashSet;
import java.util.Set;

import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;

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
		
		double sum = 0.0;
		
		for(Event event : events){
			sum += 1 - Math.min( unknownHistogram.getRelativeFrequency(event), knownHistogram.getRelativeFrequency(event)) / Math.max(unknownHistogram.getRelativeFrequency(event), knownHistogram.getRelativeFrequency(event));
		}

		return sum;
	}

}
