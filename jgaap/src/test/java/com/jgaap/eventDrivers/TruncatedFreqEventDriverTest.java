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
public class TruncatedFreqEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.TruncatedFreqEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 * @throws EventGenerationException 
	 */
	@Test
	public void testCreateEventSetDocumentSet() throws EventGenerationException {
		/* test case 1 -- no punctuation */
		String text = (
"a aah Aaron aback abacus abandon abandoned zones zoning zoo " +
"zoologist zoology zoom zooming zooms zucchini Zurich");

		EventDriver eventDriver = new TruncatedFreqEventDriver();
		EventSet sampleEventSet = eventDriver.createEventSet(text.toCharArray());
		EventSet expectedEventSet = new NumericEventSet();
		Vector<Event> tmp = new Vector<Event>();

		tmp.add(new Event("16.", eventDriver));
		tmp.add(new Event("5.4", eventDriver));
		tmp.add(new Event("9.2", eventDriver));
		tmp.add(new Event("5.9", eventDriver));
		tmp.add(new Event("6.2", eventDriver));
		tmp.add(new Event("8.2", eventDriver));
		tmp.add(new Event("8.5", eventDriver));
		tmp.add(new Event("8.1", eventDriver));
		tmp.add(new Event("6.7", eventDriver));
		tmp.add(new Event("8.3", eventDriver));
		tmp.add(new Event("5.7", eventDriver));
		tmp.add(new Event("6.5", eventDriver));
		tmp.add(new Event("8.5", eventDriver));
		tmp.add(new Event("6.2", eventDriver));
		tmp.add(new Event("5.9", eventDriver));
		tmp.add(new Event("5.7", eventDriver));
		tmp.add(new Event("7.4", eventDriver));

		expectedEventSet.addEvents(tmp);

 System.out.println("Expected is " + expectedEventSet.toString());
 System.out.println("Actual is " + sampleEventSet.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));
	}

}
