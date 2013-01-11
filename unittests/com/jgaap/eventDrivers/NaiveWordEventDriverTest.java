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

import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;

/**
 * @author Patrick Juola
 *
 */
public class NaiveWordEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.NaiveWordEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 */
	@Test
	public void testCreateEventSetDocumentSet() {
		/* test case 1 -- no punctuation */
		String text = (
"We hold these truths to be self-evident,\n"+
"\"My phone # is 867-5309; don't forget it!\" she said.\n"+
"\t\t\"I won't,\" \t he grumbled.\n"

		);

		EventSet sampleEventSet = new NaiveWordEventDriver().createEventSet(text.toCharArray());
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();

		tmp.add(new Event("We", null));
		tmp.add(new Event("hold", null));
		tmp.add(new Event("these", null));
		tmp.add(new Event("truths", null));
		tmp.add(new Event("to", null));
		tmp.add(new Event("be", null));
		tmp.add(new Event("self-evident,", null));
		tmp.add(new Event("\"My", null));
		tmp.add(new Event("phone", null));
		tmp.add(new Event("#", null));
		tmp.add(new Event("is", null));
		tmp.add(new Event("867-5309;", null));
		tmp.add(new Event("don't", null));
		tmp.add(new Event("forget", null));
		tmp.add(new Event("it!\"", null));
		tmp.add(new Event("she", null));
		tmp.add(new Event("said.", null));
		tmp.add(new Event("\"I", null));
		tmp.add(new Event("won't,\"", null));
		tmp.add(new Event("he", null));
		tmp.add(new Event("grumbled.", null));

		expectedEventSet.addEvents(tmp);
		assertTrue(expectedEventSet.equals(sampleEventSet));
	}

}
