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
