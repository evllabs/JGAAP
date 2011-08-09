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
    public String longDescription() { return tooltipText(); }



    public int compareTo(EventCuller o){
    	return displayName().compareTo(o.displayName());
    }
}
