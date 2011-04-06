// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
package com.jgaap.canonicizers;

import java.awt.Color;

import com.jgaap.generics.Canonicizer;

/**
 * Canonicizer for unifying (smashing) case. Converts all characters in the
 * document unto lower case using Character.toLowerCase().
 * 
 * @author Juola
 * @since 1.0
 **/
public class UnifyCase extends Canonicizer {

	@Override
    public String displayName(){
    	return "Unify Case";
    }
    
    @Override
    public String tooltipText(){
    	return "Converts all text to lower case.";
    }

    @Override
    public String longDescription(){
    	return "Converts all text to lower case.";
    }
    
    @Override
    public boolean showInGUI(){
    	return true;
    }
    
    @Override
    public Color guiColor(){
    	return Color.BLUE;
    }
	
    /**
     * unify (smash) case
     * 
     * @param procText
     *            Array of Characters to be processed
     * @return Array of Characters after conversion to lower case
     */
    @Override
    public char[] process(char[] procText) {
        for (int i = 0; i < procText.length; i++) {
            procText[i] = Character.toLowerCase(procText[i]);
        }
        return procText;
    }
}
