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
public class WordBiGramEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.WordNGramEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 * @throws EventGenerationException 
	 */
	@Test
	public void testCreateEventSetDocumentSet() throws EventGenerationException {
		String text = (
"Mary had a little lamb;\n" +
"Its fleece was white as snow.\n" +
"And everywhere that Mary went,\n" +
"The lamb was sure to go.");
		EventDriver eventDriver = new WordNGramEventDriver();
		eventDriver.setParameter("N", 2);
		EventSet sampleEventSet = eventDriver.createEventSet(text.toCharArray());
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		tmp.add(new Event("[Mary, had]", eventDriver));
		tmp.add(new Event("[had, a]", eventDriver));
		tmp.add(new Event("[a, little]", eventDriver));
		tmp.add(new Event("[little, lamb;]", eventDriver));
		tmp.add(new Event("[lamb;, Its]", eventDriver));
		tmp.add(new Event("[Its, fleece]", eventDriver));
		tmp.add(new Event("[fleece, was]", eventDriver));
		tmp.add(new Event("[was, white]", eventDriver));
		tmp.add(new Event("[white, as]", eventDriver));
		tmp.add(new Event("[as, snow.]", eventDriver));
		tmp.add(new Event("[snow., And]", eventDriver));
		tmp.add(new Event("[And, everywhere]", eventDriver));
		tmp.add(new Event("[everywhere, that]", eventDriver));
		tmp.add(new Event("[that, Mary]", eventDriver));
		tmp.add(new Event("[Mary, went,]", eventDriver));
		tmp.add(new Event("[went,, The]", eventDriver));
		tmp.add(new Event("[The, lamb]", eventDriver));
		tmp.add(new Event("[lamb, was]", eventDriver));
		tmp.add(new Event("[was, sure]", eventDriver));
		tmp.add(new Event("[sure, to]", eventDriver));
		tmp.add(new Event("[to, go.]", eventDriver));

		expectedEventSet.addEvents(tmp);
//System.out.println(expectedEventSet.toString());
//System.out.println(sampleEventSet.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));
		
	}

}
