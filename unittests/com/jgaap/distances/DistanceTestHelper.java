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
