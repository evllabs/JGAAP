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
import com.jgaap.util.EventSet;

/**
 * This event set is all "words" (NaiveWordEventDriver) with M <= length <= N (M
 * and N being parameters "M" and "N" respectively)
 * 
 * @since 4.1
 **/
public class MNLetterWordEventDriver extends NaiveWordEventDriver {

	public MNLetterWordEventDriver() {
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
		return "M--N letter Words";
	}

	@Override
	public String tooltipText() {
		return "Words with between M and N letters";
	}

	@Override
	public String longDescription() {
		return "Words with between M and N letters (M and N are given as parameters)";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public EventSet createEventSet(char[] text) {

		// lots of error checking
		int N = getParameter("N", 3);
		/* Negative upper bounds mean no upper bound */
		if (N < 0){
			N = Integer.MAX_VALUE;
		}
		int M = getParameter("M", 2);

		EventSet es = super.createEventSet(text);
		EventSet newEs = new EventSet();
		String s;

		/**
		 * Check length of each event and accept if in range
		 */
		for (Event e : es) {
			s = e.toString();
			if (s.length() >= M && s.length() <= N) {
				newEs.addEvent(e);
			}
		}
		return newEs;
	}
}
