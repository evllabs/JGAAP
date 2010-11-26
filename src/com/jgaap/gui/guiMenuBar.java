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
package com.jgaap.gui;

/**
 * guiMenuBar.java
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

import com.jgaap.backend.AutoPopulate;
import com.jgaap.backend.CSVIO;
import com.jgaap.generics.Language;


/**
 * This class encapsulates the menus and menu items used in the JGAAP GUI. It
 * handles their events and calls specific methods in the main GUI as needed.
 * 
 * @author Chuck Liddell created 11/12/08 TODO: Finish code for saving / loading
 */
public class guiMenuBar extends JMenuBar implements ActionListener {

	/**
     *
     */
	private static final long serialVersionUID = 1L;
	// ---- Global Variables ----
	private JMenu menuFile;
	private JMenuItem menuFileExit;
	private JMenuItem menuFileLoad;
	private JMenuItem menuFileSaveKnown;
	private JMenuItem menuFileSaveUnknown;
	private JMenuItem menuFileSaveAll;
	private JMenu menuFileLanguage;
	private ButtonGroup menuFileLanguageSelection;
	private JRadioButtonMenuItem[] menuFileLanguageOptions;
	private Vector<Language> languages;
	private JMenu menuDemos;
	private Vector<JMenuItem> menuItemDemos = new Vector<JMenuItem>();
	private JMenu menuHelp;
	private JMenuItem menuHelpDemos;
	private JMenuItem menuHelpAbout;

	private JFileChooser loadFile = new JFileChooser();

	/**
	 * A list of demo names that will be displayed in the GUI for loading.
	 */
	public final String[] DEMO_NAMES = { "A", "B", "C", "D", "E", "F", "G",
			"H", "I", "J", "K", "L", "M" };

	/**
	 * A reference to the main GUI that we will use to call certain methods.
	 */
	private final jgaapGUI theGUI;

	/**
	 * A helpful message that is displayed when a user clicks the 'Running the
	 * Demos' menu item.
	 */
	private final String HELP_MESSAGE = "To Run The Demos:\n\n"
			+ "1. Go to the \"Demos\" menu and select a demo to load.\n"
			+ "2. Use the buttons at the bottom of the window to navigate the wizard.\n"
			+ "3. Canonicizers - Choose any combination (including none at all) of Canonicizers.\n"
			+ "4. Event Sets - Decide what should constitute an event and select one of the available event sets (e.g. \"Words\").\n"
			+ "5. Analysis Methods - Choose an analysis method to be applied to the generated event set.\n"
			+ "6. Click on the \"Analyze\" button and wait for the analysis to complete. This may take several moments.\n"
			+ "7. The results of the analysis are displayed in the Report section.";

	/**
	 * A brief message that provides basic information about this program.
	 */
	private final String ABOUT_MESSAGE = "Java Graphical Authorship Attribution Program\n\n"
			+ "Version: "
			+ jgaapGUI.VERSION
			+ "\n"
			+ "Copyright 2009 - Released Under the GPLv3.\n\n"
			+ "Developed in the MathCS department at Duquesne University\n"
			+ "Authors: Patrick Juola, Mike Ryan, John Noecker, Chuck Liddell, Sandy Spears";

	/**
	 * Message that is displayed before each demo name.
	 */
	private final String DEMO_PREFIX = "Load Problem ";

