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
 * @author darrenvescovi
 *
 */
public class LDATest {

	/**
	 * Test method for {@link com.jgaap.classifiers.LDA#analyze(com.jgaap.util.EventSet)}.
	 * @throws AnalyzeException 
	 */
	@Test
	public void testAnalyze() throws AnalyzeException {
		//Test 1
		EventSet known1 = new EventSet();
		EventSet known2 = new EventSet();
		EventSet unknown = new EventSet();

		known1.addEvent(new Event("Mary", null));
		known1.addEvent(new Event("had", null));
		known1.addEvent(new Event("a", null));
		known1.addEvent(new Event("little", null));
		known1.addEvent(new Event("lamb", null));
		known1.addEvent(new Event("whose", null));
		known1.addEvent(new Event("fleece", null));
		known1.addEvent(new Event("was", null));
		known1.addEvent(new Event("white", null));
		known1.addEvent(new Event("as", null));
		known1.addEvent(new Event("snow.", null));
		//known1.setAuthor("Mary");


		known2.addEvent(new Event("Peter", null));
		known2.addEvent(new Event("piper", null));
		known2.addEvent(new Event("picked", null));
		known2.addEvent(new Event("a", null));
		known2.addEvent(new Event("pack", null));
		known2.addEvent(new Event("of", null));
		known2.addEvent(new Event("pickled", null));
		known2.addEvent(new Event("peppers.", null));
		//known2.setAuthor("Peter");

		unknown.addEvent(new Event("Mary", null));
		unknown.addEvent(new Event("had", null));
		unknown.addEvent(new Event("a", null));
		unknown.addEvent(new Event("little", null));
		unknown.addEvent(new Event("lambda", null));
		unknown.addEvent(new Event("whose", null));
		unknown.addEvent(new Event("syntax", null));
		unknown.addEvent(new Event("was", null));
		unknown.addEvent(new Event("white", null));
		unknown.addEvent(new Event("as", null));
		unknown.addEvent(new Event("snow.", null));

		List<Document> knowns = new ArrayList<Document>();
		Document knownDocument1 = new Document();
		knownDocument1.setAuthor("Mary");
		knownDocument1.addEventSet(null, known1);
		knowns.add(knownDocument1);
		Document knownDocument2 = new Document();
		knownDocument2.setAuthor("Peter");
		knownDocument2.addEventSet(null, known2);
		knowns.add(knownDocument2);
		
		Document unknownDocument = new Document();
		unknownDocument.addEventSet(null, unknown);
		LDA classifier = new LDA();
		classifier.train(knowns);
		List<Pair<String, Double>> t = classifier.analyze(unknownDocument);
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
		t = classifier.analyze(unknownDocument);
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
		LDA lda = new LDA();
		lda.train(knowns);
		t = lda.analyze(unknownDocument);
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
		unknown2.addEvent(new Event("Peter", null));
		unknown2.addEvent(new Event("pumpkin", null));
		unknown2.addEvent(new Event("picked", null));
		unknown2.addEvent(new Event("a", null));
		unknown2.addEvent(new Event("pack", null));
		unknown2.addEvent(new Event("of", null));
		unknown2.addEvent(new Event("pickled", null));
		unknown2.addEvent(new Event("potatoes.", null));
		
		Document unknownDocument2 = new Document();
		unknownDocument2.addEventSet(null, unknown2);
		
		List<List<Pair<String, Double>>> t2 = new ArrayList<List<Pair<String,Double>>>();
		t2.add(lda.analyze(unknownDocument));
		t2.add(lda.analyze(unknownDocument2));
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
