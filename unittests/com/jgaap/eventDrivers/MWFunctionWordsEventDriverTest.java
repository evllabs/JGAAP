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
public class MWFunctionWordsEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.MWFunctionWordsEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 * @throws EventGenerationException 
	 */
	@Test
	public void testCreateEventSetDocumentSet() throws EventGenerationException {
		String text = (
// list of acceptable words
"a all also an and any are as at be been but by can do down\n" +
"even every for from had has have her his if in into is it its\n" +
"may more must my no not now of on one only or our shall should\n" +
"so some such than that the their then there things this to up\n" +
"upon was were what when which who will with would your\n" +
// line below should be eliminated 
"distractor fail eliminate megafail lose gark hoser shimatta"
		);
		EventDriver eventDriver = new MWFunctionWordsEventDriver();
		EventSet sampleEventSet = eventDriver.createEventSet(text.toCharArray());
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		tmp.add(new Event("a", eventDriver));
		tmp.add(new Event("all", eventDriver));
		tmp.add(new Event("also", eventDriver));
		tmp.add(new Event("an", eventDriver));
		tmp.add(new Event("and", eventDriver));
		tmp.add(new Event("any", eventDriver));
		tmp.add(new Event("are", eventDriver));
		tmp.add(new Event("as", eventDriver));
		tmp.add(new Event("at", eventDriver));
		tmp.add(new Event("be", eventDriver));
		tmp.add(new Event("been", eventDriver));
		tmp.add(new Event("but", eventDriver));
		tmp.add(new Event("by", eventDriver));
		tmp.add(new Event("can", eventDriver));
		tmp.add(new Event("do", eventDriver));
		tmp.add(new Event("down", eventDriver));
		tmp.add(new Event("even", eventDriver));
		tmp.add(new Event("every", eventDriver));
		tmp.add(new Event("for", eventDriver));
		tmp.add(new Event("from", eventDriver));
		tmp.add(new Event("had", eventDriver));
		tmp.add(new Event("has", eventDriver));
		tmp.add(new Event("have", eventDriver));
		tmp.add(new Event("her", eventDriver));
		tmp.add(new Event("his", eventDriver));
		tmp.add(new Event("if", eventDriver));
		tmp.add(new Event("in", eventDriver));
		tmp.add(new Event("into", eventDriver));
		tmp.add(new Event("is", eventDriver));
		tmp.add(new Event("it", eventDriver));
		tmp.add(new Event("its", eventDriver));
		tmp.add(new Event("may", eventDriver));
		tmp.add(new Event("more", eventDriver));
		tmp.add(new Event("must", eventDriver));
		tmp.add(new Event("my", eventDriver));
		tmp.add(new Event("no", eventDriver));
		tmp.add(new Event("not", eventDriver));
		tmp.add(new Event("now", eventDriver));
		tmp.add(new Event("of", eventDriver));
		tmp.add(new Event("on", eventDriver));
		tmp.add(new Event("one", eventDriver));
		tmp.add(new Event("only", eventDriver));
		tmp.add(new Event("or", eventDriver));
		tmp.add(new Event("our", eventDriver));
		tmp.add(new Event("shall", eventDriver));
		tmp.add(new Event("should", eventDriver));
		tmp.add(new Event("so", eventDriver));
		tmp.add(new Event("some", eventDriver));
		tmp.add(new Event("such", eventDriver));
		tmp.add(new Event("than", eventDriver));
		tmp.add(new Event("that", eventDriver));
		tmp.add(new Event("the", eventDriver));
		tmp.add(new Event("their", eventDriver));
		tmp.add(new Event("then", eventDriver));
		tmp.add(new Event("there", eventDriver));
		tmp.add(new Event("things", eventDriver));
		tmp.add(new Event("this", eventDriver));
		tmp.add(new Event("to", eventDriver));
		tmp.add(new Event("up", eventDriver));
		tmp.add(new Event("upon", eventDriver));
		tmp.add(new Event("was", eventDriver));
		tmp.add(new Event("were", eventDriver));
		tmp.add(new Event("what", eventDriver));
		tmp.add(new Event("when", eventDriver));
		tmp.add(new Event("which", eventDriver));
		tmp.add(new Event("who", eventDriver));
		tmp.add(new Event("will", eventDriver));
		tmp.add(new Event("with", eventDriver));
		tmp.add(new Event("would", eventDriver));
		tmp.add(new Event("your", eventDriver));
		expectedEventSet.addEvents(tmp);
//		System.out.println(expectedEventSet.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));
	}

}
