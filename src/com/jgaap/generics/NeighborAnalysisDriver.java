// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
package com.jgaap.generics;
/**
 * @author Michael Ryan
 * @since 5.0.0
 */

public abstract class NeighborAnalysisDriver extends AnalysisDriver {

	public DistanceFunction distance;
	
	public void setDistance(DistanceFunction distance){
		this.distance = distance;
	}
	
	public DistanceFunction getDistanceFunction(){
		return distance;
	}
	
	public String getDistanceName(){
		String result ="";
		if(distance!=null){
			result = " with metric "+distance.displayName();
		}
		return result;
	}

}
