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

import com.google.common.collect.Sets;
import com.jgaap.generics.EventCullingException;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class LeastCommonEventsTest {
    @Test
    public void testProcess() throws EventCullingException {
        EventSet es1 = new EventSet();
        EventSet es2 = new EventSet();

        es1.addEvent(new Event("A", null));
        es1.addEvent(new Event("A", null));
        es1.addEvent(new Event("A", null));
        es1.addEvent(new Event("A", null));
        es1.addEvent(new Event("A", null));
        es1.addEvent(new Event("A", null));
        es1.addEvent(new Event("A", null));
        es1.addEvent(new Event("A", null));
        es1.addEvent(new Event("B", null));
        es1.addEvent(new Event("B", null));
        es1.addEvent(new Event("B", null));
        es1.addEvent(new Event("C", null));

        es2.addEvent(new Event("A", null));
        es2.addEvent(new Event("A", null));
        es2.addEvent(new Event("A", null));
        es2.addEvent(new Event("A", null));
        es2.addEvent(new Event("A", null));
        es2.addEvent(new Event("A", null));
        es2.addEvent(new Event("A", null));
        es2.addEvent(new Event("A", null));
        es2.addEvent(new Event("A", null));
        es2.addEvent(new Event("A", null));
        es2.addEvent(new Event("B", null));
        es2.addEvent(new Event("B", null));

        LeastCommonEvents culler = new LeastCommonEvents();
        culler.setParameter("numEvents", 1);

        List<EventSet> list = new ArrayList<EventSet>();
        list.add(es1);
        list.add(es2);

        Set<Event> events = culler.train(list);
                
        assertTrue(events.equals(Sets.newHashSet(new Event("C", null))));


    }
}
