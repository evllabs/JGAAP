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

		tmp.add(new Event("Hello,"));
        tmp.add(new Event("I'm"));
        tmp.add(new Event("What's"));
        tmp.add(new Event("My"));

		expectedEventSet.addEvents(tmp);

		assertTrue(expectedEventSet.equals(sampleEventSet));
	}

}

