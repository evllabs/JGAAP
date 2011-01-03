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

import com.jgaap.jgaapConstants;
import com.jgaap.generics.Document;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;

/**
 * Uses function words as defined by Mosteller-Wallace in their Federalist
 * papers study.
 */
public class MWFunctionWordsEventDriver extends EventDriver {


    @Override
    public String displayName(){
    	return "MW Function Words";
    }
    
    @Override
    public String tooltipText(){
    	return "Function Words from Mosteller-Wallace";
    }
    
    @Override
    public boolean showInGUI(){
    	return true;
    }

    /** Static field for efficiency */
    // private static EventDriver e;
    // Peter Jan 21 2010 ^ if this is static, then why are we setting it in the constructor?
    private EventDriver e;

    /** Default constructor. Sets parameters for WhiteList */
    // Peter Jan 21 2010 - this needs to be public for autopopulator
    public MWFunctionWordsEventDriver() {
        e = new WhiteListEventDriver();
        e.setParameter("underlyingEvents", "NaiveWordEventDriver");
        e.setParameter("filename", jgaapConstants.libDir()
                + "/MWfunctionwords.dat");
    }

    /** Creates EventSet using M-W function word list */
    @Override
    public EventSet createEventSet(Document ds) {
        return e.createEventSet(ds);
    }

}
