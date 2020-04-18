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
 * @author Juola
 *
 */
public class PearsonCorrelationDistanceTest {

	/**
	 * Test method for {@link com.jgaap.distances.PearsonCorrelationDistance#distance(com.jgaap.util.EventSet, com.jgaap.util.EventSet)}.
	 */
	@Test
	public void testDistance() {
		// n.b. data from testset 2 from http://davidmlane.com/hyperstat/A56626.html


		// test 1 : identical distributions 
		EventSet es1 = new EventSet();
		EventSet es2 = new EventSet();
		Vector<Event> test1 = new Vector<Event>();
		test1.add(new Event("alpha", null));
		test1.add(new Event("beta", null));
		test1.add(new Event("beta", null));
		test1.add(new Event("gamma", null));
		test1.add(new Event("gamma", null));
		test1.add(new Event("gamma", null));
		es1.addEvents(test1);
		es2.addEvents(test1);
		assertTrue(new PearsonCorrelationDistance().distance(new EventMap(es1), new EventMap(es2)) == 0);

		// test 2 : identical probabilities but different distributions 
		es1=new EventSet();
		es2=new EventSet();
		test1 = new Vector<Event>();
		Vector<Event> test2 = new Vector<Event>();
		test1.add(new Event("A", null));
		test1.add(new Event("B", null));
		test1.add(new Event("B", null));
		test1.add(new Event("C", null));
		test1.add(new Event("D", null));
		test1.add(new Event("E", null));

		test2.add(new Event("A", null));
		test2.add(new Event("A", null));
		test2.add(new Event("B", null));
		test2.add(new Event("B", null));
		test2.add(new Event("B", null));
		test2.add(new Event("B", null));
		test2.add(new Event("C", null));
		test2.add(new Event("C", null));
		test2.add(new Event("D", null));
		test2.add(new Event("D", null));
		test2.add(new Event("E", null));
		test2.add(new Event("E", null));

		es1.addEvents(test1);
		es2.addEvents(test2);
		double result = new PearsonCorrelationDistance().distance(new EventMap(es1), new EventMap(es2));
		//System.out.println(result);
		assertTrue(DistanceTestHelper.inRange(result, 0.0, 0.0000000001));

		// test 3 : Perfect anticorrelation
		es1=new EventSet();
		es2=new EventSet();
		test1 = new Vector<Event>();
		test2 = new Vector<Event>();

		test1.add(new Event("A", null));
		test1.add(new Event("A", null));
		test1.add(new Event("A", null));
		test1.add(new Event("B", null));
		test1.add(new Event("B", null));
		test1.add(new Event("C", null));

		test2.add(new Event("C", null));
		test2.add(new Event("C", null));
		test2.add(new Event("C", null));
		test2.add(new Event("B", null));
		test2.add(new Event("B", null));
		test2.add(new Event("A", null));

		es1.addEvents(test1);
		es2.addEvents(test2);
		result = new PearsonCorrelationDistance().distance(new EventMap(es1), new EventMap(es2));
		//System.out.println(result);
		assertTrue(DistanceTestHelper.inRange(result, 2.0, 0.0000000001));

		// test 4 : non-trivial calculation
		es1=new EventSet();
		es2=new EventSet();
		test1 = new Vector<Event>();
		test2 = new Vector<Event>();

		test1.add(new Event("A", null));
		test1.add(new Event("B", null));
		test1.add(new Event("B", null));
		test1.add(new Event("C", null));
		test1.add(new Event("C", null));
		test1.add(new Event("C", null));

		test2.add(new Event("A", null));
		test2.add(new Event("A", null));
		test2.add(new Event("B", null));
		test2.add(new Event("B", null));
		test2.add(new Event("B", null));
		test2.add(new Event("B", null));
		test2.add(new Event("B", null));
		test2.add(new Event("C", null));
		test2.add(new Event("C", null));
		test2.add(new Event("C", null));
		test2.add(new Event("C", null));
		test2.add(new Event("C", null));
		test2.add(new Event("C", null));

		es1.addEvents(test1);
		es2.addEvents(test2);
		result = new PearsonCorrelationDistance().distance(new EventMap(es1), new EventMap(es2));
		System.out.println(result);
		assertTrue(DistanceTestHelper.inRange(result, 1-0.9608, 0.001));

		// test 5 : edge case (NaN)
		es1=new EventSet();
		es2=new EventSet();
		test1 = new Vector<Event>();
		test2 = new Vector<Event>();

		test1.add(new Event("A", null));
		test1.add(new Event("B", null));
		test1.add(new Event("C", null));

		test2.add(new Event("A", null));
		test2.add(new Event("B", null));
		test2.add(new Event("B", null));
		test2.add(new Event("C", null));
		test2.add(new Event("C", null));
		test2.add(new Event("C", null));

		es1.addEvents(test1);
		es2.addEvents(test2);
		result = new PearsonCorrelationDistance().distance(new EventMap(es1), new EventMap(es2));
		System.out.println(result);
		assertTrue(DistanceTestHelper.inRange(result, 1.0, 0.001));

		// test 6 : edge case (point mass)
		es1=new EventSet();
		es2=new EventSet();
		test1 = new Vector<Event>();
		test2 = new Vector<Event>();

		test1.add(new Event("A", null));
		test1.add(new Event("B", null));
		test1.add(new Event("C", null));

		test2.add(new Event("A", null));
		test2.add(new Event("A", null));
		test2.add(new Event("B", null));
		test2.add(new Event("B", null));
		test2.add(new Event("C", null));
		test2.add(new Event("C", null));

		es1.addEvents(test1);
		es2.addEvents(test2);
		result = new PearsonCorrelationDistance().distance(new EventMap(es1), new EventMap(es2));
		System.out.println(result);
		assertTrue(DistanceTestHelper.inRange(result, 0.0, 0.001));


	}

}
