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
package com.jgaap.classifiers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.junit.Test;

import com.jgaap.util.Document;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;
import com.jgaap.util.Pair;

/**
 * @author darrenvescovi
 *
 */
public class NullHistAnalysisTest {

	/**
	 * Test method for {@link com.jgaap.classifiers.NullHistAnalysis#analyze(com.jgaap.generics.EventSet, List<EventSet>)}.
	 */
	@Test
	public void testAnalyze() {
		EventSet known1 = new EventSet();
		EventSet unknown = new EventSet();
		
		Vector<Event> test1 = new Vector<Event>();
		test1.add(new Event("The", null));
		test1.add(new Event("quick", null));
		test1.add(new Event("brown", null));
		test1.add(new Event("fox", null));
		test1.add(new Event("jumps", null));
		test1.add(new Event("over", null));
		test1.add(new Event("the", null));
		test1.add(new Event("lazy", null));
		test1.add(new Event("dog", null));
		test1.add(new Event(".", null));
		known1.addEvents(test1);
		unknown.addEvents(test1);
		
		List<Document> knowns = new ArrayList<Document>();
		Document knownDocument1 = new Document();
		//knownDocument1.setAuthor(known1.getAuthor());
		knownDocument1.addEventSet(null, known1);
		knowns.add(knownDocument1);

		
		Document unknownDocument = new Document();
		unknownDocument.addEventSet(null, unknown);
		NullHistAnalysis nullHistAnalysis = new NullHistAnalysis();
		nullHistAnalysis.train(knowns);
		List<Pair<String, Double>> t = nullHistAnalysis.analyze(unknownDocument);
		String r = t.get(0).getFirst();
		String s = "No analysis performed.\n";
		assertTrue(r.equals(s));
	}

}
