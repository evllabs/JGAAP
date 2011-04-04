// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
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
 * @author Patrick Juola
 *
 */
public class IntersectionDistanceTest {

	/**
	 * Test method for {@link com.jgaap.distances.IntersectionDistance#distance(com.jgaap.generics.EventSet, com.jgaap.generics.EventSet)}.
	 */
	@Test
	public void testDistance() {
		/* test 1 -- identical distributions */
		EventSet es1 = new EventSet();
		EventSet es2 = new EventSet();
		Vector<Event> test1 = new Vector<Event>();
		test1.add(new Event("alpha"));
		test1.add(new Event("alpha"));
		test1.add(new Event("beta"));
		test1.add(new Event("gamma"));
		es1.addEvents(test1);
		es2.addEvents(test1);
		assertTrue(new IntersectionDistance().distance(es1, es2) == 0);

		/* test 2 -- different distributions, total overlap */
		Vector<Event> test2 = new Vector<Event>();
		es2=new EventSet();
		test2.add(new Event("alpha"));
		test2.add(new Event("beta"));
		test2.add(new Event("beta"));
		test2.add(new Event("beta"));
		test2.add(new Event("beta"));
		test2.add(new Event("beta"));
		test2.add(new Event("beta"));
		test2.add(new Event("gamma"));
		es2.addEvents(test2);
		assertTrue(new IntersectionDistance().distance(es1, es2) == 0);

		/* test 3 -- totally disjoint (== 1.0) */
		es2=new EventSet();
		test2 = new Vector<Event>();
		test2.add(new Event("omega"));
		es2.addEvents(test2);
		assertTrue(new IntersectionDistance().distance(es1, es2) == 1.0);
		
		/* test 4 -- Partial overlap.  5 (3/3) elem., one in common */
		es2 = new EventSet();
		test2 = new Vector<Event>();
		test2.add(new Event("gamma"));
		test2.add(new Event("delta"));
		test2.add(new Event("epsilon"));
		es2.addEvents(test2);

		double result = new IntersectionDistance().distance(es1, es2);
		assertTrue(DistanceTestHelper.inRange(result, 0.8, 0.0000000001));

		/* test 5 -- subset:  4 elem., one in common */
		es1=new EventSet();
		test1 = new Vector<Event>();
		test1.add(new Event("alpha"));
		test1.add(new Event("beta"));
		test1.add(new Event("gamma"));
		test1.add(new Event("delta"));
		es1.addEvents(test1);

		es2=new EventSet();
		test2 = new Vector<Event>();
		test2.add(new Event("delta"));
		es2.addEvents(test2);
		result = new IntersectionDistance().distance(es1, es2);
		assertTrue(DistanceTestHelper.inRange(result, 0.75, 0.0000000001));

		/* test 6 -- superset:  4 elem., one in common */
		result = new IntersectionDistance().distance(es2, es1);
		assertTrue(DistanceTestHelper.inRange(result, 0.75, 0.0000000001));


	}


}
