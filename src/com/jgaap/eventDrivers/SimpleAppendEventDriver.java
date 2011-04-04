/**
 **/
package com.jgaap.eventDrivers;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;
import javax.swing.*;


/**
 * Appends two or more underlying EventSets (parameterized as
 * underlyingevents, a comma-separated list of EventDrivers) into
 * one EventSet.
 * WARNING: LOSES TIMING INFORMATION AS THE FIRST EVENT OF THE SECOND DRIVER
 * FOLLOWS THE LAST EVENT OF THE FIRST DRIVER
 * E.g. (1,2,3) and (x,y,z) would join as (1,2,3,x,y,z), not (1,x,2,y,3,z)
 *
 * @author Patrick Juola
 * @since 5.0
 **/
public class SimpleAppendEventDriver extends EventDriver {


    @Override
    public String displayName(){
    	return "Appending Multiple EventDrivers";
    }
    
    @Override
    public String tooltipText(){
    	return "(Should never be user-visible)";
    }
    
    @Override
    public boolean showInGUI(){
    	return false;
    }

    @Override
    public GroupLayout getGUILayout(JPanel panel){
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
        return layout;
    }

  /** Underlying EventSets from which Events are drawn. */
    public EventDriver underlyingevents = new NaiveWordEventDriver();

    /**
     */
    @Override
    public EventSet createEventSet(Document ds) {

        // Extract local field values based on parameter settings
        String param;
	System.out.println("muskmelon");
        if ((param = (getParameter("underlyingEvents"))).equals("")) {
		// Just for testing purposes, we'll use words, lengths,
		// and bigrams
		param =
		 "NaiveWordEventDriver,WordLengthEventDriver,WordBiGramEventDriver";
	}

	System.out.println("Starting processing "+param);

        EventSet es, newEs = new EventSet();
        newEs.setAuthor(ds.getAuthor());
        newEs.setNewEventSetID(ds.getAuthor());
        String s, theEvent;
	String [] set = param.split(",");
	for (int i = 0; i< set.length; i++) {
	    System.out.println("Processing "+set[i]);

            try {
                Object o = Class.forName(
		    "com.jgaap.eventDrivers." + set[i].trim()).newInstance();
                if (o instanceof EventDriver) {
                    underlyingevents = (EventDriver) o;
                } else {
                    throw new ClassCastException();
                }
            } catch (Exception e) {
                System.out.println("Error: cannot create EventDriver " + param);
                System.out.println(" -- Using NaiveWordEventSet");
                setEvents(new NaiveWordEventDriver());
            }
        
            es = underlyingevents.createEventSet(ds);

            for (int j = 0; j < es.size(); j++) 
        	newEs.addEvent( es.eventAt(j) );
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
