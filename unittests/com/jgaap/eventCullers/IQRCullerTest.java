package com.jgaap.eventCullers;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventCullingException;
import com.jgaap.generics.EventSet;

public class IQRCullerTest {
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
		
		EventSet eventSet4 = new EventSet();
		eventSet4.addEvent(new Event("A"));
		eventSet4.addEvent(new Event("A"));
		eventSet4.addEvent(new Event("A"));
		eventSet4.addEvent(new Event("C"));
		eventSet4.addEvent(new Event("B"));
		eventSet4.addEvent(new Event("B"));
		eventSet4.addEvent(new Event("E"));
		eventSet4.addEvent(new Event("F"));
		eventSet4.addEvent(new Event("G"));
		eventSets.add(eventSet4);
		
		EventSet eventSet5 = new EventSet();
		eventSet5.addEvent(new Event("A"));
		eventSet5.addEvent(new Event("A"));
		eventSet5.addEvent(new Event("A"));
		eventSet5.addEvent(new Event("C"));
		eventSet5.addEvent(new Event("B"));
		eventSet5.addEvent(new Event("B"));
		eventSet5.addEvent(new Event("E"));
		eventSet5.addEvent(new Event("E"));
		eventSet5.addEvent(new Event("F"));
		eventSets.add(eventSet5);
		
		EventSet eventSet6 = new EventSet();
		eventSet6.addEvent(new Event("A"));
		eventSet6.addEvent(new Event("C"));
		eventSet6.addEvent(new Event("B"));
		eventSet6.addEvent(new Event("B"));
		eventSet6.addEvent(new Event("B"));
		eventSet6.addEvent(new Event("E"));
		eventSet6.addEvent(new Event("E"));
		eventSet6.addEvent(new Event("E"));
		eventSet6.addEvent(new Event("E"));
		eventSets.add(eventSet6);		
		
		EventSet eventSet7 = new EventSet();
		eventSet7.addEvent(new Event("A"));
		eventSet7.addEvent(new Event("A"));
		eventSet7.addEvent(new Event("A"));
		eventSet7.addEvent(new Event("A"));
		eventSet7.addEvent(new Event("C"));
		eventSet7.addEvent(new Event("E"));
		eventSet7.addEvent(new Event("F"));
		eventSet7.addEvent(new Event("F"));
		eventSet7.addEvent(new Event("F"));
		eventSets.add(eventSet7);
		
		EventSet eventSet8 = new EventSet();
		eventSet8.addEvent(new Event("A"));
		eventSet8.addEvent(new Event("A"));
		eventSet8.addEvent(new Event("C"));
		eventSet8.addEvent(new Event("F"));
		eventSet8.addEvent(new Event("F"));
		eventSet8.addEvent(new Event("F"));
		eventSet8.addEvent(new Event("F"));
		eventSet8.addEvent(new Event("F"));
		eventSet8.addEvent(new Event("F"));
		eventSets.add(eventSet8);
		
		EventSet eventSet9 = new EventSet();
		eventSet9.addEvent(new Event("A"));
		eventSet9.addEvent(new Event("A"));
		eventSet9.addEvent(new Event("C"));
		eventSet9.addEvent(new Event("E"));
		eventSet9.addEvent(new Event("E"));
		eventSet9.addEvent(new Event("E"));
		eventSet9.addEvent(new Event("E"));
		eventSet9.addEvent(new Event("E"));
		eventSet9.addEvent(new Event("E"));
		eventSets.add(eventSet9);
		
		EventSet eventSet10 = new EventSet();
		eventSet10.addEvent(new Event("A"));
		eventSet10.addEvent(new Event("C"));
		eventSet10.addEvent(new Event("B"));
		eventSet10.addEvent(new Event("B"));
		eventSet10.addEvent(new Event("B"));
		eventSet10.addEvent(new Event("B"));
		eventSet10.addEvent(new Event("E"));
		eventSet10.addEvent(new Event("F"));
		eventSet10.addEvent(new Event("G"));
		eventSets.add(eventSet10);
		
		

