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
import com.jgaap.generics.EventSet;

/**
 * @author Chris
 *
 */
public class CharacterTetraGramEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.CharacterTetraGramEventDriver#createEventSet(com.jgaap.generics.JGAAP)}.
	 */
	@Test
	public void testCreateEventSetDocumentSet() {
		Document doc = new Document();
		doc.readStringText("abcdefghijklmnopqrstuvwxyz .");
		EventDriver eventDriver = new CharacterNGramEventDriver();
		eventDriver.setParameter("N", 4);
		EventSet sampleEventSet = eventDriver.createEventSet(doc);
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
