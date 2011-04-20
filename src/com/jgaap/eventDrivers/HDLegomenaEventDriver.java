// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
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
 * @since 4.1
 **/
public class HDLegomenaEventDriver extends EventDriver {

	@Override
	public String displayName() {
		return "Hapax/Dis Legomena";
	}

	@Override
	public String tooltipText() {
		return "Words appearing only once or twice per document";
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
		 * Re-search event stream for unique events as measured by histogram
		 * count. If count is 1, it's a hapax so add
		 */
		for (Event e : es) {
			int n = hist.getAbsoluteFrequency(e);
			if (n == 1 || n == 2)
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
