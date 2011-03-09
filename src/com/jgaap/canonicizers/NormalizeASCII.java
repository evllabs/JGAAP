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
 * @author pjuola
 * Normalizes the document to printable ASCII
 */
public class NormalizeASCII extends Canonicizer {

    private String punc = ",.?!\"\'`;:-()&$"; // Characters which will be

    @Override
    public String displayName(){
    	return "Normalize to ASCII";
    }
    
    @Override
    public String tooltipText(){
    	return "Strip all non-ASCII, non-printing characters from the text.  Whitespace is preserved.";
    }
    
    @Override
    public boolean showInGUI(){
    	return true;
    }
    
    @Override
    public Color guiColor(){
    	return Color.LIGHT_GRAY;
    }
    

    /**
     * Strip punctuation from input characters
     * @param procText array of characters to be processed.
     * @return array of processed characters.
     */
    @Override
    public char[] process(char[] procText) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < procText.length; i++) {
	    int c = procText[i];
	    if ((c> 0x08 && c <= 0x0D) || // whitespace control codes
	        (c> 0x1F && c <= 0x7E))   // printable ASCII
		{
                	stringBuilder.append(procText[i]);
            } else {
                ; // Do nothing
            }
        }
        return stringBuilder.toString().toCharArray();
    }
}
