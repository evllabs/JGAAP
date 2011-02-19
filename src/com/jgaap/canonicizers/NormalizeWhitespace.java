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

import com.jgaap.generics.Canonicizer;

/**
 * Changes length of all white spaces to 1. Any sequence of whitespaces
 * including newline, tab, and space, will become a single space in the
 * processed document.
 * 
 * @since 1.0
 **/
public class NormalizeWhitespace extends Canonicizer {

	@Override
    public String displayName(){
    	return "Normalize Whitespace";
    }
    
    @Override
    public String tooltipText(){
    	return "Converts all whitespace characters (newline, space and tab) to a single space.";
    }
    
    @Override
    public boolean showInGUI(){
    	return true;
    }
    
    @Override
    public Color guiColor(){
    	return Color.RED;
    }
	
    /**
     * normalize whitespace in argument
     * 
     * @param procText
     *            Vector of Characters to be processed
     * @return Vector of Characters after converting tab, newline, etc. to
     *         single space.
     */
    @Override
    public char[] process(char[] procText) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean spaceflag = false;
        for (int i = 0; i < procText.length; i++) {
            if (Character.isWhitespace(procText[i]) && !spaceflag) {
                stringBuilder.append(' ');
                spaceflag = true;
            } else if (!Character.isWhitespace(procText[i])) {
                stringBuilder.append(procText[i]);
                spaceflag = false;
            }
        }
        return stringBuilder.toString().toCharArray();
    }
}
