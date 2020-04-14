package com.jgaap.eventCullers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.jgaap.generics.EventCullingException;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

public class PercentageRangeCullerTest {

	@Test
	public void testCull() {
		PercentageRangeCuller p = new PercentageRangeCuller();
		p.setParameter("minPercent", 0.0);
		p.setParameter("maxPercent", 0.25);
		//System.out.println(p.toString());
		
		List<EventSet> events = new ArrayList<EventSet>();
		List<EventSet> expected = new ArrayList<EventSet>();
		EventSet holder = new EventSet();
		holder.addEvent(new Event("This", null));
		holder.addEvent(new Event("is", null));
		holder.addEvent(new Event("a", null));
		holder.addEvent(new Event("test.", null));
		events.add(holder);
		expected.add(holder);
		holder = new EventSet();
		holder.addEvent(new Event("This", null));
		holder.addEvent(new Event("is", null));
		holder.addEvent(new Event("another", null));
		holder.addEvent(new Event("test.", null));
		events.add(holder);
		expected.add(holder);
		holder = new EventSet();
		holder.addEvent(new Event("test", null));
		holder.addEvent(new Event("test", null));
		holder.addEvent(new Event("test", null));
		holder.addEvent(new Event("test", null));
		events.add(holder);
		expected.add(new EventSet());
		
		try {
			p.init(events);
			for(int i = 0; i < events.size(); i++){
				assertTrue(p.cull(events.get(i)).equals(expected.get(i)));
			}
		} catch (EventCullingException e) {
			//e.printStackTrace();
			assertTrue(false);
		}		
	}

	@Test
	public void testCull2() {
		PercentageRangeCuller p = new PercentageRangeCuller();
		p.setParameter("minPercent", 0.0);
		p.setParameter("maxPercent", 0.25);
		//System.out.println(p.toString());
		
		List<EventSet> events = new ArrayList<EventSet>();
		EventSet holder = new EventSet();
		holder.addEvent(new Event("test", null));
		holder.addEvent(new Event("test", null));
		holder.addEvent(new Event("blah", null));
		events.add(holder);
		holder = new EventSet();
		holder.addEvent(new Event("it", null));
		holder.addEvent(new Event("is", null));
		holder.addEvent(new Event("test", null));
		events.add(holder);
		holder = new EventSet();
		holder.addEvent(new Event("blah", null));
		holder.addEvent(new Event("test", null));
		holder.addEvent(new Event("test", null));
		events.add(holder);
		
		List<EventSet> expected = new ArrayList<EventSet>();
		holder = new EventSet();
		holder.addEvent(new Event("blah", null));
		expected.add(holder);
		holder = new EventSet();
		holder.addEvent(new Event("it", null));
		holder.addEvent(new Event("is", null));
		expected.add(holder);
		holder = new EventSet();
		holder.addEvent(new Event("blah", null));
		expected.add(holder);
		
		try {
			p.init(events);
			for(int i = 0; i < events.size(); i++){
				assertTrue(p.cull(events.get(i)).equals(expected.get(i)));
			}
		} catch (EventCullingException e) {
			//e.printStackTrace();
			assertTrue(false);
		}		
	}	
	
	@Test
	public void testCull3() {
		PercentageRangeCuller p = new PercentageRangeCuller();
		p.setParameter("minPercent", 0.5);
		p.setParameter("maxPercent", 0.75);
		//System.out.println(p.toString());
		
		List<EventSet> events = new ArrayList<EventSet>();
		EventSet holder = new EventSet();
		holder.addEvent(new Event("test", null));
		holder.addEvent(new Event("test", null));
		holder.addEvent(new Event("blah", null));
		events.add(holder);
		holder = new EventSet();
		holder.addEvent(new Event("it", null));
		holder.addEvent(new Event("is", null));
		holder.addEvent(new Event("test", null));
		events.add(holder);
		holder = new EventSet();
		holder.addEvent(new Event("blah", null));
		holder.addEvent(new Event("test", null));
		holder.addEvent(new Event("test", null));
		events.add(holder);
		
		List<EventSet> expected = new ArrayList<EventSet>();
		holder = new EventSet();
		holder.addEvent(new Event("test", null));
		holder.addEvent(new Event("test", null));
		expected.add(holder);
		holder = new EventSet();
		holder.addEvent(new Event("test", null));
		expected.add(holder);
		holder = new EventSet();
		holder.addEvent(new Event("test", null));
		holder.addEvent(new Event("test", null));
		expected.add(holder);
		
		try {
			p.init(events);
			for(int i = 0; i < events.size(); i++){
				assertTrue(p.cull(events.get(i)).equals(expected.get(i)));
			}
		} catch (EventCullingException e) {
			//e.printStackTrace();
			assertTrue(false);
		}		
	}
	
	@Test
	public void testMinPercentError() {
		PercentageRangeCuller p = new PercentageRangeCuller();
		p.setParameter("minPercent", 5.0);
		
		List<EventSet> events = new ArrayList<EventSet>();
		
		try {
			p.init(events);
			//System.out.println(p.toString());
			assertTrue(p.toString().equals("Percentage Range Culler with parameter values:\nminPercent = 0.0075\nmaxPercent = 1.0"));
		} catch (EventCullingException e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testMaxPercentError() {
		PercentageRangeCuller p = new PercentageRangeCuller();
		p.setParameter("maxPercent", -3.0);
		
		List<EventSet> events = new ArrayList<EventSet>();
		
		try {
			p.init(events);
			//System.out.println(p.toString());
			assertTrue(p.toString().equals("Percentage Range Culler with parameter values:\nminPercent = 0.0\nmaxPercent = 0.0025"));
		} catch (EventCullingException e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testSwitchPercentError() {
		PercentageRangeCuller p = new PercentageRangeCuller();
		p.setParameter("maxPercent", 0.25);
		p.setParameter("minPercent", 0.75);
		
		List<EventSet> events = new ArrayList<EventSet>();
		
		try {
			p.init(events);
			//System.out.println(p.toString());
			assertTrue(p.toString().equals("Percentage Range Culler with parameter values:\nminPercent = 0.25\nmaxPercent = 0.75"));
		} catch (EventCullingException e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testDoubleParseError() {
		PercentageRangeCuller p = new PercentageRangeCuller();
		p.setParameter("minPercent", "Test");
		
		List<EventSet> events = new ArrayList<EventSet>();
		
		try {
			p.init(events);
			//System.out.println(p.toString());
			assertTrue(p.toString().equals("Percentage Range Culler with parameter values:\nminPercent = 0.0025\nmaxPercent = 0.0075"));
		} catch (EventCullingException e) {
			assertTrue(false);
		}
	}
}
