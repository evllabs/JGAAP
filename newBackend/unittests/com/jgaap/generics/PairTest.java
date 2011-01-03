package com.jgaap.generics;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PairTest {

    /**
	 * Test method for {@link com.jgaap.generics.Pair}.
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
