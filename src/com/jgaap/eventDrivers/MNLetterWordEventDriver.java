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
import javax.swing.*;


/**
 * This event set is all "words" (NaiveWordEventDriver) with M <= length <= N (M
 * and N being parameters "M" and "N" respectively)
 * 
 * @since 4.1
 **/
public class MNLetterWordEventDriver extends EventDriver {

	@Override
	public String displayName() {
		return "M--N letter words";
	}

	@Override
	public String tooltipText() {
		return "(Should never be user-visible)";
	}

	@Override
	public boolean showInGUI() {
		return false;
	}

        @Override
        public GroupLayout getGUILayout(JPanel panel){
            return null;
        }

	/** Underlying EventDriver from which Events are drawn. */
	public EventDriver underlyingevents = new NaiveWordEventDriver();

	/** Limits on characters per word */
	public int M = 2;
	public int N = 3;

	@Override
	public EventSet createEventSet(Document ds) {

		// Extract local field values based on parameter settings
		String param;

		// lots of error checking
		if (!(param = (getParameter("N"))).equals("")) {
			try {
				int value = Integer.parseInt(param);
				setN(value);
			} catch (NumberFormatException e) {
				System.out.println("Warning: cannot parse N(upper bound):"
						+ param + " as int");
				System.out.println(" -- Using default value (3)");
				setN(3);
			}
		}
		if (!(param = (getParameter("M"))).equals("")) {
			try {
				int value = Integer.parseInt(param);
				setM(value);
			} catch (NumberFormatException e) {
				System.out.println("Warning: cannot parse M(lower bound):"
						+ param + " as int");
				System.out.println(" -- Using default value (2)");
				setM(2);
			}
		}

		if (!(param = (getParameter("underlyingEvents"))).equals("")) {
			try {
				/*
				 * TODO: If ever use Event Sets that are not part of
				 * com.jgaap.eventSets, this will need to be changed. You can
				 * catch the first exception, try appending com.jgaap.eventSets,
				 * then catch a second exception if even that doesn't work, but
				 * since all our eventSets are in one place right now, I didn't
				 * do it that way -- JN 04/26/09
				 */
				Object o = Class.forName("com.jgaap.eventDrivers." + param)
						.newInstance();
				if (o instanceof EventDriver) {
					setEvents((EventDriver) o);
				} else {
					throw new ClassCastException();
				}
			} catch (Exception e) {
				// System.out.println("Error: cannot create EventDriver " +
				// param);
				// System.out.println(" -- Using NaiveWordEventDriver");
				setEvents(new NaiveWordEventDriver());
			}
		}
		EventSet es = underlyingevents.createEventSet(ds);
		EventSet newEs = new EventSet();
		newEs.setAuthor(es.getAuthor());
		newEs.setNewEventSetID(es.getAuthor());
		String s;

		/* Negative upper bounds mean no upper bound */
		if (N < 0)
			N = Integer.MAX_VALUE;

		/**
		 * Check length of each event and accept if in range
		 */
		for (Event e : es) {
			s = e.toString();
			if (s.length() >= M && s.length() <= N) {
				// should we clone event before adding? PMJ
				newEs.addEvent(e);
			}
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

	/* Parameter settings */
	/**
	 * Get N (upper bound)
	 * 
	 * @return length upper bound
	 */
	public int getN() {
		return N;
	};

	/* Parameter settings */
	/**
	 * Get M (lower bound)
	 * 
	 * @return length lower bound
	 */
	public int getM() {
		return M;
	};

	/**
	 * Set EventDriver for relevant Events *
	 * 
	 * @param underlyingevents
	 *            underlying EventDriver
	 */
	public void setEvents(EventDriver underlyingevents) {
		this.underlyingevents = underlyingevents;
	}

	/**
	 * Set upper bound on length
	 * 
	 * @param N
	 *            length upper bound
	 */
	public void setN(int N) {
		this.N = N;
	}

	/**
	 * Set lower bound on length
	 * 
	 * @param M
	 *            length lower bound
	 */
	public void setM(int M) {
		this.M = M;
	}
}
