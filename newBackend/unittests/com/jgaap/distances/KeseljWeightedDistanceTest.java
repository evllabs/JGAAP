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
 * @author Juola
 *
 */
public class KeseljWeightedDistanceTest {

	/**
	 * Test method for {@link com.jgaap.distances.HistogramDistance#distance(com.jgaap.generics.EventSet, com.jgaap.generics.EventSet)}.
	 */
	@Test
	public void testDistance() {
		// first two tests taken from ordinary histogram distance 
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
		assertTrue(new KeseljWeightedDistance().distance(es1, es2) == 0);
		Vector<Event> test2 = new Vector<Event>();
		test2.add(new Event("3"));
		test2.add(new Event(".."));
		test2.add(new Event("1"));
		test2.add(new Event("4"));
		test2.add(new Event("11"));
		test2.add(new Event("5"));
		test2.add(new Event("2"));
		test2.add(new Event("6"));
		test2.add(new Event("55"));
		test2.add(new Event("33"));
		es2 = new EventSet();
		es2.addEvents(test2);
		double result = new KeseljWeightedDistance().distance(es1, es2);
		//System.out.println("test 2 result is " + result);
		assertTrue(DistanceTestHelper.inRange(result, 20.0, 0.0000000001));

		// and now for the fun stuff, where the weighting matters
		Vector<Event> test3 = new Vector<Event>();
		// each event has probability 0.2, generates 0.111... distance
		test3.add(new Event("The"));
		test3.add(new Event("quick"));
		test3.add(new Event("brown"));
		test3.add(new Event("fox"));
		test3.add(new Event("jumps"));
		// five events missing -- should add 5.0 as distance
		es2 = new EventSet();
		es2.addEvents(test3);
		result = new KeseljWeightedDistance().distance(es1, es2);
		//System.out.println("test 3 result is " + result);
		assertTrue(DistanceTestHelper.inRange(result, 5.5555555555, 0.000001));
	}

}
