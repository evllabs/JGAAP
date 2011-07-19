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
public class SimpleAppendEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.SimpleAppendEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
	 */
	@Test
	public void testCreateEventSetDocumentSet() {
		Document doc = new Document();
		doc.readStringText(
"Mary had a little lamb;\n" +
"Its fleece was white as snow.\n");
		EventSet sampleEventSet = new SimpleAppendEventDriver().createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();

		tmp.add(new Event("Mary"));
		tmp.add(new Event("had"));
		tmp.add(new Event("a"));
		tmp.add(new Event("little"));
		tmp.add(new Event("lamb;"));
		tmp.add(new Event("Its"));
		tmp.add(new Event("fleece"));
		tmp.add(new Event("was"));
		tmp.add(new Event("white"));
		tmp.add(new Event("as"));
		tmp.add(new Event("snow."));
		
		tmp.add(new Event("4"));
		tmp.add(new Event("3"));
		tmp.add(new Event("1"));
		tmp.add(new Event("6"));
		tmp.add(new Event("5"));
		tmp.add(new Event("3"));
		tmp.add(new Event("6"));
		tmp.add(new Event("3"));
		tmp.add(new Event("5"));
		tmp.add(new Event("2"));
		tmp.add(new Event("5"));
		
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


		expectedEventSet.addEvents(tmp);
System.out.println(expectedEventSet.toString());
System.out.println(sampleEventSet.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));
		
	}

}
