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

import weka.classifiers.trees.J48;

import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

/**
 * @author Amanda Kroft
 * 
 */
public class WEKAPaceRegressionTest {

	/**
	 * Test method for {@link
	 * com.jgaap.classifiers.WEKAJ48DecisionTree#analyze(com.jgaap.generics.EventSet,
	 * List<EventSet>)}.
	 */
	@Test
	public void testAnalyze() {
		
		//weka.classifiers.functions.PaceRegression: Not enough training instances with class labels (required: 30, provided: 2)!
		// So need 15 documents per author?
		// Or 15 different authors?
		
		//Test 1

		//Create known texts
		//Not enough training instances with class labels (required: 52, provided: 30)!
		/*EventSet known1 = new EventSet();
		EventSet known2 = new EventSet();
		EventSet known3 = new EventSet();
		EventSet known4 = new EventSet();
		EventSet known5 = new EventSet();
		EventSet known6 = new EventSet();
		EventSet known7 = new EventSet();
		EventSet known8 = new EventSet();
		EventSet known9 = new EventSet();
		EventSet known10 = new EventSet();
		EventSet known11 = new EventSet();
		EventSet known12 = new EventSet();
		EventSet known13 = new EventSet();
		EventSet known14 = new EventSet();
		EventSet known15 = new EventSet();
		
		known1.addEvent(new Event("mary"));
		known1.addEvent(new Event("had"));
		known1.addEvent(new Event("a"));
		known1.addEvent(new Event("little"));
		known1.addEvent(new Event("lamb"));
		known1.setAuthor("Mary");
		
		known2.addEvent(new Event("mary"));
		known2.addEvent(new Event("owned"));
		known2.addEvent(new Event("a"));
		known2.addEvent(new Event("little"));
		known2.addEvent(new Event("lamb"));
		known2.setAuthor("Mary");

		known3.addEvent(new Event("mary"));
		known3.addEvent(new Event("had"));
		known3.addEvent(new Event("a"));
		known3.addEvent(new Event("small"));
		known3.addEvent(new Event("lamb"));
		known3.setAuthor("Mary");
		
		known4.addEvent(new Event("mary"));
		known4.addEvent(new Event("owned"));
		known4.addEvent(new Event("a"));
		known4.addEvent(new Event("small"));
		known4.addEvent(new Event("lamb"));
		known4.setAuthor("Mary");

		known5.addEvent(new Event("mary"));
		known5.addEvent(new Event("had"));
		known5.addEvent(new Event("a"));
		known5.addEvent(new Event("little"));
		known5.addEvent(new Event("lamb"));
		known5.setAuthor("Mary");
		
		known6.addEvent(new Event("melissa"));
		known6.addEvent(new Event("had"));
		known6.addEvent(new Event("a"));
		known6.addEvent(new Event("little"));
		known6.addEvent(new Event("lamb"));
		known6.setAuthor("Mary");

		known7.addEvent(new Event("mary"));
		known7.addEvent(new Event("had"));
		known7.addEvent(new Event("one"));
		known7.addEvent(new Event("little"));
		known7.addEvent(new Event("lamb"));
		known7.setAuthor("Mary");
		
		known8.addEvent(new Event("mary"));
		known8.addEvent(new Event("owned"));
		known8.addEvent(new Event("one"));
		known8.addEvent(new Event("little"));
		known8.addEvent(new Event("lamb"));
		known8.setAuthor("Mary");

		known9.addEvent(new Event("mary"));
		known9.addEvent(new Event("had"));
		known9.addEvent(new Event("two"));
		known9.addEvent(new Event("little"));
		known9.addEvent(new Event("lambs"));
		known9.setAuthor("Mary");

		known10.addEvent(new Event("melissa"));
		known10.addEvent(new Event("had"));
		known10.addEvent(new Event("a"));
		known10.addEvent(new Event("small"));
		known10.addEvent(new Event("lamb"));
		known10.setAuthor("Mary");

		known11.addEvent(new Event("melissa"));
		known11.addEvent(new Event("had"));
		known11.addEvent(new Event("one"));
		known11.addEvent(new Event("little"));
		known11.addEvent(new Event("lamb"));
		known11.setAuthor("Mary");

		known12.addEvent(new Event("mary"));
		known12.addEvent(new Event("dismissed"));
		known12.addEvent(new Event("a"));
		known12.addEvent(new Event("little"));
		known12.addEvent(new Event("lamb"));
		known12.setAuthor("Mary");
		
		known13.addEvent(new Event("mary"));
		known13.addEvent(new Event("had"));
		known13.addEvent(new Event("a"));
		known13.addEvent(new Event("little"));
		known13.addEvent(new Event("lambda"));
		known13.setAuthor("Mary");
		
		known14.addEvent(new Event("melissa"));
		known14.addEvent(new Event("had"));
		known14.addEvent(new Event("two"));
		known14.addEvent(new Event("little"));
		known14.addEvent(new Event("lambs"));
		known14.setAuthor("Mary");

		known15.addEvent(new Event("mary"));
		known15.addEvent(new Event("had"));
		known15.addEvent(new Event("a"));
		known15.addEvent(new Event("little"));
		known15.addEvent(new Event("lamb"));
		known15.setAuthor("Mary");
		
		EventSet known21 = new EventSet();
		EventSet known22 = new EventSet();
		EventSet known23 = new EventSet();
		EventSet known24 = new EventSet();
		EventSet known25 = new EventSet();
		EventSet known26 = new EventSet();
		EventSet known27 = new EventSet();
		EventSet known28 = new EventSet();
		EventSet known29 = new EventSet();
		EventSet known210 = new EventSet();
		EventSet known211 = new EventSet();
		EventSet known212 = new EventSet();
		EventSet known213 = new EventSet();
		EventSet known214 = new EventSet();
		EventSet known215 = new EventSet();
		
		known21.addEvent(new Event("peter"));
		known21.addEvent(new Event("piper"));
		known21.addEvent(new Event("picked"));
		known21.addEvent(new Event("a"));
		known21.addEvent(new Event("peck"));
		known21.setAuthor("Peter");
		
		known22.addEvent(new Event("peter"));
		known22.addEvent(new Event("piper"));
		known22.addEvent(new Event("had"));
		known22.addEvent(new Event("a"));
		known22.addEvent(new Event("peck"));
		known21.setAuthor("Peter");

		known23.addEvent(new Event("peter"));
		known23.addEvent(new Event("piper"));
		known23.addEvent(new Event("found"));
		known23.addEvent(new Event("a"));
		known23.addEvent(new Event("peck"));
		known21.setAuthor("Peter");
		
		known24.addEvent(new Event("a"));
		known24.addEvent(new Event("peck"));
		known24.addEvent(new Event("peter"));
		known24.addEvent(new Event("piper"));
		known24.addEvent(new Event("picked"));
		known21.setAuthor("Peter");

		known25.addEvent(new Event("charles"));
		known25.addEvent(new Event("picked"));
		known25.addEvent(new Event("a"));
		known25.addEvent(new Event("pepper"));
		known25.addEvent(new Event("peck"));
		known21.setAuthor("Peter");
		
		known26.addEvent(new Event("peter"));
		known26.addEvent(new Event("picker"));
		known26.addEvent(new Event("picked"));
		known26.addEvent(new Event("a"));
		known26.addEvent(new Event("peck"));
		known21.setAuthor("Peter");

		known27.addEvent(new Event("peck"));
		known27.addEvent(new Event("was"));
		known27.addEvent(new Event("picked"));
		known27.addEvent(new Event("by"));
		known27.addEvent(new Event("peter"));
		known21.setAuthor("Peter");
		
		known28.addEvent(new Event("peter"));
		known28.addEvent(new Event("piper"));
		known28.addEvent(new Event("stole"));
		known28.addEvent(new Event("pepper"));
		known28.addEvent(new Event("peck"));
		known21.setAuthor("Peter");

		known29.addEvent(new Event("pepper"));
		known29.addEvent(new Event("peck"));
		known29.addEvent(new Event("was"));
		known29.addEvent(new Event("recently"));
		known29.addEvent(new Event("stolen"));
		known21.setAuthor("Peter");

		known210.addEvent(new Event("charles"));
		known210.addEvent(new Event("piper"));
		known210.addEvent(new Event("a"));
		known210.addEvent(new Event("peck"));
		known210.addEvent(new Event("picked"));
		known21.setAuthor("Peter");

		known211.addEvent(new Event("piper"));
		known211.addEvent(new Event("peter"));
		known211.addEvent(new Event("picked"));
		known211.addEvent(new Event("a"));
		known211.addEvent(new Event("peck"));
		known21.setAuthor("Peter");

		known212.addEvent(new Event("a"));
		known212.addEvent(new Event("peck"));
		known212.addEvent(new Event("charles"));
		known212.addEvent(new Event("piper"));
		known212.addEvent(new Event("picked"));
		known21.setAuthor("Peter");
		
		known213.addEvent(new Event("piper"));
		known213.addEvent(new Event("peter"));
		known213.addEvent(new Event("borrowed"));
		known213.addEvent(new Event("a"));
		known213.addEvent(new Event("peck"));
		known21.setAuthor("Peter");
		
		known214.addEvent(new Event("peter"));
		known214.addEvent(new Event("piper"));
		known214.addEvent(new Event("ate"));
		known214.addEvent(new Event("pickled"));
		known214.addEvent(new Event("peppers"));
		known21.setAuthor("Peter");

		known215.addEvent(new Event("charles"));
		known215.addEvent(new Event("ate"));
		known215.addEvent(new Event("a"));
		known215.addEvent(new Event("whole"));
		known215.addEvent(new Event("peck"));
		known21.setAuthor("Peter");

		
		Vector<EventSet> esv = new Vector<EventSet>();
		esv.add(known1);
		esv.add(known2);
		esv.add(known3);
		esv.add(known4);
		esv.add(known5);
		esv.add(known6);
		esv.add(known7);
		esv.add(known8);
		esv.add(known9);
		esv.add(known10);
		esv.add(known11);
		esv.add(known12);
		esv.add(known13);
		esv.add(known14);
		esv.add(known15);
		
		esv.add(known21);
		esv.add(known22);
		esv.add(known23);
		esv.add(known24);
		esv.add(known25);
		esv.add(known26);
		esv.add(known27);
		esv.add(known28);
		esv.add(known29);
		esv.add(known210);
		esv.add(known211);
		esv.add(known212);
		esv.add(known213);
		esv.add(known214);
		esv.add(known215);*/
		
		//weka.core.WekaException: weka.classifiers.functions.PaceRegression: Not enough training instances with class labels (required: 31, provided: 4)!
		EventSet known1 = new EventSet();

		known1.addEvent(new Event("mary"));
		known1.addEvent(new Event("lamb"));
		known1.setAuthor("Mary");
		
		EventSet known21 = new EventSet();
		
		known21.addEvent(new Event("peter"));
		known21.addEvent(new Event("peck"));
		known21.setAuthor("Peter");
		
		Vector<EventSet> esv = new Vector<EventSet>();
		esv.add(known1);
		esv.add(known21);

		//Create unknown text
		EventSet unknown1 = new EventSet();

		unknown1.addEvent(new Event("mary"));
		unknown1.addEvent(new Event("beta"));

		Vector<EventSet> uesv = new Vector<EventSet>();
		uesv.add(unknown1);

		//Classify unknown based on the knowns
		WEKAPaceRegression tree = new WEKAPaceRegression();
		List<List<Pair<String, Double>>> t;
		try {
			t = tree.analyze(uesv, esv);
			System.out.println(t.toString());

			//Assert that the authors match
			assertTrue(t.get(0).get(0).getFirst().equals("Mary"));
		} catch (AnalyzeException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			assertTrue(false);
		}

		
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
		EventSet known3 = new EventSet();
		EventSet known4 = new EventSet();
		EventSet known6 = new EventSet();
		
		known3.addEvent(new Event("mary"));
		known3.addEvent(new Event("had"));
		known3.addEvent(new Event("a"));
		known3.addEvent(new Event("small"));
		known3.addEvent(new Event("lamb"));
		known3.setAuthor("Mary");

		known4.addEvent(new Event("peter"));
		known4.addEvent(new Event("piper"));
		known4.addEvent(new Event("collected"));
		known4.addEvent(new Event("a"));
		known4.addEvent(new Event("peck"));
		known4.setAuthor("Peter");
		
		known6.addEvent(new Event("susie"));
		known6.addEvent(new Event("sells"));
		known6.addEvent(new Event("shells"));
		known6.addEvent(new Event("by"));
		known6.addEvent(new Event("seashore"));
		known6.setAuthor("Susie");

		esv.add(known3);
		esv.add(known4);
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
