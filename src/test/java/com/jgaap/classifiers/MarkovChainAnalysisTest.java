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


import com.jgaap.util.Document;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;
import com.jgaap.util.Pair;


/**
 * @author student
 *
 */
public class MarkovChainAnalysisTest {

	/**
	 * Test method for {@link com.jgaap.classifiers.MarkovChainAnalysis#analyze(com.jgaap.util.EventSet, java.util.List)}.
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
		known1.addEvent(new Event(sample.charAt(i), null));
		}
		//known1.setAuthor("Frodo");
		
		for(int i=0; i<sample2.length(); i++){
			known2.addEvent(new Event(sample2.charAt(i), null));
		}
		//known2.setAuthor("Sam");
		
		for(int i=0; i<sample3.length(); i++){
			unknown.addEvent(new Event(sample3.charAt(i), null));
		}
		
		List<Document> knowns = new ArrayList<Document>();
		Document knownDocument1 = new Document();
		knownDocument1.setAuthor("Frodo");
		knownDocument1.addEventSet(null, known1);
		knowns.add(knownDocument1);
		Document knownDocument2 = new Document();
		knownDocument2.setAuthor("Sam");
		knownDocument2.addEventSet(null, known2);
		knowns.add(knownDocument2);
		
		Document unknownDocument = new Document();
		unknownDocument.addEventSet(null, unknown);
		
		MarkovChainAnalysis gandolf = new MarkovChainAnalysis();
		gandolf.train(knowns);
		List<Pair<String, Double>> t = gandolf.analyze(unknownDocument);
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
