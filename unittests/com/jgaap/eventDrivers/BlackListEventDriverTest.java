// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 * 
 */
package com.jgaap.eventDrivers;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import com.jgaap.jgaapConstants;
import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.EventDriver;

/**
 * @author Chris
 *
 */
public class BlackListEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.BlackListEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
	 */
	@Test
	public void testCreateEventSetDocumentSet() {
		Document doc = new Document();
		doc.readStringText("Humpty Dumpty sat on the wall. Humpty Dumpty had a great fall. An itsy bitsy spider ran up the water spout.");
		EventDriver ed = new BlackListEventDriver();
		ed.setParameter("filename",
			jgaapConstants.libDir()+"/articles.txt");
		EventSet sampleEventSet = ed.createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		tmp.add(new Event("Humpty"));
		tmp.add(new Event("Dumpty"));
		tmp.add(new Event("sat"));
		tmp.add(new Event("on"));
		tmp.add(new Event("wall."));
		tmp.add(new Event("Humpty"));
		tmp.add(new Event("Dumpty"));
		tmp.add(new Event("had"));
		tmp.add(new Event("great"));
		tmp.add(new Event("fall."));
		tmp.add(new Event("itsy"));
		tmp.add(new Event("bitsy"));
		tmp.add(new Event("spider"));
		tmp.add(new Event("ran"));
		tmp.add(new Event("up"));
		tmp.add(new Event("water"));
		tmp.add(new Event("spout."));

		expectedEventSet.addEvents(tmp);
System.out.println(expectedEventSet.toString());
System.out.println(sampleEventSet.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));
		
	}

}
