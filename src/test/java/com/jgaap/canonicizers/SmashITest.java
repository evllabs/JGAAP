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
package com.jgaap.canonicizers;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Unit test for the Smash I canonicizer.
 * 
 * @author David
 * @since 8.0.0
 */
public class SmashITest {
	@Test
	public void testProcess() {
		SmashI smashI = new SmashI();
		
		// Test 1 - I on ends
		String in = "I don't care if IPad is supposed to be spelled with a lowercase I";
		char[] correct = "i don't care if IPad is supposed to be spelled with a lowercase i".toCharArray();
		char[] actual = smashI.process(in.toCharArray());
		assertArrayEquals(correct, actual);
		
		// Test 2 - I in middle surrounded by spaces
		in = "Sometimes I cannot think of creative things to write for unit tests.";
		correct = "Sometimes i cannot think of creative things to write for unit tests.".toCharArray();
		actual = smashI.process(in.toCharArray());
		assertArrayEquals(correct, actual);
		
		// Test 3 - I in middle surrounded by tabs
		in = "Sometimes		I	cannot think of creative things to write for unit tests.";
		correct = in.toCharArray();
		correct[11] = 'i';
		actual = smashI.process(in.toCharArray());
		assertArrayEquals(correct, actual);
		
		// Test 4 - Bunch of I's next to each other with varying case
		in = "iIiIiiIIiiiiiiIIiiIiIiIIiiIIiiIiiIiiIIiiiIiiiIiI";
		correct = in.toCharArray();
		actual = smashI.process(in.toCharArray());
		assertArrayEquals(correct, actual);

		// Test 5 - Non-whitespaced I
		in = "\"I am here\", she said.";
		correct = "\"i am here\", she said.".toCharArray();
		actual = smashI.process(in.toCharArray());
		assertArrayEquals(correct, actual);

		// Test 6 - Non-whitespaced I
		in = "I'm here.";
		correct = "i'm here.".toCharArray();
		actual = smashI.process(in.toCharArray());
		assertArrayEquals(correct, actual);

		// Test 7 - Non-whitespaced I
		in = "It is I.";
		correct = "It is i.".toCharArray();
		actual = smashI.process(in.toCharArray());
		assertArrayEquals(correct, actual);
	}
}
