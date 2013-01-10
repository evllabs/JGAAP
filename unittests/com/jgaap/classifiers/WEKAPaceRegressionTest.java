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
public class WEKAPaceRegressionTest {

	/**
	 * Test method for {@link
	 * com.jgaap.classifiers.WEKAJ48DecisionTree#analyze(com.jgaap.generics.EventSet,
	 * List<EventSet>)}.
	 * @throws AnalyzeException 
	 */
	@Test
	public void testAnalyze() throws AnalyzeException {
		
		//weka.classifiers.functions.PaceRegression: Not enough training instances with class labels (required: 30, provided: 2)!
		// So need 15 documents per author?
		// Or 15 different authors?
		
		//Test 1

		//Create known texts
		//Not enough training instances with class labels (required: 52, provided: 30)!
		//What the heck??
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
		
		known1.addEvent(new Event("mary", null));
		known1.addEvent(new Event("had", null));
		known1.addEvent(new Event("a", null));
		known1.addEvent(new Event("little", null));
		known1.addEvent(new Event("lamb", null));
		known1.setAuthor("Mary");
		
		known2.addEvent(new Event("mary", null));
		known2.addEvent(new Event("owned", null));
		known2.addEvent(new Event("a", null));
		known2.addEvent(new Event("little", null));
		known2.addEvent(new Event("lamb", null));
		known2.setAuthor("Mary");

		known3.addEvent(new Event("mary", null));
		known3.addEvent(new Event("had", null));
		known3.addEvent(new Event("a", null));
		known3.addEvent(new Event("small", null));
		known3.addEvent(new Event("lamb", null));
		known3.setAuthor("Mary");
		
		known4.addEvent(new Event("mary", null));
		known4.addEvent(new Event("owned", null));
		known4.addEvent(new Event("a", null));
		known4.addEvent(new Event("small", null));
		known4.addEvent(new Event("lamb", null));
		known4.setAuthor("Mary");

		known5.addEvent(new Event("mary", null));
		known5.addEvent(new Event("had", null));
		known5.addEvent(new Event("a", null));
		known5.addEvent(new Event("little", null));
		known5.addEvent(new Event("lamb", null));
		known5.setAuthor("Mary");
		
		known6.addEvent(new Event("melissa", null));
		known6.addEvent(new Event("had", null));
		known6.addEvent(new Event("a", null));
		known6.addEvent(new Event("little", null));
		known6.addEvent(new Event("lamb", null));
		known6.setAuthor("Mary");

		known7.addEvent(new Event("mary", null));
		known7.addEvent(new Event("had", null));
		known7.addEvent(new Event("one", null));
		known7.addEvent(new Event("little", null));
		known7.addEvent(new Event("lamb", null));
		known7.setAuthor("Mary");
		
		known8.addEvent(new Event("mary", null));
		known8.addEvent(new Event("owned", null));
		known8.addEvent(new Event("one", null));
		known8.addEvent(new Event("little", null));
		known8.addEvent(new Event("lamb", null));
		known8.setAuthor("Mary");

		known9.addEvent(new Event("mary", null));
		known9.addEvent(new Event("had", null));
		known9.addEvent(new Event("two", null));
		known9.addEvent(new Event("little", null));
		known9.addEvent(new Event("lambs", null));
		known9.setAuthor("Mary");

		known10.addEvent(new Event("melissa", null));
		known10.addEvent(new Event("had", null));
		known10.addEvent(new Event("a", null));
		known10.addEvent(new Event("small", null));
		known10.addEvent(new Event("lamb", null));
		known10.setAuthor("Mary");

		known11.addEvent(new Event("melissa", null));
		known11.addEvent(new Event("had", null));
		known11.addEvent(new Event("one", null));
		known11.addEvent(new Event("little", null));
		known11.addEvent(new Event("lamb", null));
		known11.setAuthor("Mary");

		known12.addEvent(new Event("mary", null));
		known12.addEvent(new Event("dismissed", null));
		known12.addEvent(new Event("a", null));
		known12.addEvent(new Event("little", null));
		known12.addEvent(new Event("lamb", null));
		known12.setAuthor("Mary");
		
		known13.addEvent(new Event("mary", null));
		known13.addEvent(new Event("had", null));
		known13.addEvent(new Event("a", null));
		known13.addEvent(new Event("little", null));
		known13.addEvent(new Event("lambda", null));
		known13.setAuthor("Mary");
		
		known14.addEvent(new Event("melissa", null));
		known14.addEvent(new Event("had", null));
		known14.addEvent(new Event("two", null));
		known14.addEvent(new Event("little", null));
		known14.addEvent(new Event("lambs", null));
		known14.setAuthor("Mary");

		known15.addEvent(new Event("mary", null));
		known15.addEvent(new Event("had", null));
		known15.addEvent(new Event("a", null));
		known15.addEvent(new Event("little", null));
		known15.addEvent(new Event("lamb", null));
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
		
		known21.addEvent(new Event("peter", null));
		known21.addEvent(new Event("piper", null));
		known21.addEvent(new Event("picked", null));
		known21.addEvent(new Event("a", null));
		known21.addEvent(new Event("peck", null));
		known21.setAuthor("Peter");
		
		known22.addEvent(new Event("peter", null));
		known22.addEvent(new Event("piper", null));
		known22.addEvent(new Event("had", null));
		known22.addEvent(new Event("a", null));
		known22.addEvent(new Event("peck", null));
		known21.setAuthor("Peter");

		known23.addEvent(new Event("peter", null));
		known23.addEvent(new Event("piper", null));
		known23.addEvent(new Event("found", null));
		known23.addEvent(new Event("a", null));
		known23.addEvent(new Event("peck", null));
		known21.setAuthor("Peter");
		
		known24.addEvent(new Event("a", null));
		known24.addEvent(new Event("peck", null));
		known24.addEvent(new Event("peter", null));
		known24.addEvent(new Event("piper", null));
		known24.addEvent(new Event("picked", null));
		known21.setAuthor("Peter");

		known25.addEvent(new Event("charles", null));
		known25.addEvent(new Event("picked", null));
		known25.addEvent(new Event("a", null));
		known25.addEvent(new Event("pepper", null));
		known25.addEvent(new Event("peck", null));
		known21.setAuthor("Peter");
		
		known26.addEvent(new Event("peter", null));
		known26.addEvent(new Event("picker", null));
		known26.addEvent(new Event("picked", null));
		known26.addEvent(new Event("a", null));
		known26.addEvent(new Event("peck", null));
		known21.setAuthor("Peter");

		known27.addEvent(new Event("peck", null));
		known27.addEvent(new Event("was", null));
		known27.addEvent(new Event("picked", null));
		known27.addEvent(new Event("by", null));
		known27.addEvent(new Event("peter", null));
		known21.setAuthor("Peter");
		
		known28.addEvent(new Event("peter", null));
		known28.addEvent(new Event("piper", null));
		known28.addEvent(new Event("stole", null));
		known28.addEvent(new Event("pepper", null));
		known28.addEvent(new Event("peck", null));
		known21.setAuthor("Peter");

		known29.addEvent(new Event("pepper", null));
		known29.addEvent(new Event("peck", null));
		known29.addEvent(new Event("was", null));
		known29.addEvent(new Event("recently", null));
		known29.addEvent(new Event("stolen", null));
		known21.setAuthor("Peter");

		known210.addEvent(new Event("charles", null));
		known210.addEvent(new Event("piper", null));
		known210.addEvent(new Event("a", null));
		known210.addEvent(new Event("peck", null));
		known210.addEvent(new Event("picked", null));
		known21.setAuthor("Peter");

		known211.addEvent(new Event("piper", null));
		known211.addEvent(new Event("peter", null));
		known211.addEvent(new Event("picked", null));
		known211.addEvent(new Event("a", null));
		known211.addEvent(new Event("peck", null));
		known21.setAuthor("Peter");

		known212.addEvent(new Event("a", null));
		known212.addEvent(new Event("peck", null));
		known212.addEvent(new Event("charles", null));
		known212.addEvent(new Event("piper", null));
		known212.addEvent(new Event("picked", null));
		known21.setAuthor("Peter");
		
		known213.addEvent(new Event("piper", null));
		known213.addEvent(new Event("peter", null));
		known213.addEvent(new Event("borrowed", null));
		known213.addEvent(new Event("a", null));
		known213.addEvent(new Event("peck", null));
		known21.setAuthor("Peter");
		
		known214.addEvent(new Event("peter", null));
		known214.addEvent(new Event("piper", null));
		known214.addEvent(new Event("ate", null));
		known214.addEvent(new Event("pickled", null));
		known214.addEvent(new Event("peppers", null));
		known21.setAuthor("Peter");

		known215.addEvent(new Event("charles", null));
		known215.addEvent(new Event("ate", null));
		known215.addEvent(new Event("a", null));
		known215.addEvent(new Event("whole", null));
		known215.addEvent(new Event("peck", null));
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
		// >:{
		EventSet known1 = new EventSet();

		known1.addEvent(new Event("mary", null));
		known1.addEvent(new Event("lamb", null));
		known1.setAuthor("Mary");
		
		EventSet known21 = new EventSet();
		
		known21.addEvent(new Event("peter", null));
		known21.addEvent(new Event("peck", null));
		known21.setAuthor("Peter");
		
		Vector<EventSet> esv = new Vector<EventSet>();
		esv.add(known1);
		esv.add(known21);

		//Create unknown text
		EventSet unknown1 = new EventSet();

		unknown1.addEvent(new Event("mary", null));
		unknown1.addEvent(new Event("beta", null));

		Vector<EventSet> uesv = new Vector<EventSet>();
		uesv.add(unknown1);

		//Classify unknown based on the knowns
		WEKAPaceRegression tree = new WEKAPaceRegression();
		List<Pair<String, Double>> t;
		tree.train(esv);
		t = tree.analyze(unknown1);
		System.out.println(t.toString());

		// Assert that the authors match
		assertTrue(t.get(0).getFirst().equals("Mary", null));
		

		
		/*
		//Test 2 - Add in third known author

		EventSet known5 = new EventSet();
		
		known5.addEvent(new Event("she", null));
		known5.addEvent(new Event("sells", null));
		known5.addEvent(new Event("seashells", null));
		known5.addEvent(new Event("by", null));
		known5.addEvent(new Event("seashore", null));
		known5.setAuthor("Susie");

		esv.add(known5);

		t = tree.analyze(uesv, esv);
		System.out.println(t.toString());

		assertTrue(t.get(0).get(0).getFirst().equals("Mary", null));
		

		//Test 3 - Add in another unknown

		EventSet unknown2 = new EventSet();

		unknown2.addEvent(new Event("peter", null));
		unknown2.addEvent(new Event("piper", null));
		unknown2.addEvent(new Event("picked", null));
		unknown2.addEvent(new Event("a", null));
		unknown2.addEvent(new Event("shells", null));

		uesv.add(unknown2);

		t = tree.analyze(uesv, esv);
		System.out.println(t.toString());

		assertTrue(t.get(0).get(0).getFirst().equals("Mary") && t.get(1).get(0).getFirst().equals("Peter", null));
		
		// Test 6 - Test unknown that is almost equally likely to be of two authors
		
		EventSet unknown3 = new EventSet();
		
		unknown3.addEvent(new Event("peter", null));
		unknown3.addEvent(new Event("piper", null));
		unknown3.addEvent(new Event("a", null));
		unknown3.addEvent(new Event("little", null));
		unknown3.addEvent(new Event("lamb", null));
		
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
		
		known3.addEvent(new Event("mary", null));
		known3.addEvent(new Event("had", null));
		known3.addEvent(new Event("a", null));
		known3.addEvent(new Event("small", null));
		known3.addEvent(new Event("lamb", null));
		known3.setAuthor("Mary");

		known4.addEvent(new Event("peter", null));
		known4.addEvent(new Event("piper", null));
		known4.addEvent(new Event("collected", null));
		known4.addEvent(new Event("a", null));
		known4.addEvent(new Event("peck", null));
		known4.setAuthor("Peter");
		
		known6.addEvent(new Event("susie", null));
		known6.addEvent(new Event("sells", null));
		known6.addEvent(new Event("shells", null));
		known6.addEvent(new Event("by", null));
		known6.addEvent(new Event("seashore", null));
		known6.setAuthor("Susie");

		esv.add(known3);
		esv.add(known4);
		esv.add(known6);
		
		uesv = new Vector<EventSet>();
		uesv.add(unknown1);
		uesv.add(unknown2);
		
		t = tree.analyze(uesv, esv);
		System.out.println(t.toString());
		
		assertTrue(t.get(0).get(0).getFirst().equals("Mary") && t.get(1).get(0).getFirst().equals("Peter", null));
		 */
	}

}
