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
package com.jgaap.gui.generics;

import java.awt.LayoutManager;
import java.util.Vector;

import javax.swing.JPanel;


/**
 * Simple class that extends JPanel and represents a single step of a multi-step
 * process.
 * 
 * @author Chuck Liddell
 */
public class StepPanel extends JPanel {

    /**
     *
     */
    private static final long    serialVersionUID = 1L;

    /**
     * Represents the name of the step this panel represents. This name will be
     * displayed in the GUI's step progress indicator, and will also be used to
     * identify to classes implementing StepListener which step is ready for
     * execution. Once set, the stepName cannot be changed.
     */
    public final String          stepName;

    /**
     * nextButtonText allows programmers to override the default text that will
     * be displayed on the 'Next' button in the step GUI.
     */
    private String               nextButtonText   = "Next";

    /**
     * Vector of objects that implement StepListener and are waiting for this
     * panel to trigger execute events.
     */
    private Vector<StepListener> stepListeners    = new Vector<StepListener>();

    /**
     * Basic constructor. Creates a new StepPanel with a double buffer and a
     * flow layout.
     * 
     * @param stepName
     *            the name of the step that this panel represents
     */
    public StepPanel(String stepName) {
        super();
        this.stepName = stepName;
    }

    /**
     * Creates a new StepPanel with FlowLayout and the specified buffering
     * strategy.
     * 
     * @param stepName
     *            the name of the step that this panel represents
     * @param isDoubleBuffered
     *            flag indicating this panel's buffering strategy
     */
    public StepPanel(String stepName, boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        this.stepName = stepName;
    }

    /**
     * Create a new buffered StepPanel with the specified layout manager.
     * 
     * @param stepName
     *            the name of the step that this panel represents
     * @param layout
     *            a specific LayoutManager to override the default
     */
    public StepPanel(String stepName, LayoutManager layout) {
        super(layout);
        this.stepName = stepName;
    }

    /**
     * Creates a new StepPanel with the specified layout manager and buffering
     * strategy.
     * 
     * @param stepName
     *            the name of the step that this panel represents
     * @param layout
     *            a specific LayoutManager to override the default
     * @param isDoubleBuffered
     *            flag indicating this panel's buffering strategy
     */
    public StepPanel(String stepName, LayoutManager layout,
            boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        this.stepName = stepName;
    }

    /**
     * Add a StepListener to this panel, which will receive notification of any
     * calls this panel gets to execute.
     * 
     * @param listener
     *            the StepListener to be added to this step panel.
     */
    public void addStepListener(StepListener listener) {
        stepListeners.add(listener);
    }

    /**
     * Tell this step panel, and any StepListeners it has, to execute.
     */
    public void executeStep() {
    	
        for (StepListener listener : stepListeners) {
            listener.executeStep(stepName);
        }
    }

    /**
     * Get the text that should be displayed on the 'Next' button.
     * 
     * @return String containing the text to overlay on the 'Next' button
     */
    public String getNextButtonText() {
        return nextButtonText;
    }

    /**
     * Change the text that is displayed on the 'Next' button in the step GUI.
     * 
     * @param newText
     *            the new text to be displayed on the button
     */
    public void setNextButtonText(String newText) {
        nextButtonText = newText;
    }
}
