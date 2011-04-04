// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
package com.jgaap.generics;

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
