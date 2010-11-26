package com.jgaap.generics;

public abstract class NeighborAnalysisDriver extends AnalysisDriver {

	public abstract void setDistance(DistanceFunction distance);
	
	public abstract DistanceFunction getDistanceFunction();

}
