package com.jgaap.distances;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.util.Event;
import com.jgaap.util.EventMap;
import com.jgaap.util.EventSet;

public class AngularSeparationDistanceTest {

	@Test
	public void testDistance() 
			throws DistanceCalculationException {
		EventSet set1 = new EventSet();
		EventSet set2 = new EventSet();
		Vector<Event> test1 = new Vector<Event>();
		test1.add(new Event("one", null));
		test1.add(new Event("two", null));
		test1.add(new Event("three", null));
		test1.add(new Event("four", null));
		test1.add(new Event("five", null));
		test1.add(new Event("six", null));
		test1.add(new Event("seven", null));
		test1.add(new Event("eight", null));
		test1.add(new Event("nine", null));
		test1.add(new Event("ten", null));			
		set1.addEvents(test1);
		set2.addEvents(test1);
		double result = new AngularSeparationDistance().distance(new EventMap(set1), new EventMap(set2));
		assertTrue(DistanceTestHelper.inRange(result, 0.90, 0.0000000001));
		
		
		set2 = new EventSet();
		Vector<Event> test2 = new Vector<Event>();
		test2.add(new Event("1", null));
		test2.add(new Event("2", null));
		test2.add(new Event("3", null));
		test2.add(new Event("4", null));
		test2.add(new Event("5", null));
		test2.add(new Event("6", null));
		test2.add(new Event("7", null));
		test2.add(new Event("8", null));
		test2.add(new Event("9", null));
		test2.add(new Event("10", null));
		set2.addEvents(test2);
		result = new AngularSeparationDistance().distance(new EventMap(set1), new EventMap(set2));
		assertTrue(DistanceTestHelper.inRange(result, 1.0, 0.0000000001));
	}

}
