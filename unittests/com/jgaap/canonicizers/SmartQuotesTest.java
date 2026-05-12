package com.jgaap.canonicizers;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class SmartQuotesTest {

	@Test
	public void testProcess() {
		SmartQuotes canon = new SmartQuotes();

		// Curly single quotes → '
		assertTrue(Arrays.equals(new char[]{'\''},
				canon.process(new char[]{'‘'})));
		assertTrue(Arrays.equals(new char[]{'\''},
				canon.process(new char[]{'’'})));

		// Curly double quotes → "
		assertTrue(Arrays.equals(new char[]{'"'},
				canon.process(new char[]{'“'})));
		assertTrue(Arrays.equals(new char[]{'"'},
				canon.process(new char[]{'”'})));

		// Double low-9 quotation mark → "
		assertTrue(Arrays.equals(new char[]{'"'},
				canon.process(new char[]{'„'})));

		// Angle quotation marks → "
		assertTrue(Arrays.equals(new char[]{'"'},
				canon.process(new char[]{'«'})));
		assertTrue(Arrays.equals(new char[]{'"'},
				canon.process(new char[]{'»'})));

		// Plain ASCII characters pass through unchanged
		String plain = "Hello, \"world\"! It's fine.";
		assertTrue(Arrays.equals(plain.toCharArray(),
				canon.process(plain.toCharArray())));

		// Mixed smart and plain text
		String input   = "“Hello” ‘world’";
		String expected = "\"Hello\" 'world'";
		assertTrue(Arrays.equals(expected.toCharArray(),
				canon.process(input.toCharArray())));
	}
}
