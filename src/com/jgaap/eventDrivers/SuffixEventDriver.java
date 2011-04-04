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

        @Override
        public GroupLayout getGUILayout(JPanel panel){
            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
            return layout;
        }

	private EventDriver underlyingEvents;
	private int length;
	private int minimumlength;

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
