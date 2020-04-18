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
 * @author Patrick Juola
 *
 */
public class _23LetterWordEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.MNLetterWordEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 * @throws EventGenerationException 
	 */
	@Test
	public void testCreateEventSetDocumentSet() throws EventGenerationException {

		String text = "a bb ccc dddd eeeee " +
						"1 22 333 4444 55555 " +
						"! @@ ### $$$$ %%%%% " +
						"A BB CCC DDDD EEEEE " ;


		EventDriver eventDriver = new MNLetterWordEventDriver();
		eventDriver.setParameter("M", 2);
		eventDriver.setParameter("N", 3);
		
		EventSet sampleEventSet = eventDriver.createEventSet(text.toCharArray());
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();

		tmp.add(new Event("bb", eventDriver));
		tmp.add(new Event("ccc", eventDriver));
		tmp.add(new Event("22", eventDriver));
		tmp.add(new Event("333", eventDriver));
		tmp.add(new Event("@@", eventDriver));
		tmp.add(new Event("###", eventDriver));
		tmp.add(new Event("BB", eventDriver));
		tmp.add(new Event("CCC", eventDriver));


		expectedEventSet.addEvents(tmp);
		assertTrue(expectedEventSet.equals(sampleEventSet));
	}
}
