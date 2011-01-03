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
public class StripNumbersTest {

	/**
	 * Test method for {@link com.jgaap.canonicizers.Strip#process(java.util.Vector)}.
	 */
	@Test
	public void testProcess() {
		Vector<Character> sample = new Vector<Character>();
		Vector<Character> expected = new Vector<Character>();
		
		sample.add('P');
		sample.add('i');
		sample.add(' ');
		sample.add('i');
		sample.add('s');
		sample.add(' ');
		sample.add('3');
		sample.add('.');
		sample.add('1');
		sample.add('4');
		sample.add('1');
		sample.add('5');
		sample.add(';');
		sample.add('2');
		sample.add('^');
		sample.add('6');
		sample.add(' ');
		sample.add('i');
		sample.add('s');
		sample.add(' ');
		sample.add('6');
		sample.add('4');



		expected.add('P');
		expected.add('i');
		expected.add(' ');
		expected.add('i');
		expected.add('s');
		expected.add(' ');
		expected.add('#');
		expected.add(';');
		expected.add('#');
		expected.add('^');
		expected.add('#');
		expected.add(' ');
		expected.add('i');
		expected.add('s');
		expected.add(' ');
		expected.add('#');


		List<Character> test = new StripNumbers().process(sample);
		assertTrue(expected.equals(test));
	}

}
