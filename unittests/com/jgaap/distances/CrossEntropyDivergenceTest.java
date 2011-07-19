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
package com.jgaap.distances;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;

/**
 * @author darrenvescovi
 *
 */
public class CrossEntropyDivergenceTest {

	/**
	 * Test method for {@link com.jgaap.distances.CrossEntropyDivergence#divergence(com.jgaap.generics.EventSet, com.jgaap.generics.EventSet)}.
	 */
	@Test
	public void testDivergence() {
		
		
		EventSet known1 = new EventSet();
		EventSet known2;
		
		
		
		Vector<Event> test1 = new Vector<Event>();
		test1.add(new Event("mary"));
		test1.add(new Event("had"));
		test1.add(new Event("a"));
		test1.add(new Event("little"));
		test1.add(new Event("lamb"));
		test1.add(new Event("whose"));
		test1.add(new Event("fleece"));
		test1.add(new Event("was"));
		test1.add(new Event("white"));
		test1.add(new Event("as"));
		test1.add(new Event("snow"));
		known1.addEvents(test1);
		known1.setAuthor("Mary");
		
		
		
		
		//Same event set
		double Result = new CrossEntropyDivergence().divergence(known1, known1);
		
		//System.out.println(s);
		
		
		assertTrue(DistanceTestHelper.inRange(Result, 2.3978952, 0.0000001));
		
		//different event sets
		
		test1  = new Vector<Event>();
		Vector<Event> test2 = new Vector<Event>();
		
		test1.add(new Event("alpha"));
		test1.add(new Event("beta"));
		known1 = new EventSet();
		known1.addEvents(test1);
		
		test2.add(new Event("alpha"));
		test2.add(new Event("alpha"));
		test2.add(new Event("alpha"));
		test2.add(new Event("beta"));
		known2 = new EventSet();
		known2.addEvents(test2);
		
		Result = new CrossEntropyDivergence().divergence(known1, known2);
		
		//System.out.println(Result);
		
		assertTrue(DistanceTestHelper.inRange(Result, 0.836988, 0.000001));
		
		//Reversed Event Sets
		
		Result = new CrossEntropyDivergence().divergence(known2, known1);
		
		//System.out.println(Result);
		
		assertTrue(DistanceTestHelper.inRange(Result, 0.693147, 0.000001));
		
		//Test with Smoothing
		test2 = new Vector<Event>();
		
		test2.add(new Event("alpha"));
		test2.add(new Event("alpha"));
		test2.add(new Event("beta"));
		test2.add(new Event("gamma"));
		known2 = new EventSet();
		known2.addEvents(test2);
	
		
		//System.out.println("Start Here"); 
		
		Result = new CrossEntropyDivergence().divergence(known2, known1);
		
		//System.out.println(Result);
		
		assertTrue(DistanceTestHelper.inRange(Result, 0.5198603854199589, 0.000001));
		
		//revese the event sets
		
		Result = new CrossEntropyDivergence().divergence(known1, known2);
		
		//System.out.println(Result);
		
		assertTrue(DistanceTestHelper.inRange(Result, 1.0397207, 0.0000001));
	}
	

}
