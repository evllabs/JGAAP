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
 * Extract word bigrams as features.
 * @author John Noecker Jr.
 *
 */
public class WordBiGramEventDriver extends NGramEventDriver {

    @Override
    public String displayName(){
        return "Word BiGrams";
    }

    @Override
    public String tooltipText(){
        return "Word Pairs in Sequence";
    }

    @Override
    public boolean showInGUI(){
        return false;
    }


    private NGramEventDriver theDriver;

    @Override
    public EventSet createEventSet(Document ds) {
        theDriver = new NGramEventDriver();
        // no changes needed because it uses the default parameters
        return theDriver.createEventSet(ds);
    }
}
