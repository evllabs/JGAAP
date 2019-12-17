package com.jgaap.distances;

import java.util.Set;

import com.google.common.collect.Sets;
import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.util.Event;
import com.jgaap.util.Histogram;

/**
 * Bhattacharyya Distance
 * d = -log(sum(sqrt(xi * yi)))
 * 
 * @author David Berdik
 * @version 2.0
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
		double distance = 0.0;
		
		for(Event event : events)
			distance += Math.sqrt(unknownHistogram.relativeFrequency(event) * knownHistogram.relativeFrequency(event));
		
		distance = -Math.log(distance);
		return distance;
	}

}