	/**
	 * Default constructor.
	 */
	public guiMenuBar(jgaapGUI theGUI) {
		super();

		this.theGUI = theGUI; // Set a reference to the gui object

		// -------------------------------------
		// -- Instantiate Menus and MenuItems --
		// -------------------------------------
		menuFile = new JMenu("File");
		menuFileLoad = new JMenuItem("Load CSV");
		menuFileSaveKnown = new JMenuItem("Save Known");
		menuFileSaveAll = new JMenuItem("Save All");
		menuFileSaveUnknown = new JMenuItem("Save Unknown");
		menuFileLanguage = new JMenu("Language");
		menuFileLanguageSelection = new ButtonGroup();
		languages = new Vector<Language>();
		languages.addAll(AutoPopulate.getLanguages());
		menuFileLanguageOptions = new JRadioButtonMenuItem[languages.size()];
		menuFileExit = new JMenuItem("Exit");
		menuDemos = new JMenu("Demos");
		for (String demoName : DEMO_NAMES) {
			menuItemDemos.add(new JMenuItem(DEMO_PREFIX + demoName));
		}
		menuHelp = new JMenu("Help");
		menuHelpDemos = new JMenuItem("Running the Demos");
		menuHelpAbout = new JMenuItem("About");

		// -------------------------------------------------------------
		// -- Set additional properties and add components to the GUI --
		// -------------------------------------------------------------

		// -- File Menu --
		menuFileLoad.addActionListener(this);
		menuFile.add(menuFileLoad);
		menuFileSaveKnown.addActionListener(this);
		menuFile.add(menuFileSaveKnown);
		menuFileSaveUnknown.addActionListener(this);
		menuFile.add(menuFileSaveUnknown);
		menuFileSaveAll.addActionListener(this);
		menuFile.add(menuFileSaveAll);
		menuFile.add(menuFileLanguage);
		// -- File Sub-Menu Language --
		int l = 0;
		for(Language lang : languages){
			menuFileLanguageOptions[l] = new JRadioButtonMenuItem(lang.getName());
			menuFileLanguage.add(menuFileLanguageOptions[l]);
			menuFileLanguageSelection.add(menuFileLanguageOptions[l]);
			menuFileLanguageOptions[l].setActionCommand(lang.getName());
			if(lang.getName().equalsIgnoreCase("English"))
				menuFileLanguageOptions[l].setSelected(true);
			l++;
		}
		// -- END Language Sub-Menu --
		menuFileExit.addActionListener(this);
		menuFile.add(menuFileExit);
		this.add(menuFile); // Add this Menu to the Menu Bar

		// -- Demo Menu --
		for (JMenuItem demo : menuItemDemos) {
			// The action command is 'demo' plus the name of this demo, taken
			// from DEMO_NAMES
			demo.setActionCommand("demo"
					+ demo.getText().substring(DEMO_PREFIX.length()));
			demo.addActionListener(this);
			menuDemos.add(demo);
		}
		this.add(menuDemos); // Add this Menu to the Menu Bar

		// -- Help Menu --
		menuHelpDemos.addActionListener(this);
		menuHelp.add(menuHelpDemos);
		menuHelpAbout.addActionListener(this);
		menuHelp.add(menuHelpAbout);
		this.add(menuHelp); // Add this Menu to the Menu Bar
	}

	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		String command = e.getActionCommand();
		
		if (src == menuFileExit) {
			System.exit(0);
		}

		if (src == menuHelpDemos) {
			JOptionPane.showMessageDialog(null, HELP_MESSAGE,
					"Running the Demos", JOptionPane.INFORMATION_MESSAGE);
		}

		if (src == menuHelpAbout) {
			JOptionPane.showMessageDialog(null, ABOUT_MESSAGE, "JGAAP - About",
					JOptionPane.INFORMATION_MESSAGE);
		}

		if (e.getActionCommand().startsWith("demo")) {
			theGUI.loadDemo(e.getActionCommand().substring(4));
		}

		if (src == menuFileLoad) {
			if (theGUI.DEBUG) {
				System.out.println("Load: ");
			}
			// Show a dialog box for the user to select a file to load, then
			// process it using CSVIO
			if (loadFile.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				theGUI.addDocumentsToGUI(CSVIO.readCSV(loadFile
						.getSelectedFile()));
				if (theGUI.DEBUG) {
					System.out.println("Loading: "
							+ loadFile.getSelectedFile().getName() + ".");
				}
			} else if (theGUI.DEBUG) {
				System.out.println("User canceled load.");
			}
		}
		if (command.startsWith("Save")) {
			System.out.println(command + ":");
			theGUI.saveDocuments(command);
		}
	}

	public String getSelectedLanguage() {
		String selected = this.menuFileLanguageSelection.getSelection().getActionCommand();
		return selected;
	}
}
