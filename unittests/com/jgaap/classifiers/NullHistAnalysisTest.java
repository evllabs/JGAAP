// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
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
public class NullHistAnalysisTest {

	/**
	 * Test method for {@link com.jgaap.classifiers.NullHistAnalysis#analyze(com.jgaap.generics.EventSet, List<EventSet>)}.
	 */
	@Test
	public void testAnalyze() {
		EventSet known1 = new EventSet();
		EventSet unknown = new EventSet();
		
		Vector<Event> test1 = new Vector<Event>();
		test1.add(new Event("The"));
		test1.add(new Event("quick"));
		test1.add(new Event("brown"));
		test1.add(new Event("fox"));
		test1.add(new Event("jumps"));
		test1.add(new Event("over"));
		test1.add(new Event("the"));
		test1.add(new Event("lazy"));
		test1.add(new Event("dog"));
		test1.add(new Event("."));
		known1.addEvents(test1);
		unknown.addEvents(test1);
		
		Vector<EventSet> test = new Vector<EventSet>();
		test.add(known1);
		List<Pair<String, Double>> t = new NullHistAnalysis().analyze(unknown, test);
		String r = t.get(0).getFirst();
		String s = "No analysis performed.\n";
		assertTrue(r.equals(s));
	}

}
