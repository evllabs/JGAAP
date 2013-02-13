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
 * @author Patrick Juola
 *
 */
public class NaiveWordEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.NaiveWordEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 * @throws EventGenerationException 
	 */
	@Test
	public void testCreateEventSetDocumentSet() throws EventGenerationException {
		/* test case 1 -- no punctuation */
		String text = (
"We hold these truths to be self-evident,\n"+
"\"My phone # is 867-5309; don't forget it!\" she said.\n"+
"\t\t\"I won't,\" \t he grumbled.\n"

		);

		EventDriver eventDriver = new NaiveWordEventDriver();
		EventSet sampleEventSet = eventDriver.createEventSet(text.toCharArray());
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();

		tmp.add(new Event("We", eventDriver));
		tmp.add(new Event("hold", eventDriver));
		tmp.add(new Event("these", eventDriver));
		tmp.add(new Event("truths", eventDriver));
		tmp.add(new Event("to", eventDriver));
		tmp.add(new Event("be", eventDriver));
		tmp.add(new Event("self-evident,", eventDriver));
		tmp.add(new Event("\"My", eventDriver));
		tmp.add(new Event("phone", eventDriver));
		tmp.add(new Event("#", eventDriver));
		tmp.add(new Event("is", eventDriver));
		tmp.add(new Event("867-5309;", eventDriver));
		tmp.add(new Event("don't", eventDriver));
		tmp.add(new Event("forget", eventDriver));
		tmp.add(new Event("it!\"", eventDriver));
		tmp.add(new Event("she", eventDriver));
		tmp.add(new Event("said.", eventDriver));
		tmp.add(new Event("\"I", eventDriver));
		tmp.add(new Event("won't,\"", eventDriver));
		tmp.add(new Event("he", eventDriver));
		tmp.add(new Event("grumbled.", eventDriver));

		expectedEventSet.addEvents(tmp);
		assertTrue(expectedEventSet.equals(sampleEventSet));
	}

}
