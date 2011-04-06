// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
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
    public String longDescription(){
    	return "Converts all whitespace characters (newline, space and tab) to a single space.  Uses Java Character.isWhitespace for classification.";
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
     *            Array of Characters to be processed
     * @return Array of Characters after converting tab, newline, etc. to
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
