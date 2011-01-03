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
 * @author Nicole
 *
 */
public class NominalKSDistanceTest {

	/**
	 * Test method for {@link com.jgaap.distances.NominalKSDistance#distance(com.jgaap.generics.EventSet, com.jgaap.generics.EventSet)}.
	 */
	@Test
	public void testDistance() {
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
		
		assertTrue(new NominalKSDistance().distance(es1, es2) == 0.00);
		
		Vector<Event> test2 = new Vector<Event>();//set with the same number of elements, only one repeats and none are the same as in test1
		test2.add(new Event("33"));
		test2.add(new Event("5"));
		test2.add(new Event("6"));
		test2.add(new Event("8"));
		test2.add(new Event("44"));
		test2.add(new Event("7"));
		test2.add(new Event("33"));
		test2.add(new Event("10"));
		test2.add(new Event("2"));
		test2.add(new Event(".."));
		es2 = new EventSet();
		es2.addEvents(test2);
		double result = new NominalKSDistance().distance(es1, es2);
		assertTrue(DistanceTestHelper.inRange(result, 1.00, 0.0000000001));
	}

}
