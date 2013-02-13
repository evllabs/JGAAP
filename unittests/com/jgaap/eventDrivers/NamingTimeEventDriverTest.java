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
public class NamingTimeEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.NamingTimeEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 * @throws EventGenerationException 
	 */
	@Test
	public void testCreateEventSetDocumentSet() throws EventGenerationException {
		/* test case 1 -- no punctuation */
		String text = (
"a aah Aaron aback abacus abandon abandoned zones zoning zoo " +
"zoologist zoology zoom zooming zooms zucchini Zurich");

		EventDriver eventDriver = new NamingTimeEventDriver();
		EventSet sampleEventSet = eventDriver.createEventSet(text.toCharArray());
		EventSet expectedEventSet = new NumericEventSet();
		Vector<Event> tmp = new Vector<Event>();


		tmp.add(new Event("662.09", eventDriver));
		tmp.add(new Event("646.40", eventDriver));
		tmp.add(new Event("686.11", eventDriver));
		tmp.add(new Event("596.54", eventDriver));
		tmp.add(new Event("792.69", eventDriver));
		tmp.add(new Event("623.96", eventDriver));
		tmp.add(new Event("635.16", eventDriver));
		tmp.add(new Event("590.08", eventDriver));
		tmp.add(new Event("694.85", eventDriver));
		tmp.add(new Event("662.57", eventDriver));
		tmp.add(new Event("732.70", eventDriver));
		tmp.add(new Event("687.12", eventDriver));
		tmp.add(new Event("639.86", eventDriver));
		tmp.add(new Event("672.37", eventDriver));
		tmp.add(new Event("613.83", eventDriver));
		tmp.add(new Event("756.00", eventDriver));
		tmp.add(new Event("822.64", eventDriver));

		expectedEventSet.addEvents(tmp);

// System.out.println("Expected is " + expectedEventSet.toString());/
// System.out.println("Actual is " + sampleEventSet.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));
	}

}
