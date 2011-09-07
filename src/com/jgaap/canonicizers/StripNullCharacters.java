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
package com.jgaap.canonicizers;

import com.jgaap.generics.Canonicizer;

import java.awt.Color;

/**
 * Created by IntelliJ IDEA.
 * User: jnoecker
 * Date: Feb 11, 2011
 * Time: 2:03:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class StripNullCharacters extends Canonicizer {
    @Override
    public String displayName() {
        return "Strip Null Characters";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String tooltipText() {
        return "Strip Null (0x00) characters from text";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String longDescription() {
        return "Strip Null (0x00) characters from text"; 
    }

    @Override
    public boolean showInGUI() {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * eliminate null characters '\u0000'  in argument.  Should be handled with general
     *  UTF-16 processing instead
     *
     * @param procText
     *            Array of Characters to be processed
     * @return Array of Characters with null characters eliminated
     */

    @Override
    public char[] process(char[] procText) {
    	String procString = new String(procText);
    	procString = procString.replaceAll("\\s\\u0000\\s", " ");
    	procString = procString.replaceAll("\\u0000", "");

    	return procString.toCharArray();
    }
}
