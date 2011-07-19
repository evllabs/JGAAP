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
import static org.junit.Assert.assertTrue;

import java.util.List;

/**
 * Test EventHistogram
 */
public class EventHistogramTest {
    @Test
    public void testProcess() {
        EventHistogram hist = new EventHistogram();
        hist.add(new Event("B"));
        hist.add(new Event("B"));
        hist.add(new Event("B"));
        hist.add(new Event("B"));
        hist.add(new Event("B"));
        hist.add(new Event("Z"));
        hist.add(new Event("Z"));
        hist.add(new Event("Z"));
        hist.add(new Event("Z"));
        hist.add(new Event("A"));
        hist.add(new Event("A"));
        hist.add(new Event("A"));
        hist.add(new Event("N"));
        hist.add(new Event("N"));
        hist.add(new Event("I"));

        List<Pair<Event, Integer> > list = hist.getSortedHistogram();

        System.out.println(list.get(0));

        assertTrue(list.get(4).equals(new Pair<Event, Integer>(new Event("I"), 1)));
        assertTrue(list.get(3).equals(new Pair<Event, Integer>(new Event("N"), 2)));
        assertTrue(list.get(2).equals(new Pair<Event, Integer>(new Event("A"), 3)));
        assertTrue(list.get(1).equals(new Pair<Event, Integer>(new Event("Z"), 4)));
        assertTrue(list.get(0).equals(new Pair<Event, Integer>(new Event("B"), 5)));

    }
}
