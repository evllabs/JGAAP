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

import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventDriver;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

/**
 * @author Patrick Juola
 *
 */
public class SyllableTransitionEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.WordSyllablesEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 * @throws EventGenerationException 
	 */
	@Test
	public void testCreateEventSetDocumentSet() throws EventGenerationException {
		/* test case 1 -- bigrams */
		String text = (
			"cat " +	// 1 syllable
			"at " +		// 1 syllable
			"a " + 		// 1 syllable
			"to " + 	// 1 syllable
			"oat " + 	// 1 syllable
			"oooo " + 	// 1 syllable
			"too " + 	// 1 syllable
			"dye " + 	// 1 syllable
			"eieio " + 	// 1 syllable
			"otto " + 	// 2 syllables
			"duty " + 	// 2 syllables
			"atom " + 	// 2 syllables
			"abloom " + 	// 2 syllables
			"banana " + 	// 3 syllables
			"dr " + 	// 0 syllables but becomes 1
			"!@#$%^&^ " + 	// 0 syllables but becomes 1
			"867-5309 " + 	// 0 syllables but becomes 1
			"CAT " +	// 1 syllable
			"AT " +		// 1 syllable
			"A " + 		// 1 syllable
			"TO " + 	// 1 syllable
			"OAT " + 	// 1 syllable
			"OOOO " + 	// 1 syllable
			"TOO " + 	// 1 syllable
			"DYE " + 	// 1 syllable
			"EIEIO " + 	// 1 syllable
			"EieIOo " + 	// 1 syllable
			"OTTO " 	// 2 syllables
		);

		EventDriver eventDriver = new SyllableTransitionEventDriver();
		EventSet sampleEventSet = eventDriver.createEventSet(text.toCharArray());
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		tmp.add(new Event("[1, 1]", eventDriver));
		tmp.add(new Event("[1, 1]", eventDriver));
		tmp.add(new Event("[1, 1]", eventDriver));
		tmp.add(new Event("[1, 1]", eventDriver));
		tmp.add(new Event("[1, 1]", eventDriver));
		tmp.add(new Event("[1, 1]", eventDriver));
		tmp.add(new Event("[1, 1]", eventDriver));
		tmp.add(new Event("[1, 1]", eventDriver));
		tmp.add(new Event("[1, 2]", eventDriver));
		tmp.add(new Event("[2, 2]", eventDriver));
		tmp.add(new Event("[2, 2]", eventDriver));
		tmp.add(new Event("[2, 2]", eventDriver));
		tmp.add(new Event("[2, 3]", eventDriver));
		tmp.add(new Event("[3, 1]", eventDriver));
		tmp.add(new Event("[1, 1]", eventDriver));
		tmp.add(new Event("[1, 1]", eventDriver));
		tmp.add(new Event("[1, 1]", eventDriver));
		tmp.add(new Event("[1, 1]", eventDriver));
		tmp.add(new Event("[1, 1]", eventDriver));
		tmp.add(new Event("[1, 1]", eventDriver));
		tmp.add(new Event("[1, 1]", eventDriver));
		tmp.add(new Event("[1, 1]", eventDriver));
		tmp.add(new Event("[1, 1]", eventDriver));
		tmp.add(new Event("[1, 1]", eventDriver));
		tmp.add(new Event("[1, 1]", eventDriver));
		tmp.add(new Event("[1, 1]", eventDriver));
		tmp.add(new Event("[1, 2]", eventDriver));

		expectedEventSet.addEvents(tmp);
		System.out.println(expectedEventSet.toString());
		System.out.println(sampleEventSet.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));

		/* test case 2 -- trigrams */
		EventDriver ed = new SyllableTransitionEventDriver();
		ed.setParameter("N","3"); 
		sampleEventSet = ed.createEventSet(text.toCharArray());

		expectedEventSet = new EventSet();
		tmp = new Vector<Event>();

		tmp.add(new Event("[1, 1, 1]", ed));
		tmp.add(new Event("[1, 1, 1]", ed));
		tmp.add(new Event("[1, 1, 1]", ed));
		tmp.add(new Event("[1, 1, 1]", ed));
		tmp.add(new Event("[1, 1, 1]", ed));
		tmp.add(new Event("[1, 1, 1]", ed));
		tmp.add(new Event("[1, 1, 1]", ed));
		tmp.add(new Event("[1, 1, 2]", ed));
		tmp.add(new Event("[1, 2, 2]", ed));
		tmp.add(new Event("[2, 2, 2]", ed));
		tmp.add(new Event("[2, 2, 2]", ed));
		tmp.add(new Event("[2, 2, 3]", ed));
		tmp.add(new Event("[2, 3, 1]", ed));
		tmp.add(new Event("[3, 1, 1]", ed));
		tmp.add(new Event("[1, 1, 1]", ed));
		tmp.add(new Event("[1, 1, 1]", ed));
		tmp.add(new Event("[1, 1, 1]", ed));
		tmp.add(new Event("[1, 1, 1]", ed));
		tmp.add(new Event("[1, 1, 1]", ed));
		tmp.add(new Event("[1, 1, 1]", ed));
		tmp.add(new Event("[1, 1, 1]", ed));
		tmp.add(new Event("[1, 1, 1]", ed));
		tmp.add(new Event("[1, 1, 1]", ed));
		tmp.add(new Event("[1, 1, 1]", ed));
		tmp.add(new Event("[1, 1, 1]", ed));
		tmp.add(new Event("[1, 1, 2]", ed));

		expectedEventSet.addEvents(tmp);
		System.out.println(expectedEventSet.toString());
		System.out.println(sampleEventSet.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));
	}
}
