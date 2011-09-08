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
public class CharacterBiGramEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.CharacterBiGramEventDriver#createEventSet(com.jgaap.generics.JGAAP)}.
	 */
	@Test
	public void testCreateEventSetDocumentSet() {
		Document doc = new Document();
		doc.readStringText("abcdefghijklmnopqrstuvwxyz .");
		EventSet sampleEventSet = new CharacterBiGramEventDriver().createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		tmp.add(new Event("ab"));
		tmp.add(new Event("bc"));
		tmp.add(new Event("cd"));
		tmp.add(new Event("de"));
		tmp.add(new Event("ef"));
		tmp.add(new Event("fg"));
		tmp.add(new Event("gh"));
		tmp.add(new Event("hi"));
		tmp.add(new Event("ij"));
		tmp.add(new Event("jk"));
		tmp.add(new Event("kl"));
		tmp.add(new Event("lm"));
		tmp.add(new Event("mn"));
		tmp.add(new Event("no"));
		tmp.add(new Event("op"));
		tmp.add(new Event("pq"));
		tmp.add(new Event("qr"));
		tmp.add(new Event("rs"));
		tmp.add(new Event("st"));
		tmp.add(new Event("tu"));
		tmp.add(new Event("uv"));
		tmp.add(new Event("vw"));
		tmp.add(new Event("wx"));
		tmp.add(new Event("xy"));
		tmp.add(new Event("yz"));
		expectedEventSet.addEvents(tmp);
		expectedEventSet.addEvent(new Event("z "));
		//expectedEventSet.addEvents(tmp);
		expectedEventSet.addEvent(new Event(" ."));
// System.out.println(expectedEventSet.toString());
// System.out.println(sampleEventSet.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));
		
	}

}
