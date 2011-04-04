// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 * 
 */
package com.jgaap.eventDrivers;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;

/**
 * @author Joshua Booth
 *
 */

public class HapaxLegomenaEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.HapaxLegomenaEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
	 */

	@Test
	    public void testCreateEventSetDocumentSet(){

	    /*Note: Default underlying event set is NaiveWordEventDriver*/
	    /*HapaxLegomenaEventDriver - Returns only events that occurs once*/
	    /*Test One - Normal Text w/ one event to return*/

	    Document doc = new Document();
	    doc.readStringText("The Quick Brown Fox Jumped Over The Lazy Dog 3 3 3 4 4 4 4 5 5 5 5 5");

	    EventSet sampleSet = new HapaxLegomenaEventDriver().createEventSet(doc);
	    
	    EventSet expectedSet = new EventSet();
	    Vector<Event> tmp = new Vector<Event>();
	    tmp.add(new Event("Quick"));
	    tmp.add(new Event("Brown"));
	    tmp.add(new Event("Fox"));
	    tmp.add(new Event("Jumped"));
	    tmp.add(new Event("Over"));
	    tmp.add(new Event("Lazy"));
	    tmp.add(new Event("Dog"));

	     expectedSet.addEvents(tmp);
	     
		    assertTrue(expectedSet.equals(sampleSet));
		    }
}




