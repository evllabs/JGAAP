package com.jgaap.distances;

import java.util.HashSet;
import java.util.Set;

import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;

/**
 * Chord Distance
 * d = sqrt( 2 - 2 * (sum(xi * yi)/sqrt( sum(xi)^2 * sum(yi)^2 )) )
 * 
 * @author Adam Sargent
 * @version 1.0
 */

public class ChordDistance extends DistanceFunction {

	@Override
	public String displayName() {
		return "Chord Distance";
	}

	@Override
	public String tooltipText() {
		return "Chord Distance";
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

		double distance = 0.0, sumNumer = 0.0, sumUnknown = 0.0, sumKnown = 0.0;
		
		for(Event event : events){
			sumNumer += unknownHistogram.getRelativeFrequency(event) * knownHistogram.getRelativeFrequency(event);
			sumUnknown += unknownHistogram.getRelativeFrequency(event);
			sumKnown += knownHistogram.getRelativeFrequency(event);
		}
		distance = Math.sqrt(2 - 2 * (sumNumer / Math.sqrt(sumUnknown * sumUnknown * sumKnown * sumKnown)));
		
		return distance;
	}

}
