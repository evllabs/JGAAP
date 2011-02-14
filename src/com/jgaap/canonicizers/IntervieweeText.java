package com.jgaap.canonicizers;

import com.jgaap.generics.Canonicizer;

import java.awt.*;
import java.awt.List;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: jnoecker
 * Date: Feb 11, 2011
 * Time: 1:21:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class IntervieweeText extends Canonicizer {
    @Override
    public String displayName() {
        return "Interviewee Text";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String tooltipText() {
        return "Extract Interviewee Text from LINDSEI L1 Corpus";  //To change body of implemented methods use File | Settings | File Templates.
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
        StringBuffer text = new StringBuffer();
        StringBuffer newText = new StringBuffer();
        for(Character c : procText) {
            text.append(c);
        }

        int start = 0;
        int end = 0;

        while(true) {

            start = text.indexOf("<B>", end + 3);
            end = text.indexOf("</B>", start);

            if(start == -1) {

                ArrayList<Character> returnText = new ArrayList<Character>();

                for(Character c : newText.toString().toCharArray()) {
                    returnText.add(c);
                }

                return returnText;
            }
            newText.append(" " + text.substring(start+3, end) + " ");
        }
    }
}
