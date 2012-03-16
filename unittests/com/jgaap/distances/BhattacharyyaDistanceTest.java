package com.jgaap.distances;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;

public class BhattacharyyaDistanceTest {

	@Test
	public void testDistance() 
			throws DistanceCalculationException {
		EventSet set1 = new EventSet();
		EventSet set2 = new EventSet();
		Vector<Event> test1 = new Vector<Event>();
		test1.add(new Event("one"));
		test1.add(new Event("two"));
		test1.add(new Event("three"));
		test1.add(new Event("four"));
		test1.add(new Event("five"));
		test1.add(new Event("six"));
		test1.add(new Event("seven"));
		test1.add(new Event("eight"));
		test1.add(new Event("nine"));
		test1.add(new Event("ten"));			
		set1.addEvents(test1);
		set2.addEvents(test1);
		double result = new BhattacharyyaDistance().distance(set1, set2);
		assertTrue(Double.isInfinite(result));
		
		
		set2 = new EventSet();
		Vector<Event> test2 = new Vector<Event>();
		test2.add(new Event("1"));
		test2.add(new Event("2"));
		test2.add(new Event("3"));
		test2.add(new Event("4"));
		test2.add(new Event("5"));
		test2.add(new Event("6"));
		test2.add(new Event("7"));
		test2.add(new Event("8"));
		test2.add(new Event("9"));
		test2.add(new Event("10"));
		set2.addEvents(test2);
		result = new BhattacharyyaDistance().distance(set1, set2);
		assertTrue(DistanceTestHelper.inRange(result, Math.log(20 * Math.sqrt(.1)), 0.0000000001));
	}

}
