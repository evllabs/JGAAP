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
package com.jgaap.generics;

import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

/**
 * Skip Gram Generic Event Driver
 * 
 * @author David Berdik
 */

public abstract class KSkipNGramEventDriver extends EventDriver {
	public KSkipNGramEventDriver() {
		addParams("K", "K", "1", new String[] { "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
				"18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
				"28", "29", "30", "31", "32", "33", "34", "35", "36", "37",
				"38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
				"48", "49", "50" }, false);
		
		addParams("N", "N", "2", new String[] { "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
				"18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
				"28", "29", "30", "31", "32", "33", "34", "35", "36", "37",
				"38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
				"48", "49", "50" }, false);
	}
	
	protected EventSet transformToKSkipNGram(EventSet eventSet) {
		// Using the entries in "eventSet", create a new event set of k-skip-n-grams.
		int k = getParameter("k", 1);
		int n = getParameter("n", 2);
		EventSet kSkipNGramEventSet = new EventSet();
		
		// Iterate through "eventSet" until we reach a point where attempting to complete
		// a skip gram would result in an ArrayIndexOutOfBoundsException.
		for(int x = 0; x + (k + 1) * (n - 1) < eventSet.size(); x++) {
			int gramTracker = x;
			String gramBuilder = "";
			
			for(int y = 0; y < n; y++) {
				// The inner loop is used for adding events to each gram as well as for keeping
				// track of how many have been added.
				gramBuilder += eventSet.eventAt(gramTracker).toString() + " ";
				gramTracker += k + 1;
			}
			
			kSkipNGramEventSet.addEvent(new Event(gramBuilder.toString().trim(), this));
		}		
		return kSkipNGramEventSet;
	}
}
