package com.jgaap.eventCullers;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.jgaap.generics.EventCullingException;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

public class StandardDeviationCullerTest {

	@Test
	public void testProcess() throws EventCullingException {
		List<EventSet> eventSets = new ArrayList<EventSet>();
		EventSet eventSet1 = new EventSet();
		eventSet1.addEvent(new Event("A", null));
		eventSet1.addEvent(new Event("A", null));
		eventSet1.addEvent(new Event("A", null));
		eventSet1.addEvent(new Event("A", null));
		eventSet1.addEvent(new Event("A", null));
		eventSet1.addEvent(new Event("B", null));
		eventSet1.addEvent(new Event("B", null));
		eventSet1.addEvent(new Event("B", null));
		eventSet1.addEvent(new Event("C", null));
		eventSets.add(eventSet1);
		EventSet eventSet2 = new EventSet();
		eventSet2.addEvent(new Event("A", null));
		eventSet2.addEvent(new Event("B", null));
		eventSet2.addEvent(new Event("C", null));
		eventSet2.addEvent(new Event("D", null));
		eventSet2.addEvent(new Event("E", null));
		eventSet2.addEvent(new Event("F", null));
		eventSet2.addEvent(new Event("F", null));
		eventSet2.addEvent(new Event("G", null));
		eventSet2.addEvent(new Event("H", null));
		eventSets.add(eventSet2);
		EventSet eventSet3 = new EventSet();
		eventSet3.addEvent(new Event("E", null));
		eventSet3.addEvent(new Event("E", null));
		eventSet3.addEvent(new Event("E", null));
		eventSet3.addEvent(new Event("F", null));
		eventSet3.addEvent(new Event("A", null));
		eventSet3.addEvent(new Event("B", null));
		eventSet3.addEvent(new Event("D", null));
		eventSet3.addEvent(new Event("H", null));
		eventSet3.addEvent(new Event("C", null));
		eventSets.add(eventSet3);

		StandardDeviationCuller culler = new StandardDeviationCuller();
        culler.setParameter("numEvents", 4);
        Set<Event> results = culler.train(eventSets);
		Set<Event> expected = new HashSet<Event>();
		expected.add(new Event("A", null));
		expected.add(new Event("B", null));	
		expected.add(new Event("E", null));		
		expected.add(new Event("F", null));
		assertTrue(results.equals(expected));

	}

}
