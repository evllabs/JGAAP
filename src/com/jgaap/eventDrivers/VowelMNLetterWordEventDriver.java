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
 * Extract vowel-initial words with between M and N letters as features
 * @author Patrick Juola
 * @since 5.0
 *
 */
public class VowelMNLetterWordEventDriver extends MNLetterWordEventDriver {

    @Override
    public String displayName(){
    	return "Vowel M--N letter Words";
    }
    
    @Override
    public String tooltipText(){
    	return "Vowel-initial Words with between M and N letters";
    }
    
    @Override
    public boolean showInGUI(){
    	return true;
    }

    private MNLetterWordEventDriver theDriver;

    @Override
    public EventSet createEventSet(Document ds) {
        theDriver = new MNLetterWordEventDriver();
        theDriver.setParameter("underlyingevents", "VowelInitialWordEventDriver");
        theDriver.setParameter("M", this.getParameter("M"));
        theDriver.setParameter("N", this.getParameter("N"));
        return theDriver.createEventSet(ds);
    }
}
