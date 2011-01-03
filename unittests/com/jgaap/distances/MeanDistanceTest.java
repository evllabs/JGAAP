/**
 * 
 */
package com.jgaap.distances;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.NumericEventSet;

/**
 * @author michael
 *
 */
public class MeanDistanceTest {

	/**
	 * Test method for {@link com.jgaap.distances.MeanDistance#distance(com.jgaap.generics.EventSet, com.jgaap.generics.EventSet)}.
	 */
	@Test
	public void testDistance() {
		EventSet es1 = new NumericEventSet();
		EventSet es2 = new NumericEventSet();
		Vector<Event> test1 = new Vector<Event>();
		test1.add(new Event("1.0"));
		test1.add(new Event("2.0"));
		test1.add(new Event("3.0"));
		test1.add(new Event("4.0"));
		test1.add(new Event("5.0"));

		es1.addEvents(test1);
		es2.addEvents(test1);
		assertTrue(new MeanDistance().distance(es1, es2) == 0);

		Vector<Event> test2 = new Vector<Event>();
		test2.add(new Event("1.0"));
		test2.add(new Event("2.0"));
		test2.add(new Event("3.0"));
		
		es2 = new EventSet();
		es2.addEvents(test2);
		double result = new MeanDistance().distance(es1, es2);
		assertTrue(DistanceTestHelper.inRange(result, 1.0, 0.0000000001));
	}

}
