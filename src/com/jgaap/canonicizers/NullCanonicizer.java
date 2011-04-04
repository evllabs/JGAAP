// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
package com.jgaap.canonicizers;

import java.awt.Color;

import com.jgaap.generics.Canonicizer;

/**
 * A "fake" canonicizer that doesn't change its input but prints data. Used for
 * debugging purposes.
 */
public class NullCanonicizer extends Canonicizer {
	

    public String displayName(){
    	return "Null Canonicizer";
    }
    

    public String tooltipText(){
    	return "This preprocessor makes no changes to the text, but prints it to the console.";
    }
    

    public boolean showInGUI(){
    	return true;
    }
    

    public Color guiColor(){
    	return Color.GRAY;
    }

	/**
	 * Does not affect its input in any way, but prints out the texts
	 * to standard output.
	 * @param procText Array of characters to be processed.
	 * @return Array of processed characters 
	 */

    public char[] process(char[] procText) {

        // Reimplemented to play nicely with threading
        StringBuffer tmp = new StringBuffer();
        for (int i = 0; i < procText.length; i++) {
            tmp.append(procText[i]);
        }
		System.out.print(" --- Begin Document ---\n" + tmp + "\n --- End Document ---\n");

        return procText;
    }
}
