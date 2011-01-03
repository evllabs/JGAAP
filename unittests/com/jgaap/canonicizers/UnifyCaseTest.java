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
public class UnifyCaseTest {

	/**
	 * Test method for {@link com.jgaap.canonicizers.UnifyCase#process(java.util.Vector)}.
	 */
	@Test
	public void testProcess() {
		Vector<Character> sample = new Vector<Character>();
		Vector<Character> expected = new Vector<Character>();
		
		sample.add('H');
		sample.add('e');
		sample.add('l');
		sample.add('l');
		sample.add('o');
		sample.add(' ');
		sample.add('W');
		sample.add('o');
		sample.add('r');
		sample.add('l');
		sample.add('d');
		sample.add('!');
		expected.add('h');
		expected.add('e');
		expected.add('l');
		expected.add('l');
		expected.add('o');
		expected.add(' ');
		expected.add('w');
		expected.add('o');
		expected.add('r');
		expected.add('l');
		expected.add('d');
		expected.add('!');
		List<Character> test = new UnifyCase().process(sample);
		assertTrue(expected.equals(test));
	}

}
