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
package com.jgaap.eventDrivers;

import com.jgaap.generics.EventDriver;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;


/**
 * NullEventSet : no eventification, returns entire document as a single event, prints arguments
 * 
 * Deprecated, because ordered canonicizers means we can print
 * the documents with the null canonicizer
 */
@Deprecated
public class NullEventDriver extends EventDriver {
    @Override
    public String displayName(){
    	return "Null Event Set";
    }
    
    @Override
    public String tooltipText(){
    	return "Null events for debugging previous sets";
    }
    
    @Override
    public boolean showInGUI(){
    	return false;
    }

    @Override
    public EventSet createEventSet(char[] text) {
        EventSet es = new EventSet();

        // again, use logging facilities when they exist
        //System.out.println(ds.getDocument(0).getAuthor());
        //for (int i = 0; i < ds.documentCount(); i++) {

            // again, use logging facilities when they exist
	    // System.out.println("--- Document #" + i + " --- ");
	    // System.out.println(ds.getDocument(i).stringify());

            es.addEvent(new Event(new String(text), this));
        //}

        return es;
    }

}
