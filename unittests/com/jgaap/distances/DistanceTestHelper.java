// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
package com.jgaap.distances;

public class DistanceTestHelper {

	/*
	 * because computers are very bad at storing floating point numbers when
	 * working with doubles you should rarely use == this allows for a margin
	 * of error (range) 
	 */
	public static Boolean inRange(double value, double target, double range) {
		if (value <= target + range) {
			if (value >= target - range) {
				return true;
			}
		}
		return false;
	}

}
