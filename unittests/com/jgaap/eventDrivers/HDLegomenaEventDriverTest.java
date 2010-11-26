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
 * @author Chris
 *
 */

public class HDLegomenaEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.HDLegomenaEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
	 */

	@Test
	    public void testCreateEventSetDocumentSet(){

	    /*Note: Default underlying event set is NaiveWordEventDriver*/
	    /*HDLegomenaEventDriver - Returns only events that occurs once or twice*/
	    /*Test One - Normal Text w/ one or two events to return*/

	    Document doc = new Document();
	    doc.readStringText("Jack be nimble, Jack be quick, Jack jump over the candlestick.");
	    EventSet sampleSet = new HDLegomenaEventDriver().createEventSet(doc);    
	    EventSet expectedSet = new EventSet();
	    Vector<Event> tmp = new Vector<Event>();
	    tmp.add(new Event("be"));
	    tmp.add(new Event("nimble,"));
	    tmp.add(new Event("be"));
	    tmp.add(new Event("quick,"));
	    tmp.add(new Event("jump"));
	    tmp.add(new Event("over"));
	    tmp.add(new Event("the"));
	    tmp.add(new Event("candlestick."));
	    expectedSet.addEvents(tmp);     
	    assertTrue(expectedSet.equals(sampleSet));
	    }
}




