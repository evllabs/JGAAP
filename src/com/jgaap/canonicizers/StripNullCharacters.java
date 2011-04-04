// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
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
    public boolean showInGUI() {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Color guiColor() {
        return Color.BLUE;  //To change body of implemented methods use File | Settings | File Templates.
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
        StringBuilder stringBuilder = new StringBuilder();
        for(Character c : procText) {
            if(c != '\u0000') {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString().toCharArray();  //To change body of implemented methods use File | Settings | File Templates.
    }
}
