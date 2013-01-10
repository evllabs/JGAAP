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

import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

/**
 * @author Amanda Kroft
 * 
 */
public class WEKADecisionStumpTest {

	/**
	 * Test method for {@link
	 * com.jgaap.classifiers.WEKADecisionStump#analyze(com.jgaap.generics.EventSet,
	 * List<EventSet>)}.
	 * @throws AnalyzeException 
	 */
	@Test
	public void testAnalyze() throws AnalyzeException {
		
		//Note, Decision Stumps have only one node, so only one decision is made.
		
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

		//Create unknown texts
		EventSet unknown1 = new EventSet();
		EventSet unknown2 = new EventSet();

		unknown1.addEvent(new Event("mary", null));
		unknown1.addEvent(new Event("had", null));
		unknown1.addEvent(new Event("a", null));
		unknown1.addEvent(new Event("little", null));
		unknown1.addEvent(new Event("beta", null));
		
		unknown2.addEvent(new Event("peter", null));
		unknown2.addEvent(new Event("piper", null));
		unknown2.addEvent(new Event("picked", null));
		unknown2.addEvent(new Event("a", null));
		unknown2.addEvent(new Event("shells", null));

		Vector<EventSet> uesv = new Vector<EventSet>();
		uesv.add(unknown1);
		uesv.add(unknown2);

		//Classify unknown based on the knowns
		WEKADecisionStump classifier = new WEKADecisionStump();
		classifier.train(esv);
		List<List<Pair<String, Double>>> t = new ArrayList<List<Pair<String,Double>>>(); 
		for(EventSet unknown : uesv)		
			t.add(classifier.analyze(unknown));
		//System.out.println(t.toString());
		//[[[Mary:1.0], [Peter:0.0]], [[Peter:1.0], [Mary:0.0]]]

			//System.out.println(t.toString());
			//[[[Mary:1.0], [Peter:0.0]], [[Peter:1.0], [Mary:0.0]]]

			//Assert that the authors match
			assertTrue(t.get(0).get(0).getFirst().equals("Mary") && t.get(1).get(0).getFirst().equals("Peter", null));

		//Test 2 - Different documents
		
		//Redefine known documents
		known1 = new EventSet();
		known2 = new EventSet();
		known3 = new EventSet();
		known4 = new EventSet();
		
		known1.addEvent(new Event("alpha", null));
		known1.addEvent(new Event("alpha", null));
		known1.addEvent(new Event("alpha", null));
		known1.addEvent(new Event("alpha", null));
		known1.addEvent(new Event("beta", null));
		known1.setAuthor("Mary");
		
		known3.addEvent(new Event("alpha", null));
		known3.addEvent(new Event("alpha", null));
		known3.addEvent(new Event("alpha", null));
		known3.addEvent(new Event("beta", null));
		known3.addEvent(new Event("beta", null));
		known3.setAuthor("Mary");

		known2.addEvent(new Event("alpha", null));
		known2.addEvent(new Event("beta", null));
		known2.addEvent(new Event("beta", null));
		known2.addEvent(new Event("beta", null));
		known2.addEvent(new Event("beta", null));
		known2.setAuthor("Peter");
		
		known4.addEvent(new Event("alpha", null));
		known4.addEvent(new Event("alpha", null));
		known4.addEvent(new Event("beta", null));
		known4.addEvent(new Event("beta", null));
		known4.addEvent(new Event("beta", null));
		known4.setAuthor("Peter");
		
		esv = new Vector<EventSet>();
		esv.add(known1);
		esv.add(known2);
		esv.add(known3);
		esv.add(known4);

		//Create unknown texts
		unknown1 = new EventSet();
		unknown2 = new EventSet();

		unknown1.addEvent(new Event("alpha", null));
		unknown1.addEvent(new Event("alpha", null));
		unknown1.addEvent(new Event("alpha", null));
		unknown1.addEvent(new Event("alpha", null));
		unknown1.addEvent(new Event("alpha", null));
		
		unknown2.addEvent(new Event("beta", null));
		unknown2.addEvent(new Event("beta", null));
		unknown2.addEvent(new Event("beta", null));
		unknown2.addEvent(new Event("beta", null));
		unknown2.addEvent(new Event("beta", null));

		uesv = new Vector<EventSet>();
		uesv.add(unknown1);
		uesv.add(unknown2);
		
		//Classify unknown based on the knowns
		classifier = new WEKADecisionStump();
		classifier.train(esv);
		t = new ArrayList<List<Pair<String,Double>>>(); 
		for(EventSet unknown : uesv)		
			t.add(classifier.analyze(unknown));
		//System.out.println(classifier.classifier.toString());
		//System.out.println(t.toString());
		//[[[Mary:1.0], [Peter:0.0]], [[Peter:1.0], [Mary:0.0]]]


			//Assert that the authors match
			assertTrue(t.get(0).get(0).getFirst().equals("Mary") && t.get(1).get(0).getFirst().equals("Peter", null));

	}
	
}
