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
public class _23LetterWordEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers._23LetterWordEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
	 */
	@Test
	public void testCreateEventSetDocumentSet() {

		Document doc = new Document();
		doc.readStringText(
"a bb ccc dddd eeeee " +
"1 22 333 4444 55555 " +
"! @@ ### $$$$ %%%%% " +
"A BB CCC DDDD EEEEE "
		);


		EventSet sampleEventSet = new _23LetterWordEventDriver().createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();

		tmp.add(new Event("bb"));
		tmp.add(new Event("ccc"));
		tmp.add(new Event("22"));
		tmp.add(new Event("333"));
		tmp.add(new Event("@@"));
		tmp.add(new Event("###"));
		tmp.add(new Event("BB"));
		tmp.add(new Event("CCC"));


		expectedEventSet.addEvents(tmp);
		assertTrue(expectedEventSet.equals(sampleEventSet));
	}
}
