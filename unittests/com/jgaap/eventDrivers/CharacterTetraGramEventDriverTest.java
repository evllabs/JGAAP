/*
 * JGAAP -- a graphical program for stylometric authorship attribution
 * Copyright (C) 2009,2011 by Patrick Juola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package com.jgaap.eventDrivers;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventSet;

/**
 * @author Chris
 *
 */
public class CharacterTetraGramEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.CharacterNGramEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 * @throws EventGenerationException 
	 */
	@Test
	public void testCreateEventSetDocumentSet() throws EventGenerationException {
		Document doc = new Document();
		doc.readStringText("abcdefghijklmnopqrstuvwxyz .");
		EventDriver eventDriver = new CharacterNGramEventDriver();
		eventDriver.setParameter("N", 4);
		EventSet sampleEventSet = eventDriver.createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		tmp.add(new Event("abcd", null));
		tmp.add(new Event("bcde", null));
		tmp.add(new Event("cdef", null));
		tmp.add(new Event("defg", null));
		tmp.add(new Event("efgh", null));
		tmp.add(new Event("fghi", null));
		tmp.add(new Event("ghij", null));
		tmp.add(new Event("hijk", null));
		tmp.add(new Event("ijkl", null));
		tmp.add(new Event("jklm", null));
		tmp.add(new Event("klmn", null));
		tmp.add(new Event("lmno", null));
		tmp.add(new Event("mnop", null));
		tmp.add(new Event("nopq", null));
		tmp.add(new Event("opqr", null));
		tmp.add(new Event("pqrs", null));
		tmp.add(new Event("qrst", null));
		tmp.add(new Event("rstu", null));
		tmp.add(new Event("stuv", null));
		tmp.add(new Event("tuvw", null));
		tmp.add(new Event("uvwx", null));
		tmp.add(new Event("vwxy", null));
		tmp.add(new Event("wxyz", null));
		tmp.add(new Event("xyz ", null));
		expectedEventSet.addEvents(tmp);
		expectedEventSet.addEvent(new Event("yz .", null));
		
		assertTrue(expectedEventSet.equals(sampleEventSet));
		
	}

}
