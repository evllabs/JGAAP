package com.jgaap.canonicizers;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;


public class PunctuationSeparatorTest {

	@Test
	public void processTest(){
		char[] sample = "This, is ,a test.of what\", happens[']with punctuation.".toCharArray();
		PunctuationSeparator canon = new PunctuationSeparator();
		char[] result = canon.process(sample);
		char[] ex = {'T', 'h', 'i', 's', ' ', ',', ' ', 'i', 's', ' ', ',', ' ', 'a', ' ', 't', 'e', 's', 't', ' ', '.', ' ', 'o', 'f', ' ', 'w', 'h', 'a', 't', ' ', '"', ' ', ',', ' ', 'h', 'a', 'p', 'p', 'e', 'n', 's', ' ', '[', ' ', '\'', ' ', ']', ' ', 'w', 'i', 't', 'h', ' ', 'p', 'u', 'n', 'c', 't', 'u', 'a', 't', 'i', 'o', 'n', ' ', '.'};
		assertTrue(Arrays.equals(result, ex));
	}
}
