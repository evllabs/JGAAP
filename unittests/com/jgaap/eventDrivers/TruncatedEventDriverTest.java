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
package com.jgaap.eventDrivers;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventSet;

/**
 * @author Patrick Juola
 *
 */
public class TruncatedEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.TruncatedEventDriver#createEventSet(com.jgaap.generics.JGAAP)}.
	 * @throws EventGenerationException 
	 */
	@Test
	public void testCreateEventSetDocumentSet() throws EventGenerationException {

		/* test 1 -- straight up */
		Document doc = new Document();
		doc.readStringText(
"We hold these truths to be self-evident,\n"+
"\"My phone # is 867-5309; don't forget it!\" she said.\n"+
"\t\t\"I won't,\" \t he grumbled.\n"

		);

		TruncatedEventDriver n = new TruncatedEventDriver();
		n.setParameter("length","3");
		EventSet sampleEventSet = n.createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();

		tmp.add(new Event("We"));
		tmp.add(new Event("hol"));
		tmp.add(new Event("the"));
		tmp.add(new Event("tru"));
		tmp.add(new Event("to"));
		tmp.add(new Event("be"));
		tmp.add(new Event("sel"));
		tmp.add(new Event("\"My"));
		tmp.add(new Event("pho"));
		tmp.add(new Event("#"));
		tmp.add(new Event("is"));
		tmp.add(new Event("867"));
		tmp.add(new Event("don"));
		tmp.add(new Event("for"));
		tmp.add(new Event("it!"));
		tmp.add(new Event("she"));
		tmp.add(new Event("sai"));
		tmp.add(new Event("\"I"));
		tmp.add(new Event("won"));
		tmp.add(new Event("he"));
		tmp.add(new Event("gru"));

		expectedEventSet.addEvents(tmp);

//System.out.println("Expected is " +expectedEventSet.events.toString());
//System.out.println("Actual is " +sampleEventSet.events.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));

		// need somethign to test whether ot not it handles Numerc
		// correctly.
	}

}
