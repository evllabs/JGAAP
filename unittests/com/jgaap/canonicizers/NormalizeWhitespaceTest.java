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
public class NormalizeWhitespaceTest {

	/**
	 * Test method for
	 * {@link com.jgaap.canonicizers.NormalizeWhitespace#process(java.util.Vector)}
	 * .
	 */
	@Test
	public void testProcess() {
		char[] sample = { 'H', 'e', 'l', 'l', 'o', ' ', ' ', '\t', '\n', 'W',
				'o', 'r', 'l', 'd', '!', '\n' };
		char[] expected = { 'H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l',
				'd', '!', ' ' };
		char[] test = new NormalizeWhitespace().process(sample);
		assertTrue(Arrays.equals(expected, test));
	}
}
