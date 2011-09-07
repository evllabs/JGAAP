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
