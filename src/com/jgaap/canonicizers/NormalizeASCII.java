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
