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
public class StripNumbersTest {

	/**
	 * Test method for
	 * {@link com.jgaap.canonicizers.JGAAP#process(java.util.Vector)}.
	 */
	@Test
	public void testProcess() {
		char[] sample = { 'P', 'i', ' ', 'i', 's', ' ', '3', '.', '1', '4',
				'1', '5', ';', '2', '^', '6', ' ', 'i', 's', ' ', '6', '4' };

		char[] expected = { 'P', 'i', ' ', 'i', 's', ' ', '0', ';', '0', '^',
				'0', ' ', 'i', 's', ' ', '0' };

		char[] test = new StripNumbers().process(sample);
		assertTrue(Arrays.equals(expected, test));
	}

}
