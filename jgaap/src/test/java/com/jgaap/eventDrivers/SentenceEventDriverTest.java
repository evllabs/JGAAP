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
 * @author Michael Ryan
 */
import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

public class SentenceEventDriverTest {

	@Test
	public void testCreateEventSetDocumentSet() throws EventGenerationException {

		String text = ("Hello, Dr. Jones!  I'm not.feeling.too well today.  What's the matter Mr. Adams?  My stomach hurts, A.K.A, cramps.");

		EventDriver eventDriver = new SentenceEventDriver();
		EventSet sampleEventSet = eventDriver.createEventSet(text.toCharArray());
		EventSet expectedEventSet = new EventSet();
		List<Event> tmp = new ArrayList<Event>();

		tmp.add(new Event("Hello, Dr. Jones!", eventDriver));
        tmp.add(new Event("I'm not.feeling.too well today.", eventDriver));
        tmp.add(new Event("What's the matter Mr. Adams?", eventDriver));
        tmp.add(new Event("My stomach hurts, A.K.A, cramps.", eventDriver));

		expectedEventSet.addEvents(tmp);

		for(Event event : sampleEventSet){
			System.out.println(event);
		}
		
		assertTrue(expectedEventSet.equals(sampleEventSet));
	}

}

