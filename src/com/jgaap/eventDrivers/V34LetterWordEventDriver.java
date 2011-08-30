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
 * Extract vowel-initial words with 3 or 4 letters as features
 * @author Patrick Juola
 * @since 4.1
 *
 */
@Deprecated
public class V34LetterWordEventDriver extends MNLetterWordEventDriver {

    @Override
    public String displayName(){
    	return "Vowel 3--4 letter Words";
    }
    
    @Override
    public String tooltipText(){
    	return "Vowel-initial Words with 3 or 4 letters";
    }
    
    @Override
    public boolean showInGUI(){
    	return false;
    }

    private MNLetterWordEventDriver theDriver;

    @Override
    public EventSet createEventSet(Document ds) {
        theDriver = new MNLetterWordEventDriver();
        theDriver.setParameter("underlyingevents", "Vowel-initial Words");
        theDriver.setParameter("M", "3");
        theDriver.setParameter("N", "4");
        return theDriver.createEventSet(ds);
    }
}
