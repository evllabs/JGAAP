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

import com.jgaap.classifiers.NearestNeighborDriver;
import com.jgaap.distances.CosineDistance;
import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

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
		test1.add(new Event("Mary"));
		test1.add(new Event("had"));
		test1.add(new Event("a"));
		test1.add(new Event("little"));
		test1.add(new Event("lamb"));
		test1.add(new Event("whose"));
		test1.add(new Event("fleece"));
		test1.add(new Event("was"));
		test1.add(new Event("white"));
		test1.add(new Event("as"));
		test1.add(new Event("snow."));
		known1.addEvents(test1);
		known1.setAuthor("Mary");

		Vector<Event> test2 = new Vector<Event>();
		test2.add(new Event("Peter"));
		test2.add(new Event("piper"));
		test2.add(new Event("picked"));
		test2.add(new Event("a"));
		test2.add(new Event("pack"));
		test2.add(new Event("of"));
		test2.add(new Event("pickled"));
		test2.add(new Event("peppers."));
		known2.addEvents(test2);
		known2.setAuthor("Peter");

		Vector<Event> test3 = new Vector<Event>();
		test3.add(new Event("Mary"));
		test3.add(new Event("had"));
		test3.add(new Event("a"));
		test3.add(new Event("little"));
		test3.add(new Event("lambda"));
		test3.add(new Event("whose"));
		test3.add(new Event("syntax"));
		test3.add(new Event("was"));
		test3.add(new Event("white"));
		test3.add(new Event("as"));
		test3.add(new Event("snow."));
		unknown.addEvents(test3);

		Vector<EventSet> esv = new Vector<EventSet>();
		esv.add(known1);
		esv.add(known2);

		System.out.println("There once was a lass from Nantucket");
		nearest.train(esv);
		List<Pair<String, Double>> t = nearest.analyze(unknown);
		String r = t.get(0).getFirst();

		String s = "Mary";

		assertTrue(r.startsWith(s));

	}

}
