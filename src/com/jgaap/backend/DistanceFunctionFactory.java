// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
package com.jgaap.backend;

import java.util.HashMap;
import java.util.Map;

import com.jgaap.generics.DistanceFunction;
/**
 * @author Michael Ryan
 * @since 5.0.0
 */

public class DistanceFunctionFactory {

	private Map<String, DistanceFunction> distanceFunctions;
	
	public DistanceFunctionFactory() {
		// Load the distance functions dynamically
		distanceFunctions = new HashMap<String, DistanceFunction>();
		for(DistanceFunction distanceFunction: AutoPopulate.getDistanceFunctions()){
			distanceFunctions.put(distanceFunction.displayName().toLowerCase().trim(), distanceFunction);
		}
	}
	
	public DistanceFunction getDistanceFunction(String action) throws Exception{
		DistanceFunction distanceFunction;
		action = action.toLowerCase().trim();
		if(distanceFunctions.containsKey(action)){
			distanceFunction= distanceFunctions.get(action).getClass().newInstance();
		}else{
			throw new Exception("Distance Function "+action+" was not found!");
		}
		return distanceFunction;
	}
	
}
