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

public class DisLegomenaEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.DisLegomenaEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
	 */

	@Test
	    public void testCreateEventSetDocumentSet(){

	    /*Note: Default underlying event set is NaiveWordEventDriver*/
	    /*DisLegomenaEventDriver - Returns only events that occur twice*/
	    /*Test One - Normal Text w/ one event to return*/

	    Document doc = new Document();
	    doc.readStringText("The Quick Brown Fox Jumped Over The Lazy Dog 3 3 3 4 4 4 4 5 5 5 5 5");

	    EventSet sampleSet = new DisLegomenaEventDriver().createEventSet(doc);
	    
	    EventSet expectedSet = new EventSet();
	    Vector<Event> tmp = new Vector<Event>();
	    tmp.add(new Event("The"));
	    tmp.add(new Event("The"));
	     expectedSet.addEvents(tmp);
	     
		    assertTrue(expectedSet.equals(sampleSet));
		    }
}




