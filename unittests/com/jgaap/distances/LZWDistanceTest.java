package com.jgaap.distances;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;

/**
 * @author Joshua Booth
 *
 */
public class LZWDistanceTest {

	/**
	 * Test method for {@link com.jgaap.distances.LZWDivergence#divergence(com.jgaap.generics.EventSet, com.jgaap.generics.EventSet)}.
	 */
@Test
	public void testDistance() {

        /*Test 1 - Same*/
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

		  
		assertTrue(new LZWDivergence().divergence(es1,es2) == 5);


		/*Note: Adding redundnat data will change the value based on the permutation*/




		/*Test 2 - Order*/


		es2 = new EventSet();
		    test1 = new Vector<Event>();
		test1.add(new Event("."));
		test1.add(new Event("dog"));
		test1.add(new Event("lazy"));
		test1.add(new Event("the"));
		test1.add(new Event("over"));
		test1.add(new Event("jumps"));
		test1.add(new Event("fox"));
		test1.add(new Event("brown"));
		test1.add(new Event("quick"));
		test1.add(new Event("The"));
		es1.addEvents(test1);
		  
		double distanceOne = new LZWDivergence().divergence(es1,es1);
		double distanceTwo = new  LZWDivergence().divergence(es2, es1);

		assertTrue(distanceOne == 10.0);
		 assertTrue(distanceTwo == 21.0);    

}
}


