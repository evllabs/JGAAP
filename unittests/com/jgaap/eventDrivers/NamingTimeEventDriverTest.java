// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
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
public class NamingTimeEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.NamingTimeEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
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


		EventSet sampleEventSet = new NamingTimeEventDriver().createEventSet(doc);
		EventSet expectedEventSet = new NumericEventSet();
		Vector<Event> tmp = new Vector<Event>();


		tmp.add(new Event("662.09"));
		tmp.add(new Event("646.40"));
		tmp.add(new Event("686.11"));
		tmp.add(new Event("596.54"));
		tmp.add(new Event("792.69"));
		tmp.add(new Event("623.96"));
		tmp.add(new Event("635.16"));
		tmp.add(new Event("590.08"));
		tmp.add(new Event("694.85"));
		tmp.add(new Event("662.57"));
		tmp.add(new Event("732.70"));
		tmp.add(new Event("687.12"));
		tmp.add(new Event("639.86"));
		tmp.add(new Event("672.37"));
		tmp.add(new Event("613.83"));
		tmp.add(new Event("756.00"));
		tmp.add(new Event("822.64"));

		expectedEventSet.addEvents(tmp);

// System.out.println("Expected is " + expectedEventSet.toString());/
// System.out.println("Actual is " + sampleEventSet.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));
	}

}
