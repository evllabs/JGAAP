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
public class VowelInitialWordEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.VowelInitialWordEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
	 */
	@Test
	public void testCreateEventSetDocumentSet() {
		/* test case 1 -- no punctuation */
		Document doc = new Document();
		doc.readStringText(
"alpha bravo charlie delta echo foxtrot golf hotel india " +
"juliet kilo lima mike november oscar papa quebec romeo " +
"sierra tango uniform victor whiskey x-ray yankee zebra " +
"Alpha Bravo Charlie Delta Echo Foxtrot Golf Hotel India " +
"Juliet Kilo Lima Mike November Oscar Papa Quebec Romeo " +
"Sierra Tango Uniform Victor Whiskey X-ray Yankee Zebra " +
"_none ?of #these *should 1be 4included +in ^output"
		);

		EventSet sampleEventSet = new VowelInitialWordEventDriver().createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();

		tmp.add(new Event("alpha"));
		tmp.add(new Event("echo"));
		tmp.add(new Event("india"));
		tmp.add(new Event("oscar"));
		tmp.add(new Event("uniform"));
		tmp.add(new Event("yankee"));
		tmp.add(new Event("Alpha"));
		tmp.add(new Event("Echo"));
		tmp.add(new Event("India"));
		tmp.add(new Event("Oscar"));
		tmp.add(new Event("Uniform"));
		tmp.add(new Event("Yankee"));

		expectedEventSet.addEvents(tmp);
		assertTrue(expectedEventSet.equals(sampleEventSet));
	}
}
