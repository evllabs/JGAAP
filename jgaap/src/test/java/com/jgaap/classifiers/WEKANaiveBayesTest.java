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
public class WEKANaiveBayesTest {

	/**
	 * Test method for {@link
	 * com.jgaap.classifiers.WEKANaiveBayes#analyze(com.jgaap.generics.EventSet,
	 * List<EventSet>)}.
	 * @throws AnalyzeException 
	 */
	@Test
	public void testAnalyze() throws AnalyzeException{
		
		//Test 1
		
		EventSet known1 = new EventSet();
		EventSet known2 = new EventSet();
		EventSet known3 = new EventSet();
		EventSet known4 = new EventSet();
		EventSet unknown = new EventSet();

		known1.addEvent(new Event("alpha", null));
		known1.addEvent(new Event("alpha", null));
		known1.addEvent(new Event("alpha", null));
		known1.addEvent(new Event("alpha", null));
		known1.addEvent(new Event("betta", null));
		//known1.setAuthor("Mary");
		
		known3.addEvent(new Event("alpha", null));
		known3.addEvent(new Event("alpha", null));
		known3.addEvent(new Event("alpha", null));
		known3.addEvent(new Event("betta", null));
		known3.addEvent(new Event("betta", null));
		//known3.setAuthor("Mary");

		known2.addEvent(new Event("alpha", null));
		known2.addEvent(new Event("betta", null));
		known2.addEvent(new Event("betta", null));
		known2.addEvent(new Event("betta", null));
		known2.addEvent(new Event("betta", null));
		//known2.setAuthor("Peter");
		
		known4.addEvent(new Event("alpha", null));
		known4.addEvent(new Event("alpha", null));
		known4.addEvent(new Event("betta", null));
		known4.addEvent(new Event("betta", null));
		known4.addEvent(new Event("betta", null));
		//known4.setAuthor("Peter");

		unknown.addEvent(new Event("alpha", null));
		unknown.addEvent(new Event("alpha", null));
		unknown.addEvent(new Event("betta", null));
		unknown.addEvent(new Event("alpha", null));
		unknown.addEvent(new Event("alpha", null));
		
		double[] probs = new double[2];
		//R code : pnorm(80,70,sqrt(200),lower.tail=FALSE)*pnorm(20,30,sqrt(200))*.5
		probs[0] = 0.02874005; //Mary
		//R code : pnorm(80,30,sqrt(200),lower.tail=FALSE)*pnorm(20,70,sqrt(200))*.5
		probs[1] = 2.070124e-08; //Peter
		weka.core.Utils.normalize(probs);
		
		Document unknownDocument = new Document();
		unknownDocument.addEventSet(null, unknown);
		
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

		WEKANaiveBayes classifier = new WEKANaiveBayes();
		classifier.train(knowns);
		List<Pair<String, Double>> t = classifier.analyze(unknownDocument);


		// Assert that the probability for each author match within a threshold
		if (t.get(0).getFirst().equals("Mary")) {
			assertTrue(Math.abs(t.get(0).getSecond() - probs[0]) < .005
					&& Math.abs(t.get(1).getSecond() - probs[1]) < .005);
		} else {
			assertTrue(Math.abs(t.get(1).getSecond() - probs[0]) < .005
					&& Math.abs(t.get(0).getSecond() - probs[1]) < .005);
		}
		
		//Test 2
		unknown = new EventSet();
		
		unknown.addEvent(new Event("alpha", null));
		unknown.addEvent(new Event("alpha", null));
		unknown.addEvent(new Event("betta", null));
		unknown.addEvent(new Event("alpha", null));
		unknown.addEvent(new Event("betta", null));
		
		unknownDocument.addEventSet(null, unknown);
		
		//R code : pnorm(60,70,sqrt(200))*pnorm(40,30,sqrt(200),lower.tail=FALSE)*.5
		probs[0] = 0.02874005; //Mary
		//R code : pnorm(60,30,sqrt(200),lower.tail=FALSE)*pnorm(40,70,sqrt(200))*.5
		probs[1] = 0.0001436076; //Peter
		weka.core.Utils.normalize(probs);
		
		classifier = new WEKANaiveBayes();
		classifier.train(knowns);
		t = classifier.analyze(unknownDocument);
		
		/*System.out.println("Classified");
		System.out.println("First : "+t.get(0).getFirst()+" "+t.get(0).getSecond());
		System.out.println("Second: "+t.get(1).getFirst()+" "+t.get(1).getSecond());
		System.out.println("Expected");
		System.out.println("First : Mary "+probs[0]);
		System.out.println("Second: Peter "+probs[1]);*/
		
		if(t.get(0).getFirst().equals("Mary")){
			assertTrue(Math.abs(t.get(0).getSecond()-probs[0])<.005 && Math.abs(t.get(1).getSecond()-probs[1])<.005);
		}else{
			assertTrue(Math.abs(t.get(1).getSecond()-probs[0])<.005 && Math.abs(t.get(0).getSecond()-probs[1])<.005);
		}

		
		//Test 3
		unknown = new EventSet();
		
		unknown.addEvent(new Event("alpha", null));
		unknown.addEvent(new Event("alpha", null));
		unknown.addEvent(new Event("betta", null));
		unknown.addEvent(new Event("betta", null));
		
		unknownDocument.addEventSet(null, unknown);
		
		probs[0] = .5; //Mary
		probs[1] = .5; //Peter
		
		classifier = new WEKANaiveBayes();
		classifier.train(knowns);
		t = classifier.analyze(unknownDocument);
		
		if(t.get(0).getFirst().equals("Mary")){
			assertTrue(Math.abs(t.get(0).getSecond()-probs[0])<.005 && Math.abs(t.get(1).getSecond()-probs[1])<.005);
		}else{
			assertTrue(Math.abs(t.get(1).getSecond()-probs[0])<.005 && Math.abs(t.get(0).getSecond()-probs[1])<.005);

		}

		
		//Test 4
		unknown = new EventSet();
		
		unknown.addEvent(new Event("alpha", null));
		unknown.addEvent(new Event("alpha", null));
		unknown.addEvent(new Event("betta", null));
		unknown.addEvent(new Event("betta", null));
		unknown.addEvent(new Event("betta", null));
		
		unknownDocument.addEventSet(null, unknown);
		
		//R code : pnorm(40,70,sqrt(200))*pnorm(60,30,sqrt(200),lower.tail=FALSE)*.5
		probs[0] = 0.0001436076; //Mary
		//R code : pnorm(40,30,sqrt(200),lower.tail=FALSE)*pnorm(60,70,sqrt(200))*.5
		probs[1] = 0.02874005; //Peter
		weka.core.Utils.normalize(probs);
		
		classifier = new WEKANaiveBayes();
		classifier.train(knowns);
		t = classifier.analyze(unknownDocument);
		
		/*System.out.println("Classified");
		System.out.println(t.get(0).getFirst()+" "+t.get(0).getSecond());
		System.out.println(t.get(1).getFirst()+" "+t.get(1).getSecond());
		System.out.println("Expected");
		System.out.println("Mary "+probs[0]);
		System.out.println("Peter "+probs[1]);*/
		
		if(t.get(0).getFirst().equals("Mary")){
			assertTrue(Math.abs(t.get(0).getSecond()-probs[0])<.005 && Math.abs(t.get(1).getSecond()-probs[1])<.005);
		}else{
			assertTrue(Math.abs(t.get(1).getSecond()-probs[0])<.005 && Math.abs(t.get(0).getSecond()-probs[1])<.005);
		}

		
		//Test 5
		unknown = new EventSet();
		
		unknown.addEvent(new Event("betta", null));
		unknown.addEvent(new Event("betta", null));
		unknown.addEvent(new Event("betta", null));
		unknown.addEvent(new Event("betta", null));
		unknown.addEvent(new Event("betta", null));
		
		unknownDocument.addEventSet(null, unknown);
		
		//R code : pnorm(0,70,sqrt(200))*pnorm(100,30,sqrt(200),lower.tail=FALSE)*.5
		probs[0] = 6.90244e-14; //Mary
		//R code : pnorm(0,30,sqrt(200))*pnorm(100,70,sqrt(200),lower.tail=FALSE)*.5
		probs[1] = 0.0001436076; //Peter
		weka.core.Utils.normalize(probs);
		
		classifier = new WEKANaiveBayes();
		classifier.train(knowns);
		t = classifier.analyze(unknownDocument);
		
		System.out.println("Classified");
		System.out.println(t.get(0).getFirst()+" "+t.get(0).getSecond());
		System.out.println(t.get(1).getFirst()+" "+t.get(1).getSecond());
		System.out.println("Expected");
		System.out.println("Mary "+probs[0]);
		System.out.println("Peter "+probs[1]);
		
		if(t.get(0).getFirst().equals("Mary")){
			assertTrue(Math.abs(t.get(0).getSecond()-probs[0])<.005 && Math.abs(t.get(1).getSecond()-probs[1])<.005);
		}else{
			assertTrue(Math.abs(t.get(1).getSecond()-probs[0])<.005 && Math.abs(t.get(0).getSecond()-probs[1])<.005);

		}

		
		//Test 6
		unknown = new EventSet();
		
		unknown.addEvent(new Event("betta", null));
		unknown.addEvent(new Event("betta", null));
		unknown.addEvent(new Event("betta", null));
		unknown.addEvent(new Event("gamma", null));
		unknown.addEvent(new Event("betta", null));
		
		unknownDocument.addEventSet(null, unknown);;
		
		//R code : pnorm(0,70,sqrt(200))*pnorm(80,30,sqrt(200),lower.tail=FALSE)*.5*1e-50
		probs[0] = 3.780067e-61; //Mary
		//R code : pnorm(0,30,sqrt(200))*pnorm(80,70,sqrt(200),lower.tail=FALSE)*.5*1e-50
		probs[1] = 2.031573e-53; //Peter
		weka.core.Utils.normalize(probs);
		
		classifier = new WEKANaiveBayes();
		classifier.train(knowns);
		t = classifier.analyze(unknownDocument);
		
		System.out.println("Classified");
		System.out.println(t.get(0).getFirst()+" "+t.get(0).getSecond());
		System.out.println(t.get(1).getFirst()+" "+t.get(1).getSecond());
		System.out.println("Expected");
		System.out.println("Mary "+probs[0]);
		System.out.println("Peter "+probs[1]);
		
		if(t.get(0).getFirst().equals("Mary")){
			assertTrue(Math.abs(t.get(0).getSecond()-probs[0])<.005 && Math.abs(t.get(1).getSecond()-probs[1])<.005);
		}else{
			assertTrue(Math.abs(t.get(1).getSecond()-probs[0])<.005 && Math.abs(t.get(0).getSecond()-probs[1])<.005);
		}

	}

}
