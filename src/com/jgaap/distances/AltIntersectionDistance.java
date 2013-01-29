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
package com.jgaap.distances;

import com.google.common.collect.Sets;
import com.jgaap.generics.*;

/**
 * Given two event type-sets, A,B, calculate 1 - ||A intersect B|| // ||A union B||
 * 
 * @author Juola
 * @version 4.1
 */
public class AltIntersectionDistance extends DistanceFunction{
	public String displayName() {
		return "Alt Intersection Distance";
	}

	public String tooltipText() {
		return "One over Event type set intersection plus one";
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
	public double distance(EventMap unknownEventMap, EventMap knownEventMap) {
		double intersectioncount = Sets.intersection(unknownEventMap.uniqueEvents(), knownEventMap.uniqueEvents()).size();
		return 1/(intersectioncount+1);
	}
}
