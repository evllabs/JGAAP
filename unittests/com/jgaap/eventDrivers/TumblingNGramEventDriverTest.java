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
package com.jgaap.eventDrivers;

import static org.junit.Assert.*;

import java.util.Vector;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;

import org.junit.Test;


public class TumblingNGramEventDriverTest {

	@Test
	public void testCreateEventSetDocumentSet() {
		Document doc = new Document();
		
		doc.readStringText("Testing sentence. My name is Joe. run jump, game-start?  Hello!");
		EventSet sampleEventSet = new TumblingNGramEventDriver().createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		
		
		
		tmp.add(new Event("(Testing)-(sentence.)"));
		//tmp.add(new Event("(sentence.)-(My)"));
		tmp.add(new Event("(My)-(name)"));
		//tmp.add(new Event("(name)-(is)"));
		tmp.add(new Event("(is)-(Joe.)"));
		//tmp.add(new Event("(Joe.)-(run)"));
		tmp.add(new Event("(run)-(jump,)"));
		//tmp.add(new Event("(jump,)-(game-start?)"));
		tmp.add(new Event("(game-start?)-(Hello!)"));
		
		
		
		expectedEventSet.addEvents(tmp);
		
//System.out.println(expectedEventSet.toString());
//System.out.println(sampleEventSet.toString());

		
		assertTrue(expectedEventSet.equals(sampleEventSet));
	}

}
