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
/**
 * 
 */
package com.jgaap.eventDrivers;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

/**
 * @author michael
 *
 */
public class CharacterEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.CharacterEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 * @throws EventGenerationException 
	 */
	@Test
	public void testCreateEventSetDocumentSet() throws EventGenerationException {
		String text = ("abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz.");
		EventDriver eventDriver = new CharacterEventDriver();
		EventSet sampleEventSet = eventDriver.createEventSet(text.toCharArray());
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		tmp.add(new Event("a", eventDriver));
		tmp.add(new Event("b", eventDriver));
		tmp.add(new Event("c", eventDriver));
		tmp.add(new Event("d", eventDriver));
		tmp.add(new Event("e", eventDriver));
		tmp.add(new Event("f", eventDriver));
		tmp.add(new Event("g", eventDriver));
		tmp.add(new Event("h", eventDriver));
		tmp.add(new Event("i", eventDriver));
		tmp.add(new Event("j", eventDriver));
		tmp.add(new Event("k", eventDriver));
		tmp.add(new Event("l", eventDriver));
		tmp.add(new Event("m", eventDriver));
		tmp.add(new Event("n", eventDriver));
		tmp.add(new Event("o", eventDriver));
		tmp.add(new Event("p", eventDriver));
		tmp.add(new Event("q", eventDriver));
		tmp.add(new Event("r", eventDriver));
		tmp.add(new Event("s", eventDriver));
		tmp.add(new Event("t", eventDriver));
		tmp.add(new Event("u", eventDriver));
		tmp.add(new Event("v", eventDriver));
		tmp.add(new Event("w", eventDriver));
		tmp.add(new Event("x", eventDriver));
		tmp.add(new Event("y", eventDriver));
		tmp.add(new Event("z", eventDriver));
		expectedEventSet.addEvents(tmp);
		expectedEventSet.addEvent(new Event(" ", eventDriver));
		expectedEventSet.addEvents(tmp);
		expectedEventSet.addEvent(new Event(".", eventDriver));
		assertTrue(expectedEventSet.equals(sampleEventSet));
		
	}

}
