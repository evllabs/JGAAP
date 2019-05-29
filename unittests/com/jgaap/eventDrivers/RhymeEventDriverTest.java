package com.jgaap.eventDrivers;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

/**
 * 
 * @author David Berdik
 *
 */
public class RhymeEventDriverTest {
	@Test
	public void testCreateEventDocumentSet() {
		RhymeEventDriver eventDriver = new RhymeEventDriver();
		
		String sampleInput = "There once was a man from NantucKet\r\n" + 
			"Who kept all his cash in a buDket.\r\n" + 
			"	But his daughter, named Nan,\r\n" + 
			"	Ran away with a man\r\n" + 
			"And as for the bucket, Nantuffet.\r\n" + 
			"But he followed the pair to Pawtutfet,\r\n" + 
			"The man and the girl with the bucket;\r\n" + 
			"	And he said to the man,\r\n" + 
			"	He was welcome to Nan,\r\n" + 
			"But as for the bucket, Pawtufget.";
		EventSet actualSet = eventDriver.createEventSet(sampleInput.toCharArray());
		
		EventSet expectedSet = new EventSet();
		expectedSet.addEvent(new Event("cK", eventDriver));
		expectedSet.addEvent(new Event("Dk", eventDriver));
		expectedSet.addEvent(new Event("ff", eventDriver));
		expectedSet.addEvent(new Event("tf", eventDriver));
		expectedSet.addEvent(new Event("ck", eventDriver));
		expectedSet.addEvent(new Event("fg", eventDriver));
		
		assertTrue(expectedSet.equals(actualSet));
	}
}
