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

import com.jgaap.JGAAPConstants;
import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.EventDriver;

/**
 * @author Chris
 *
 */
public class BlackListEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.BlackListEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 * @throws EventGenerationException 
	 */
	@Test
	public void testCreateEventSetDocumentSet() throws EventGenerationException {
		Document doc = new Document();
		doc.readStringText("Humpty Dumpty sat on the wall. Humpty Dumpty had a great fall. An itsy bitsy spider ran up the water spout.");
		EventDriver ed = new BlackListEventDriver();
		ed.setParameter("filename",
			JGAAPConstants.JGAAP_RESOURCE_PACKAGE+"articles.txt");
		EventSet sampleEventSet = ed.createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		tmp.add(new Event("Humpty", null));
		tmp.add(new Event("Dumpty", null));
		tmp.add(new Event("sat", null));
		tmp.add(new Event("on", null));
		tmp.add(new Event("wall.", null));
		tmp.add(new Event("Humpty", null));
		tmp.add(new Event("Dumpty", null));
		tmp.add(new Event("had", null));
		tmp.add(new Event("great", null));
		tmp.add(new Event("fall.", null));
		tmp.add(new Event("itsy", null));
		tmp.add(new Event("bitsy", null));
		tmp.add(new Event("spider", null));
		tmp.add(new Event("ran", null));
		tmp.add(new Event("up", null));
		tmp.add(new Event("water", null));
		tmp.add(new Event("spout.", null));

		expectedEventSet.addEvents(tmp);
System.out.println(expectedEventSet.toString());
System.out.println(sampleEventSet.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));
		
	}

}
