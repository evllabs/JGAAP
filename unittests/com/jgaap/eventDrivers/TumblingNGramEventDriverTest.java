// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
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
