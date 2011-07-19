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
import com.jgaap.generics.NumericEventSet;

/**
 * @author Patrick Juola
 *
 */
public class TruncatedFreqEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.TruncatedFreqEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
	 */
	@Test
	public void testCreateEventSetDocumentSet() {
		/* test case 1 -- no punctuation */
		Document doc = new Document();
		// test just first and last few words rathre than
		// entire 40k corpus
		doc.readStringText(
"a aah Aaron aback abacus abandon abandoned zones zoning zoo " +
"zoologist zoology zoom zooming zooms zucchini Zurich");


		EventSet sampleEventSet = new TruncatedFreqEventDriver().createEventSet(doc);
		EventSet expectedEventSet = new NumericEventSet();
		Vector<Event> tmp = new Vector<Event>();

		tmp.add(new Event("16."));
		tmp.add(new Event("5.4"));
		tmp.add(new Event("9.2"));
		tmp.add(new Event("5.9"));
		tmp.add(new Event("6.2"));
		tmp.add(new Event("8.2"));
		tmp.add(new Event("8.5"));
		tmp.add(new Event("8.1"));
		tmp.add(new Event("6.7"));
		tmp.add(new Event("8.3"));
		tmp.add(new Event("5.7"));
		tmp.add(new Event("6.5"));
		tmp.add(new Event("8.5"));
		tmp.add(new Event("6.2"));
		tmp.add(new Event("5.9"));
		tmp.add(new Event("5.7"));
		tmp.add(new Event("7.4"));

		expectedEventSet.addEvents(tmp);

// System.out.println("Expected is " + expectedEventSet.toString());
// System.out.println("Actual is " + sampleEventSet.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));
	}

}
