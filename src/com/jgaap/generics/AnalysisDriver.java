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
import java.util.List;

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
/*
    public GroupLayout getGUILayout(JPanel panel)
    {
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
        return layout;
    } */
}
