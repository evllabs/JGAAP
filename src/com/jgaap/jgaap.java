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
package com.jgaap;

import com.jgaap.backend.CLI;
import com.jgaap.ui.JGAAP_UI_MainForm;

/**
 * The jgaap main file.
 *  
 * @author michael ryan
 */

public class jgaap {

    public static boolean commandline = false;
    public static final boolean DEBUG = true;

    /**
     * Launches the jgaap GUI.
     */
    private static void createAndShowGUI() {
    	// Note that the GUI object is stored in the globalObjects HashMap
    	// under the key "gui" so other objects can access it easily
    	JGAAP_UI_MainForm gui = new JGAAP_UI_MainForm();
    	gui.setVisible(true);
        jgaapConstants.globalObjects.put("gui", (gui));
    }
/**
 * Initializes predefined global parameters.
 */
    public static void initParameters() {
        jgaapConstants.globalParams.setParameter("Language", "English");
    }
/**
 * launches either the CLI or the GUI based on the command line arguments
 * @param args the command line arguments
 */
    public static void main(String[] args) {
        initParameters();
        if (args.length == 0) {
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI();
                }
            });
        } else {
            CLI.main(args);
        }
    }
}
