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

import com.jgaap.util.Event;
import com.jgaap.util.EventMap;
import com.jgaap.util.EventSet;

/**
 * @author darrenvescovi
 *
 */
public class CrossEntropyDivergenceTest {

	/**
	 * Test method for {@link com.jgaap.distances.CrossEntropyDivergence#divergence(com.jgaap.util.EventSet, com.jgaap.util.EventSet)}.
	 */
	@Test
	public void testDivergence() {
		
		
		EventSet known1 = new EventSet();
		EventSet known2;
		
		
		
		Vector<Event> test1 = new Vector<Event>();
		test1.add(new Event("mary", null));
		test1.add(new Event("had", null));
		test1.add(new Event("a", null));
		test1.add(new Event("little", null));
		test1.add(new Event("lamb", null));
		test1.add(new Event("whose", null));
		test1.add(new Event("fleece", null));
		test1.add(new Event("was", null));
		test1.add(new Event("white", null));
		test1.add(new Event("as", null));
		test1.add(new Event("snow", null));
		known1.addEvents(test1);
		//known1.setAuthor("Mary");
		
		
		
		
		//Same event set
		double Result = new CrossEntropyDivergence().divergence(new EventMap(known1), new EventMap(known1));
		
		//System.out.println(s);
		
		
		assertTrue(DistanceTestHelper.inRange(Result, 2.3978952, 0.0000001));
		
		//different event sets
		
		test1  = new Vector<Event>();
		Vector<Event> test2 = new Vector<Event>();
		
		test1.add(new Event("alpha", null));
		test1.add(new Event("beta", null));
		known1 = new EventSet();
		known1.addEvents(test1);
		
		test2.add(new Event("alpha", null));
		test2.add(new Event("alpha", null));
		test2.add(new Event("alpha", null));
		test2.add(new Event("beta", null));
		known2 = new EventSet();
		known2.addEvents(test2);
		
		Result = new CrossEntropyDivergence().divergence(new EventMap(known1), new EventMap(known2));
		
		//System.out.println(Result);
		
		assertTrue(DistanceTestHelper.inRange(Result, 0.836988, 0.000001));
		
		//Reversed Event Sets
		
		Result = new CrossEntropyDivergence().divergence(new EventMap(known2), new EventMap(known1));
		
		//System.out.println(Result);
		
		assertTrue(DistanceTestHelper.inRange(Result, 0.693147, 0.000001));
		
		//Test with Smoothing
		test2 = new Vector<Event>();
		
		test2.add(new Event("alpha", null));
		test2.add(new Event("alpha", null));
		test2.add(new Event("beta", null));
		test2.add(new Event("gamma", null));
		known2 = new EventSet();
		known2.addEvents(test2);
	
		
		//System.out.println("Start Here"); 
		
		Result = new CrossEntropyDivergence().divergence(new EventMap(known2), new EventMap(known1));
		
		//System.out.println(Result);
		
		assertTrue(DistanceTestHelper.inRange(Result, 0.5198603854199589, 0.000001));
		
		//revese the event sets
		
		Result = new CrossEntropyDivergence().divergence(new EventMap(known1), new EventMap(known2));
		
		//System.out.println(Result);
		
		assertTrue(DistanceTestHelper.inRange(Result, 1.0397207, 0.0000001));
	}
	

}
