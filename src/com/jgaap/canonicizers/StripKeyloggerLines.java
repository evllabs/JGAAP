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

import java.util.Scanner;

import com.jgaap.generics.Canonicizer;

/**
 * Created by IntelliJ IDEA.
 * User: jnoecker
 * Date: Feb 11, 2011
 * Time: 2:03:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class StripKeyloggerLines extends Canonicizer {
	
	private String right = "\u001a";
	private String down = "\u0019";
	private String esc = "\u0013";
	private String back = "\u0011";
	private String tab = "\t";
	private String delete = "\u0010";
	private String enter = "\n";
	private String up = "\u0018";
	private String left = "\u001b";
	private String newline = "\u009d";
	private String capslock = "\u0015";
	
    @Override
    public String displayName() {
        return "Strip Keylogger Lines";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String tooltipText() {
        return "Strip Keylogger Lines (�) characters from text";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String longDescription() {
        return "Strip Keylogger Lines (�) characters from text"; 
    }

    @Override
    public boolean showInGUI() {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * eliminate keylogger newline characters '�'  in argument.
     *
     * @param procText
     *            Array of Characters to be processed
     * @return Array of Characters with null characters eliminated
     */

    @Override
    public char[] process(char[] procText) {
    	String procString = new String(procText);
    	StringBuilder builder = new StringBuilder();
    	Scanner textScanner = new Scanner(procString);
    	boolean inText = false;
    	while(textScanner.hasNextLine()) {
    		String line = textScanner.nextLine();
    		if(line.indexOf("--->") == 0) {
    			inText = false;
    		}
    		if(inText) {
    			line = line.replaceAll("\n", newline);
    			line = line.replaceAll("\r", "");
    			line = line.replaceAll(" -Right- ", right);
    			line = line.replaceAll(" -Down- ", down);
    			line = line.replaceAll(" -Esc- ", esc);
    			line = line.replaceAll(" -Back- ", back);
    			line = line.replaceAll(" -Tab- ", tab);
    			line = line.replaceAll(" -Delete- ", delete);
    			line = line.replaceAll(" -Enter- ", enter);
    			line = line.replaceAll(" -Up- ", up);
    			line = line.replaceAll(" -Left- ", left);
    			line = line.replaceAll(" -Caps Lock- ", capslock);
    			line = line.replaceAll("\\s�\\s", " ");
    			line = line.replaceAll("�", "");
    			builder.append("\n").append(line);

    		}
    		if(line.indexOf("--->") == 0 && line.indexOf("PRESSED:") != -1) { 
    			inText = true;
    		}
    	}
    	textScanner.close();

    	return builder.toString().toCharArray();
    }
}
