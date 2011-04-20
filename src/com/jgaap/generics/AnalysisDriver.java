// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
package com.jgaap.generics;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * Class for statistical analysis methods. As an abstract class, can only be
 * instantiated through subclasses. Legacy code inherited from WAY back.
 * 
 * @author unknown
 * @since 1.0
 */
public abstract class AnalysisDriver extends Parameterizable implements Comparable<AnalysisDriver>, Displayable {

	public abstract String displayName();

	public abstract String tooltipText();

	public String longDescription() { return tooltipText(); }

	public abstract boolean showInGUI();


    /**
     * Generic statistical analysis method (abstract). Analyze a given unknown
     * EventSet in terms of its similarity (broadly defined) to elements of a
     * Vector of EventSets of known authorship. Legacy code from WAY back. We
     * should probably add a verify() method as well once the technology
     * improves.
     * 
     * @param unknown
     *            the EventSet to be analyzed
     * @param known
     *            a vector of EventSets of known authorship
     * @return a String representing the name of the author assigned
     */
    abstract public List<Pair<String, Double>> analyze(EventSet unknown, List<EventSet> known);

    public void analyze(Document unknown, List<Document> known){
    	for(EventDriver eventDriver : unknown.getEventSets().keySet()){
    		List<EventSet> knownEventSet = new ArrayList<EventSet>();
    		for(Document document : known){
    			knownEventSet.add(document.getEventSet(eventDriver));
    		}
    		unknown.addResult(this, eventDriver, analyze(unknown.getEventSet(eventDriver), knownEventSet));
    	}
    }
    
    public int compareTo(AnalysisDriver o){
    	return displayName().compareTo(o.displayName());
    }

    public GroupLayout getGUILayout(JPanel panel)
    {
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
        return layout;
    }
}
