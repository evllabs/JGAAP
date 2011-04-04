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
public class CosineDistanceTest {

	/**
	 * Test method for
	 * {@link com.jgaap.distances.CosineDistance#distance(com.jgaap.generics.EventSet, com.jgaap.generics.EventSet)}
	 * .
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
		double result = new CosineDistance().distance(es1, es2);
		assertTrue(DistanceTestHelper.inRange(result, 0.0, 0.0000000001));

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
		result = new CosineDistance().distance(es1, es2);
		assertTrue(DistanceTestHelper.inRange(result, 1.0, 0.0000000001));

		es1 = new EventSet();
		es2 = new EventSet();
		test1=new Vector<Event>();
		test1.add( new Event("alpha"));
		test2=new Vector<Event>();
		test2.add( new Event("alpha"));
		test2.add( new Event("alpha"));
		es1.addEvents(test1);
		es2.addEvents(test2);
		result = new CosineDistance().distance(es1, es2);
		assertTrue(DistanceTestHelper.inRange(result, 0.0, 0.0000000001));

		es1=new EventSet();
		es2 =new EventSet();
		test1=new Vector<Event>();
		test1.add( new Event("alpha"));
		test2=new Vector<Event>();
		test2.add( new Event("alpha"));
		test2.add( new Event("beta"));
		es1.addEvents(test1);
		es2.addEvents(test2);
		result = new CosineDistance().distance(es1, es2);
		assertTrue(DistanceTestHelper.inRange(result, 0.29289321881345, 0.0000000001));
	}
}
