// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 * 
 */
package com.jgaap.classifiers;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Vector;
import java.lang.Math;

import org.junit.Test;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

/**
 * @author darrenvescovi
 * 
 */
public class RandomAnalysisTest {

	/**
	 * Test method for {@link
	 * com.jgaap.classifiers.RandomAnalysis#analyze(com.jgaap.generics.EventSet,
	 * List<EventSet>)}.
	 */
	@Test
	public void testAnalyze() {

		EventSet known1 = new EventSet();
		EventSet known2 = new EventSet();
		EventSet unknownMary = new EventSet();

		Vector<Event> test1 = new Vector<Event>();
		test1.add(new Event("Mary"));
		test1.add(new Event("had"));
		test1.add(new Event("a"));
		test1.add(new Event("little"));
		test1.add(new Event("lamb"));
		test1.add(new Event("whose"));
		test1.add(new Event("fleece"));
		test1.add(new Event("was"));
		test1.add(new Event("white"));
		test1.add(new Event("as"));
		test1.add(new Event("snow."));
		known1.addEvents(test1);
		known1.setAuthor("Mary");

		Vector<Event> test2 = new Vector<Event>();
		test2.add(new Event("Peter"));
		test2.add(new Event("piper"));
		test2.add(new Event("picked"));
		test2.add(new Event("a"));
		test2.add(new Event("pack"));
		test2.add(new Event("of"));
		test2.add(new Event("pickled"));
		test2.add(new Event("peppers."));
		known2.addEvents(test2);
		known2.setAuthor("Peter");

		Vector<Event> test3 = new Vector<Event>();
		test3.add(new Event("Mary"));
		test3.add(new Event("had"));
		test3.add(new Event("a"));
		test3.add(new Event("little"));
		test3.add(new Event("lambda"));
		test3.add(new Event("whose"));
		test3.add(new Event("syntax"));
		test3.add(new Event("was"));
		test3.add(new Event("white"));
		test3.add(new Event("as"));
		test3.add(new Event("snow."));
		unknownMary.addEvents(test3);

		Vector<EventSet> esv = new Vector<EventSet>();

		for (int i = 1; i <= 62; i = i + 1) {
			esv.add(known1);
		}

		for (int i = 63; i <= 100; i = i + 1) {
			esv.add(known2);
		}

		RandomAnalysis thing = new RandomAnalysis();

		int goodTest = 0; // hold the number of times Confidence interval covers
							// Prob of Mary = .62

		for (int z = 1; z <= 100; z = z + 1) {
			int j = 0; // hold number of marys

			for (int i = 1; i <= 1000; i = i + 1) {
				List<Pair<String,Double>> t = thing.analyze(unknownMary, esv);
				String r = t.get(0).getFirst();
				if (r.equals("Mary")) {
					j = j + 1;
				}
			}

			double probMary;
			double q = (double) j;
			probMary = q / 1000.0;

			double testStat;

			double p;
			p = (double) ((q * (1000 - q) / 1000.0) / 1000.0);

			testStat = 1.96 * Math.sqrt(p);

			if (.62 >= probMary - testStat && .62 <= probMary + testStat) {
				goodTest = goodTest + 1;
			}

		}

		double probGood;
		probGood = goodTest / 100; // this prob should be greater than .95
									// because the confidence interval
									// may not always cover the .62 mark for
									// author Mary

		assertTrue(probGood >= .95);

	}

}
