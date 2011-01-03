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
package com.jgaap.canonicizers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.jgaap.generics.Canonicizer;

/** 
 * Strips any punctuation from the document. 
 */
public class StripPunctuation extends Canonicizer {

    private String punc = ",.?!\"\'`;:-()&$"; // Characters which will be

    @Override
    public String displayName(){
    	return "Strip Punctuation";
    }
    
    @Override
    public String tooltipText(){
    	return "Strip all punctuation characters from the text.";
    }
    
    @Override
    public boolean showInGUI(){
    	return true;
    }
    
    @Override
    public Color guiColor(){
    	return Color.GREEN;
    }
    
    // considered "punctuation"

    /**
     * Strip punctuation from input characters
     * @param procText Vector of characters to be processed.
     * @return Vector of processed characters.
     */
    @Override
    public List<Character> process(List<Character> procText) {
        List<Character> processed = new ArrayList<Character>();
        for (int i = 0; i < procText.size(); i++) {
            if (punc.indexOf(procText.get(i)) == -1) { // If the character
                // is not
                // punctuation
                processed.add(procText.get(i));
            } else {
                ; // Do nothing
            }
        }
        return processed;
    }
}
