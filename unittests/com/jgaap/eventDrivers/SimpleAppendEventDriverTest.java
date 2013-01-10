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
public class SimpleAppendEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.SimpleAppendEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 * @throws EventGenerationException 
	 */
	@Test
	public void testCreateEventSetDocumentSet() throws EventGenerationException {
		Document doc = new Document();
		doc.readStringText(
"Mary had a little lamb;\n" +
"Its fleece was white as snow.\n");
		EventSet sampleEventSet = new SimpleAppendEventDriver().createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();

		tmp.add(new Event("Mary", null));
		tmp.add(new Event("had", null));
		tmp.add(new Event("a", null));
		tmp.add(new Event("little", null));
		tmp.add(new Event("lamb;", null));
		tmp.add(new Event("Its", null));
		tmp.add(new Event("fleece", null));
		tmp.add(new Event("was", null));
		tmp.add(new Event("white", null));
		tmp.add(new Event("as", null));
		tmp.add(new Event("snow.", null));
		
		tmp.add(new Event("4", null));
		tmp.add(new Event("3", null));
		tmp.add(new Event("1", null));
		tmp.add(new Event("6", null));
		tmp.add(new Event("5", null));
		tmp.add(new Event("3", null));
		tmp.add(new Event("6", null));
		tmp.add(new Event("3", null));
		tmp.add(new Event("5", null));
		tmp.add(new Event("2", null));
		tmp.add(new Event("5", null));
		
		tmp.add(new Event("(Mary)-(had)", null));
		tmp.add(new Event("(had)-(a)", null));
		tmp.add(new Event("(a)-(little)", null));
		tmp.add(new Event("(little)-(lamb;)", null));
		tmp.add(new Event("(lamb;)-(Its)", null));
		tmp.add(new Event("(Its)-(fleece)", null));
		tmp.add(new Event("(fleece)-(was)", null));
		tmp.add(new Event("(was)-(white)", null));
		tmp.add(new Event("(white)-(as)", null));
		tmp.add(new Event("(as)-(snow.)", null));


		expectedEventSet.addEvents(tmp);
System.out.println(expectedEventSet.toString());
System.out.println(sampleEventSet.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));
		
	}

}
