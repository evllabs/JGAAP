// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
package com.jgaap.generics;

import java.util.Vector;

/**
 * This is an abstract class for distance functions. It is used, for example, in
 * variations of nearest-neighbor algorihms.
 * 
 */
abstract public class DistanceFunction extends Parameterizable implements Comparable<DistanceFunction>, Displayable{

	public abstract String displayName();
	

	public abstract String tooltipText();

        public String longDescription() { return tooltipText(); }



	public abstract boolean showInGUI();

    /**
     * Returns (as double) the distance between two EventSets.
     * 
     * @param es1
     *            The first EventSet
     * @param es2
     *            The first EventSet
     * @return a double precision number (ideally a distance) btw es1 and es2
     */
    abstract public double distance(EventSet es1, EventSet es2);

    public double distance(Vector<Double> v1, Vector<Double> v2) {
        EventSet es1 = new EventSet();
        EventSet es2 = new EventSet();
        int max = 0;

        if(v1.size() > v2.size()) {
            max = v1.size();
        }
        else {
            max = v2.size();
        }

        for(Integer i = 0; i < max; i++) {
            Event e = new Event(i.toString());
            for(int j = 0; j < Math.round(1000 * v1.elementAt(i)); j++) {
                es1.addEvent(e);
            }
        }

        for(Integer i = 0; i < max; i++) {
            Event e = new Event(i.toString());
            for(int j = 0; j < Math.round(1000 * v2.elementAt(i)); j++) {
                es2.addEvent(e);
            }
        }

        return distance(es1, es2);

    }

    public int compareTo(DistanceFunction o){
    	return displayName().compareTo(o.displayName());
    }

}
