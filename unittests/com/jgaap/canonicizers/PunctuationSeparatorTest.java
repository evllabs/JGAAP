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
