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
 * Changes length of all white spaces to 1. Any sequence of whitespaces
 * including newline, tab, and space, will become a single space in the
 * processed document.
 * 
 * @since 4.1
 **/
public class StripNumbers extends Canonicizer {

	@Override
    public String displayName(){
    	return "Strip Numbers";
    }
    
    @Override
    public String tooltipText(){
    	return "Converts numbers to a single # sign";
    }
    
    @Override
    public boolean showInGUI(){
    	return true;
    }
    
    @Override
    public Color guiColor(){
    	return Color.MAGENTA;
    }
	
    /**
     * strip numbers in argument
     * 
     * @param procText
     *            Vector of Characters to be processed
     * @return Vector of Characters after converting digit string to '#'
     *
     */
    @Override
    public List<Character> process(List<Character> procText) {
        List<Character> processed = new ArrayList<Character>();
        boolean spaceflag = false;
        for (int i = 0; i < procText.size(); i++) {
            if (Character.isDigit(procText.get(i)) && !spaceflag) {
                processed.add(new Character('#'));
                spaceflag = true;
            } else if (!Character.isDigit(procText.get(i)) &&
		procText.get(i) != ',' &&
		procText.get(i) != '.' ) {
			// handle numbers like 3.14 and 20,000 as well
			// TODO : handle numbers like .001 or 12. (?)

                processed.add(procText.get(i));
                spaceflag = false;
            }
        }
        return processed;
    }
}
