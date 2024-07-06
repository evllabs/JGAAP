package com.jgaap.distances;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.util.Event;
import com.jgaap.util.EventMap;
import com.jgaap.util.EventSet;

public class BhattacharyyaDistanceTest {

	@Test
	public void testDistance() 
			throws DistanceCalculationException {
		EventSet set1 = new EventSet();
		EventSet set2 = new EventSet();
		Vector<Event> test1 = new Vector<Event>();
		test1.add(new Event("Lorem", null));
		test1.add(new Event("Lorem", null));
		test1.add(new Event("ipsum", null));
		test1.add(new Event("ipsum", null));
		test1.add(new Event("ipsum", null));
		test1.add(new Event("ipsum", null));
		test1.add(new Event("ipsum", null));
		set1.addEvents(test1);
		set2.addEvents(test1);
		double result = new BhattacharyyaDistance().distance(new EventMap(set1), new EventMap(set2));
		assertTrue(result == 0);
		
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
		result = new BhattacharyyaDistance().distance(new EventMap(set1), new EventMap(set2));
		assertTrue(Double.isInfinite(result));
	}

}
