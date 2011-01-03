/**
 * 
 */
package com.jgaap.canonicizers;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Vector;

import org.junit.Test;

/**
 * @author michael
 *
 */
public class NormalizeWhitespaceTest {

	/**
	 * Test method for {@link com.jgaap.canonicizers.NormalizeWhitespace#process(java.util.Vector)}.
	 */
	@Test
	public void testProcess() {
		Vector<Character> sample = new Vector<Character>();
		sample.add('H');
		sample.add('e');
		sample.add('l');
		sample.add('l');
		sample.add('o');
		sample.add(' ');
		sample.add(' ');
		sample.add('\t');
		sample.add('\n');
		sample.add('W');
		sample.add('o');
		sample.add('r');
		sample.add('l');
		sample.add('d');
		sample.add('!');
		sample.add('\n');
		Vector<Character> expected = new Vector<Character>();
		expected.add('H');
		expected.add('e');
		expected.add('l');
		expected.add('l');
		expected.add('o');
		expected.add(' ');
		expected.add('W');
		expected.add('o');
		expected.add('r');
		expected.add('l');
		expected.add('d');
		expected.add('!');
		expected.add(' ');
		List<Character> test = new NormalizeWhitespace().process(sample);
		assertTrue(expected.equals(test));
	}

}
