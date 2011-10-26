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
 * @author Amanda Kroft
 * 
 */
public class WEKADecisionStumpTest {

	/**
	 * Test method for {@link
	 * com.jgaap.classifiers.WEKADecisionStump#analyze(com.jgaap.generics.EventSet,
	 * List<EventSet>)}.
	 */
	@Test
	public void testAnalyze() {
		
		//Test 1
		
		EventSet known1 = new EventSet();
		EventSet known2 = new EventSet();
		EventSet known3 = new EventSet();
		EventSet known4 = new EventSet();
		EventSet unknown1 = new EventSet();
		EventSet unknown2 = new EventSet();

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

		unknown1.addEvent(new Event("alpha"));
		unknown1.addEvent(new Event("alpha"));
		unknown1.addEvent(new Event("alpha"));
		unknown1.addEvent(new Event("betta"));
		unknown1.addEvent(new Event("alpha"));
		
		unknown2.addEvent(new Event("alpha"));
		unknown2.addEvent(new Event("betta"));
		unknown2.addEvent(new Event("betta"));
		unknown2.addEvent(new Event("betta"));
		unknown2.addEvent(new Event("betta"));
		
		Vector<EventSet> esv = new Vector<EventSet>();
		esv.add(known1);
		esv.add(known2);
		esv.add(known3);
		esv.add(known4);
		
		Vector<EventSet> uesv = new Vector<EventSet>();
		uesv.add(unknown1);
		uesv.add(unknown2);

		List<List<Pair<String, Double>>> t = new WEKADecisionStump().analyze(uesv, esv);
		System.out.println(t.toString());

		//Assert that the authors match
		assertTrue(t.get(0).get(0).getFirst().equals("Mary") && t.get(1).get(0).getFirst().equals("Peter"));
		//assertTrue(true);
	}

}
