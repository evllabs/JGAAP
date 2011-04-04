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
 * Given two event type-sets, A,B, calculate 1 - ||A intereset B|| // ||A union
 * B||
 * 
 * @author Juola
 * @version 4.1
 */
public class IntersectionDistance extends DistanceFunction {
	public String displayName() {
		return "Intersection Distance";
	}

	public String tooltipText() {
		return "Event type set intersection divided by event type set union";
	}

	public boolean showInGUI() {
		return true;
	}

	/**
	 * Returns intersection distance between event sets es1 and es2.
	 * 
	 * @param es1
	 *            The first EventSet
	 * @param es2
	 *            The second EventSet
	 * @return the intersection distance between them
	 */

	@Override
	public double distance(EventSet es1, EventSet es2) {
		EventHistogram h1 = new EventHistogram();
		EventHistogram h2 = new EventHistogram();

		long intersectioncount = 0;
		long unioncount;

		for (int i = 0; i < es1.size(); i++) {
			h1.add(es1.eventAt(i));
		}

		for (int i = 0; i < es2.size(); i++) {
			h2.add(es2.eventAt(i));
		}

		unioncount = h1.getNTypes();
		// unioncount now has the number of distinct types in h1

		for (Event event : h2) {
			if (h1.getAbsoluteFrequency(event) == 0) {
				// present in h2, not in h1, so add to union count
				unioncount++;
			} else {
				// present in h2 and in h1
				// already in union (from h1)
				intersectioncount++; // add to intersection

			}
		}
		/*
		 * FOR DEBUGGING ONLY System.out.println("intersection count is " +
		 * intersectioncount); System.out.println("unioncount is " +
		 * unioncount); System.out.println("ratio is " +
		 * (1.0*intersectioncount)/unioncount);
		 * System.out.println("distance is " + (1.0 -
		 * (1.0*intersectioncount)/unioncount) ); System.out.println("");
		 */
		return 1.0 - (1.0 * intersectioncount) / unioncount;
	}

    public double distance(Vector<Double> v1, Vector<Double> v2) {
        double intersectionCount = 0.0;
        double unionCount = v1.size();
        for(int i = 0; i < unionCount; i++) {
            if(v1.get(i) > 0 && v2.get(i) > 0) {
                intersectionCount++;
            }
        }

        return 1.0 - intersectionCount / unionCount;
    }

}
