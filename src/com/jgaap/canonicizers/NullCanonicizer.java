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
