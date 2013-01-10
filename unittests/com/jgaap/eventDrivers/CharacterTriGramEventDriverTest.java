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
public class CharacterTriGramEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.CharacterNGramEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 * @throws EventGenerationException 
	 */
	@Test
	public void testCreateEventSetDocumentSet() throws EventGenerationException {
		Document doc = new Document();
		doc.readStringText("abcdefghijklmnopqrstuvwxyz .");
		EventDriver eventDriver = new CharacterNGramEventDriver();
		eventDriver.setParameter("N", 3);
		EventSet sampleEventSet = eventDriver.createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		tmp.add(new Event("abc", null));
		tmp.add(new Event("bcd", null));
		tmp.add(new Event("cde", null));
		tmp.add(new Event("def", null));
		tmp.add(new Event("efg", null));
		tmp.add(new Event("fgh", null));
		tmp.add(new Event("ghi", null));
		tmp.add(new Event("hij", null));
		tmp.add(new Event("ijk", null));
		tmp.add(new Event("jkl", null));
		tmp.add(new Event("klm", null));
		tmp.add(new Event("lmn", null));
		tmp.add(new Event("mno", null));
		tmp.add(new Event("nop", null));
		tmp.add(new Event("opq", null));
		tmp.add(new Event("pqr", null));
		tmp.add(new Event("qrs", null));
		tmp.add(new Event("rst", null));
		tmp.add(new Event("stu", null));
		tmp.add(new Event("tuv", null));
		tmp.add(new Event("uvw", null));
		tmp.add(new Event("vwx", null));
		tmp.add(new Event("wxy", null));
		tmp.add(new Event("xyz", null));
		expectedEventSet.addEvents(tmp);
		expectedEventSet.addEvent(new Event("yz ", null));
		expectedEventSet.addEvent(new Event("z .", null));
		
		assertTrue(expectedEventSet.equals(sampleEventSet));
		
	}

}
