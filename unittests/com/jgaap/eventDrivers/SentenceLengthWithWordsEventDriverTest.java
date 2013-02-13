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

import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

public class SentenceLengthWithWordsEventDriverTest {

	@Test
	public void testCreateEventSetDocumentSet() {

		String text = ("Hello, Dr. Jones!  I'm not.feeling.too well today.  What's the matter Mr. Adams? My stomach hurts, or A.K.A, cramps.");

		SentenceLengthWithWordsEventDriver eventDriver = new SentenceLengthWithWordsEventDriver();
		EventSet sampleEventSet = eventDriver.createEventSet(text.toCharArray());
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();

		tmp.add(new Event("3", eventDriver));
        tmp.add(new Event("4", eventDriver));
        tmp.add(new Event("5", eventDriver));
        tmp.add(new Event("6", eventDriver));

		expectedEventSet.addEvents(tmp);

		assertTrue(expectedEventSet.equals(sampleEventSet));
	}

}

