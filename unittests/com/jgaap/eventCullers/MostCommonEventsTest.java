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
package com.jgaap.eventCullers;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventCuller;
import com.jgaap.generics.EventCullingException;
import com.jgaap.generics.EventSet;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class MostCommonEventsTest {
    @Test
    public void testProcess() throws EventCullingException {
        EventSet es1 = new EventSet();
        EventSet es2 = new EventSet();

        es1.addEvent(new Event("A"));
        es1.addEvent(new Event("A"));
        es1.addEvent(new Event("A"));
        es1.addEvent(new Event("A"));
        es1.addEvent(new Event("A"));
        es1.addEvent(new Event("A"));
        es1.addEvent(new Event("A"));
        es1.addEvent(new Event("A"));
        es1.addEvent(new Event("B"));
        es1.addEvent(new Event("B"));
        es1.addEvent(new Event("B"));
        es1.addEvent(new Event("C"));

        es2.addEvent(new Event("A"));
        es2.addEvent(new Event("A"));
        es2.addEvent(new Event("A"));
        es2.addEvent(new Event("A"));
        es2.addEvent(new Event("A"));
        es2.addEvent(new Event("A"));
        es2.addEvent(new Event("A"));
        es2.addEvent(new Event("A"));
        es2.addEvent(new Event("A"));
        es2.addEvent(new Event("A"));
        es2.addEvent(new Event("B"));
        es2.addEvent(new Event("B"));

        EventCuller culler = new MostCommonEvents();
        culler.setParameter("numEvents", 1);

        List<EventSet> list = new ArrayList<EventSet>();
        list.add(es1);
        list.add(es2);

        list = culler.cull(list);

        for(EventSet es : list) {
            for(Event e : es) {
                assertTrue(e.getEvent().equals("A"));
            }
        }

    }
}
