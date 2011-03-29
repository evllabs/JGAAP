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
public class SuffixEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.NaiveWordEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
	 */
	@Test
	public void testCreateEventSetDocumentSet() {
		/* test case 1 -- no punctuation */
		Document doc = new Document();
		doc.readStringText(
"test test test tested testers happiest test test test"
		);

		EventSet sampleEventSet = new SuffixEventDriver().createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();

		tmp.add(new Event("ted"));
		tmp.add(new Event("ers"));
		tmp.add(new Event("est"));

		expectedEventSet.addEvents(tmp);
		System.out.println(sampleEventSet.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));
	}

}
