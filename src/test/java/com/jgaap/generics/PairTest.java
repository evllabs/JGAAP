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
package com.jgaap.generics;

import org.junit.Test;

import com.jgaap.util.Pair;

import static org.junit.Assert.assertTrue;

public class PairTest {

    /**
	 * Test method for {@link com.jgaap.util.Pair}.
	 */
	@Test
	public void testProcess() {
        Pair<String, Integer> p1 = new Pair<String, Integer>("A", 3);
        Pair<String, Integer> p2 = new Pair<String, Integer>("B", 2);
        Pair<String, Integer> p3 = new Pair<String, Integer>("A", 3, 2);
        Pair<String, Integer> p4 = new Pair<String, Integer>("B", 2, 2);
        Pair<String, Integer> p5 = new Pair<String, Integer>("A", 3, 1);

		assertTrue(p1.compareTo(p2) < 0);
        assertTrue(p3.compareTo(p4) > 0);
        assertTrue(p1.compareTo(p5) == 0);
        assertTrue(p5.equals(p1));
	}
}
