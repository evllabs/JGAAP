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
public class CharacterTriGramEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.CharacterNGramEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 * @throws EventGenerationException 
	 */
	@Test
	public void testCreateEventSetDocumentSet() throws EventGenerationException {
		String text = ("abcdefghijklmnopqrstuvwxyz .");
		EventDriver eventDriver = new CharacterNGramEventDriver();
		eventDriver.setParameter("N", 3);
		EventSet sampleEventSet = eventDriver.createEventSet(text.toCharArray());
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		tmp.add(new Event("abc", eventDriver));
		tmp.add(new Event("bcd", eventDriver));
		tmp.add(new Event("cde", eventDriver));
		tmp.add(new Event("def", eventDriver));
		tmp.add(new Event("efg", eventDriver));
		tmp.add(new Event("fgh", eventDriver));
		tmp.add(new Event("ghi", eventDriver));
		tmp.add(new Event("hij", eventDriver));
		tmp.add(new Event("ijk", eventDriver));
		tmp.add(new Event("jkl", eventDriver));
		tmp.add(new Event("klm", eventDriver));
		tmp.add(new Event("lmn", eventDriver));
		tmp.add(new Event("mno", eventDriver));
		tmp.add(new Event("nop", eventDriver));
		tmp.add(new Event("opq", eventDriver));
		tmp.add(new Event("pqr", eventDriver));
		tmp.add(new Event("qrs", eventDriver));
		tmp.add(new Event("rst", eventDriver));
		tmp.add(new Event("stu", eventDriver));
		tmp.add(new Event("tuv", eventDriver));
		tmp.add(new Event("uvw", eventDriver));
		tmp.add(new Event("vwx", eventDriver));
		tmp.add(new Event("wxy", eventDriver));
		tmp.add(new Event("xyz", eventDriver));
		expectedEventSet.addEvents(tmp);
		expectedEventSet.addEvent(new Event("yz ", eventDriver));
		expectedEventSet.addEvent(new Event("z .", eventDriver));
		
		assertTrue(expectedEventSet.equals(sampleEventSet));
		
	}

}
