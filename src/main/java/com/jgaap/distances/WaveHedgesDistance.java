package com.jgaap.distances;

import java.util.Set;

import com.google.common.collect.Sets;
import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.util.Event;
import com.jgaap.util.Histogram;

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
		return true;
	}

	@Override
	public double distance(Histogram unknownHistogram, Histogram knownHistogram)
			throws DistanceCalculationException {
		Set<Event> events = Sets.union(unknownHistogram.uniqueEvents(), knownHistogram.uniqueEvents());
		
		double sum = 0.0;
		
		for(Event event : events){
			sum += 1 - Math.min( unknownHistogram.relativeFrequency(event), knownHistogram.relativeFrequency(event)) / Math.max(unknownHistogram.relativeFrequency(event), knownHistogram.relativeFrequency(event));
		}

		return sum;
	}

}
