package com.jgaap.generics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EventBagging {

	private List<Event> bag;
	private Random random;
	
	public EventBagging() {
		bag = new ArrayList<Event>();
		random = new Random();
	}
	public EventBagging(EventSet eventSet) { 
		bag = new ArrayList<Event>(eventSet.size());
		addAll(eventSet);
		random = new Random();
	}
	
	public boolean add(Event event){
		return bag.add(event);
	}
	
	public void addAll(EventSet eventSet){
		for(Event event : eventSet){
			add(event);
		}
	}
	
	public Event next(){
		return bag.get(random.nextInt(bag.size()));
	}
}
