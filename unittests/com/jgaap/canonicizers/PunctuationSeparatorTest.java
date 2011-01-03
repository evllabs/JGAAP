package com.jgaap.canonicizers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class PunctuationSeparatorTest {

	@Test
	public void processTest(){
		List<Character> sample = new ArrayList<Character>();
		String test = "This, is ,a test.of what\", happens[']with punctuation.";
		for(Character character : test.toCharArray()){
			sample.add(character);
		}
		PunctuationSeparator canon = new PunctuationSeparator();
		List<Character> result = canon.process(sample);
		char[] ex = {'T', 'h', 'i', 's', ' ', ',', ' ', 'i', 's', ' ', ',', ' ', 'a', ' ', 't', 'e', 's', 't', ' ', '.', ' ', 'o', 'f', ' ', 'w', 'h', 'a', 't', ' ', '"', ' ', ',', ' ', 'h', 'a', 'p', 'p', 'e', 'n', 's', ' ', '[', ' ', '\'', ' ', ']', ' ', 'w', 'i', 't', 'h', ' ', 'p', 'u', 'n', 'c', 't', 'u', 'a', 't', 'i', 'o', 'n', ' ', '.'};
		List<Character> expected = new ArrayList<Character>();
		for(Character character : ex){
			expected.add(character);
		}
		assertTrue(expected.equals(result));
	}
}
