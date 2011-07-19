/*
 * JGAAP -- a graphical program for stylometric authorship attribution
 * Copyright (C) 2009,2011 by Patrick Juola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
public class StripCommentsTest {

	/**
	 * Test method for
	 * {@link com.jgaap.canonicizers.StripComments#process(java.util.Vector)}
	 * .
	 */
	@Test
	public void testProcess() {
		// test case 1 (C-style)
		String sampleString = "This/* remove me */ is a sample";
		char [] sample = sampleString.toCharArray();

		String expectedString = "This is a sample";
		char [] expected = expectedString.toCharArray();

		char[] test = new StripComments().process(sample);
		
		System.out.println("sample is: " + new String(sample) );
		System.out.println("expected is: " + new String(expected) );
		System.out.println("test is: " + new String(test) );
		assertTrue(Arrays.equals(expected, test));

		// test case 2 (C-style)
		sampleString =
	 "/*remove me */This/* remove *//*remove \n*/ is a sample/*remove */";
		sample = sampleString.toCharArray();

		expectedString = "This is a sample";
		expected = expectedString.toCharArray();

		test = new StripComments().process(sample);
		
		System.out.println("sample is: " + new String(sample) );
		System.out.println("expected is: " + new String(expected) );
		System.out.println("test is: " + new String(test) );
		assertTrue(Arrays.equals(expected, test));

		// test case 3 (Java-style)
		sampleString =
	 "This is a sample//and this is a comment to be removed";
		sample = sampleString.toCharArray();

		expectedString = "This is a sample";
		expected = expectedString.toCharArray();

		test = new StripComments().process(sample);
		
		System.out.println("sample is: " + new String(sample) );
		System.out.println("expected is: " + new String(expected) );
		System.out.println("test is: " + new String(test) );
		assertTrue(Arrays.equals(expected, test));

		// test case 4 (Java-style)
		sampleString =
	 "This is//to remove\n a sample//and this is a comment to be removed";
		sample = sampleString.toCharArray();

		expectedString = "This is a sample";
		expected = expectedString.toCharArray();

		test = new StripComments().process(sample);
		
		System.out.println("sample is: " + new String(sample) );
		System.out.println("expected is: " + new String(expected) );
		System.out.println("test is: " + new String(test) );
		assertTrue(Arrays.equals(expected, test));

		// test case 5 (Java-style)
		sampleString =
	 "This is a sample#and this is a comment to be removed";
		sample = sampleString.toCharArray();

		expectedString = "This is a sample";
		expected = expectedString.toCharArray();

		test = new StripComments().process(sample);
		
		System.out.println("sample is: " + new String(sample) );
		System.out.println("expected is: " + new String(expected) );
		System.out.println("test is: " + new String(test) );
		assertTrue(Arrays.equals(expected, test));

		// test case 6 (Perl-style)
		sampleString =
	 "This is#to remove\n a sample#and this is a comment to be removed";
		sample = sampleString.toCharArray();

		expectedString = "This is a sample";
		expected = expectedString.toCharArray();

		test = new StripComments().process(sample);
		
		System.out.println("sample is: " + new String(sample) );
		System.out.println("expected is: " + new String(expected) );
		System.out.println("test is: " + new String(test) );
		assertTrue(Arrays.equals(expected, test));

		// test case 7 (nested comments
		sampleString =
	 "This is/* //remove */ a sample#and /*this*/ is to be removed";
		sample = sampleString.toCharArray();

		expectedString = "This is a sample";
		expected = expectedString.toCharArray();

		test = new StripComments().process(sample);
		
		System.out.println("sample is: " + new String(sample) );
		System.out.println("expected is: " + new String(expected) );
		System.out.println("test is: " + new String(test) );
		assertTrue(Arrays.equals(expected, test));
	}

}
