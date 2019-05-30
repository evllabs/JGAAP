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
		
		String sampleInput = "There once was a man from NantucKetDE\r\n" + 
			"Who kept all his cash in a buDketDE.\r\n" + 
			"	But his daughter, named Nan,\r\n" + 
			"	Ran away with a man\r\n" + 
			"And as for the bucket, Nantuffer.\r\n" + 
			"But he followed the pair to Pawtutfex,\r\n" + 
			"The man and the girl with the bucket;\r\n" + 
			"	And he said to the man,\r\n" + 
			"	He was welcome to Nan,\r\n" + 
			"But as for the bucket, PawtufgebcDFghjkLmNpQrStvWXz.";
		EventSet actualSet = eventDriver.createEventSet(sampleInput.toCharArray());
		
		EventSet expectedSet = new EventSet();
		
		for(int x = 0; x < 2; x++)
			expectedSet.addEvent(new Event("tD", eventDriver));
		
		for(int x = 0; x < 2; x++)
			expectedSet.addEvent(new Event("n", eventDriver));
		
		expectedSet.addEvent(new Event("r", eventDriver));
		expectedSet.addEvent(new Event("x", eventDriver));
		expectedSet.addEvent(new Event("t", eventDriver));
		
		for(int x = 0; x < 2; x++)
			expectedSet.addEvent(new Event("n", eventDriver));
		
		expectedSet.addEvent(new Event("bcDFghjkLmNpQrStvWXz", eventDriver));
		assertTrue(expectedSet.equals(actualSet));
	}
}
