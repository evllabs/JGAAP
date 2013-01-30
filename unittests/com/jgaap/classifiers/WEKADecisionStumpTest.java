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

import org.junit.Test;

import com.jgaap.generics.AnalyzeException;
import com.jgaap.util.Document;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;
import com.jgaap.util.Pair;

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
		//known1.setAuthor("Mary");
		
		known3.addEvent(new Event("mary", null));
		known3.addEvent(new Event("had", null));
		known3.addEvent(new Event("a", null));
		known3.addEvent(new Event("small", null));
		known3.addEvent(new Event("lamb", null));
		//known3.setAuthor("Mary");

		known2.addEvent(new Event("peter", null));
		known2.addEvent(new Event("piper", null));
		known2.addEvent(new Event("picked", null));
		known2.addEvent(new Event("a", null));
		known2.addEvent(new Event("peck", null));
		//known2.setAuthor("Peter");
		
		known4.addEvent(new Event("peter", null));
		known4.addEvent(new Event("piper", null));
		known4.addEvent(new Event("collected", null));
		known4.addEvent(new Event("a", null));
		known4.addEvent(new Event("peck", null));
		//known4.setAuthor("Peter");

		List<Document> knowns = new ArrayList<Document>();
		Document knownDocument1 = new Document();
		knownDocument1.setAuthor("Mary");
		knownDocument1.addEventSet(null, known1);
		knowns.add(knownDocument1);
		Document knownDocument2 = new Document();
		knownDocument2.setAuthor("Peter");
		knownDocument2.addEventSet(null, known2);
		knowns.add(knownDocument2);
		Document knownDocument3 = new Document();
		knownDocument3.setAuthor("Mary");
		knownDocument3.addEventSet(null, known3);
		knowns.add(knownDocument3);
		Document knownDocument4 = new Document();
		knownDocument4.setAuthor("Peter");
		knownDocument4.addEventSet(null, known4);
		knowns.add(knownDocument4);

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

		Document unknownDocument = new Document();
		unknownDocument.addEventSet(null, unknown1);
		Document unknownDocument2 = new Document();
		unknownDocument2.addEventSet(null, unknown2);

		//Classify unknown based on the knowns
		WEKADecisionStump classifier = new WEKADecisionStump();
		classifier.train(knowns);
		List<List<Pair<String, Double>>> t = new ArrayList<List<Pair<String,Double>>>(); 
		t.add(classifier.analyze(unknownDocument));
		t.add(classifier.analyze(unknownDocument2));
		System.out.println(knowns);
		System.out.println(unknownDocument.getEventSet(null));
		System.out.println(unknownDocument2.getEventSet(null));
		System.out.println(t.toString());
		//[[[Mary:1.0], [Peter:0.0]], [[Peter:1.0], [Mary:0.0]]]

			//System.out.println(t.toString());
			//[[[Mary:1.0], [Peter:0.0]], [[Peter:1.0], [Mary:0.0]]]

			//Assert that the authors match
			assertTrue(t.get(0).get(0).getFirst().equals("Mary") && t.get(1).get(0).getFirst().equals("Peter"));

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
		//known1.setAuthor("Mary");
		
		known3.addEvent(new Event("alpha", null));
		known3.addEvent(new Event("alpha", null));
		known3.addEvent(new Event("alpha", null));
		known3.addEvent(new Event("beta", null));
		known3.addEvent(new Event("beta", null));
		//known3.setAuthor("Mary");

		known2.addEvent(new Event("alpha", null));
		known2.addEvent(new Event("beta", null));
		known2.addEvent(new Event("beta", null));
		known2.addEvent(new Event("beta", null));
		known2.addEvent(new Event("beta", null));
		//known2.setAuthor("Peter");
		
		known4.addEvent(new Event("alpha", null));
		known4.addEvent(new Event("alpha", null));
		known4.addEvent(new Event("beta", null));
		known4.addEvent(new Event("beta", null));
		known4.addEvent(new Event("beta", null));
		//known4.setAuthor("Peter");
		
		knowns = new ArrayList<Document>();
		knownDocument1 = new Document();
		knownDocument1.setAuthor("Mary");
		knownDocument1.addEventSet(null, known1);
		knowns.add(knownDocument1);
		knownDocument2 = new Document();
		knownDocument2.setAuthor("Peter");
		knownDocument2.addEventSet(null, known2);
		knowns.add(knownDocument2);
		knownDocument3 = new Document();
		knownDocument3.setAuthor("Mary");
		knownDocument3.addEventSet(null, known3);
		knowns.add(knownDocument3);
		knownDocument4 = new Document();
		knownDocument4.setAuthor("Peter");
		knownDocument4.addEventSet(null, known4);
		knowns.add(knownDocument4);

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

		unknownDocument = new Document();
		unknownDocument.addEventSet(null, unknown1);
		unknownDocument2 = new Document();
		unknownDocument2.addEventSet(null, unknown2);
		
		//Classify unknown based on the knowns
		classifier = new WEKADecisionStump();
		classifier.train(knowns);
		t = new ArrayList<List<Pair<String,Double>>>(); 
		t.add(classifier.analyze(unknownDocument));
		t.add(classifier.analyze(unknownDocument2));
		//System.out.println(classifier.classifier.toString());
		//System.out.println(t.toString());
		//[[[Mary:1.0], [Peter:0.0]], [[Peter:1.0], [Mary:0.0]]]


			//Assert that the authors match
			assertTrue(t.get(0).get(0).getFirst().equals("Mary") && t.get(1).get(0).getFirst().equals("Peter"));

	}
	
}
