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
 * AnalyzeStepPanel.java
 */
package com.jgaap.gui.stepPanels;

import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;

import com.jgaap.backend.AutoPopulate;
import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.DivergenceType;
import com.jgaap.gui.generics.StepPanel;

/**
 * This panel represents a single step in a multi-step process, both graphically
 * and logically. It encapsulates all the necessary graphical elements and
 * program logic needed for this step, and provides access methods for necessary
 * data to be passed along to the next step. Specifically, it handles Analysis
 * Method processing for the JGAAP program.
 * 
 * @author Chuck Liddell Created 11/13/08 TODO: Redesign this panel to be more
 *         efficient and dynamic TODO: Separate checkboxes visually in the
 *         layout TODO: Disable Next button while no methods have been selected
 *         TODO: Provide Tooltip explanation on Next button for why it is
 *         disabled
 */
public class AnalyzeStepPanel extends StepPanel {

	/**
     *
     */
	private static final long serialVersionUID = 1L;
	// ---- Global Variables ----
	private JRadioButton[] analysisMethodsRadioButtons;
	private ButtonGroup amButtonGroup;
	private JCheckBox verboseCheckBox;
	private JRadioButton divergenceStandardRadioButton;
	private JRadioButton divergenceAverageRadioButton;
	private JRadioButton divergenceMaxRadioButton;
	private JRadioButton divergenceMinRadioButton;
	private JRadioButton divergenceReverseRadioButton;
	private ButtonGroup dmButtonGroup;

	/**
	 * Defines the possible analysis methods to use. (done dynamically)
	 */
	Vector<DistanceFunction> distance_methods;
	Vector<AnalysisDriver> analysis_methods;

	/**
	 * Default Constructor
	 */
	public AnalyzeStepPanel() {

		super("Analyze");
		setNextButtonText("Analyze");
		setLayout(new GridLayout(0, 3)); // 3 columns, unlimited rows

		distance_methods = new Vector<DistanceFunction>();
		for (DistanceFunction event : AutoPopulate.getDistanceFunctions()) {
			if (event.showInGUI())
				distance_methods.add(event);
		}
		analysis_methods = new Vector<AnalysisDriver>();
		for (AnalysisDriver event : AutoPopulate.getAnalysisDrivers()) {
			if (event.showInGUI())
				analysis_methods.add(event);
		}
		analysisMethodsRadioButtons = new JRadioButton[distance_methods.size()+analysis_methods.size()]; // One
		// radio
		// button
		// for
		// each
		// analysis
		// method
		// defined
		// in
		// ANALYSIS_METHODS
		amButtonGroup = new ButtonGroup(); // Forces single selection across the
		// different radio buttons

		// Instantiate and configure the radio buttons, then add them to the
		// panel
		int i = 0;
		for(DistanceFunction method : distance_methods){
			analysisMethodsRadioButtons[i] = new JRadioButton(method.displayName());
			analysisMethodsRadioButtons[i].setActionCommand(method.displayName());
			analysisMethodsRadioButtons[i].setToolTipText(method.tooltipText());
			amButtonGroup.add(analysisMethodsRadioButtons[i]);
			this.add(analysisMethodsRadioButtons[i]);
			if(i==0){
				analysisMethodsRadioButtons[i].setSelected(true);
			}
			i++;
		}
		for(AnalysisDriver method : analysis_methods){
			analysisMethodsRadioButtons[i] = new JRadioButton(method.displayName());
			analysisMethodsRadioButtons[i].setActionCommand(method.displayName());
			analysisMethodsRadioButtons[i].setToolTipText(method.tooltipText());
			amButtonGroup.add(analysisMethodsRadioButtons[i]);
			this.add(analysisMethodsRadioButtons[i]);
			i++;
		}
		
		verboseCheckBox = new JCheckBox("Verbose Report Output");
		verboseCheckBox.setEnabled(false);
		this.add(verboseCheckBox);
		
		dmButtonGroup = new ButtonGroup();
		divergenceStandardRadioButton = new JRadioButton("Std");
		divergenceStandardRadioButton.setActionCommand("0");
		divergenceStandardRadioButton.setToolTipText("The standard method of performing divergences Known -> Unknown");
		dmButtonGroup.add(divergenceStandardRadioButton);
		this.add(divergenceStandardRadioButton);
		divergenceStandardRadioButton.setSelected(true);
		divergenceAverageRadioButton = new JRadioButton("Average");
		divergenceAverageRadioButton.setActionCommand("1");
		divergenceAverageRadioButton.setToolTipText("The average method of performing divergences (Known -> Unknown + Unknown -> Known)/2");
		dmButtonGroup.add(divergenceAverageRadioButton);
		this.add(divergenceAverageRadioButton);
		divergenceMaxRadioButton = new JRadioButton("Max");
		divergenceMaxRadioButton.setActionCommand("2");
		divergenceMaxRadioButton.setToolTipText("The max method of performing divergences MAX{Known -> Unknown, Unknown -> Known}");
		dmButtonGroup.add(divergenceMaxRadioButton);
		this.add(divergenceMaxRadioButton);
		divergenceMinRadioButton = new JRadioButton("Min");
		divergenceMinRadioButton.setActionCommand("3");
		divergenceMinRadioButton.setToolTipText("The min method of performing divergences MIN{Known -> Unknown, Unknown -> Known}");
		dmButtonGroup.add(divergenceMinRadioButton);
		this.add(divergenceMinRadioButton);
		divergenceReverseRadioButton = new JRadioButton("Reverse");
		divergenceReverseRadioButton.setActionCommand("4");
		divergenceReverseRadioButton.setToolTipText("The reverse method of performing divergences Unknown -> Known");
		dmButtonGroup.add(divergenceReverseRadioButton);
		this.add(divergenceReverseRadioButton);
		
	}

	/**
	 * Returns the state of the 'Divergence Average' checkbox.
	 * 
	 * @return the boolean state of the Divergence Average checkbox.
	 */
	public DivergenceType getDivergenceTypeSelection() {
		
		int selected = Integer.parseInt(dmButtonGroup.getSelection().getActionCommand());
		DivergenceType tmpD;
		switch(selected){
		
		case 1:
			tmpD=DivergenceType.Average;
			break;
		case 2:
			tmpD=DivergenceType.Max;
			break;
		case 3:
			tmpD=DivergenceType.Min;
			break;
		case 4:
			tmpD=DivergenceType.Reverse;
			break;
		case 0:
		default:
			tmpD=DivergenceType.Standard;	
		}
		return tmpD;

	}

	/**
	 * Returns the action command string of the selected radio button.
	 * 
	 * @return String representation of the selected radio button.
	 */
	public String getRadioSelection() {
		return amButtonGroup.getSelection().getActionCommand();
	}

	/**
	 * Returns the state of the 'Verbose' checkbox.
	 * 
	 * @return the boolean state of the Verbose checkbox.
	 */
	public boolean getVerboseSelection() {
		return verboseCheckBox.isSelected();
	}
}
