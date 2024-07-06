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

import com.jgaap.util.Histogram;

/**
 * This is an abstract class for distance functions. It is used, for example, in
 * variations of nearest-neighbor algorithms.
 * 
 */
abstract public class DistanceFunction extends Parameterizable implements Comparable<DistanceFunction>, Displayable{

    public String longDescription() { return tooltipText(); }

    /**
     * Returns (as double) the distance between two EventSets.
     * 
     * @param eventMap1
     *            The first EventSet
     * @param eventMap2
     *            The first EventSet
     * @return a double precision number with distance like behavior between eventMap1 and eventMap2
     */
    abstract public double distance(Histogram histogram1, Histogram histogram2) throws DistanceCalculationException;


    public int compareTo(DistanceFunction o){
    	return displayName().compareTo(o.displayName());
    }

}
