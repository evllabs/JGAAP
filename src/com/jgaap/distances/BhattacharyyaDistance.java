package com.jgaap.distances;

import java.util.HashSet;
import java.util.Set;

import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;

/**
 * Bhattacharyya Distance
 * d = abs( log(  sum( sqrt(  abs(xi - yi)  ) ) / sum(xi) ) )
 * 
 * @author Adam Sargent
 * @version 1.0
 */

public class BhattacharyyaDistance extends DistanceFunction {

	@Override
	public String displayName() {
		return "Bhattacharyya Distance";
	}

	@Override
	public String tooltipText() {
		return "Bhattacharyya Distance";
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
		
		double distance = 0.0, sumNumer = 0.0, sumDenom = 0.0;

		for(Event event: events){
			sumNumer += Math.sqrt(Math.abs(unknownHistogram.getRelativeFrequency(event) - knownHistogram.getRelativeFrequency(event)));
			sumDenom += unknownHistogram.getRelativeFrequency(event);
		}
		distance = Math.abs(Math.log(sumNumer / sumDenom));
		return distance;
	}

}
