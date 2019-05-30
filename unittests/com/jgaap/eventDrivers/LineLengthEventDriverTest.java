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
public class LineLengthEventDriverTest {
	@Test
	public void testCreateEventDocumentSet() {
		LineLengthEventDriver eventDriver = new LineLengthEventDriver();
		
		String sampleInput = "There once was a man from Nantucket\n" + 
			"Who kept all his cash in a bucket.\n" + 
			"	But his daughter, named Nan,\n" + 
			"	Ran away with a man\n" + 
			"And as for the bucket, Nantucket.\n" + 
			"\n" + 
			"But he followed the pair to Pawtucket,\n" + 
			"The man and the girl with the bucket;\n" + 
			"	And he said to the man,\n" + 
			"	He was welcome to Nan,\n" + 
			"But as for the bucket, Pawtucket.\n" + 
			"\n" + 
			"Then the pair followed Pa to Manhasset,\n" + 
			"Where he still held the cash as an asset,\n" + 
			"	But Nan and the man\n" + 
			"	Stole the money and ran,\n" + 
			"And as for the asset, Manhasset.";
		EventSet actualSet = eventDriver.createEventSet(sampleInput.toCharArray());
		
		EventSet expectedSet = new EventSet();
		int[] expectedVals = {7, 8, 5, 5, 6, 7, 8, 6, 5, 6, 7, 9, 5, 5, 6};
		for (int val : expectedVals)
			expectedSet.addEvent(new Event(Integer.toString(val), eventDriver));
		
		assertTrue(expectedSet.equals(actualSet));
	}
}
