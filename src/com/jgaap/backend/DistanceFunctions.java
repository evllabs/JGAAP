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
package com.jgaap.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.jgaap.generics.DistanceFunction;
/**
 * 
 * Instances new Distance Functions based on display name.
 * If parameters are also passed in the form DisplayName|name:value|name:value they are added to the Distance Function before it is returned
 * 
 * @author Michael Ryan
 * @since 5.0.0
 */

public class DistanceFunctions {

	private static final ImmutableList<DistanceFunction> DISTANCE_FUNCTIONS = loadDistanceFunctions();
	private static final ImmutableMap<String, DistanceFunction> distanceFunctions = loadDistanceFunctionsMap();
	
	/**
	 * A read-only list of the DistanceFunctions
	 */
	public static List<DistanceFunction> getDistanceFunctions() {
		return DISTANCE_FUNCTIONS;
	}

	private static ImmutableList<DistanceFunction> loadDistanceFunctions() {
		List<Object> objects = AutoPopulate.findObjects("com.jgaap.distances", DistanceFunction.class);
		for(Object tmp : AutoPopulate.findClasses("com.jgaap.generics", DistanceFunction.class)){
			objects.addAll(AutoPopulate.findObjects("com.jgaap.distances", (Class<?>)tmp));
		}
		List<DistanceFunction> distances = new ArrayList<DistanceFunction>(objects.size());
		for (Object tmpD : objects) {
			distances.add((DistanceFunction) tmpD);
		}
		Collections.sort(distances);
		return ImmutableList.copyOf(distances);
	}
	
	private static ImmutableMap<String, DistanceFunction> loadDistanceFunctionsMap() {
		// Load the distance functions dynamically
		ImmutableMap.Builder<String, DistanceFunction> builder = ImmutableMap.builder();
		for(DistanceFunction distanceFunction: DISTANCE_FUNCTIONS){
			builder.put(distanceFunction.displayName().toLowerCase().trim(), distanceFunction);
		}
		return builder.build();
	}
	
	public static DistanceFunction getDistanceFunction(String action) throws Exception{
		DistanceFunction distanceFunction;
		String[] tmp = action.split("\\|", 2);
		action = tmp[0].trim().toLowerCase();
		if(distanceFunctions.containsKey(action)){
			distanceFunction= distanceFunctions.get(action).getClass().newInstance();
		}else{
			throw new Exception("Distance Function "+action+" was not found!");
		}
		if(tmp.length > 1) {
			distanceFunction.setParameters(tmp[1]);
		}
		return distanceFunction;
	}
	
}
