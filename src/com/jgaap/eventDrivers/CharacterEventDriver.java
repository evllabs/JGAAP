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

import java.util.List;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;

/**
 * This event set is all individual characters, as determined by the
 * preprocessing applied in the previous stage.
 **/
public class CharacterEventDriver extends EventDriver {
    // test all named files direcly using main() method
    /**
     * test using files named on command line
     * 
     * @param args
     *            files to process
     */

    @Override
    public String displayName(){
    	return "Characters";
    }
    
    @Override
    public String tooltipText(){
    	return "UNICODE Characters";
    }
    
    @Override
    public boolean showInGUI(){
    	return true;
    }

 
    /**
     * Create sequence of characters from document set.
     * 
     * @param ds
     *            document set of interest
     */
    @Override
    public EventSet createEventSet(Document document) {
        EventSet es = new EventSet(document.getAuthor());
//        for (int i = 0; i < ds.documentCount(); i++) {
//            Document current = ds.getDocument(i);
            List<Character> cd = document.getProcessedText();
            for (int j = 0; j < cd.size(); j++) {
                es.addEvent(new Event(cd.get(j)));
            }
//        }
        return es;
    }

}
