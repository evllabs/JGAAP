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

import org.junit.Test;

import com.jgaap.distances.CosineDistance;
import com.jgaap.generics.AnalyzeException;
import com.jgaap.util.Document;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;
import com.jgaap.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @author darrenvescovi
 * 
 */
public class NearestNeighborDriverTest {

	/**
	 * Test method for {@link
	 * com.jgaap.generics.NearestNeighborDriver#analyze(com.jgaap.generics.
	 * EventSet, List<EventSet>)}.
	 * @throws AnalyzeException 
	 */
	@Test
	public void testAnalyze() throws AnalyzeException {
		NearestNeighborDriver nearest = new NearestNeighborDriver();
		CosineDistance cosine = new CosineDistance();
		nearest.setDistance(cosine);

		EventSet known1 = new EventSet();
		EventSet known2 = new EventSet();
		EventSet unknown = new EventSet();

		Vector<Event> test1 = new Vector<Event>();
		test1.add(new Event("Mary", null));
		test1.add(new Event("had", null));
		test1.add(new Event("a", null));
		test1.add(new Event("little", null));
		test1.add(new Event("lamb", null));
		test1.add(new Event("whose", null));
		test1.add(new Event("fleece", null));
		test1.add(new Event("was", null));
		test1.add(new Event("white", null));
		test1.add(new Event("as", null));
		test1.add(new Event("snow.", null));
		known1.addEvents(test1);
		//known1.setAuthor("Mary");

		Vector<Event> test2 = new Vector<Event>();
		test2.add(new Event("Peter", null));
		test2.add(new Event("piper", null));
		test2.add(new Event("picked", null));
		test2.add(new Event("a", null));
		test2.add(new Event("pack", null));
		test2.add(new Event("of", null));
		test2.add(new Event("pickled", null));
		test2.add(new Event("peppers.", null));
		known2.addEvents(test2);
		//known2.setAuthor("Peter");

		Vector<Event> test3 = new Vector<Event>();
		test3.add(new Event("Mary", null));
		test3.add(new Event("had", null));
		test3.add(new Event("a", null));
		test3.add(new Event("little", null));
		test3.add(new Event("lambda", null));
		test3.add(new Event("whose", null));
		test3.add(new Event("syntax", null));
		test3.add(new Event("was", null));
		test3.add(new Event("white", null));
		test3.add(new Event("as", null));
		test3.add(new Event("snow.", null));
		unknown.addEvents(test3);

		List<Document> knowns = new ArrayList<Document>();
		Document knownDocument1 = new Document();
		knownDocument1.setAuthor("Mary");
		knownDocument1.addEventSet(null, known1);
		knowns.add(knownDocument1);
		Document knownDocument2 = new Document();
		knownDocument2.setAuthor("Peter");
		knownDocument2.addEventSet(null, known2);
		knowns.add(knownDocument2);
		
		Document unknownDocument = new Document();
		unknownDocument.addEventSet(null, unknown);

		nearest.train(knowns);
		List<Pair<String, Double>> t = nearest.analyze(unknownDocument);
		String r = t.get(0).getFirst();

		String s = "Mary";

		assertTrue(r.startsWith(s));

	}

}
