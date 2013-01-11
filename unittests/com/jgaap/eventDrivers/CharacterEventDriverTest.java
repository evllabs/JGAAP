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

import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;

/**
 * @author michael
 *
 */
public class CharacterEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.CharacterEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 */
	@Test
	public void testCreateEventSetDocumentSet() {
		String text = ("abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz.");
		EventSet sampleEventSet = new CharacterEventDriver().createEventSet(text.toCharArray());
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		tmp.add(new Event("a", null));
		tmp.add(new Event("b", null));
		tmp.add(new Event("c", null));
		tmp.add(new Event("d", null));
		tmp.add(new Event("e", null));
		tmp.add(new Event("f", null));
		tmp.add(new Event("g", null));
		tmp.add(new Event("h", null));
		tmp.add(new Event("i", null));
		tmp.add(new Event("j", null));
		tmp.add(new Event("k", null));
		tmp.add(new Event("l", null));
		tmp.add(new Event("m", null));
		tmp.add(new Event("n", null));
		tmp.add(new Event("o", null));
		tmp.add(new Event("p", null));
		tmp.add(new Event("q", null));
		tmp.add(new Event("r", null));
		tmp.add(new Event("s", null));
		tmp.add(new Event("t", null));
		tmp.add(new Event("u", null));
		tmp.add(new Event("v", null));
		tmp.add(new Event("w", null));
		tmp.add(new Event("x", null));
		tmp.add(new Event("y", null));
		tmp.add(new Event("z", null));
		expectedEventSet.addEvents(tmp);
		expectedEventSet.addEvent(new Event(" ", null));
		expectedEventSet.addEvents(tmp);
		expectedEventSet.addEvent(new Event(".", null));
		assertTrue(expectedEventSet.equals(sampleEventSet));
		
	}

}
