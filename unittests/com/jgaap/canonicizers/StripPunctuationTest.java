/**
 * 
 */
package com.jgaap.canonicizers;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

/**
 * @author michael
 * 
 */
public class StripPunctuationTest {

	/**
	 * Test method for
	 * {@link com.jgaap.canonicizers.StripPunctuation#process(java.util.Vector)}
	 * .
	 */
	@Test
	public void testProcess() {
		char[] sample = { 'H', 'e', 'l', 'l', 'o', ',', ' ', 'W', ';', ':',
				'$', 'o', 'r', 'l', 'd', '!', '.', '?', '(', ')' };

		char[] expected = { 'H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l',
				'd' };

		char[] test = new StripPunctuation().process(sample);
		assertTrue(Arrays.equals(expected, test));
	}

}
