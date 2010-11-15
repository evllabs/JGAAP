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

/**
 * Extract the 50 most common words across the entire corpus as features.
 *
 */
public class CommonWordEventDriver extends MostCommonEventDriver {
    
    @Override
    public String displayName(){
    	return "Common words";
    }
    
    @Override
    public String tooltipText(){
    	return "50 Most Common Words";
    }
    
    @Override
    public boolean showInGUI(){
    	return false;
    }

    private MostCommonEventDriver theDriver;

    @Override
    public EventSet createEventSet(Document ds) {
        EventSet es;
        theDriver = new MostCommonEventDriver();
        if (!getParameter("N").equals("")) {
            theDriver.setParameter("N", "50");
        } else {
            theDriver.setParameter("N", getParameter("N"));
            // Error checking for invalid parameters handled at MCES.class
        }

        es = theDriver.createEventSet(ds);
        es.setAuthor(ds.getAuthor());
        es.setNewEventSetID(ds.getAuthor());
        return es;
    }
}
