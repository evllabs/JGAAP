// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
package com.jgaap.distances;

import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;

/**
 * Nominal Kolmogorov-Smirnov distance for Nearest Neighbor algorithm
 */
public class NominalKSDistance extends DistanceFunction {
	public String displayName(){
	    return "KS Distance";
	}

	public String tooltipText(){
	    return "Nominal Kolmogorov-Smirnov distance (also known as the Minkowski L-infinity metric)";
	}

	public boolean showInGUI(){
	    return true;
	}
	/**
	 * Calculate the Kolmogorov-Smirnov distance between two event sets.
	 * @return The nominal KS distance between two event sets.
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
            distance += Math.abs(h1.getRelativeFrequency(event)
                    - h2.getRelativeFrequency(event));
        }

        for (Event event : h2) {
            if (h1.getAbsoluteFrequency(event) == 0) {
                distance += Math.abs(h1.getRelativeFrequency(event)
                        - h2.getRelativeFrequency(event));
            }
        }
        return (1.0 / 2.0) * distance;
    }
}
