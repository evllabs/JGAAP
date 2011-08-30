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
import com.jgaap.generics.EventSet;

/**
 * Extract bigrams of characters as features.
 *
 */
@Deprecated
public class POSPentaGramEventDriver extends NGramEventDriver {

    @Override
    public String displayName(){
    	return "POS PentaGrams";
    }
    
    @Override
    public String tooltipText(){
    	return "Part-of- Speech Pentairs in Sequence";
    }
    
    @Override
    public boolean showInGUI(){
    	return false;
    }

  private NGramEventDriver theDriver;

    @Override
    public EventSet createEventSet(Document ds) {
        theDriver = new NGramEventDriver();
        // default value of N is 2 already
        theDriver.setParameter("N", "5");
        theDriver.setParameter("underlyingEvents", "POS");
        return theDriver.createEventSet(ds);
    }
}
