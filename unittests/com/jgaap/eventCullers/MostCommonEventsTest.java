// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
package com.jgaap.eventCullers;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventCuller;
import com.jgaap.generics.EventSet;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class MostCommonEventsTest {
    @Test
    public void testProcess() {
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
