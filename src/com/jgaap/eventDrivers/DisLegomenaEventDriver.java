/**
 *   JGAAP -- Java Graphical Authorship Attribution Program
 *   Copyright (C) 2009 Patrick Juola
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation under version 3 of the License.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/
package com.jgaap.eventDrivers;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.EventHistogram;
import javax.swing.*;


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
	public boolean showInGUI() {
		return false;
	}

        @Override
        public GroupLayout getGUILayout(JPanel panel){
            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
            return layout;
        }

	/** Underlying EventDriver from which Events are drawn. */
	public EventDriver underlyingevents = new NaiveWordEventDriver();

	@Override
	public EventSet createEventSet(Document ds) {

		String param;
		if (!(param = (getParameter("underlyingEvents"))).equals("")) {
			try {
				/*
				 * TODO: If ever use Event Driver that are not part of
				 * com.jgaap.eventDriver, this will need to be changed. You can
				 * catch the first exception, try appending
				 * com.jgaap.eventDriver, then catch a second exception if even
				 * that doesn't work, but since all our eventDriver are in one
				 * place right now, I didn't do it that way -- JN 04/26/09
				 */
				Object o = Class.forName("com.jgaap.eventDriver." + param)
						.newInstance();
				if (o instanceof EventDriver) {
					setEvents((EventDriver) o);
				} else {
					throw new ClassCastException();
				}
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
