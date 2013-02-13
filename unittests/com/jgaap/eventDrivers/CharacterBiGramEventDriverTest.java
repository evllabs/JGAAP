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
 * @author Chris
 *
 */
public class CharacterBiGramEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.CharacterNGramEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 * @throws EventGenerationException 
	 */
	@Test
	public void testCreateEventSetDocumentSet() throws EventGenerationException {
		String text = ("abcdefghijklmnopqrstuvwxyz .");
		EventDriver eventDriver = new CharacterNGramEventDriver();
		eventDriver.setParameter("N", 2);
		EventSet sampleEventSet = eventDriver.createEventSet(text.toCharArray());
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		tmp.add(new Event("ab", eventDriver));
		tmp.add(new Event("bc", eventDriver));
		tmp.add(new Event("cd", eventDriver));
		tmp.add(new Event("de", eventDriver));
		tmp.add(new Event("ef", eventDriver));
		tmp.add(new Event("fg", eventDriver));
		tmp.add(new Event("gh", eventDriver));
		tmp.add(new Event("hi", eventDriver));
		tmp.add(new Event("ij", eventDriver));
		tmp.add(new Event("jk", eventDriver));
		tmp.add(new Event("kl", eventDriver));
		tmp.add(new Event("lm", eventDriver));
		tmp.add(new Event("mn", eventDriver));
		tmp.add(new Event("no", eventDriver));
		tmp.add(new Event("op", eventDriver));
		tmp.add(new Event("pq", eventDriver));
		tmp.add(new Event("qr", eventDriver));
		tmp.add(new Event("rs", eventDriver));
		tmp.add(new Event("st", eventDriver));
		tmp.add(new Event("tu", eventDriver));
		tmp.add(new Event("uv", eventDriver));
		tmp.add(new Event("vw", eventDriver));
		tmp.add(new Event("wx", eventDriver));
		tmp.add(new Event("xy", eventDriver));
		tmp.add(new Event("yz", eventDriver));
		expectedEventSet.addEvents(tmp);
		expectedEventSet.addEvent(new Event("z ", eventDriver));
		//expectedEventSet.addEvents(tmp);
		expectedEventSet.addEvent(new Event(" .", eventDriver));
// System.out.println(expectedEventSet.toString());
// System.out.println(sampleEventSet.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));
		
	}

}
