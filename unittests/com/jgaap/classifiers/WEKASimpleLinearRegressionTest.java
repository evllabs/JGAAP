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
package com.jgaap.classifiers;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Vector;

import org.junit.Test;

import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

/**
 * @author Amanda Kroft
 * 
 */
public class WEKASimpleLinearRegressionTest {

	/**
	 * Test method for {@link
	 * com.jgaap.classifiers.WEKAARBFNetwork#analyze(com.jgaap.generics.EventSet,
	 * List<EventSet>)}.
	 * @throws AnalyzeException 
	 */
	@Test
	public void testAnalyze() throws AnalyzeException {
		
		//"weka.classifiers.functions.SimpleLinearRegression: Cannot handle multi-valued nominal class!"
		//Tried the following: putting only events which are the exact same strings, but different quantities
		//  : putting only one string per event
		//Still get error
		
		//Test 1

		//Create known texts
		EventSet known1 = new EventSet();
		EventSet known2 = new EventSet();
		EventSet known3 = new EventSet();
		EventSet known4 = new EventSet();

		/*known1.addEvent(new Event("alpha"));
		known1.addEvent(new Event("alpha"));
		known1.addEvent(new Event("alpha"));
		known1.addEvent(new Event("alpha"));
		known1.addEvent(new Event("beta"));*/
		known1.addEvent(new Event("beta"));
		known1.setAuthor("Mary");
		
		known2.addEvent(new Event("alpha"));
		//known2.addEvent(new Event("beta"));
		//known2.addEvent(new Event("beta"));
		//known2.addEvent(new Event("beta"));
		//known2.addEvent(new Event("beta"));
		known2.setAuthor("Peter");
		
		/*known3.addEvent(new Event("alpha"));
		known3.addEvent(new Event("alpha"));
		known3.addEvent(new Event("alpha"));
		//known3.addEvent(new Event("beta"));
		//known3.addEvent(new Event("beta"));
		known3.setAuthor("Mary");

		known4.addEvent(new Event("alpha"));
		known4.addEvent(new Event("alpha"));
		//known4.addEvent(new Event("beta"));
		//known4.addEvent(new Event("beta"));
		//known4.addEvent(new Event("beta"));
		known4.setAuthor("Peter");*/
		
		Vector<EventSet> esv = new Vector<EventSet>();
		esv.add(known1);
		esv.add(known2);
		esv.add(known3);
		esv.add(known4);

		//Create unknown text
		EventSet unknown1 = new EventSet();

		/*unknown1.addEvent(new Event("alpha"));
		unknown1.addEvent(new Event("alpha"));
		unknown1.addEvent(new Event("alpha"));
		unknown1.addEvent(new Event("alpha"));
		unknown1.addEvent(new Event("alpha"));*/
		unknown1.addEvent(new Event("bat"));

		Vector<EventSet> uesv = new Vector<EventSet>();
		uesv.add(unknown1);

		//Classify unknown based on the knowns
		WEKASimpleLinearRegression tree = new WEKASimpleLinearRegression();
		List<Pair<String, Double>> t;
		tree.train(esv);
		t = tree.analyze(unknown1);
		System.out.println(t.toString());

		// Assert that the authors match
		assertTrue(t.get(0).getFirst().equals("Mary"));
		
		/*
		//Test 2 - Add in third known author

		EventSet known5 = new EventSet();
		
		known5.addEvent(new Event("she"));
		known5.addEvent(new Event("sells"));
		known5.addEvent(new Event("seashells"));
		known5.addEvent(new Event("by"));
		known5.addEvent(new Event("seashore"));
		known5.setAuthor("Susie");

		esv.add(known5);

		t = tree.analyze(uesv, esv);
		System.out.println(t.toString());

		assertTrue(t.get(0).get(0).getFirst().equals("Mary"));
		

		//Test 3 - Add in another unknown

		EventSet unknown2 = new EventSet();

		unknown2.addEvent(new Event("peter"));
		unknown2.addEvent(new Event("piper"));
		unknown2.addEvent(new Event("picked"));
		unknown2.addEvent(new Event("a"));
		unknown2.addEvent(new Event("shells"));

		uesv.add(unknown2);

		t = tree.analyze(uesv, esv);
		System.out.println(t.toString());

		assertTrue(t.get(0).get(0).getFirst().equals("Mary") && t.get(1).get(0).getFirst().equals("Peter"));
		
		// Test 6 - Test unknown that is almost equally likely to be of two authors
		
		EventSet unknown3 = new EventSet();
		
		unknown3.addEvent(new Event("peter"));
		unknown3.addEvent(new Event("piper"));
		unknown3.addEvent(new Event("a"));
		unknown3.addEvent(new Event("little"));
		unknown3.addEvent(new Event("lamb"));
		
		uesv = new Vector<EventSet>();
		uesv.add(unknown3);
		
		//t = tree.analyze(uesv, esv);
		tree = new WEKAPaceRegression();
		t = tree.analyze(uesv, esv);
		System.out.println(t.toString());
		
		assertTrue(t.get(0).get(0).getSecond()-.5 < .1 && t.get(0).get(1).getSecond()-.5 < .1);
		
		// Test 5 - Add in more known documents for existing authors

		EventSet known6 = new EventSet();
		

		
		known6.addEvent(new Event("susie"));
		known6.addEvent(new Event("sells"));
		known6.addEvent(new Event("shells"));
		known6.addEvent(new Event("by"));
		known6.addEvent(new Event("seashore"));
		known6.setAuthor("Susie");


		esv.add(known6);
		
		uesv = new Vector<EventSet>();
		uesv.add(unknown1);
		uesv.add(unknown2);
		
		t = tree.analyze(uesv, esv);
		System.out.println(t.toString());
		
		assertTrue(t.get(0).get(0).getFirst().equals("Mary") && t.get(1).get(0).getFirst().equals("Peter"));
		 */
	}

}
