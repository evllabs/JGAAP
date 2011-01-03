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
public class KullbackLeiblerDivergenceTest {

	/**
	 * Test method for {@link com.jgaap.distances.KullbackLeiblerDivergence#distance(com.jgaap.generics.EventSet, com.jgaap.generics.EventSet)}.
	 */
	@Test
	public void testDistance() {
		/*
		 *  n.b.  The KL function uses logarithms, for which we use
		 *  Java's built-in Math.log() function.  
		 *  Math.log() returns the _natural_ (base e) log of a number,
		 *  not the more usual/intuitive base 2.
		 *  Formally, we are using units of 'nats' instead of the more
		 *  normal 'bits.'  Not a problem as long as we can remember
		 *  to do the unit conversions and don't mind working in
		 *  furlongs per fortnight.
		 */
		
	/* test 1 -- the same histogram should yield distance 0.0 */
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
		
		assertTrue(new KullbackLeiblerDivergence().distance(es1, es2) == 0.00);
		
	/* test 2 -- different hist, same distribution (still 0.0) */
		/* use prior data and add another copy */
		es2.addEvents(test1);
		assertTrue(new KullbackLeiblerDivergence().distance(es1, es2) == 0.00);


	/* test 3 -- different distributions */


		test1 = new Vector<Event>(); // no need to re-create test1
		Vector<Event> test2 = new Vector<Event>();

		/* es1 gets a 50/50 split between alpha and beta */
		test1.add(new Event("alpha"));
		test1.add(new Event("beta"));
		es1 = new EventSet();
		es1.addEvents(test1);

		/* es2 gets a 75/25 split between alpha and beta */
		test2.add(new Event("alpha"));
		test2.add(new Event("alpha"));
		test2.add(new Event("alpha"));
		test2.add(new Event("beta"));
		es2 = new EventSet();
		es2.addEvents(test2);
		double result = new
			 KullbackLeiblerDivergence().distance(es1, es2);
System.out.println(result);
		assertTrue(DistanceTestHelper.inRange(result, 0.1438410, 0.00001));

	/* test 4 -- reversed distributions */
		result = new
			 KullbackLeiblerDivergence().distance(es2, es1);
		assertTrue(DistanceTestHelper.inRange(result, 0.13081203594, 0.00001));
	/* test 5 -- distributions with 0 (need rounding) */
		/* use same 50/50 es1 */
		/* value of nonce-term gamma should be 0.25 (half of 0.5,
		   because each element appears once */

		/* es2 gets a 50/25/25 split between alpha, beta,gamma */
		test2 = new Vector<Event>();
		test2.add(new Event("alpha"));
		test2.add(new Event("alpha"));
		test2.add(new Event("beta"));
		test2.add(new Event("gamma"));

		es2 = new EventSet();
		es2.addEvents(test2);
System.out.println("Start here");
		result = new
			 KullbackLeiblerDivergence().distance(es1, es2);
System.out.println(result);
		assertTrue(DistanceTestHelper.inRange(result, 0.346574, 0.00001));

	/* test 6 -- reversed distributions */
		result = new
			 KullbackLeiblerDivergence().distance(es2, es1);
System.out.println(result);
		assertTrue(DistanceTestHelper.inRange(result, -0.173287, 0.00001));
	}

}
