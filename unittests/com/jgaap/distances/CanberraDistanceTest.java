/*
 * JGAAP -- a graphical program for stylometric authorship attribution
 * Copyright (C) 2009,2011 by Patrick Juola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
public class CanberraDistanceTest {

	/**
	 * Test method for {@link com.jgaap.distances.CamberraDistance#distance(com.jgaap.generics.EventSet, com.jgaap.generics.EventSet)}.
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
		assertTrue(new CanberraDistance().distance(es1, es2) == 0.00);
		
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
		double result = new CanberraDistance().distance(es1, es2);
		new Double(result).compareTo(20.0);
		System.out.println(result);
		assertTrue(DistanceTestHelper.inRange(result, 20.0, 0.0000000001));
	}
	
	

}
