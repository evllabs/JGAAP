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
package com.jgaap.distances;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;

/**
 * @author Joshua Booth
 *
 */
public class LZWDistanceTest {

	/**
	 * Test method for {@link com.jgaap.distances.LZWDivergence#divergence(com.jgaap.generics.EventSet, com.jgaap.generics.EventSet)}.
	 */
@Test
	public void testDistance() {

        /*Test 1 - Same*/
                EventSet es1 = new EventSet();
                EventSet es2 = new EventSet();
		Vector<Event> test1 = new Vector<Event>();
		test1.add(new Event("The"));
		test1.add(new Event("quick"));
		test1.add(new Event("brown"));
		test1.add(new Event("fox"));
		test1.add(new Event("jumps"));
		test1.add(new Event("over"));
		test1.add(new Event("the"));
		test1.add(new Event("lazy"));
		test1.add(new Event("dog"));
		test1.add(new Event("."));
		es1.addEvents(test1);
		es2.addEvents(test1);

		  
		assertTrue(new LZWDivergence().divergence(es1,es2) == 5);


		/*Note: Adding redundnat data will change the value based on the permutation*/




		/*Test 2 - Order*/


		es2 = new EventSet();
		    test1 = new Vector<Event>();
		test1.add(new Event("."));
		test1.add(new Event("dog"));
		test1.add(new Event("lazy"));
		test1.add(new Event("the"));
		test1.add(new Event("over"));
		test1.add(new Event("jumps"));
		test1.add(new Event("fox"));
		test1.add(new Event("brown"));
		test1.add(new Event("quick"));
		test1.add(new Event("The"));
		es1.addEvents(test1);
		  
		double distanceOne = new LZWDivergence().divergence(es1,es1);
		double distanceTwo = new  LZWDivergence().divergence(es2, es1);

		assertTrue(distanceOne == 10.0);
		 assertTrue(distanceTwo == 21.0);    

}
}


