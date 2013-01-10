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

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventSet;

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
		Document doc = new Document();
		doc.readStringText(
// list of acceptable words
"a all also an and any are as at be been but by can do down\n" +
"even every for from had has have her his if in into is it its\n" +
"may more must my no not now of on one only or our shall should\n" +
"so some such than that the their then there things this to up\n" +
"upon was were what when which who will with would your\n" +
// line below should be eliminated 
"distractor fail eliminate megafail lose gark hoser shimatta"
		);
		EventSet sampleEventSet = new MWFunctionWordsEventDriver().createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		tmp.add(new Event("a", null));
		tmp.add(new Event("all", null));
		tmp.add(new Event("also", null));
		tmp.add(new Event("an", null));
		tmp.add(new Event("and", null));
		tmp.add(new Event("any", null));
		tmp.add(new Event("are", null));
		tmp.add(new Event("as", null));
		tmp.add(new Event("at", null));
		tmp.add(new Event("be", null));
		tmp.add(new Event("been", null));
		tmp.add(new Event("but", null));
		tmp.add(new Event("by", null));
		tmp.add(new Event("can", null));
		tmp.add(new Event("do", null));
		tmp.add(new Event("down", null));
		tmp.add(new Event("even", null));
		tmp.add(new Event("every", null));
		tmp.add(new Event("for", null));
		tmp.add(new Event("from", null));
		tmp.add(new Event("had", null));
		tmp.add(new Event("has", null));
		tmp.add(new Event("have", null));
		tmp.add(new Event("her", null));
		tmp.add(new Event("his", null));
		tmp.add(new Event("if", null));
		tmp.add(new Event("in", null));
		tmp.add(new Event("into", null));
		tmp.add(new Event("is", null));
		tmp.add(new Event("it", null));
		tmp.add(new Event("its", null));
		tmp.add(new Event("may", null));
		tmp.add(new Event("more", null));
		tmp.add(new Event("must", null));
		tmp.add(new Event("my", null));
		tmp.add(new Event("no", null));
		tmp.add(new Event("not", null));
		tmp.add(new Event("now", null));
		tmp.add(new Event("of", null));
		tmp.add(new Event("on", null));
		tmp.add(new Event("one", null));
		tmp.add(new Event("only", null));
		tmp.add(new Event("or", null));
		tmp.add(new Event("our", null));
		tmp.add(new Event("shall", null));
		tmp.add(new Event("should", null));
		tmp.add(new Event("so", null));
		tmp.add(new Event("some", null));
		tmp.add(new Event("such", null));
		tmp.add(new Event("than", null));
		tmp.add(new Event("that", null));
		tmp.add(new Event("the", null));
		tmp.add(new Event("their", null));
		tmp.add(new Event("then", null));
		tmp.add(new Event("there", null));
		tmp.add(new Event("things", null));
		tmp.add(new Event("this", null));
		tmp.add(new Event("to", null));
		tmp.add(new Event("up", null));
		tmp.add(new Event("upon", null));
		tmp.add(new Event("was", null));
		tmp.add(new Event("were", null));
		tmp.add(new Event("what", null));
		tmp.add(new Event("when", null));
		tmp.add(new Event("which", null));
		tmp.add(new Event("who", null));
		tmp.add(new Event("will", null));
		tmp.add(new Event("with", null));
		tmp.add(new Event("would", null));
		tmp.add(new Event("your", null));
		expectedEventSet.addEvents(tmp);
//		System.out.println(expectedEventSet.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));
	}

}
