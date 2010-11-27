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
 * EventSetPanel.java
 */
package com.jgaap.gui.stepPanels;

import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import com.jgaap.backend.AutoPopulate;
import com.jgaap.generics.EventDriver;
import com.jgaap.gui.generics.StepPanel;

/**
 * This panel represents a single step in a multi-step process, both graphically
 * and logically. It encapsulates all the necessary graphical elements and
 * program logic needed for this step, and provides access methods for necessary
 * data to be passed along to the next step. Specifically, it handles Event Set
 * processing for the JGAAP program.
 * 
 * @author Chuck Liddell Created 11/13/08 TODO: Some event set redesigning (for
 *         example: n-grams) TODO: Redesign this panel to be more efficient and
 *         dynamic TODO: Separate checkboxes visually in the layout TODO:
 *         Disable Next button while no event sets have been selected TODO:
 *         Provide Tooltip explanation on Next button for why it is disabled
 */
public class EventSetStepPanel extends StepPanel {

	/**
     *
     */
	private static final long serialVersionUID = 1L;
	// ---- Global Variables ----
	private JRadioButton[] eventifierRadioButtons;
	private ButtonGroup esButtonGroup;
	/**
	 * Defines the possible eventifiers to use. ( done dynamically)
	 */
	private Vector<EventDriver> eventifiers;

	/**
	 * Default constructor.
	 */
	public EventSetStepPanel() {
		super("Event Set");
		setNextButtonText("Create Event Set");
		setLayout(new GridLayout(0, 3));

		eventifiers = new Vector<EventDriver>();
		for (EventDriver event : AutoPopulate.getEventDrivers()) {
			if (event.showInGUI())
				eventifiers.add(event);
		}
		eventifierRadioButtons = new JRadioButton[eventifiers.size()];
		// One radio button for each eventifier

		esButtonGroup = new ButtonGroup(); // Forces single selection across the
		// different radio buttons
		int i = 0;
		for (EventDriver event : eventifiers) {
			eventifierRadioButtons[i] = new JRadioButton(event.displayName());
			eventifierRadioButtons[i].setActionCommand(event.displayName());
			eventifierRadioButtons[i].setToolTipText(event.tooltipText());
			esButtonGroup.add(eventifierRadioButtons[i]);
			this.add(eventifierRadioButtons[i]);
			if (i == 0) {
				eventifierRadioButtons[i].setSelected(true);
			}
			i++;
		}
	}

	/**
	 * Returns the action command string of the selected radio button.
	 * 
	 * @return String representation of the selected radio button.
	 */
	public String getRadioSelection() {
		return esButtonGroup.getSelection().getActionCommand();
	}
}
