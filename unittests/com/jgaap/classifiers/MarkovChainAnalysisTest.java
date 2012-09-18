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
 * @author student
 *
 */
public class MarkovChainAnalysisTest {

	/**
	 * Test method for {@link com.jgaap.classifiers.MarkovChainAnalysis#analyze(com.jgaap.generics.EventSet, java.util.List)}.
	 */
	@Test
	public void testAnalyzeEventSetListOfEventSet() {
		
		String sample = "GATCATTGATATGTTGCTAGAACTATGATGTTAAAGGTGCTTGTGGTGAGTTA"+
							"TCAGACAGAAACGCAGAAGARGRRARRGGAAGCTTGAGGAAAAGTGATCCTGG"+
							"ATTTACAGTGCCAAGAATTGGCCTGTATTGTGTTCTCAATGTTTTTGAGGAAG"+
							"GTAGAAACTGTAAGTGATGA";
		
		String sample2 = "HJSNDSKAKSKDKAKSKDKSKAKSKDKAKAKSDKSKSKAKSKDKAKAHFKDLASAKDHFKDLSK";
		
		String sample3 ="ACGCA";

		EventSet known1 = new EventSet();
		EventSet known2 = new EventSet();
		EventSet unknown = new EventSet();
		
		for(int i=0; i<sample.length(); i++){
		known1.addEvent(new Event(sample.charAt(i)));
		}
		known1.setAuthor("Frodo");
		
		for(int i=0; i<sample2.length(); i++){
			known2.addEvent(new Event(sample2.charAt(i)));
		}
		known2.setAuthor("Sam");
		
		for(int i=0; i<sample3.length(); i++){
			unknown.addEvent(new Event(sample3.charAt(i)));
		}
		
		Vector<EventSet> esv = new Vector<EventSet>();
		esv.add(known1);
		esv.add(known2);
		
		MarkovChainAnalysis gandolf = new MarkovChainAnalysis();
		gandolf.train(esv);
		List<Pair<String, Double>> t = gandolf.analyze(unknown);
		for(int i=0; i<t.size(); i++){
			System.out.println(t.get(i).getFirst()+" "+t.get(i).getSecond());
		}
		
		System.out.println("-----------------");
		
		String r = t.get(0).getFirst();
		System.out.println(r);
		String s = "Frodo";
		System.out.println("-----------------");
		
		assertTrue(r.equals(s));
		
		
		
	}

}
