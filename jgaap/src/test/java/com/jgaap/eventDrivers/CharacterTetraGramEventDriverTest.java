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

import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

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
		String text = ("abcdefghijklmnopqrstuvwxyz .");
		EventDriver eventDriver = new CharacterNGramEventDriver();
		eventDriver.setParameter("N", 4);
		EventSet sampleEventSet = eventDriver.createEventSet(text.toCharArray());
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		tmp.add(new Event("abcd", eventDriver));
		tmp.add(new Event("bcde", eventDriver));
		tmp.add(new Event("cdef", eventDriver));
		tmp.add(new Event("defg", eventDriver));
		tmp.add(new Event("efgh", eventDriver));
		tmp.add(new Event("fghi", eventDriver));
		tmp.add(new Event("ghij", eventDriver));
		tmp.add(new Event("hijk", eventDriver));
		tmp.add(new Event("ijkl", eventDriver));
		tmp.add(new Event("jklm", eventDriver));
		tmp.add(new Event("klmn", eventDriver));
		tmp.add(new Event("lmno", eventDriver));
		tmp.add(new Event("mnop", eventDriver));
		tmp.add(new Event("nopq", eventDriver));
		tmp.add(new Event("opqr", eventDriver));
		tmp.add(new Event("pqrs", eventDriver));
		tmp.add(new Event("qrst", eventDriver));
		tmp.add(new Event("rstu", eventDriver));
		tmp.add(new Event("stuv", eventDriver));
		tmp.add(new Event("tuvw", eventDriver));
		tmp.add(new Event("uvwx", eventDriver));
		tmp.add(new Event("vwxy", eventDriver));
		tmp.add(new Event("wxyz", eventDriver));
		tmp.add(new Event("xyz ", eventDriver));
		expectedEventSet.addEvents(tmp);
		expectedEventSet.addEvent(new Event("yz .", eventDriver));
		
		assertTrue(expectedEventSet.equals(sampleEventSet));
		
	}

}
