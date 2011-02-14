package com.jgaap.canonicizers;

import com.jgaap.generics.Canonicizer;

import java.awt.*;
import java.awt.List;
import java.util.*;

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

    @Override
    public java.util.List<Character> process(java.util.List<Character> procText) {
        java.util.List<Character> retList = new ArrayList<Character>();
        for(Character c : procText) {
            if(c != '\u0000') {
                retList.add(c);
            }
        }
        return retList;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
