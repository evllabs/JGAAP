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

import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventSet;

/**
 * Extract vowel-initial words with between M and N letters as features
 * 
 * @author Patrick Juola
 * @since 5.0
 * 
 */
public class VowelMNLetterWordEventDriver extends MNLetterWordEventDriver {

	public VowelMNLetterWordEventDriver() {
		addParams("M", "M", "2", new String[] { "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
				"18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
				"28", "29", "30", "31", "32", "33", "34", "35", "36", "37",
				"38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
				"48", "49", "50" }, false);
		addParams("N", "N", "3", new String[] { "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
				"18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
				"28", "29", "30", "31", "32", "33", "34", "35", "36", "37",
				"38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
				"48", "49", "50" }, false);
	}

	@Override
	public String displayName() {
		return "Vowel M--N letter Words";
	}

	@Override
	public String tooltipText() {
		return "Vowel-initial Words with between M and N letters";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	private MNLetterWordEventDriver theDriver = new MNLetterWordEventDriver();

	@Override
	public EventSet createEventSet(char[] text) throws EventGenerationException {
		theDriver.setParameter("underlyingevents", "Vowel-initial Words");
		String temp = this.getParameter("M");
		if (temp.equals("")) {
			this.setParameter("M", 2);
		}
		theDriver.setParameter("M", this.getParameter("M"));
		temp = this.getParameter("N");
		if (temp.equals("")) {
			this.setParameter("N", 3);
		}
		theDriver.setParameter("N", this.getParameter("N"));
		return theDriver.createEventSet(text);
	}
}
