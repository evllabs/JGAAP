package com.jgaap.distances;

import java.util.HashSet;
import java.util.Set;

import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.DivergenceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;

/**
 * Jeffrey Divergence
 * d = sum( xi * log(xi / mi) + yi * log(yi / mi) )
 * where mi = (xi + yi) / 2
 * 
 * @author Adam Sargent
 * @version 1.0
 */

public class JeffreyDivergence extends DivergenceFunction {

	@Override
	public String displayName() {
		return "Jeffrey Divergence";
	}

	@Override
	protected double divergence(EventSet unknownEventSet, EventSet knownEventSet)
			throws DistanceCalculationException {
		EventHistogram unknownHistogram = new EventHistogram(unknownEventSet);
		EventHistogram knownHistogram = new EventHistogram(knownEventSet);

		Set<Event> events = new HashSet<Event>();
		events.addAll(unknownHistogram.events());
		events.addAll(knownHistogram.events());
		
		double sum = 0.0, mi = 0.0;

		/*sum( xi * log(xi / mi) + yi * log(yi / mi) )
		where mi = (xi + yi) / 2*/
		for(Event event: events){
			mi = (unknownHistogram.getRelativeFrequency(event) + knownHistogram.getRelativeFrequency(event))/ 2;
			sum += unknownHistogram.getRelativeFrequency(event) * Math.log(unknownHistogram.getRelativeFrequency(event) / mi) + knownHistogram.getRelativeFrequency(event) * Math.log(knownHistogram.getRelativeFrequency(event) / mi);
		}
		return sum;
	}

	@Override
	public boolean showInGUI() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String tooltipText() {
		return "Jeffrey Divergence";
	}

}
