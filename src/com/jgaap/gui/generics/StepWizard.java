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

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


/**
 * Encapsulating class for several other classes in the guiComponents package.
 * This class uses a StepProgressIndicator and a series of StepPanels to present
 * the end-user with a wizard-like GUI, where StepPanels are shown one at a time
 * with buttons to go to the next and previous steps, and the overall step
 * progress is tracked and displayed.
 * 
 * @author Chuck Liddell
 */
public class StepWizard extends JPanel implements ActionListener {

    /**
     *
     */
    private static final long     serialVersionUID = 1L;

    /**
     * Tracks the progress visually for the user, so they can see what step they
     * are on.
     */
    private StepProgressIndicator stepPI;

    /**
     * Stores the StepPanels used to represent the different steps in this
     * wizard. StepPanels are shown one at a time using a CardLayout.
     */
    private JPanel                stepsContainer;

    /**
     * The buttons used to change step in the wizard.
     */
    private JButton               prevButton, nextButton;

    /**
     * Keeps track of the step that is currently visible to the user.
     */
    private int                   currentStep      = 0;

    /**
     * The total number of steps in this wizard.
     */
    private int                   totalSteps       = 0;

    /**
     * Basic constructor.
     */
    public StepWizard() {
        super(new BorderLayout()); // Init & set the layout

        // Top of the wizard
        stepPI = new StepProgressIndicator();
        this.add(stepPI, BorderLayout.NORTH);

        // Center of the wizard
        stepsContainer = new JPanel(new CardLayout());
        this.add(stepsContainer, BorderLayout.CENTER);

        // Bottom of the wizard
        JPanel buttonsPanel = new JPanel(); // Containing panel for the buttons
        prevButton = new JButton("Back");
        prevButton.addActionListener(this);
        prevButton.setEnabled(false);
        buttonsPanel.add(prevButton);
        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        nextButton.setEnabled(false);
        buttonsPanel.add(nextButton);
        this.add(buttonsPanel, BorderLayout.SOUTH);
    }

    /**
     * Called whenever an ActionEvent is generated on a component for which this
     * class is a registered ActionListener.
     * 
     * @param e
     *            the event that was generated
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == prevButton) {
            if (currentStep > 0) {
                // change which step is currently active
                currentStep--;
                // change the step that is displayed
                ((CardLayout) stepsContainer.getLayout())
                        .previous(stepsContainer);
                // change the text on the Next button
                nextButton.setText(((StepPanel) stepsContainer
                        .getComponent(currentStep)).getNextButtonText());
                // disable Back button if this is first step
                if (currentStep == 0) {
                    prevButton.setEnabled(false);
                }
                // enable next button
                nextButton.setEnabled(true);
                // move the step indicator to the previous step
                stepPI.previousStep();
            }
        }

        if (e.getSource() == nextButton) {
            if (currentStep < totalSteps - 1) {
            	prevButton.setEnabled(false);
            	nextButton.setEnabled(false);
            	// Run execute step and such in its own thread so the GUI doesn't hang. -- JN 4/26/09
            	Runnable stepRunnable = new Runnable() {
            		public void run() {
		                ((StepPanel) stepsContainer.getComponent(currentStep))
		                        .executeStep();
		                currentStep++;
		                ((CardLayout) stepsContainer.getLayout()).next(stepsContainer);
		                nextButton.setText(((StepPanel) stepsContainer
		                        .getComponent(currentStep)).getNextButtonText());
		                if (currentStep == totalSteps - 1) {
		                    nextButton.setEnabled(false);
		                }
		                else {
		                	nextButton.setEnabled(true);
		                }
		                prevButton.setEnabled(true);
		                stepPI.nextStep();
            		}
            	};
            	Thread stepThread = new Thread(stepRunnable);
            	stepThread.start();
            }
        }
    }

    /**
     * Add a step to this wizard by passing a StepPanel to this method. Steps
     * will be displayed in the wizard in the order they are added.
     * 
     * @param newStep
     *            the new step to be added to this wizard
     */
    public void addStep(StepPanel newStep) {
        // special case for first step to be added
        if (totalSteps == 0) {
            nextButton.setText(newStep.getNextButtonText());
            nextButton.setEnabled(true);
        }
        stepsContainer.add(newStep, newStep.stepName);
        stepPI.addStep(newStep.stepName);
        totalSteps++;
    }

}

