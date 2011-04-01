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
 * This event set is all events occurring only once of an underlying event model
 * * (parameterized as underlyingevents)
 * 
 * @author Patrick Juola
 * @since 5.0
 **/
public class RareWordsEventDriver extends EventDriver {

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
        public GroupLayout getGUILayout(JPanel panel){
            return null;
        }

	/** Underlying EventDriver from which Events are drawn. */
	public EventDriver underlyingevents = new NaiveWordEventDriver();
	public int M,N;

	@Override
	public EventSet createEventSet(Document ds) {

		String param;
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
				System.out.println("Error: cannot create EventDriver " + param);
				System.out.println(" -- Using NaiveWordEventDriver");
				setEvents(new NaiveWordEventDriver());
			}
		}

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
		EventSet es = underlyingevents.createEventSet(ds);
		EventSet newEs = new EventSet();
		newEs.setAuthor(es.getAuthor());
		newEs.setNewEventSetID(es.getAuthor());

		/**
		 * Create histogram with all events from stream
		 */
		EventHistogram hist = new EventHistogram();
		for (int i = 0; i < es.size(); i++) {
			hist.add(es.eventAt(i));
		}

		/**
		 * Re-search event stream for rare events as measured by histogram
		 * count. If count is 1, it's a hapax, etc. 
		 */
		System.out.println("M = " + M + "; N = " + N);
		for (Event e : es) {
			int n = hist.getAbsoluteFrequency(e);
			System.out.println(e.toString() + " " + n);
			if (n >= M && n <= N)
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

	public int getM() {
		return M;
	}
	public void setM(int M) {
		this.M = M;
	}

	public int getN() {
		return N;
	}
	public void setN(int N) {
		this.N = N;
	}

}
