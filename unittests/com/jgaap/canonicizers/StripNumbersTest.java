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
public class StripNumbersTest {

	/**
	 * Test method for
	 * {@link com.jgaap.canonicizers.Strip#process(java.util.Vector)}.
	 */
	@Test
	public void testProcess() {
		char[] sample = { 'P', 'i', ' ', 'i', 's', ' ', '3', '.', '1', '4',
				'1', '5', ';', '2', '^', '6', ' ', 'i', 's', ' ', '6', '4' };

		char[] expected = { 'P', 'i', ' ', 'i', 's', ' ', '#', ';', '#', '^',
				'#', ' ', 'i', 's', ' ', '#' };

		char[] test = new StripNumbers().process(sample);
		assertTrue(Arrays.equals(expected, test));
	}

}
