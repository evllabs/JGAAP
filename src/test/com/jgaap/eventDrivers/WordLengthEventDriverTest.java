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
public class WordLengthEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.WordLengthEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 * @throws EventGenerationException 
	 */
	@Test
	public void testCreateEventSetDocumentSet() throws EventGenerationException {
		/* test case 1 -- no punctuation */
		String text = ("sir I send a rhyme excelling\n"+
				   "in sacred truth and rigid spelling\n"+
				   "numerical sprites elucidate\n"+
				   "for me the lexicons full weight\n"+
				   "if nature gain who can complain\n"+
				   "tho dr johnson fulminate");
		EventDriver eventDriver = new WordLengthEventDriver();
		EventSet sampleEventSet = eventDriver.createEventSet(text.toCharArray());
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		tmp.add(new Event("3", eventDriver));
		tmp.add(new Event("1", eventDriver));
		tmp.add(new Event("4", eventDriver));
		tmp.add(new Event("1", eventDriver));
		tmp.add(new Event("5", eventDriver));
		tmp.add(new Event("9", eventDriver));
		tmp.add(new Event("2", eventDriver));
		tmp.add(new Event("6", eventDriver));
		tmp.add(new Event("5", eventDriver));
		tmp.add(new Event("3", eventDriver));
		tmp.add(new Event("5", eventDriver));
		tmp.add(new Event("8", eventDriver));
		tmp.add(new Event("9", eventDriver));
		tmp.add(new Event("7", eventDriver));
		tmp.add(new Event("9", eventDriver));
		tmp.add(new Event("3", eventDriver));
		tmp.add(new Event("2", eventDriver));
		tmp.add(new Event("3", eventDriver));
		tmp.add(new Event("8", eventDriver));
		tmp.add(new Event("4", eventDriver));
		tmp.add(new Event("6", eventDriver));
		tmp.add(new Event("2", eventDriver));
		tmp.add(new Event("6", eventDriver));
		tmp.add(new Event("4", eventDriver));
		tmp.add(new Event("3", eventDriver));
		tmp.add(new Event("3", eventDriver));
		tmp.add(new Event("8", eventDriver));
		tmp.add(new Event("3", eventDriver));
		tmp.add(new Event("2", eventDriver));
		tmp.add(new Event("7", eventDriver));
		tmp.add(new Event("9", eventDriver));
		expectedEventSet.addEvents(tmp);
		assertTrue(expectedEventSet.equals(sampleEventSet));

		/* test case 2 -- punctuation */
		/* n.b. no new declarations */
		text = (
			"`the' quick brown \"fox\" isn't very? dumb!");
		sampleEventSet = eventDriver.createEventSet(text.toCharArray());
		expectedEventSet = new EventSet();
		tmp = new Vector<Event>();
		tmp.add(new Event("5", eventDriver));
		tmp.add(new Event("5", eventDriver));
		tmp.add(new Event("5", eventDriver));
		tmp.add(new Event("5", eventDriver));
		tmp.add(new Event("5", eventDriver));
		tmp.add(new Event("5", eventDriver));
		tmp.add(new Event("5", eventDriver));
		expectedEventSet.addEvents(tmp);
		assertTrue(expectedEventSet.equals(sampleEventSet));

		/* test case 3 -- no words */
		text = ("\t         \t\n");
		sampleEventSet = new WordLengthEventDriver().createEventSet(text.toCharArray());
		expectedEventSet = new EventSet();
		assertTrue(expectedEventSet.equals(sampleEventSet));
		
	}

}
