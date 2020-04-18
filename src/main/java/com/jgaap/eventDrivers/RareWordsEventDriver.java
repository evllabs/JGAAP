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
package com.jgaap.eventDrivers;

import com.jgaap.util.Event;
import com.jgaap.util.EventHistogram;
import com.jgaap.util.EventSet;

/**
 * This event set is all events occurring only once of an underlying event model
 * * (parameterized as underlyingevents)
 * 
 * @author Patrick Juola
 * @since 5.0
 **/
public class RareWordsEventDriver extends NaiveWordEventDriver {

	public RareWordsEventDriver() {
		addParams("M", "M", "1", new String[] { "1", "2", "3", "4", "5", "6",
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

	@Override
	public String displayName() {
		return "Rare Words";
	}

	@Override
	public String tooltipText() {
		return "Rare words such as Words appearing only once or twice per document";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public EventSet createEventSet(char[] text) {
		int N = getParameter("N", 3);
		int M = getParameter("M", 2);

		EventSet es = super.createEventSet(text);
		EventSet newEs = new EventSet();

		/**
		 * Create histogram with all events from stream
		 */
		EventHistogram hist = new EventHistogram(es);

		/**
		 * Re-search event stream for rare events as measured by histogram
		 * count. If count is 1, it's a hapax, etc.
		 */
		System.out.println("M = " + M + "; N = " + N);
		for (Event e : es) {
			int n = hist.getAbsoluteFrequency(e);
			//System.out.println(e.toString() + " " + n);
			if (n >= M && n <= N)
				newEs.addEvent(e);
		}
		return newEs;
	}
}
