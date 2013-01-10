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
package com.jgaap.classifiers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.junit.Test;

import weka.classifiers.Classifier;

import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

/**
 * @author Amanda Kroft
 * 
 */
public class WEKALinearRegressionTest {

	/**
	 * Test method for {@link
	 * com.jgaap.classifiers.WEKAJ48DecisionTree#analyze(com.jgaap.generics.EventSet,
	 * List<EventSet>)}.
	 * @throws AnalyzeException 
	 */
	@Test
	public void testAnalyze() throws AnalyzeException {
		
		//Notes: Needs at least two docs per author, otherwise results become strange

		//Test 1

		//Create known texts
		EventSet known1 = new EventSet();
		EventSet known2 = new EventSet();
		EventSet known3 = new EventSet();
		EventSet known4 = new EventSet();

		known1.addEvent(new Event("mary", null));
		known1.addEvent(new Event("had", null));
		known1.addEvent(new Event("a", null));
		known1.addEvent(new Event("little", null));
		known1.addEvent(new Event("lamb", null));
		known1.setAuthor("Mary");
		
		known3.addEvent(new Event("mary", null));
		known3.addEvent(new Event("had", null));
		known3.addEvent(new Event("a", null));
		known3.addEvent(new Event("small", null));
		known3.addEvent(new Event("lamb", null));
		known3.setAuthor("Mary");

		known2.addEvent(new Event("peter", null));
		known2.addEvent(new Event("piper", null));
		known2.addEvent(new Event("picked", null));
		known2.addEvent(new Event("a", null));
		known2.addEvent(new Event("peck", null));
		known2.setAuthor("Peter");
		
		known4.addEvent(new Event("peter", null));
		known4.addEvent(new Event("piper", null));
		known4.addEvent(new Event("collected", null));
		known4.addEvent(new Event("a", null));
		known4.addEvent(new Event("peck", null));
		known4.setAuthor("Peter");

		Vector<EventSet> esv = new Vector<EventSet>();
		esv.add(known1);
		esv.add(known2);
		esv.add(known3);
		esv.add(known4);

		//Create unknown text
		EventSet unknown1 = new EventSet();

		unknown1.addEvent(new Event("mary", null));
		unknown1.addEvent(new Event("had", null));
		unknown1.addEvent(new Event("a", null));
		unknown1.addEvent(new Event("little", null));
		unknown1.addEvent(new Event("beta", null));

		Vector<EventSet> uesv = new Vector<EventSet>();
		uesv.add(unknown1);

		//Classify unknown based on the knowns
		WEKALinearRegression classifier = new WEKALinearRegression();
		List<List<Pair<String, Double>>> t = new ArrayList<List<Pair<String,Double>>>(); 
		classifier.train(esv);
		for(EventSet unknown : uesv){
			t.add(classifier.analyze(unknown));
		}
		System.out.println(t.toString());
		Classifier stuff = classifier.classifier;
		if(stuff != null)
			System.out.println(stuff.toString());

		//Assert that the authors match
		assertTrue(t.get(0).get(0).getFirst().equals("Mary", null));
		
		// Test 2 - Test equal likelihood
		
		EventSet unknown2 = new EventSet();
		
		unknown2.addEvent(new Event("piper", null));
		unknown2.addEvent(new Event("mary", null));
		unknown2.addEvent(new Event("a", null));
		unknown2.addEvent(new Event("peter", null));
		unknown2.addEvent(new Event("had", null));
		
		uesv = new Vector<EventSet>();
		uesv.add(unknown2);
		
		t = new ArrayList<List<Pair<String,Double>>>(); 
		classifier.train(esv);
		for(EventSet unknown : uesv){
			t.add(classifier.analyze(unknown));
		}
		System.out.println(t.toString());
		assertTrue(Math.abs(t.get(0).get(0).getSecond()-0.5)<.1 && Math.abs(t.get(0).get(1).getSecond()-0.5)<.1);

		// Test 3 - more training documents
		
		EventSet known5 = new EventSet();
		EventSet known6 = new EventSet();
		
		known5.addEvent(new Event("she", null));
		known5.addEvent(new Event("sells", null));
		known5.addEvent(new Event("seashells", null));
		known5.addEvent(new Event("by", null));
		known5.addEvent(new Event("seashore", null));
		known5.setAuthor("Susie");

		known6.addEvent(new Event("susie", null));
		known6.addEvent(new Event("sells", null));
		known6.addEvent(new Event("shells", null));
		known6.addEvent(new Event("by", null));
		known6.addEvent(new Event("seashore", null));
		known6.setAuthor("Susie");

		esv.add(known5);
		esv.add(known6);

		uesv = new Vector<EventSet>();
		uesv.add(unknown1);
		
		t = new ArrayList<List<Pair<String,Double>>>(); 
		classifier.train(esv);
		for(EventSet unknown : uesv){
			t.add(classifier.analyze(unknown));
		}
		System.out.println(t.toString());
		assertTrue(t.get(0).get(0).getFirst().equals("Mary", null));
	}

	//TODO: Test 4 - test documents/author requirements and exception handling
}
