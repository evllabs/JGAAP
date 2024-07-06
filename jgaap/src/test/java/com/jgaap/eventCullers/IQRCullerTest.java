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

public class IQRCullerTest {
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
		
		EventSet eventSet4 = new EventSet();
		eventSet4.addEvent(new Event("A", null));
		eventSet4.addEvent(new Event("A", null));
		eventSet4.addEvent(new Event("A", null));
		eventSet4.addEvent(new Event("C", null));
		eventSet4.addEvent(new Event("B", null));
		eventSet4.addEvent(new Event("B", null));
		eventSet4.addEvent(new Event("E", null));
		eventSet4.addEvent(new Event("F", null));
		eventSet4.addEvent(new Event("G", null));
		eventSets.add(eventSet4);
		
		EventSet eventSet5 = new EventSet();
		eventSet5.addEvent(new Event("A", null));
		eventSet5.addEvent(new Event("A", null));
		eventSet5.addEvent(new Event("A", null));
		eventSet5.addEvent(new Event("C", null));
		eventSet5.addEvent(new Event("B", null));
		eventSet5.addEvent(new Event("B", null));
		eventSet5.addEvent(new Event("E", null));
		eventSet5.addEvent(new Event("E", null));
		eventSet5.addEvent(new Event("F", null));
		eventSets.add(eventSet5);
		
		EventSet eventSet6 = new EventSet();
		eventSet6.addEvent(new Event("A", null));
		eventSet6.addEvent(new Event("C", null));
		eventSet6.addEvent(new Event("B", null));
		eventSet6.addEvent(new Event("B", null));
		eventSet6.addEvent(new Event("B", null));
		eventSet6.addEvent(new Event("E", null));
		eventSet6.addEvent(new Event("E", null));
		eventSet6.addEvent(new Event("E", null));
		eventSet6.addEvent(new Event("E", null));
		eventSets.add(eventSet6);		
		
		EventSet eventSet7 = new EventSet();
		eventSet7.addEvent(new Event("A", null));
		eventSet7.addEvent(new Event("A", null));
		eventSet7.addEvent(new Event("A", null));
		eventSet7.addEvent(new Event("A", null));
		eventSet7.addEvent(new Event("C", null));
		eventSet7.addEvent(new Event("E", null));
		eventSet7.addEvent(new Event("F", null));
		eventSet7.addEvent(new Event("F", null));
		eventSet7.addEvent(new Event("F", null));
		eventSets.add(eventSet7);
		
		EventSet eventSet8 = new EventSet();
		eventSet8.addEvent(new Event("A", null));
		eventSet8.addEvent(new Event("A", null));
		eventSet8.addEvent(new Event("C", null));
		eventSet8.addEvent(new Event("F", null));
		eventSet8.addEvent(new Event("F", null));
		eventSet8.addEvent(new Event("F", null));
		eventSet8.addEvent(new Event("F", null));
		eventSet8.addEvent(new Event("F", null));
		eventSet8.addEvent(new Event("F", null));
		eventSets.add(eventSet8);
		
		EventSet eventSet9 = new EventSet();
		eventSet9.addEvent(new Event("A", null));
		eventSet9.addEvent(new Event("A", null));
		eventSet9.addEvent(new Event("C", null));
		eventSet9.addEvent(new Event("E", null));
		eventSet9.addEvent(new Event("E", null));
		eventSet9.addEvent(new Event("E", null));
		eventSet9.addEvent(new Event("E", null));
		eventSet9.addEvent(new Event("E", null));
		eventSet9.addEvent(new Event("E", null));
		eventSets.add(eventSet9);
		
		EventSet eventSet10 = new EventSet();
		eventSet10.addEvent(new Event("A", null));
		eventSet10.addEvent(new Event("C", null));
		eventSet10.addEvent(new Event("B", null));
		eventSet10.addEvent(new Event("B", null));
		eventSet10.addEvent(new Event("B", null));
		eventSet10.addEvent(new Event("B", null));
		eventSet10.addEvent(new Event("E", null));
		eventSet10.addEvent(new Event("F", null));
		eventSet10.addEvent(new Event("G", null));
		eventSets.add(eventSet10);
		
		

		IQRCuller culler = new IQRCuller();
        culler.setParameter("numEvents",4);
        Set<Event> results = culler.train(eventSets);
		Set<Event> expected = new HashSet<Event>();
		expected.add(new Event("A", null));
		expected.add(new Event("B", null));	
		expected.add(new Event("E", null));		
		expected.add(new Event("F", null));
		assertTrue(results.equals(expected));

	}
}
