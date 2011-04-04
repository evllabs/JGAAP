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
 * @author Patrick Juola
 *
 */
public class NaiveWordEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.NaiveWordEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
	 */
	@Test
	public void testCreateEventSetDocumentSet() {
		/* test case 1 -- no punctuation */
		Document doc = new Document();
		doc.readStringText(
"We hold these truths to be self-evident,\n"+
"\"My phone # is 867-5309; don't forget it!\" she said.\n"+
"\t\t\"I won't,\" \t he grumbled.\n"

		);

		EventSet sampleEventSet = new NaiveWordEventDriver().createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();

		tmp.add(new Event("We"));
		tmp.add(new Event("hold"));
		tmp.add(new Event("these"));
		tmp.add(new Event("truths"));
		tmp.add(new Event("to"));
		tmp.add(new Event("be"));
		tmp.add(new Event("self-evident,"));
		tmp.add(new Event("\"My"));
		tmp.add(new Event("phone"));
		tmp.add(new Event("#"));
		tmp.add(new Event("is"));
		tmp.add(new Event("867-5309;"));
		tmp.add(new Event("don't"));
		tmp.add(new Event("forget"));
		tmp.add(new Event("it!\""));
		tmp.add(new Event("she"));
		tmp.add(new Event("said."));
		tmp.add(new Event("\"I"));
		tmp.add(new Event("won't,\""));
		tmp.add(new Event("he"));
		tmp.add(new Event("grumbled."));

		expectedEventSet.addEvents(tmp);
		assertTrue(expectedEventSet.equals(sampleEventSet));
	}

}
