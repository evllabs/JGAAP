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
import com.jgaap.util.NumericEventSet;

/**
 * @author Patrick Juola
 *
 */
public class TruncatedReactionTimeEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.TruncatedReactionTimeEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 * @throws EventGenerationException 
	 */
	@Test
	public void testCreateEventSetDocumentSet() throws EventGenerationException {
		/* test case 1 -- no punctuation */
		String text = (
"a aah Aaron aback abacus abandon abandoned zones zoning zoo " +
"zoologist zoology zoom zooming zooms zucchini Zurich");

		EventDriver eventDriver = new TruncatedReactionTimeEventDriver();
		EventSet sampleEventSet = eventDriver.createEventSet(text.toCharArray());
		EventSet expectedEventSet = new NumericEventSet();
		Vector<Event> tmp = new Vector<Event>();

		tmp.add(new Event("79", eventDriver));
		tmp.add(new Event("81", eventDriver));
		tmp.add(new Event("73", eventDriver));
		tmp.add(new Event("79", eventDriver));
		tmp.add(new Event("96", eventDriver));
		tmp.add(new Event("69", eventDriver));
		tmp.add(new Event("86", eventDriver));
		tmp.add(new Event("60", eventDriver));
		tmp.add(new Event("72", eventDriver));
		tmp.add(new Event("57", eventDriver));
		tmp.add(new Event("71", eventDriver));
		tmp.add(new Event("68", eventDriver));
		tmp.add(new Event("54", eventDriver));
		tmp.add(new Event("70", eventDriver));
		tmp.add(new Event("66", eventDriver));
		tmp.add(new Event("84", eventDriver));
		tmp.add(new Event("76", eventDriver));

		expectedEventSet.addEvents(tmp);

// System.out.println("Expected is " + expectedEventSet.toString());
// System.out.println("Actual is " + sampleEventSet.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));
	}

}

