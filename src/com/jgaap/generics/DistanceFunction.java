/*
 * JGAAP -- a graphical program for stylometric authorship attribution
 * Copyright (C) 2009,2011 by Patrick Juola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 **/
package com.jgaap.generics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jgaap.backend.AutoPopulate;

/**
 * This is an abstract class for distance functions. It is used, for example, in
 * variations of nearest-neighbor algorithms.
 * 
 */
abstract public class DistanceFunction extends Parameterizable implements Comparable<DistanceFunction>, Displayable{

	private static List<DistanceFunction> DISTANCE_FUNCTIONS;
	
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
    abstract public double distance(EventMap eventMap1, EventMap eventMap2) throws DistanceCalculationException;


    public int compareTo(DistanceFunction o){
    	return displayName().compareTo(o.displayName());
    }
    
	/**
	 * A read-only list of the DistanceFunctions
	 */
	public static List<DistanceFunction> getDistanceFunctions() {
		if(DISTANCE_FUNCTIONS == null){
			DISTANCE_FUNCTIONS = Collections.unmodifiableList(loadDistanceFunctions());
		}
		return DISTANCE_FUNCTIONS;
	}

	private static List<DistanceFunction> loadDistanceFunctions() {
		List<Object> objects = AutoPopulate.findObjects("com.jgaap.distances", DistanceFunction.class);
		for(Object tmp : AutoPopulate.findClasses("com.jgaap.generics", DistanceFunction.class)){
			objects.addAll(AutoPopulate.findObjects("com.jgaap.distances", (Class<?>)tmp));
		}
		List<DistanceFunction> distances = new ArrayList<DistanceFunction>(objects.size());
		for (Object tmpD : objects) {
			distances.add((DistanceFunction) tmpD);
		}
		Collections.sort(distances);
		return distances;
	}

}
