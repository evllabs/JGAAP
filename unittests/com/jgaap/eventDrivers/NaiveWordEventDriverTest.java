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
public class NaiveWordEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.NaiveWordEventDriver#createEventSet(com.jgaap.generics.JGAAP)}.
	 */
	@Test
	public void testCreateEventSetDocumentSet() {
		/* test case 1 -- no punctuation */
		Document doc = new Document();
		doc.readStringText(
"We hold these truths to be self-evident,\n"+
"\"My phone # is 867-5309; don't forget it!\" she said.\n"+
"\t\t\"I won't,\" \t he grumbled.\n"

		);

		EventSet sampleEventSet = new NaiveWordEventDriver().createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();

		tmp.add(new Event("We"));
		tmp.add(new Event("hold"));
		tmp.add(new Event("these"));
		tmp.add(new Event("truths"));
		tmp.add(new Event("to"));
		tmp.add(new Event("be"));
		tmp.add(new Event("self-evident,"));
		tmp.add(new Event("\"My"));
		tmp.add(new Event("phone"));
		tmp.add(new Event("#"));
		tmp.add(new Event("is"));
		tmp.add(new Event("867-5309;"));
		tmp.add(new Event("don't"));
		tmp.add(new Event("forget"));
		tmp.add(new Event("it!\""));
		tmp.add(new Event("she"));
		tmp.add(new Event("said."));
		tmp.add(new Event("\"I"));
		tmp.add(new Event("won't,\""));
		tmp.add(new Event("he"));
		tmp.add(new Event("grumbled."));

		expectedEventSet.addEvents(tmp);
		assertTrue(expectedEventSet.equals(sampleEventSet));
	}

}
