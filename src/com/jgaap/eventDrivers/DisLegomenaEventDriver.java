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
import com.jgaap.generics.EventHistogram;


/**
 * This event set is all events occurring only twice of an underlying event
 * model (parameterized as underlyingevents)
 * 
 * @author Patrick Juola
 * @since 4.1
 **/
public class DisLegomenaEventDriver extends EventDriver {

	@Override
	public String displayName() {
		return "Dis Legomena";
	}

	@Override
	public String tooltipText() {
		return "Words appearing only twice per document";
	}

	@Override
	public String longDescription() {
		return "Words appearing only twice per document";
	}


	@Override
	public boolean showInGUI() {
		return false;
	}

	/** Underlying EventDriver from which Events are drawn. */
	public EventDriver underlyingevents = new NaiveWordEventDriver();

	@Override
	public EventSet createEventSet(Document ds) {

		String param;
		if (!(param = (getParameter("underlyingEvents"))).equals("")) {
			try {
	            setEvents(EventDriverFactory.getEventDriver(param));

			} catch (Exception e) {
				System.out.println("Error: cannot create EventDriver " + param);
				System.out.println(" -- Using NaiveWordEventDriver");
				setEvents(new NaiveWordEventDriver());
			}
		}
		EventSet es = underlyingevents.createEventSet(ds);
		EventSet newEs = new EventSet();
		newEs.setAuthor(es.getAuthor());
		newEs.setNewEventSetID(es.getAuthor());

		/**
		 * Create histogram will all events from stream
		 */
		EventHistogram hist = new EventHistogram();
		for (int i = 0; i < es.size(); i++) {
			hist.add(es.eventAt(i));
		}

		/**
		 * Re-search event stream for unique events as measured by histogram
		 * count. If count is 1, it's a hapax so add
		 */
		for (Event e : es) {
			if (hist.getAbsoluteFrequency(e) == 2)
				newEs.addEvent(e);
		}
		return newEs;
	}

	/**
	 * Get EventDriver for relevant Events *
	 * 
	 * @return underlying EventDriver
	 */
	public EventDriver getEvents() {
		return underlyingevents;
	}

	/**
	 * Set EventDriver for relevant Events *
	 * 
	 * @param underlyingevents
	 *            underlying EventDriver
	 */
	public void setEvents(EventDriver underlyingevents) {
		this.underlyingevents = underlyingevents;
	}

}
