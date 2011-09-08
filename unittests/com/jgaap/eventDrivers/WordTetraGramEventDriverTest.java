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
public class WordTetraGramEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.WordTetraGramEventDriver#createEventSet(com.jgaap.generics.JGAAP)}.
	 */
	@Test
	public void testCreateEventSetDocumentSet() {
		Document doc = new Document();
		doc.readStringText("Mary had a little lamb, little lamb. Its fleece was white as snow.");
		EventSet sampleEventSet = new WordTetraGramEventDriver().createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		tmp.add(new Event("(Mary)-(had)-(a)-(little)"));
		tmp.add(new Event("(had)-(a)-(little)-(lamb,)"));
		tmp.add(new Event("(a)-(little)-(lamb,)-(little)"));
		tmp.add(new Event("(little)-(lamb,)-(little)-(lamb.)"));
		tmp.add(new Event("(lamb,)-(little)-(lamb.)-(Its)"));
		tmp.add(new Event("(little)-(lamb.)-(Its)-(fleece)"));
		tmp.add(new Event("(lamb.)-(Its)-(fleece)-(was)"));
		tmp.add(new Event("(Its)-(fleece)-(was)-(white)"));
		tmp.add(new Event("(fleece)-(was)-(white)-(as)"));
		tmp.add(new Event("(was)-(white)-(as)-(snow.)"));
		expectedEventSet.addEvents(tmp);
		assertTrue(expectedEventSet.equals(sampleEventSet));
		
	}

}
