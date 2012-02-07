package com.jgaap.distances;

import java.util.HashSet;
import java.util.Set;

import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;

/**
 * Histogram Intersection Distance
 * d = 1 - (sum( min(xi, yi) ) / min( sum(xi), sum(yi)))
 * 
 * @author Adam Sargent
 * @version 1.0
 */

public class HistogramIntersectionDistance extends DistanceFunction {

	@Override
	public String displayName() {
		return "Histogram Intersection Distance";
	}

	@Override
	public String tooltipText() {
		return "Histogram Intersection Distance";
	}

	@Override
	public boolean showInGUI() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double distance(EventSet unknownEventSet, EventSet knownEventSet)
			throws DistanceCalculationException{
		EventHistogram unknownHistogram = new EventHistogram(unknownEventSet);
		EventHistogram knownHistogram = new EventHistogram(knownEventSet);

		Set<Event> events = new HashSet<Event>();
		events.addAll(unknownHistogram.events());
		events.addAll(knownHistogram.events());
		
		double distance = 0.0, sumNumer = 0.0, sumUnknown = 0.0, sumKnown = 0.0;

		for(Event event: events){
			sumNumer += Math.min(unknownHistogram.getRelativeFrequency(event), knownHistogram.getRelativeFrequency(event));
			sumUnknown += unknownHistogram.getRelativeFrequency(event);
			sumKnown += knownHistogram.getRelativeFrequency(event);
		}

		distance = 1 - (sumNumer / Math.min(sumUnknown, sumKnown));
		return distance;
	}

}
