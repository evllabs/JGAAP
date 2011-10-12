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

import com.jgaap.backend.API;
import com.jgaap.generics.Document;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.EventDriver;

/**
 * Truncate lexical frequency for discrete binning 
 *
 */
public class TruncatedFreqEventDriver extends EventDriver {

    @Override
    public String displayName(){
    	return "Binned Frequencies";
    }
    
    @Override
    public String tooltipText(){
    	return "Discretized (by truncation) ELP lexical frequencies";
    }
    
    @Override
    public boolean showInGUI(){
    	return API.getInstance().getLanguage().getLanguage().equalsIgnoreCase("english");
    }

    private EventDriver theDriver;

    @Override
    public EventSet createEventSet(Document ds) {
        theDriver = new TruncatedEventDriver();
        theDriver.setParameter("length", "3");
        theDriver.setParameter("underlyingEvents", "Lexical Frequencies");
        return theDriver.createEventSet(ds);
    }
}
