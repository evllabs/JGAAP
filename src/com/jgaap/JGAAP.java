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

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.jgaap.backend.CLI;
import com.jgaap.ui.JGAAP_UI_MainForm;

/**
 * The jgaap main file.
 *  
 * @author michael ryan
 */

public class JGAAP {

	static Logger logger = Logger.getLogger("com.jgaap");
	static Logger mainLogger = Logger.getLogger(JGAAP.class);
	
	public static boolean commandline = false;

    /**
     * Launches the jgaap GUI.
     */
    private static void createAndShowGUI() {
    	JGAAP_UI_MainForm gui = new JGAAP_UI_MainForm();
    	gui.setVisible(true);
    }

/**
 * launches either the CLI or the GUI based on the command line arguments
 * @param args the command line arguments
 */
    public static void main(String[] args) {

    	BasicConfigurator.configure();
    	logger.setLevel(Level.INFO);
    	
    	if (args.length == 0) {
    		mainLogger.info("Starting GUI");
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI();
                }
            });
        } else {
        	mainLogger.info("Starting CLI");
            CLI.main(args);
        }
    }
}
