/**
 *   JGAAP -- Java Graphical Authorship Attribution Program
 *   Copyright (C) 2009 Patrick Juola
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation under version 3 of the License.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
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
            for(int j = 0; j < Math.round(v1.elementAt(i)); j++) {
                es1.addEvent(e);
            }
        }

        for(Integer i = 0; i < max; i++) {
            Event e = new Event(i.toString());
            for(int j = 0; j < Math.round(v2.elementAt(i)); j++) {
                es2.addEvent(e);
            }
        }

        return distance(es1, es2);

    }

    public int compareTo(DistanceFunction o){
    	return displayName().compareTo(o.displayName());
    }

}
