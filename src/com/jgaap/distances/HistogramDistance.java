// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
package com.jgaap.distances;

import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;

/**
 * Histogram distance using L2 metric,(defined as D(x,y) = sum ((xi -yi)^2) This
 * is YA distance for Nearest Neighbor algorithms
 * 
 * @author Juola
 * @version 1.0
 */
public class HistogramDistance extends DistanceFunction {
	public String displayName() {
		return "Histogram Distance";
	}

	public String tooltipText() {
		return "Histogram Distance (also known as Euclidean or L2 Norm)";
	}

	public boolean showInGUI() {
		return true;
	}

	/**
	 * Returns histogram distance between event sets es1 and es2.
	 * 
	 * @param es1
	 *            The first EventSet
	 * @param es2
	 *            The second EventSet
	 * @return the (Euclidean) histogram distance between them
	 */

	@Override
	public double distance(EventSet es1, EventSet es2) {
		EventHistogram h1 = new EventHistogram();
		EventHistogram h2 = new EventHistogram();
		double distance = 0.0;

		for (int i = 0; i < es1.size(); i++) {
			h1.add(es1.eventAt(i));
		}

		for (int i = 0; i < es2.size(); i++) {
			h2.add(es2.eventAt(i));
		}

		for (Event event : h1) {
			distance += Math.pow((h1.getRelativeFrequency(event) - h2
					.getRelativeFrequency(event)), 2);
		}

		for (Event event : h2) {
			if (h1.getAbsoluteFrequency(event) == 0) {
				distance += Math.pow((h1.getRelativeFrequency(event) - h2
						.getRelativeFrequency(event)), 2);
			}
		}
		return distance;
	}
}
