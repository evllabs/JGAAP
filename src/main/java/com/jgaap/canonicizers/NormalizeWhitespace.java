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

import com.google.common.base.CharMatcher;
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
    public String longDescription(){
    	return "Converts all whitespace characters (newline, space and tab) to a single space.  Uses Java Character.isWhitespace for classification.";
    }
    
    
    @Override
    public boolean showInGUI(){
    	return true;
    }
	
    /**
     * normalize whitespace in argument
     * 
     * @param procText
     *            Array of Characters to be processed
     * @return Array of Characters after converting tab, newline, etc. to
     *         single space.
     */
    @Override
    public char[] process(char[] procText) {
        return CharMatcher.WHITESPACE.collapseFrom(new String(procText), ' ').toCharArray();
    }
}
