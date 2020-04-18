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
 * @author Patrick Juola
 *
 */
public class IntersectionDistanceTest {

	/**
	 * Test method for {@link com.jgaap.distances.IntersectionDistance#distance(com.jgaap.util.EventSet, com.jgaap.util.EventSet)}.
	 */
	@Test
	public void testDistance() {
		/* test 1 -- identical distributions */
		EventSet es1 = new EventSet();
		EventSet es2 = new EventSet();
		Vector<Event> test1 = new Vector<Event>();
		test1.add(new Event("alpha", null));
		test1.add(new Event("alpha", null));
		test1.add(new Event("beta", null));
		test1.add(new Event("gamma", null));
		es1.addEvents(test1);
		es2.addEvents(test1);
		assertTrue(new IntersectionDistance().distance(new EventMap(es1), new EventMap(es2)) == 0);

		/* test 2 -- different distributions, total overlap */
		Vector<Event> test2 = new Vector<Event>();
		es2=new EventSet();
		test2.add(new Event("alpha", null));
		test2.add(new Event("beta", null));
		test2.add(new Event("beta", null));
		test2.add(new Event("beta", null));
		test2.add(new Event("beta", null));
		test2.add(new Event("beta", null));
		test2.add(new Event("beta", null));
		test2.add(new Event("gamma", null));
		es2.addEvents(test2);
		assertTrue(new IntersectionDistance().distance(new EventMap(es1), new EventMap(es2)) == 0);

		/* test 3 -- totally disjoint (== 1.0) */
		es2=new EventSet();
		test2 = new Vector<Event>();
		test2.add(new Event("omega", null));
		es2.addEvents(test2);
		assertTrue(new IntersectionDistance().distance(new EventMap(es1), new EventMap(es2)) == 1.0);
		
		/* test 4 -- Partial overlap.  5 (3/3) elem., one in common */
		es2 = new EventSet();
		test2 = new Vector<Event>();
		test2.add(new Event("gamma", null));
		test2.add(new Event("delta", null));
		test2.add(new Event("epsilon", null));
		es2.addEvents(test2);

		double result = new IntersectionDistance().distance(new EventMap(es1), new EventMap(es2));
		assertTrue(DistanceTestHelper.inRange(result, 0.8, 0.0000000001));

		/* test 5 -- subset:  4 elem., one in common */
		es1=new EventSet();
		test1 = new Vector<Event>();
		test1.add(new Event("alpha", null));
		test1.add(new Event("beta", null));
		test1.add(new Event("gamma", null));
		test1.add(new Event("delta", null));
		es1.addEvents(test1);

		es2=new EventSet();
		test2 = new Vector<Event>();
		test2.add(new Event("delta", null));
		es2.addEvents(test2);
		result = new IntersectionDistance().distance(new EventMap(es1), new EventMap(es2));
		assertTrue(DistanceTestHelper.inRange(result, 0.75, 0.0000000001));

		/* test 6 -- superset:  4 elem., one in common */
		result = new IntersectionDistance().distance(new EventMap(es2), new EventMap(es1));
		assertTrue(DistanceTestHelper.inRange(result, 0.75, 0.0000000001));


	}


}
