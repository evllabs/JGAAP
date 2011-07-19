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
import com.jgaap.generics.EventSet;

/**
 * @author Chris
 *
 */
public class CharacterTriGramEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.CharacterTriGramEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
	 */
	@Test
	public void testCreateEventSetDocumentSet() {
		Document doc = new Document();
		doc.readStringText("abcdefghijklmnopqrstuvwxyz .");
		EventSet sampleEventSet = new CharacterTriGramEventDriver().createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		tmp.add(new Event("abc"));
		tmp.add(new Event("bcd"));
		tmp.add(new Event("cde"));
		tmp.add(new Event("def"));
		tmp.add(new Event("efg"));
		tmp.add(new Event("fgh"));
		tmp.add(new Event("ghi"));
		tmp.add(new Event("hij"));
		tmp.add(new Event("ijk"));
		tmp.add(new Event("jkl"));
		tmp.add(new Event("klm"));
		tmp.add(new Event("lmn"));
		tmp.add(new Event("mno"));
		tmp.add(new Event("nop"));
		tmp.add(new Event("opq"));
		tmp.add(new Event("pqr"));
		tmp.add(new Event("qrs"));
		tmp.add(new Event("rst"));
		tmp.add(new Event("stu"));
		tmp.add(new Event("tuv"));
		tmp.add(new Event("uvw"));
		tmp.add(new Event("vwx"));
		tmp.add(new Event("wxy"));
		tmp.add(new Event("xyz"));
		expectedEventSet.addEvents(tmp);
		expectedEventSet.addEvent(new Event("yz "));
		expectedEventSet.addEvent(new Event("z ."));
		
		assertTrue(expectedEventSet.equals(sampleEventSet));
		
	}

}
