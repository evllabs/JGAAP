// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 * 
 */
package com.jgaap.canonicizers;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

/**
 * @author Patrick Juola
 * 
 */
public class StripNonPuncTest {

	/**
	 * Test method for
	 * {@link com.jgaap.canonicizers.StripNonPunc#process(java.util.Vector)}.
	 */
	@Test
	public void testProcess() {
		char[] sample = { 'H', 'e', 'l', 'l', 'o', ',', ' ', 'W', ';', ':',
				'$', 'o', 'r', 'l', 'd', '!', ',', '.', '?', '!', '\"', '\'',
				'`', ';', ':', '-', '(', ')', '&', '$' };

		char[] expected = { ',', ' ', ';', ':', '$', '!', ',', '.', '?', '!',
				'\"', '\'', '`', ';', ':', '-', '(', ')', '&', '$' };

		char[] test = new StripNonPunc().process(sample);
		assertTrue(Arrays.equals(test, expected));
	}

}
