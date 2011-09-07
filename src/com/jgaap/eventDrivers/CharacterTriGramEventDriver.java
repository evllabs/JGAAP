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
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;

/**
 * Extract character trigrams as features.
 *
 */
@Deprecated
public class CharacterTriGramEventDriver extends NGramEventDriver {
   
    @Override
    public String displayName(){
    	return "Character TriGrams";
    }
    
    @Override
    public String tooltipText(){
    	return "Groups of Three Successive Characters";
    }
    
    @Override
    public String longDescription(){
    	return "Groups of three successive characters (sliding window)";
    }
    
    @Override
    public boolean showInGUI(){
    	return false;
    }

    private EventDriver theDriver;

    @Override
    public EventSet createEventSet(Document ds) {
        theDriver = new CharacterNGramEventDriver();
        theDriver.setParameter("N", "3");
        return theDriver.createEventSet(ds);
    }
}
