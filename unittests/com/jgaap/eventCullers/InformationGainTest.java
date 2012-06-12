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
		eventSet1.addEvent(new Event("A"));
		eventSet1.addEvent(new Event("A"));
		eventSet1.addEvent(new Event("A"));
		eventSet1.addEvent(new Event("A"));
		eventSet1.addEvent(new Event("A"));
		eventSet1.addEvent(new Event("B"));
		eventSet1.addEvent(new Event("B"));
		eventSet1.addEvent(new Event("B"));
		eventSet1.addEvent(new Event("C"));
		eventSets.add(eventSet1);
		EventSet eventSet2 = new EventSet();
		eventSet2.addEvent(new Event("A"));
		eventSet2.addEvent(new Event("B"));
		eventSet2.addEvent(new Event("C"));
		eventSet2.addEvent(new Event("D"));
		eventSet2.addEvent(new Event("E"));
		eventSet2.addEvent(new Event("F"));
		eventSet2.addEvent(new Event("F"));
		eventSet2.addEvent(new Event("G"));
		eventSet2.addEvent(new Event("H"));
		eventSets.add(eventSet2);
		EventSet eventSet3 = new EventSet();
		eventSet3.addEvent(new Event("E"));
		eventSet3.addEvent(new Event("E"));
		eventSet3.addEvent(new Event("E"));
		eventSet3.addEvent(new Event("F"));
		eventSet3.addEvent(new Event("A"));
		eventSet3.addEvent(new Event("B"));
		eventSet3.addEvent(new Event("D"));
		eventSet3.addEvent(new Event("H"));
		eventSet3.addEvent(new Event("C"));
		eventSets.add(eventSet3);

		InformationGain culler = new InformationGain();
        culler.setParameter("numEvents", 4);
        culler.setParameter("informative", "Most");
        List<EventSet> results = culler.cull(eventSets);
		List<EventSet> expected = new ArrayList<EventSet>();
		EventSet expectedEventSet1 = new EventSet();
		EventSet expectedEventSet2 = new EventSet();
		EventSet expectedEventSet3 = new EventSet();
		expectedEventSet1.addEvent(new Event("A"));
		expectedEventSet1.addEvent(new Event("A"));
		expectedEventSet1.addEvent(new Event("A"));
		expectedEventSet1.addEvent(new Event("A"));
		expectedEventSet1.addEvent(new Event("A"));
		expectedEventSet1.addEvent(new Event("B"));	
		expectedEventSet1.addEvent(new Event("B"));	
		expectedEventSet1.addEvent(new Event("B"));			
		expectedEventSet2.addEvent(new Event("A"));
		expectedEventSet2.addEvent(new Event("B"));
		expectedEventSet2.addEvent(new Event("E"));		
		expectedEventSet2.addEvent(new Event("F"));
		expectedEventSet2.addEvent(new Event("F"));		
		expectedEventSet3.addEvent(new Event("E"));
		expectedEventSet3.addEvent(new Event("E"));
		expectedEventSet3.addEvent(new Event("E"));
		expectedEventSet3.addEvent(new Event("F"));
		expectedEventSet3.addEvent(new Event("A"));		
		expectedEventSet3.addEvent(new Event("B"));
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
		expectedEventSet4.addEvent(new Event("C"));					
		expectedEventSet5.addEvent(new Event("C"));
		expectedEventSet5.addEvent(new Event("D"));
		expectedEventSet5.addEvent(new Event("G"));		
		expectedEventSet5.addEvent(new Event("H"));		
		expectedEventSet6.addEvent(new Event("D"));
		expectedEventSet6.addEvent(new Event("H"));
		expectedEventSet6.addEvent(new Event("C"));
		expected2.add(expectedEventSet4);
		expected2.add(expectedEventSet5);
		expected2.add(expectedEventSet6);
		assertTrue(results2.toString().equals(expected2.toString()));
	}

}
