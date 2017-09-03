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
        return "Extract Interviewee Text";  //To change body of implemented methods use File | Settings | File Templates.
    }
    @Override
    public String longDescription() {
        return "Extract Interviewee Text from LINDSEI L1 Corpus";
    }

    @Override
    public boolean showInGUI() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public char[] process(char[] procText) {
        String text = new String(procText);
        StringBuilder newText = new StringBuilder(procText.length);

        int start = 0;
        int end = 0;

        while(true) {
            start = text.indexOf("<B>", end + 3);
            end = text.indexOf("</B>", start);

            if(start == -1) {
                return newText.toString().toCharArray();
            }
            newText.append(" " + text.substring(start+3, end) + " ");
        }
    }
}
