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
 * @author Chris
 *
 */
public class WordTetraGramEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.WordTetraGramEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
	 */
	@Test
	public void testCreateEventSetDocumentSet() {
		Document doc = new Document();
		doc.readStringText("Mary had a little lamb, little lamb. Its fleece was white as snow.");
		EventSet sampleEventSet = new WordTetraGramEventDriver().createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		tmp.add(new Event("(Mary)-(had)-(a)-(little)"));
		tmp.add(new Event("(had)-(a)-(little)-(lamb,)"));
		tmp.add(new Event("(a)-(little)-(lamb,)-(little)"));
		tmp.add(new Event("(little)-(lamb,)-(little)-(lamb.)"));
		tmp.add(new Event("(lamb,)-(little)-(lamb.)-(Its)"));
		tmp.add(new Event("(little)-(lamb.)-(Its)-(fleece)"));
		tmp.add(new Event("(lamb.)-(Its)-(fleece)-(was)"));
		tmp.add(new Event("(Its)-(fleece)-(was)-(white)"));
		tmp.add(new Event("(fleece)-(was)-(white)-(as)"));
		tmp.add(new Event("(was)-(white)-(as)-(snow.)"));
		expectedEventSet.addEvents(tmp);
		assertTrue(expectedEventSet.equals(sampleEventSet));
		
	}

}
