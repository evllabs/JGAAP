package com.jgaap.eventCullers;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventCullingException;
import com.jgaap.generics.EventSet;

public class CoefficientOfVariationTest {
	@Test
	public void testProcess() throws EventCullingException {
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

		CoefficientOfVariation culler = new CoefficientOfVariation();
        culler.setParameter("numEvents", 4);
        List<EventSet> results = culler.cull(eventSets);
		List<EventSet> expected = new ArrayList<EventSet>();
		EventSet expectedEventSet = new EventSet();		
		expectedEventSet.addEvent(new Event("B"));
		expectedEventSet.addEvent(new Event("B"));
		expectedEventSet.addEvent(new Event("B"));
		expectedEventSet.addEvent(new Event("C"));
		expectedEventSet.addEvent(new Event("B"));	
		expectedEventSet.addEvent(new Event("C"));
		expectedEventSet.addEvent(new Event("D"));
		expectedEventSet.addEvent(new Event("H"));		
		expectedEventSet.addEvent(new Event("B"));
		expectedEventSet.addEvent(new Event("D"));
		expectedEventSet.addEvent(new Event("H"));
		expectedEventSet.addEvent(new Event("C"));		

		expected.add(expectedEventSet);
		assertTrue(results.toString().equals(expected.toString()));
		
	
	}
}
