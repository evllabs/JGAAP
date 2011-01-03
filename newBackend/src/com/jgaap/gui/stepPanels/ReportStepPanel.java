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
/**
 * ReportStepPanel.java
 */
package com.jgaap.gui.stepPanels;

import java.awt.BorderLayout;
import java.awt.Color;
//Save and Print buttons not yet implemented  
//import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.jgaap.gui.generics.StepPanel;

/**
 * This panel represents a single step in a multi-step process, both graphically
 * and logically. It encapsulates all the necessary graphical elements and
 * program logic needed for this step, and provides access methods for necessary
 * data to be passed along to the next step. Specifically, it handles Report
 * generation and display for the JGAAP program.
 * 
 * @author Chuck Liddell Created 11/13/08 TODO: Add functionality to reset and
 *         return to the first step in the process TODO: Clean up and
 *         dramatically improve the functionality of this panel
 */
public class ReportStepPanel extends StepPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    // ---- Global Variables ----
    private JScrollPane       scrollPane;
    private JTextArea         listResults;
    //save / print functionality not yet implemented
    //private JButton           saveButton, printButton;

    /**
     * Default Constructor
     */
    public ReportStepPanel() {

        super("Report");
        setNextButtonText("Done");

        scrollPane = new JScrollPane();
        listResults = new JTextArea(20, 30);
        //save and print functionality not yet implemented
        //saveButton = new JButton("Save");
        //printButton = new JButton("Print");
        /* TODO: Re-disable listResults once Save and Print are working */
        // listResults.setEnabled(false);
        listResults.setDisabledTextColor(Color.black);
        scrollPane.setViewportView(listResults);
        this.add(scrollPane, BorderLayout.CENTER);

        JPanel reportButtonsPanel = new JPanel();
        /*
         * JIN - 9/5/08 : Removed for JGAAP 3.1 release, as functionality is not
         * yet implemented
         */
        // reportButtonsPanel.add( saveButton );
        // reportButtonsPanel.add( printButton );
        this.add(reportButtonsPanel);
    }

    /**
     * Displays the string argument it receives in its main text area.
     * 
     * @param result
     *            a String to display in this Report panel
     */
    public void displayResult(String result) {
        listResults.setText(result);
    }
}
