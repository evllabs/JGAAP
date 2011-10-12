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

import java.util.List;
import java.util.Vector;

import org.junit.Test;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

/**
 * @author darrenvescovi
 *
 */
public class LDATest {

	/**
	 * Test method for {@link com.jgaap.classifiers.LDA#analyze(com.jgaap.generics.EventSet, List<EventSet>)}.
	 */
	@Test
	public void testAnalyze() {
		//Test 1
		EventSet known1 = new EventSet();
		EventSet known2 = new EventSet();
		EventSet unknown = new EventSet();

		known1.addEvent(new Event("Mary"));
		known1.addEvent(new Event("had"));
		known1.addEvent(new Event("a"));
		known1.addEvent(new Event("little"));
		known1.addEvent(new Event("lamb"));
		known1.addEvent(new Event("whose"));
		known1.addEvent(new Event("fleece"));
		known1.addEvent(new Event("was"));
		known1.addEvent(new Event("white"));
		known1.addEvent(new Event("as"));
		known1.addEvent(new Event("snow."));
		known1.setAuthor("Mary");


		known2.addEvent(new Event("Peter"));
		known2.addEvent(new Event("piper"));
		known2.addEvent(new Event("picked"));
		known2.addEvent(new Event("a"));
		known2.addEvent(new Event("pack"));
		known2.addEvent(new Event("of"));
		known2.addEvent(new Event("pickled"));
		known2.addEvent(new Event("peppers."));
		known2.setAuthor("Peter");

		unknown.addEvent(new Event("Mary"));
		unknown.addEvent(new Event("had"));
		unknown.addEvent(new Event("a"));
		unknown.addEvent(new Event("little"));
		unknown.addEvent(new Event("lambda"));
		unknown.addEvent(new Event("whose"));
		unknown.addEvent(new Event("syntax"));
		unknown.addEvent(new Event("was"));
		unknown.addEvent(new Event("white"));
		unknown.addEvent(new Event("as"));
		unknown.addEvent(new Event("snow."));

		Vector <EventSet> esv = new Vector<EventSet>();
		esv.add(known1);
		esv.add(known2);
		LDA classifier = new LDA();
		List<Pair<String, Double>> t = classifier.analyze(unknown, esv);
		String author1 = t.get(0).getFirst();
		String author2 = t.get(1).getFirst();
		Double val1 = t.get(0).getSecond();
		Double val2 = t.get(1).getSecond();
		/*System.out.println("Test 1 Classified");
		System.out.println("First : "+author1+" "+t.get(0).getSecond());
		System.out.println("Second: "+author2+" "+t.get(1).getSecond());
		System.out.println("Expected");
		System.out.println("First : Mary");
		System.out.println("Second: Peter");*/
		assertTrue(author1.equals("Mary"));

		//Test 2 - Same classifier
		//Testing for persistence
		t = classifier.analyze(unknown,esv);
		/*System.out.println("Test 2 Classified");
		System.out.println("First : "+t.get(0).getFirst()+" "+t.get(0).getSecond());
		System.out.println("Second: "+t.get(1).getFirst()+" "+t.get(1).getSecond());
		System.out.println("Expected");
		System.out.println("First : Mary");
		System.out.println("Second: Peter");*/
		assertTrue(author1.equals(t.get(0).getFirst()) && Math.abs(val1 - t.get(0).getSecond()) < .000001
				&& author2.equals(t.get(1).getFirst()) && Math.abs(val2 - t.get(1).getSecond()) < .000001);
		
		//Test 3 - Different instance of classifier
		//Again testing for persistence
		t = new LDA().analyze(unknown, esv);
		//String r = t.get(0).getFirst();
		/*System.out.println("Test 3 Classified");
		System.out.println("First : "+r+" "+t.get(0).getSecond());
		System.out.println("Second: "+t.get(1).getFirst()+" "+t.get(1).getSecond());
		System.out.println("Expected");
		System.out.println("First : Mary");
		System.out.println("Second: Peter");*/
		assertTrue(author1.equals(t.get(0).getFirst()) && Math.abs(val1 - t.get(0).getSecond()) < .000001
				&& author2.equals(t.get(1).getFirst()) && Math.abs(val2 - t.get(1).getSecond()) < .000001);
		
		//Test 4 - two unknowns
		EventSet unknown2 = new EventSet();
		unknown2.addEvent(new Event("Peter"));
		unknown2.addEvent(new Event("pumpkin"));
		unknown2.addEvent(new Event("picked"));
		unknown2.addEvent(new Event("a"));
		unknown2.addEvent(new Event("pack"));
		unknown2.addEvent(new Event("of"));
		unknown2.addEvent(new Event("pickled"));
		unknown2.addEvent(new Event("potatoes."));
		
		Vector <EventSet> uesv = new Vector<EventSet>();
		uesv.add(unknown);
		uesv.add(unknown2);
		
		List<List<Pair<String, Double>>> t2 = classifier.analyze(uesv, esv);
		/*for(int i = 0; i < t2.size(); i++){
			System.out.println("Classification of unknown #"+(i+1));
			for(int j =0; j < t2.get(i).size(); j++){
				System.out.println((j+1)+". "+t2.get(i).get(j).getFirst()+":"+t2.get(i).get(j).getSecond());
			}
			System.out.println();
		}*/
		
		assertTrue(t2.get(0).get(0).getFirst().equals("Mary") && t2.get(1).get(0).getFirst().equals("Peter"));
		
	}
}
