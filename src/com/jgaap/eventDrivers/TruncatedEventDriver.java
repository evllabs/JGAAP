// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
package com.jgaap.eventDrivers;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.NumericEventDriver;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.NumericEventSet;
import com.jgaap.jgaapConstants;
import javax.swing.*;


/**
 * Truncates Events to shorter strings -- i.e. "hello" becomes "he" Useful for
 * binning NumericEventSets among other things
 */
public class TruncatedEventDriver extends EventDriver {

	@Override
	public String displayName() {
		return "Truncated Events";
	}

	@Override
	public String tooltipText() {
		return "First (length) characters of otherwise defined Events";
	}

	@Override
	public boolean showInGUI() {
		return false;
	}

	private EventDriver underlyingEvents;
	private int length;

	@Override
	public EventSet createEventSet(Document ds) {
		String param;

		if (!(param = (getParameter("underlyingEvents"))).equals("")) {
			try {
				Object o = Class.forName(
						jgaapConstants.JGAAP_EVENTDRIVERPREFIX + param)
						.newInstance();
				if (o instanceof NumericEventDriver) {
					underlyingEvents = (NumericEventDriver) o;
				} else if (o instanceof EventDriver) {
					underlyingEvents = (EventDriver) o;
				} else {
					throw new ClassCastException();
				}
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
		} else { // no underlyingfilename,
			length = 2;
		}

		EventSet es = underlyingEvents.createEventSet(ds);
		EventSet newEs;

		// preserver "numeric"-ness
		if (es instanceof NumericEventSet)
			newEs = new NumericEventSet();
		else
			newEs = new EventSet();

		newEs.setAuthor(es.getAuthor());
		newEs.setNewEventSetID(es.getAuthor());

		for (int i = 0; i < es.size(); i++) {
			String s = (es.eventAt(i)).toString();

			if (s.length() > length) {
				try {
					newEs.addEvent(new Event(s.substring(0, length)));
				} catch (Exception e) {
					System.out.println("Error in truncating " + s);
					newEs.addEvent(new Event(s));
				}
			} else
				newEs.addEvent(new Event(s));

		}
		return newEs;
	}

}
