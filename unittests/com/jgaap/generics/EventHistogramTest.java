package com.jgaap.generics;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.util.List;

/**
 * Test EventHistogram
 */
public class EventHistogramTest {
    @Test
    public void testProcess() {
        EventHistogram hist = new EventHistogram();
        hist.add(new Event("A"));
        hist.add(new Event("A"));
        hist.add(new Event("A"));
        hist.add(new Event("A"));
        hist.add(new Event("A"));
        hist.add(new Event("B"));
        hist.add(new Event("B"));
        hist.add(new Event("B"));
        hist.add(new Event("B"));
        hist.add(new Event("C"));
        hist.add(new Event("C"));
        hist.add(new Event("C"));
        hist.add(new Event("D"));
        hist.add(new Event("D"));
        hist.add(new Event("E"));

        List<Pair<Event, Integer> > list = hist.getSortedHistogram();

        System.out.println(list.get(0));

        assertTrue(list.get(0).equals(new Pair<Event, Integer>(new Event("E"), 1)));
        assertTrue(list.get(1).equals(new Pair<Event, Integer>(new Event("D"), 2)));
        assertTrue(list.get(2).equals(new Pair<Event, Integer>(new Event("C"), 3)));
        assertTrue(list.get(3).equals(new Pair<Event, Integer>(new Event("B"), 4)));
        assertTrue(list.get(4).equals(new Pair<Event, Integer>(new Event("A"), 5)));

    }
}
