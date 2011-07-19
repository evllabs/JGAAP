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

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;


/** 
 * Extract whitespace-separated words (including punctuation) as features.
 */
public class NaiveWordEventDriver extends EventDriver {

    @Override
    public String displayName(){
    	return "Words";
    }
    
    @Override
    public String tooltipText(){
    	return "Words (White Space as Separators)";
    }
    
    @Override
    public boolean showInGUI(){
    	return true;
    }

    @Override
    public EventSet createEventSet(Document doc) {
        EventSet es = new EventSet(doc.getAuthor());
        //for (int i = 0; i < ds.documentCount(); i++) {
            String current = doc.stringify();
            // \s is shorthand for whitespace, remember the \\ to get the \
            String[] result = current.split("\\s+");
            for (int j = 0; j < result.length; j++) {
                if (result[j].length() > 0) {
                    es.addEvent(new Event(result[j]));
                }
            }
            // mark edges between documents
            //es.events.add(new Event("JGAAP:DOCUMENTBOUNDARY"));
       // }
        return es;
    }

}