		IQRCuller culler = new IQRCuller();
        culler.setParameter("numEvents",4);
        List<EventSet> results = culler.cull(eventSets);
		List<EventSet> expected = new ArrayList<EventSet>();
		EventSet expectedEventSet = new EventSet();
		expectedEventSet.addEvent(new Event("A"));
		expectedEventSet.addEvent(new Event("A"));
		expectedEventSet.addEvent(new Event("A"));
		expectedEventSet.addEvent(new Event("A"));
		expectedEventSet.addEvent(new Event("A"));
		expectedEventSet.addEvent(new Event("B"));	
		expectedEventSet.addEvent(new Event("B"));	
		expectedEventSet.addEvent(new Event("B"));	
		expectedEventSet.addEvent(new Event("A"));
		expectedEventSet.addEvent(new Event("B"));
		expectedEventSet.addEvent(new Event("E"));		
		expectedEventSet.addEvent(new Event("F"));
		expectedEventSet.addEvent(new Event("F"));
		expectedEventSet.addEvent(new Event("E"));
		expectedEventSet.addEvent(new Event("E"));	
		expectedEventSet.addEvent(new Event("E"));
		expectedEventSet.addEvent(new Event("F"));
		expectedEventSet.addEvent(new Event("A"));
		expectedEventSet.addEvent(new Event("B"));
		expectedEventSet.addEvent(new Event("A"));
		expectedEventSet.addEvent(new Event("A"));
		expectedEventSet.addEvent(new Event("A"));	
		expectedEventSet.addEvent(new Event("B"));	
		expectedEventSet.addEvent(new Event("B"));
		expectedEventSet.addEvent(new Event("E"));		
		expectedEventSet.addEvent(new Event("F"));
		expectedEventSet.addEvent(new Event("A"));
		expectedEventSet.addEvent(new Event("A"));
		expectedEventSet.addEvent(new Event("A"));	
		expectedEventSet.addEvent(new Event("B"));	
		expectedEventSet.addEvent(new Event("B"));
		expectedEventSet.addEvent(new Event("E"));	
		expectedEventSet.addEvent(new Event("E"));
		expectedEventSet.addEvent(new Event("F"));
		expectedEventSet.addEvent(new Event("A"));
		expectedEventSet.addEvent(new Event("B"));	
		expectedEventSet.addEvent(new Event("B"));	
		expectedEventSet.addEvent(new Event("B"));	
		expectedEventSet.addEvent(new Event("E"));	
		expectedEventSet.addEvent(new Event("E"));
		expectedEventSet.addEvent(new Event("E"));	
		expectedEventSet.addEvent(new Event("E"));
		expectedEventSet.addEvent(new Event("A"));
		expectedEventSet.addEvent(new Event("A"));
		expectedEventSet.addEvent(new Event("A"));
		expectedEventSet.addEvent(new Event("A"));
		expectedEventSet.addEvent(new Event("E"));
		expectedEventSet.addEvent(new Event("F"));
		expectedEventSet.addEvent(new Event("F"));
		expectedEventSet.addEvent(new Event("F"));
		expectedEventSet.addEvent(new Event("A"));
		expectedEventSet.addEvent(new Event("A"));
		expectedEventSet.addEvent(new Event("F"));
		expectedEventSet.addEvent(new Event("F"));
		expectedEventSet.addEvent(new Event("F"));
		expectedEventSet.addEvent(new Event("F"));
		expectedEventSet.addEvent(new Event("F"));
		expectedEventSet.addEvent(new Event("F"));
		expectedEventSet.addEvent(new Event("A"));
		expectedEventSet.addEvent(new Event("A"));
		expectedEventSet.addEvent(new Event("E"));
		expectedEventSet.addEvent(new Event("E"));	
		expectedEventSet.addEvent(new Event("E"));
		expectedEventSet.addEvent(new Event("E"));
		expectedEventSet.addEvent(new Event("E"));	
		expectedEventSet.addEvent(new Event("E"));
		expectedEventSet.addEvent(new Event("A"));
		expectedEventSet.addEvent(new Event("B"));	
		expectedEventSet.addEvent(new Event("B"));
		expectedEventSet.addEvent(new Event("B"));	
		expectedEventSet.addEvent(new Event("B"));
		expectedEventSet.addEvent(new Event("E"));		
		expectedEventSet.addEvent(new Event("F"));
		expected.add(expectedEventSet);
		assertTrue(results.toString().equals(expected.toString()));

	}
}
