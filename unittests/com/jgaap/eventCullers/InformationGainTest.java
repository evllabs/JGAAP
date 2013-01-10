package com.jgaap.eventCullers;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;

public class InformationGainTest {

	@Test
	public void testProcess() {
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

		InformationGain culler = new InformationGain();
        culler.setParameter("numEvents", 4);
        culler.setParameter("informative", "Most");
        List<EventSet> results = culler.cull(eventSets);
		List<EventSet> expected = new ArrayList<EventSet>();
		EventSet expectedEventSet1 = new EventSet();
		EventSet expectedEventSet2 = new EventSet();
		EventSet expectedEventSet3 = new EventSet();
		expectedEventSet1.addEvent(new Event("A", null));
		expectedEventSet1.addEvent(new Event("A", null));
		expectedEventSet1.addEvent(new Event("A", null));
		expectedEventSet1.addEvent(new Event("A", null));
		expectedEventSet1.addEvent(new Event("A", null));
		expectedEventSet1.addEvent(new Event("B", null));	
		expectedEventSet1.addEvent(new Event("B", null));	
		expectedEventSet1.addEvent(new Event("B", null));			
		expectedEventSet2.addEvent(new Event("A", null));
		expectedEventSet2.addEvent(new Event("B", null));
		expectedEventSet2.addEvent(new Event("E", null));		
		expectedEventSet2.addEvent(new Event("F", null));
		expectedEventSet2.addEvent(new Event("F", null));		
		expectedEventSet3.addEvent(new Event("E", null));
		expectedEventSet3.addEvent(new Event("E", null));
		expectedEventSet3.addEvent(new Event("E", null));
		expectedEventSet3.addEvent(new Event("F", null));
		expectedEventSet3.addEvent(new Event("A", null));		
		expectedEventSet3.addEvent(new Event("B", null));
		expected.add(expectedEventSet1);
		expected.add(expectedEventSet2);
		expected.add(expectedEventSet3);
		assertTrue(results.toString().equals(expected.toString()));
		
		culler.setParameter("numEvents", 4);
		culler.setParameter("informative", "Least");
        List<EventSet> results2 = culler.cull(eventSets);
		List<EventSet> expected2 = new ArrayList<EventSet>();
		EventSet expectedEventSet4 = new EventSet();
		EventSet expectedEventSet5 = new EventSet();
		EventSet expectedEventSet6 = new EventSet();
		expectedEventSet4.addEvent(new Event("C", null));					
		expectedEventSet5.addEvent(new Event("C", null));
		expectedEventSet5.addEvent(new Event("D", null));
		expectedEventSet5.addEvent(new Event("G", null));		
		expectedEventSet5.addEvent(new Event("H", null));		
		expectedEventSet6.addEvent(new Event("D", null));
		expectedEventSet6.addEvent(new Event("H", null));
		expectedEventSet6.addEvent(new Event("C", null));
		expected2.add(expectedEventSet4);
		expected2.add(expectedEventSet5);
		expected2.add(expectedEventSet6);
		assertTrue(results2.toString().equals(expected2.toString()));
	}

}
