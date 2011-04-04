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
 * @author Chris
 *
 */
public class CharacterTetraGramEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.CharacterTetraGramEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
	 */
	@Test
	public void testCreateEventSetDocumentSet() {
		Document doc = new Document();
		doc.readStringText("abcdefghijklmnopqrstuvwxyz .");
		EventSet sampleEventSet = new CharacterTetraGramEventDriver().createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		tmp.add(new Event("abcd"));
		tmp.add(new Event("bcde"));
		tmp.add(new Event("cdef"));
		tmp.add(new Event("defg"));
		tmp.add(new Event("efgh"));
		tmp.add(new Event("fghi"));
		tmp.add(new Event("ghij"));
		tmp.add(new Event("hijk"));
		tmp.add(new Event("ijkl"));
		tmp.add(new Event("jklm"));
		tmp.add(new Event("klmn"));
		tmp.add(new Event("lmno"));
		tmp.add(new Event("mnop"));
		tmp.add(new Event("nopq"));
		tmp.add(new Event("opqr"));
		tmp.add(new Event("pqrs"));
		tmp.add(new Event("qrst"));
		tmp.add(new Event("rstu"));
		tmp.add(new Event("stuv"));
		tmp.add(new Event("tuvw"));
		tmp.add(new Event("uvwx"));
		tmp.add(new Event("vwxy"));
		tmp.add(new Event("wxyz"));
		tmp.add(new Event("xyz "));
		expectedEventSet.addEvents(tmp);
		expectedEventSet.addEvent(new Event("yz ."));
		
		assertTrue(expectedEventSet.equals(sampleEventSet));
		
	}

}
