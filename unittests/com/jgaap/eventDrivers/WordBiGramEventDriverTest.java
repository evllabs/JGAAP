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
 * @author Patrick Juola
 *
 */
public class WordBiGramEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.WordBiGramEventDriver#createEventSet(com.jgaap.generics.JGAAP)}.
	 */
	@Test
	public void testCreateEventSetDocumentSet() {
		Document doc = new Document();
		doc.readStringText(
"Mary had a little lamb;\n" +
"Its fleece was white as snow.\n" +
"And everywhere that Mary went,\n" +
"The lamb was sure to go.");
		EventSet sampleEventSet = new WordBiGramEventDriver().createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		tmp.add(new Event("(Mary)-(had)"));
		tmp.add(new Event("(had)-(a)"));
		tmp.add(new Event("(a)-(little)"));
		tmp.add(new Event("(little)-(lamb;)"));
		tmp.add(new Event("(lamb;)-(Its)"));
		tmp.add(new Event("(Its)-(fleece)"));
		tmp.add(new Event("(fleece)-(was)"));
		tmp.add(new Event("(was)-(white)"));
		tmp.add(new Event("(white)-(as)"));
		tmp.add(new Event("(as)-(snow.)"));
		tmp.add(new Event("(snow.)-(And)"));
		tmp.add(new Event("(And)-(everywhere)"));
		tmp.add(new Event("(everywhere)-(that)"));
		tmp.add(new Event("(that)-(Mary)"));
		tmp.add(new Event("(Mary)-(went,)"));
		tmp.add(new Event("(went,)-(The)"));
		tmp.add(new Event("(The)-(lamb)"));
		tmp.add(new Event("(lamb)-(was)"));
		tmp.add(new Event("(was)-(sure)"));
		tmp.add(new Event("(sure)-(to)"));
		tmp.add(new Event("(to)-(go.)"));

		expectedEventSet.addEvents(tmp);
//System.out.println(expectedEventSet.toString());
//System.out.println(sampleEventSet.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));
		
	}

}
