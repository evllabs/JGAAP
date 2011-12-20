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
import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

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
	public void testAnalyze() throws AnalyzeException {
		
		//Test 1
		
		EventSet known1 = new EventSet();
		EventSet known2 = new EventSet();
		EventSet known3 = new EventSet();
		EventSet known4 = new EventSet();
		EventSet unknown = new EventSet();

		known1.addEvent(new Event("alpha"));
		known1.addEvent(new Event("alpha"));
		known1.addEvent(new Event("alpha"));
		known1.addEvent(new Event("alpha"));
		known1.addEvent(new Event("betta"));
		known1.setAuthor("Mary");
		
		known3.addEvent(new Event("alpha"));
		known3.addEvent(new Event("alpha"));
		known3.addEvent(new Event("alpha"));
		known3.addEvent(new Event("betta"));
		known3.addEvent(new Event("betta"));
		known3.setAuthor("Mary");

		known2.addEvent(new Event("alpha"));
		known2.addEvent(new Event("betta"));
		known2.addEvent(new Event("betta"));
		known2.addEvent(new Event("betta"));
		known2.addEvent(new Event("betta"));
		known2.setAuthor("Peter");
		
		known4.addEvent(new Event("alpha"));
		known4.addEvent(new Event("alpha"));
		known4.addEvent(new Event("betta"));
		known4.addEvent(new Event("betta"));
		known4.addEvent(new Event("betta"));
		known4.setAuthor("Peter");

		unknown.addEvent(new Event("alpha"));
		unknown.addEvent(new Event("alpha"));
		unknown.addEvent(new Event("betta"));
		unknown.addEvent(new Event("alpha"));
		unknown.addEvent(new Event("alpha"));
		
		double[] probs = new double[2];
		//R code : pnorm(80,70,sqrt(200),lower.tail=FALSE)*pnorm(20,30,sqrt(200))*.5
		probs[0] = 0.02874005; //Mary
		//R code : pnorm(80,30,sqrt(200),lower.tail=FALSE)*pnorm(20,70,sqrt(200))*.5
		probs[1] = 2.070124e-08; //Peter
		weka.core.Utils.normalize(probs);
		
		List<EventSet> unknownList = new ArrayList<EventSet>(1);
		unknownList.add(unknown);
		List<EventSet> esv = new ArrayList<EventSet>();
		esv.add(known1);
		esv.add(known2);
		esv.add(known3);
		esv.add(known4);

		List<Pair<String, Double>> t = new WEKANaiveBayes().analyze(unknownList, esv).get(0);
		String r = t.get(0).getFirst();
		/*System.out.println("Classified");
		System.out.println("First : "+r+" "+t.get(0).getSecond());
		System.out.println("Second: "+t.get(1).getFirst()+" "+t.get(1).getSecond());
		System.out.println("Expected");
		System.out.println("First : Mary "+probs[0]);
		System.out.println("Second: Peter "+probs[1]);*/
		String s = "Mary";

		//Assert that the probability for each author match within a threshold
		if(t.get(0).getFirst().equals("Mary")){
			assertTrue(Math.abs(t.get(0).getSecond()-probs[0])<.005 && Math.abs(t.get(1).getSecond()-probs[1])<.005);
		}else{
			assertTrue(Math.abs(t.get(1).getSecond()-probs[0])<.005 && Math.abs(t.get(0).getSecond()-probs[1])<.005);
		}
		
		//Assert that the authors match
		assertTrue(r.equals(s));
		
		//Test 2
		unknown = new EventSet();
		
		unknown.addEvent(new Event("alpha"));
		unknown.addEvent(new Event("alpha"));
		unknown.addEvent(new Event("betta"));
		unknown.addEvent(new Event("alpha"));
		unknown.addEvent(new Event("betta"));
		
		unknownList.clear();
		unknownList.add(unknown);
		
		//R code : pnorm(60,70,sqrt(200))*pnorm(40,30,sqrt(200),lower.tail=FALSE)*.5
		probs[0] = 0.02874005; //Mary
		//R code : pnorm(60,30,sqrt(200),lower.tail=FALSE)*pnorm(40,70,sqrt(200))*.5
		probs[1] = 0.0001436076; //Peter
		weka.core.Utils.normalize(probs);
		
		t = new WEKANaiveBayes().analyze(unknownList, esv).get(0);
		
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
		
		unknown.addEvent(new Event("alpha"));
		unknown.addEvent(new Event("alpha"));
		unknown.addEvent(new Event("betta"));
		unknown.addEvent(new Event("betta"));
		
		unknownList.clear();
		unknownList.add(unknown);
		
		probs[0] = .5; //Mary
		probs[1] = .5; //Peter
		
		t = new WEKANaiveBayes().analyze(unknownList, esv).get(0);
		
		if(t.get(0).getFirst().equals("Mary")){
			assertTrue(Math.abs(t.get(0).getSecond()-probs[0])<.005 && Math.abs(t.get(1).getSecond()-probs[1])<.005);
		}else{
			assertTrue(Math.abs(t.get(1).getSecond()-probs[0])<.005 && Math.abs(t.get(0).getSecond()-probs[1])<.005);
		}
		
		//Test 4
		unknown = new EventSet();
		
		unknown.addEvent(new Event("alpha"));
		unknown.addEvent(new Event("alpha"));
		unknown.addEvent(new Event("betta"));
		unknown.addEvent(new Event("betta"));
		unknown.addEvent(new Event("betta"));
		
		unknownList.clear();
		unknownList.add(unknown);
		
		//R code : pnorm(40,70,sqrt(200))*pnorm(60,30,sqrt(200),lower.tail=FALSE)*.5
		probs[0] = 0.0001436076; //Mary
		//R code : pnorm(40,30,sqrt(200),lower.tail=FALSE)*pnorm(60,70,sqrt(200))*.5
		probs[1] = 0.02874005; //Peter
		weka.core.Utils.normalize(probs);
		
		t = new WEKANaiveBayes().analyze(unknownList, esv).get(0);
		
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
		
		unknown.addEvent(new Event("betta"));
		unknown.addEvent(new Event("betta"));
		unknown.addEvent(new Event("betta"));
		unknown.addEvent(new Event("betta"));
		unknown.addEvent(new Event("betta"));
		
		unknownList.clear();
		unknownList.add(unknown);
		
		//R code : pnorm(0,70,sqrt(200))*pnorm(100,30,sqrt(200),lower.tail=FALSE)*.5
		probs[0] = 6.90244e-14; //Mary
		//R code : pnorm(0,30,sqrt(200))*pnorm(100,70,sqrt(200),lower.tail=FALSE)*.5
		probs[1] = 0.0001436076; //Peter
		weka.core.Utils.normalize(probs);
		
		t = new WEKANaiveBayes().analyze(unknownList, esv).get(0);
		
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
		
		unknown.addEvent(new Event("betta"));
		unknown.addEvent(new Event("betta"));
		unknown.addEvent(new Event("betta"));
		unknown.addEvent(new Event("gamma"));
		unknown.addEvent(new Event("betta"));
		
		unknownList.clear();
		unknownList.add(unknown);
		
		//R code : pnorm(0,70,sqrt(200))*pnorm(80,30,sqrt(200),lower.tail=FALSE)*.5*1e-50
		probs[0] = 3.780067e-61; //Mary
		//R code : pnorm(0,30,sqrt(200))*pnorm(80,70,sqrt(200),lower.tail=FALSE)*.5*1e-50
		probs[1] = 2.031573e-53; //Peter
		weka.core.Utils.normalize(probs);
		
		t = new WEKANaiveBayes().analyze(unknownList, esv).get(0);
		
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
