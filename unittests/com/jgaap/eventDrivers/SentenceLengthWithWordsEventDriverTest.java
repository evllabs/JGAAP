// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
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

public class SentenceLengthWithWordsEventDriverTest {

	@Test
	public void testCreateEventSetDocumentSet() {

		Document doc = new Document();
        doc.readStringText("Hello, Dr. Jones!  I'm not.feeling.too well today.  What's the matter Mr. Adams? My stomach hurts, or A.K.A, cramps.");

		EventSet sampleEventSet = new SentenceLengthWithWordsEventDriver().createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();

		tmp.add(new Event("3"));
        tmp.add(new Event("4"));
        tmp.add(new Event("5"));
        tmp.add(new Event("6"));

		expectedEventSet.addEvents(tmp);

		assertTrue(expectedEventSet.equals(sampleEventSet));
	}

}

