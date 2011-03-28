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
 * Extract syllable bigrams as features.  (Suggested by Richard Forsyth,
 * David I Holmes, and Emily K Tse, in 1998 tech report "Cicero, Sigonio and
 * Burrows: Investigating the Authenticity of the 'Consolatio'"
 * @author Patrick Juola
 *
 */
public class SyllableTransitionEventDriver extends NGramEventDriver {

    @Override
    public String displayName(){
        return "Syllable Transitions";
    }

    @Override
    public String tooltipText(){
        return "Bigrams of Syllable Numbers";
    }

    @Override
    public boolean showInGUI(){
        return true;
    }


    private NGramEventDriver theDriver;

    @Override
    public EventSet createEventSet(Document ds) {
        theDriver = new NGramEventDriver();
        // default value of N is 2 already
        theDriver.setParameter("underlyingEvents", "WordSyllablesEventDriver");
        theDriver.setParameter("opendelim", "null");
        theDriver.setParameter("closedelim", "null");

        return theDriver.createEventSet(ds);
    }
}
