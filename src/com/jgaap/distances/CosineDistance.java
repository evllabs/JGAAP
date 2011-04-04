// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
package com.jgaap.distances;

import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;

import java.util.Vector;

/**
 * Cosine Distance or normalized dot product. This is YA distance for Nearest
 * Neighbor algorithms, based on John's research at JHU. NOTE: The cosine
 * distance was modified slightly as we need to make it nonnegative and we want
 * smaller distances to imply similarity.
 * 
 * @author Noecker
 * @version 1.0
 */
public class CosineDistance extends DistanceFunction {
 	public String displayName(){
	    return "Cosine Distance";
	}

	public String tooltipText(){
	    return "Normalized Dot-Product Nearest Neighbor Classifier";
	}

	public boolean showInGUI(){
	    return true;
	}
	/**
     * Returns cosine distance between event sets es1 and es2
     * 
     * @param es1
     *            The first EventSet
     * @param es2
     *            The second EventSet
     * @return the cosine distance between them
     */
    @Override
    public double distance(EventSet es1, EventSet es2) {
        EventHistogram h1 = new EventHistogram();
        EventHistogram h2 = new EventHistogram();
        double distance = 0.0;
        double h1Magnitude = 0.0;
        double h2Magnitude = 0.0;

        for (int i = 0; i < es1.size(); i++) {
            h1.add(es1.eventAt(i));
        }

        for (int i = 0; i < es2.size(); i++) {
            h2.add(es2.eventAt(i));
        }

        for (Event event: h1) {
            distance += (h1.getAbsoluteFrequency(event) * h2
                    .getAbsoluteFrequency(event));

            h1Magnitude += h1.getAbsoluteFrequency(event)
                    * h1.getAbsoluteFrequency(event);
            h2Magnitude += h2.getAbsoluteFrequency(event)
                    * h2.getAbsoluteFrequency(event);
        }

        for (Event event : h2) {
            if (h1.getAbsoluteFrequency(event) == 0) {
                distance += (h1.getAbsoluteFrequency(event) * h2
                        .getAbsoluteFrequency(event));

                h1Magnitude += h1.getAbsoluteFrequency(event)
                        * h1.getAbsoluteFrequency(event);
                h2Magnitude += h2.getAbsoluteFrequency(event)
                        * h2.getAbsoluteFrequency(event);
            }
        }

        return Math.abs((distance / (Math.sqrt(h1Magnitude) * Math
                .sqrt(h2Magnitude))) - 1);
    }

    public double distance(Vector<Double> v1, Vector<Double> v2) {
        int max = 0;
        double distance = 0.0;
        double h1Magnitude = 0.0;
        double h2Magnitude = 0.0;

        if(v1.size() > v2.size()) {
            max = v1.size();
        }
        else {
            max = v2.size();
        }

        for(int i = 0; i < max; i++) {
            distance += (v1.elementAt(i) * v2.elementAt(i));
            h1Magnitude += (v1.elementAt(i) * v1.elementAt(i));
            h2Magnitude += (v2.elementAt(i) * v2.elementAt(i));
        }

        return Math.abs((distance / (Math.sqrt(h1Magnitude) * Math
                .sqrt(h2Magnitude))) - 1);
    }
}
