package com.jgaap.generics;

import java.util.List;

/**
 * Event Culling abstract parent class.
 * Event Culling is any transformation on a List<EventSet> that results in
 * a List<EventSet>
 */
public abstract class EventCuller extends Parameterizable implements Comparable<EventCuller>, Displayable {


    public abstract List<EventSet> cull(List<EventSet> eventSets); 

    public abstract String displayName();
	public abstract String tooltipText();
	public abstract boolean showInGUI();

    public int compareTo(EventCuller o){
    	return displayName().compareTo(o.displayName());
    }
}
