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
import com.jgaap.generics.EventDriver;

/**
 * @author Patrick Juola
 *
 */

public class RareWordsEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.HDLegomenaEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
	 */

	@Test
	    public void testCreateEventSetDocumentSet(){

	    /*Note: Default underlying event set is NaiveWordEventDriver*/
	    /*HDLegomenaEventDriver - Returns only events that occurs once or twice*/
	    /*Test One - Normal Text w/ one or two events to return*/

	    Document doc = new Document();
	    doc.readStringText(
"one " +
"two two  " +
"three three three " +
"four four four four " +
"five five five five five " +
"six six six six six six"
 );
	    EventDriver ed  = new RareWordsEventDriver();
	    ed.setParameter("M","4");
	    ed.setParameter("N","5");
	    EventSet sampleSet = ed.createEventSet(doc);    

	    EventSet expectedSet = new EventSet();
	    Vector<Event> tmp = new Vector<Event>();
	    tmp.add(new Event("four"));
	    tmp.add(new Event("four"));
	    tmp.add(new Event("four"));
	    tmp.add(new Event("four"));
	    tmp.add(new Event("five"));
	    tmp.add(new Event("five"));
	    tmp.add(new Event("five"));
	    tmp.add(new Event("five"));
	    tmp.add(new Event("five"));

	    expectedSet.addEvents(tmp);     
	    System.out.println(sampleSet.toString());
	    assertTrue(expectedSet.equals(sampleSet));
	    }
}




