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
import com.jgaap.generics.EventSet;
import com.jgaap.generics.EventDriver;
import com.jgaap.jgaapConstants;

/**
 * Truncate lexical frequency for discrete binning 
 *
 */
public class TruncatedNamingTimeEventDriver extends EventDriver {

    @Override
    public String displayName(){
    	return "Binned naming times";
    }
    
    @Override
    public String tooltipText(){
    	return "Discretized (by truncation) ELP naming latencies";
    }
    
    @Override
    public boolean showInGUI(){
    	return jgaapConstants.globalParams.getParameter("language").equals("english");
    }

    private EventDriver theDriver;

    @Override
    public EventSet createEventSet(Document ds) {
        theDriver = new TruncatedEventDriver();
        theDriver.setParameter("length", "2");
        theDriver.setParameter("underlyingEvents", "NamingTimeEventDriver");
        return theDriver.createEventSet(ds);
    }
}
