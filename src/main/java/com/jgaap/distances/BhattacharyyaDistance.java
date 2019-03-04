package com.jgaap.distances;

import java.util.Set;

import com.google.common.collect.Sets;
import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.util.Event;
import com.jgaap.util.Histogram;

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
		return true;
	}

	@Override
	public double distance(Histogram unknownHistogram, Histogram knownHistogram)
			throws DistanceCalculationException {

		Set<Event> events = Sets.union(unknownHistogram.uniqueEvents(), knownHistogram.uniqueEvents());
		
		double distance = 0.0, sumNumer = 0.0, sumDenom = 0.0;

		for(Event event: events){
			sumNumer += Math.sqrt(Math.abs(unknownHistogram.relativeFrequency(event) - knownHistogram.relativeFrequency(event)));
			sumDenom += unknownHistogram.relativeFrequency(event);
		}
		distance = Math.abs(Math.log(sumNumer / sumDenom));
		return distance;
	}

}
