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
/**
 **/
package com.jgaap.generics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jgaap.util.EventSet;

/**
 * Class for EventSet factories. As an abstract class, can only be instantiated
 * through subclasses. Legacy code inherited from WAY back.
 * 
 * @author unknown
 * @since 1.0
 */
public abstract class EventDriver extends Parameterizable implements Comparable<EventDriver>, Displayable {
		
	private List<Canonicizer> canonicizers;
	
	private List<EventCuller> cullers;
	
	public String longDescription() { return tooltipText(); }

    /**
     * Creates an EventSet from a given DocumentSet after preprocessing.
     * 
     * @since 1.0
     * @param text
     *            the text to be Event-ified
     * @return the EventSet containing the Events from the document(s)
     */
    abstract public EventSet createEventSet(String text) throws EventGenerationException;

    public List<EventSet> apply(List<String> texts) throws EventGenerationException, CanonicizationException, EventCullingException {
    	return this.apply(texts, false);
	}

	public List<EventSet> trainApply(List<String> texts) throws EventGenerationException, CanonicizationException, EventCullingException {
    	return this.apply(texts, true);
	}

    private List<EventSet> apply(List<String> texts, boolean train) throws CanonicizationException, EventGenerationException, EventCullingException {
    	List<EventSet> eventSets = new ArrayList<>(texts.size());
    	for (String text : texts) {
    		String textBuffer = text;
    		for (Canonicizer canonicizer: this.getCanonicizers()){
    			textBuffer = canonicizer.process(textBuffer);
			}
    		eventSets.add(this.createEventSet(textBuffer));
		}
    	for (EventCuller eventCuller : this.getEventCullers()){
    		if (train)
    			eventCuller.init(eventSets);
    		List<EventSet> culledEventSets = new ArrayList<>(eventSets.size());
    		for (EventSet eventSet : eventSets){
    			culledEventSets.add(eventCuller.cull(eventSet));
			}
    		eventSets = culledEventSets;
		}
    	return eventSets;
	}

    public int compareTo(EventDriver o){
    	return displayName().compareTo(o.displayName());
    }
	
	public boolean addCanonicizer(Canonicizer canonicizer) {
		if(canonicizers == null)
			canonicizers = new ArrayList<Canonicizer>();
		return canonicizers.add(canonicizer);
	}

	public boolean addCanonicizers(List<Canonicizer> canonicizers){
		if(this.canonicizers == null)
			this.canonicizers = new ArrayList<Canonicizer>();
		return this.canonicizers.addAll(canonicizers);
	}
	
	public boolean removeCanonicizer(Canonicizer canonicizer) {
		if(canonicizers != null)
			return  canonicizers.remove(canonicizer);
		else 
			return false;
	}
	
	public void clearCanonicizers() {
		if(canonicizers != null)
			canonicizers.clear();
	}
	
	public List<Canonicizer> getCanonicizers() {
		if(canonicizers != null)
			return canonicizers;
		else 
			return Collections.emptyList();
	}
	
	public boolean addCuller(EventCuller eventCuller) {
		if(cullers == null)
			cullers = new ArrayList<EventCuller>();
		return cullers.add(eventCuller);
	}

	public boolean addCullers(List<EventCuller> eventCullers) {
    	if(this.cullers == null)
    		this.cullers = new ArrayList<>();
    	return this.cullers.addAll(eventCullers);
	}
	
	public boolean removeCuller(EventCuller eventCuller) {
		if(cullers != null)
			return cullers.remove(eventCuller);
		else 
			return false;
	}
	
	public void clearCullers(){
		if(cullers != null)
			cullers.clear();
	}
	
	public List<EventCuller> getEventCullers() {
		if(cullers != null)
			return cullers;
		else 
			return Collections.emptyList();
	}
	public String toString() {
    	String tmpC = "";
    	for (Canonicizer c : getCanonicizers()){
    		tmpC = tmpC + c.toString() + " ";
		}
    	String tmpEC = "";
    	for (EventCuller e : getEventCullers()){
    		tmpEC = tmpEC + e.toString() + " ";
		}
		return displayName()+" : "+getParameters() + "\n\t" + tmpC + "\n\t" + tmpEC;
	}
}


