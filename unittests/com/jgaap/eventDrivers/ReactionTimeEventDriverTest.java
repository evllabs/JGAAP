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
public class ReactionTimeEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.ReactionTimeEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
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


		EventSet sampleEventSet = new ReactionTimeEventDriver().createEventSet(doc);
		EventSet expectedEventSet = new NumericEventSet();
		Vector<Event> tmp = new Vector<Event>();

		tmp.add(new Event("798.92"));
		tmp.add(new Event("816.43"));
		tmp.add(new Event("736.06"));
		tmp.add(new Event("796.27"));
		tmp.add(new Event("964.40"));
		tmp.add(new Event("695.72"));
		tmp.add(new Event("860.77"));
		tmp.add(new Event("605.23"));
		tmp.add(new Event("726.43"));
		tmp.add(new Event("572.56"));
		tmp.add(new Event("714.09"));
		tmp.add(new Event("685.28"));
		tmp.add(new Event("549.76"));
		tmp.add(new Event("709.69"));
		tmp.add(new Event("666.93"));
		tmp.add(new Event("848.68"));
		tmp.add(new Event("763.00"));

		expectedEventSet.addEvents(tmp);

// System.out.println("Expected is " + expectedEventSet.toString());
// System.out.println("Actual is " + sampleEventSet.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));
	}

}
