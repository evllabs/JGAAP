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

import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;


/**
 * Calculates N (parameter) character suffix of Events, useful for extracting
 * English suffixes like "-tion" or "-er" or "-est."   Of course, it also works
 * on other languages.
 * @author Patrick Juola
 * @since 5.0
 */
public class SuffixEventDriver extends EventDriver {

	public SuffixEventDriver() {
		addParams("length", "Length", "3", new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}, false);
		addParams("minimumlength", "Minimum Length", "5", new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}, false);
	}
	
	@Override
	public String displayName() {
		return "Suffices";
	}

	@Override
	public String tooltipText() {
		return "Last (default=3) characters of Events (default=words)";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	private NaiveWordEventDriver wordEventDriver = new NaiveWordEventDriver();

	@Override
	public EventSet createEventSet(char[] text) throws EventGenerationException {
		int length = getParameter("length", 3);
		int minimumlength = getParameter("mimimnmlength", 5);

		EventSet es = wordEventDriver.createEventSet(text);
		EventSet newEs = new EventSet();

		for (int i = 0; i < es.size(); i++) {
			String s = (es.eventAt(i)).toString();

			if (s.length() >= minimumlength) {
				try {
					newEs.addEvent(new Event(s.substring(s.length()-length), this));
				} catch (Exception e) {
					System.out.println("Error in truncating " + s);
				}
			} 
			// do not add if less than minimum length

		}
		return newEs;
	}

}
