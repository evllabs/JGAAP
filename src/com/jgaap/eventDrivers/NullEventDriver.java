/**
 *   JGAAP -- Java Graphical Authorship Attribution Program
 *   Copyright (C) 2009 Patrick Juola
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation under version 3 of the License.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/
package com.jgaap.eventDrivers;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;
import javax.swing.*;


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
    public GroupLayout getGUILayout(JPanel panel){
    	return null;
    }

    @Override
    public EventSet createEventSet(Document doc) {
        EventSet es = new EventSet(doc.getAuthor());

        // again, use logging facilities when they exist
        //System.out.println(ds.getDocument(0).getAuthor());
        //for (int i = 0; i < ds.documentCount(); i++) {

            // again, use logging facilities when they exist
	    // System.out.println("--- Document #" + i + " --- ");
	    // System.out.println(ds.getDocument(i).stringify());

            es.addEvent(new Event(doc.stringify()));
        //}

        return es;
    }

}
