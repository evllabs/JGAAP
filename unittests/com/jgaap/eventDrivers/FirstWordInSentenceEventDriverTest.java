package com.jgaap.eventDrivers;

/**
 * @author Mike Mehok
 */
import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;

public class FirstWordInSentenceEventDriverTest {

	@Test
	public void testCreateEventSetDocumentSet() {

		Document doc = new Document();
		doc.readStringText("Hello, Dr. Jones!  I'm not.feeling.too well today.  What's the matter Mr. Adams?  My stomach hurts, or A.K.A, cramps.");

		EventSet sampleEventSet = new FirstWordInSentenceEventDriver().createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();

		tmp.add(new Event("Hello"));
        tmp.add(new Event("I'm"));
        tmp.add(new Event("What's"));
        tmp.add(new Event("My"));

		expectedEventSet.addEvents(tmp);

		assertTrue(expectedEventSet.equals(sampleEventSet));
	}

}

