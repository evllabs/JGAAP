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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jgaap.generics.DistanceFunction;
/**
 * @author Michael Ryan
 * @since 5.0.0
 */

public class DistanceFunctionFactory {

	private static final Map<String, DistanceFunction> distanceFunctions = loadDistanceFunctions();
	
	private static Map<String, DistanceFunction> loadDistanceFunctions() {
		// Load the distance functions dynamically
		Map<String, DistanceFunction>distanceFunctions = new HashMap<String, DistanceFunction>();
		for(DistanceFunction distanceFunction: AutoPopulate.getDistanceFunctions()){
			distanceFunctions.put(distanceFunction.displayName().toLowerCase().trim(), distanceFunction);
		}
		return distanceFunctions;
	}
	
	public static DistanceFunction getDistanceFunction(String action) throws Exception{
		DistanceFunction distanceFunction;
		List<String[]> parameters = Utils.getParameters(action);
		action = parameters.remove(0)[0].toLowerCase().trim();
		if(distanceFunctions.containsKey(action)){
			distanceFunction= distanceFunctions.get(action).getClass().newInstance();
		}else{
			throw new Exception("Distance Function "+action+" was not found!");
		}
		for(String[] parameter : parameters){
			distanceFunction.setParameter(parameter[0].trim(), parameter[1].trim());
		}
		return distanceFunction;
	}
	
}
