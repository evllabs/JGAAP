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
package com.jgaap.canonicizers;

import java.awt.Color;

import com.jgaap.generics.Canonicizer;

/** 
 * @author pjuola
 * Normalizes the document to printable ASCII
 */
public class NormalizeASCII extends Canonicizer {

    @Override
    public String displayName(){
    	return "Normalize ASCII";
    }
    
    @Override
    public String tooltipText(){
    	return "Strip all non-ASCII, non-printing characters from the text.  Whitespace is preserved.";
    }

    @Override
    public String longDescription(){
    	return "Strip all non-ASCII, non-printing characters from the text.  Whitespace is preserved.  (Geek content: Whitespace is defined as characters 0x09-0x0D, inclusive, and printable ASCII is characters 0x20-0x7E, inclusive.)";
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
        StringBuilder stringBuilder = new StringBuilder(procText.length);
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
