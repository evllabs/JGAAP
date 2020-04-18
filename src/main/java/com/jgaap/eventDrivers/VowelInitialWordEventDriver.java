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
 * This event set is all "words" (NaiveWordEventDriver) beginning with vowels
 * "aeiouAEIOU"; extension may be necessary to include non-English vowels or
 * characters with diacritical marks like Danish ae digraph or German o-umlaut
 * 
 * @since 4.1
 **/
public class VowelInitialWordEventDriver extends NaiveWordEventDriver {

	@Override
	public String displayName() {
		return "Vowel-initial Words";
	}

	@Override
	public String tooltipText() {
		return "Words beginning with A, E, I, O, U (or lowercase equivalent)";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	private static String vowels = "aeiouyAEIOUY";

	@Override
	public EventSet createEventSet(char[] text) {
		EventSet es = super.createEventSet(text);
		EventSet newEs = new EventSet();

		/**
		 * Check initial leter of each event and accept if vowel
		 */
		for (Event e : es) {
			String s = e.toString();
			if (vowels.indexOf(s.charAt(0)) != -1)
				newEs.addEvent(e);
		}
		return newEs;
	}

}
