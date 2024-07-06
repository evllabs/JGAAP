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
package com.jgaap.eventCullers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

public class ExtremeCullerTest {

	@Test
	public void testCull() {
		List<EventSet> eventSets = new ArrayList<EventSet>();
		EventSet eventSet1 = new EventSet();
		eventSet1.addEvent(new Event("The", null));
		eventSet1.addEvent(new Event("quick", null));
		eventSet1.addEvent(new Event("brown", null));
		eventSet1.addEvent(new Event("fox", null));
		eventSet1.addEvent(new Event("jumps", null));
		eventSet1.addEvent(new Event("over", null));
		eventSet1.addEvent(new Event("the", null));
		eventSet1.addEvent(new Event("lazy", null));
		eventSet1.addEvent(new Event("dog", null));
		eventSets.add(eventSet1);
		EventSet eventSet2 = new EventSet();
		eventSet2.addEvent(new Event("The", null));
		eventSet2.addEvent(new Event("lazy", null));
		eventSet2.addEvent(new Event("grey", null));
		eventSet2.addEvent(new Event("fox", null));
		eventSet2.addEvent(new Event("jumps", null));
		eventSet2.addEvent(new Event("over", null));
		eventSet2.addEvent(new Event("the", null));
		eventSet2.addEvent(new Event("dead", null));
		eventSet2.addEvent(new Event("dog", null));
		eventSets.add(eventSet2);
		EventSet eventSet3 = new EventSet();
		eventSet3.addEvent(new Event("A", null));
		eventSet3.addEvent(new Event("slow", null));
		eventSet3.addEvent(new Event("brown", null));
		eventSet3.addEvent(new Event("fox", null));
		eventSet3.addEvent(new Event("leaps", null));
		eventSet3.addEvent(new Event("over", null));
		eventSet3.addEvent(new Event("the", null));
		eventSet3.addEvent(new Event("tired", null));
		eventSet3.addEvent(new Event("dog", null));
		eventSets.add(eventSet3);
		ExtremeCuller extremeCuller = new ExtremeCuller();
		Set<Event> results = extremeCuller.train(eventSets);
		Set<Event> expected = new HashSet<Event>();
		expected.add(new Event("fox", null));
		expected.add(new Event("over", null));
		expected.add(new Event("the", null));
		expected.add(new Event("dog", null));
		assertTrue(results.equals(expected));
	}

}
