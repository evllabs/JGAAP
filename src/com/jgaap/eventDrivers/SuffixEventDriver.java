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

import com.jgaap.backend.EventDriverFactory;
import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.NumericEventSet;


/**
 * Calculates N (parameter) character suffix of Events, useful for extracting
 * English suffixes like "-tion" or "-er" or "-est."   Of course, it also works
 * on other languages.
 * @author Patrick Juola
 * @since 5.0
 */
public class SuffixEventDriver extends EventDriver {

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

	private EventDriver underlyingEvents;
	private int length;
	private int minimumlength;

	@Override
	public EventSet createEventSet(Document ds) {
		String param;

		if (!(param = (getParameter("underlyingEvents"))).equals("")) {
			try {
				underlyingEvents = EventDriverFactory.getEventDriver(param);
			} catch (Exception e) {
				System.out.println("Error: cannot create EventDriver " + param);
				System.out.println(" -- Using NaiveWordEventSet");
				underlyingEvents = new NaiveWordEventDriver();
			}
		} else { // no underlyingEventsParameter, use NaiveWordEventSet
			underlyingEvents = new NaiveWordEventDriver();
		}

		if (!(param = (getParameter("length"))).equals("")) {
			length = Integer.valueOf(param);
		} else { // no defined length
			length = 3;
		}

		if (!(param = (getParameter("minimumlength"))).equals("")) {
			minimumlength = Integer.valueOf(param);
		} else { // no defined minimum length
			minimumlength = 5;
		}

		EventSet es = underlyingEvents.createEventSet(ds);
		EventSet newEs;

		// preserve "numeric"-ness
		if (es instanceof NumericEventSet)
			newEs = new NumericEventSet();
		else
			newEs = new EventSet();

		newEs.setAuthor(es.getAuthor());
		newEs.setNewEventSetID(es.getAuthor());

		for (int i = 0; i < es.size(); i++) {
			String s = (es.eventAt(i)).toString();

			if (s.length() >= minimumlength) {
				try {
					newEs.addEvent(new Event(s.substring(
						s.length()-length)));
				} catch (Exception e) {
					System.out.println("Error in truncating " + s);
				}
			} 
			// do not add if less than minimum length

		}
		return newEs;
	}

}
