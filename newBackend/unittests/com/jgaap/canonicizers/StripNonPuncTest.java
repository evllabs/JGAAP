/**
 * 
 */
package com.jgaap.canonicizers;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Vector;

import org.junit.Test;

/**
 * @author Patrick Juola
 *
 */
public class StripNonPuncTest {

	/**
	 * Test method for {@link com.jgaap.canonicizers.StripNonPunc#process(java.util.Vector)}.
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
		sample.add(',');
		sample.add(' ');
		sample.add('W');
		sample.add(';');
		sample.add(':');
		sample.add('$');
		sample.add('o');
		sample.add('r');
		sample.add('l');
		sample.add('d');
		sample.add('!');
		sample.add(',');
		sample.add('.');
		sample.add('?');
		sample.add('!');
		sample.add('\"');
		sample.add('\'');
		sample.add('`');
		sample.add(';');
		sample.add(':');
		sample.add('-');
		sample.add('(');
		sample.add(')');
		sample.add('&');
		sample.add('$');



		expected.add(',');
		expected.add(' ');
		expected.add(';');
		expected.add(':');
		expected.add('$');
		expected.add('!');
		expected.add(',');
		expected.add('.');
		expected.add('?');
		expected.add('!');
		expected.add('\"');
		expected.add('\'');
		expected.add('`');
		expected.add(';');
		expected.add(':');
		expected.add('-');
		expected.add('(');
		expected.add(')');
		expected.add('&');
		expected.add('$');

		List<Character> test = new StripNonPunc().process(sample);
		assertTrue(expected.equals(test));
	}

}
