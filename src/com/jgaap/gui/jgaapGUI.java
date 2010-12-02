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

/*
 * jgaapGUI.java
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.ProgressMonitor;

import com.jgaap.jgaapConstants;
import com.jgaap.backend.API;
import com.jgaap.backend.CSVIO;
import com.jgaap.backend.Utils;
import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.DivergenceType;
import com.jgaap.generics.Document;
import com.jgaap.generics.NeighborAnalysisDriver;
import com.jgaap.gui.dnd.IconGlassPane;
import com.jgaap.gui.generics.StepListener;
import com.jgaap.gui.generics.StepWizard;
import com.jgaap.gui.stepPanels.AnalyzeStepPanel;
import com.jgaap.gui.stepPanels.CanonicizeStepPanel;
import com.jgaap.gui.stepPanels.DocumentsStepPanel;
import com.jgaap.gui.stepPanels.EventSetStepPanel;
import com.jgaap.gui.stepPanels.ReportStepPanel;

/**
 * This class is the primary class responsible for managing and displaying the
 * GUI interface for the JGAAP project. It interacts with the engine of the
 * project through a GUIDriver class.
 * 
 * @author Chuck Liddell
 * @author Patrick Juola
 * @author Mike Ryan
 * @author John Noecker Re-created 11/12/08
 */
public class jgaapGUI extends JFrame implements StepListener {
	/**
     *
     */
	private static final long serialVersionUID = 1L;

	// --------------------------
	// ---- Global Variables ----
	// --------------------------
	/**
	 * The current program version.
	 */
	public final static String VERSION = "3.4.0";

	/**
	 * The current progress of the GUI's Progress Bar
	 */
	public static int currentProgress = 0;

	/**
	 * A flag for more verbose output in System.out.
	 */
	public final boolean DEBUG = true;

	/**
	 * The guiDriver class that provides a means of interfacing with the JGAAP
	 * back-end.
	 */
	private API driver;

	/**
	 * Encapsulates the menu instantiation and logic, and calls appropriate
	 * methods in this class as needed.
	 */
	private guiMenuBar menuBar;

	/**
	 * This is the main component that controls the different steps in our
	 * process.
	 */
	private StepWizard stepWizard;

	private static ProgressMonitor progressMonitor;

	private JFileChooser saveFile = new JFileChooser();

	// ---- Various StepPanels ----
	private DocumentsStepPanel documentsStepPanel;
	private CanonicizeStepPanel canonicizeStepPanel;
	private EventSetStepPanel eventSetStepPanel;
	private AnalyzeStepPanel analyzeStepPanel;
	private ReportStepPanel reportStepPanel;

	/**
	 * Default constructor.
	 */
	public jgaapGUI() {
		driver = new API();
		initComponents();
		this.setTitle("Java Graphical Authorship Attribution");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * Increment the progress bar by one.
	 */
	public static synchronized void incProgress() {
		currentProgress++;
		if (progressMonitor != null) {
			progressMonitor.setProgress(currentProgress);
		}
	}

	/**
	 * Convenience method for adding multiple documents to the GUI display
	 * simultaneously.
	 * 
	 * @param newDocuments
	 *            Vector of Vectors of Strings with document info
	 */
	public void addDocumentsToGUI(List<List<String>> newDocuments) {
		for (List<String> docInfo : newDocuments) {
			addDocumentToGUI(docInfo);
		}
	}

	/**
	 * Called to add the different attributes of a document to the GUI display.
	 * 
	 * @param title
	 *            the title of the document
	 * @param filePath
	 *            the file path to the document
	 * @param authorName
	 *            the name of the document's author
	 */
	public void addDocumentToGUI(String filePath, String authorName,
			String title) {
		try {
			documentsStepPanel.addDocument(driver.addDocument(filePath,
					authorName, title));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Overloaded method in to make it possible to add documents to the GUI as a
	 * vector of Strings.
	 * 
	 * @param newDocument
	 *            vector of Strings with document info
	 */
	public void addDocumentToGUI(List<String> newDocument) {
		if ((newDocument.size() < 2)) {
			return; // If we don't have at least an author and a file, don't add
			// it
		}

		// Support old-style CSV files without the "Title" field. If the Title
		// field is present, load it. Otherwise, leave it blank
		addDocumentToGUI(newDocument.get(1), newDocument.get(0),
				(newDocument.size() > 2 ? newDocument.get(2) : ""));
	}

	/**
	 * Called when a StepPanel is executed by the StepWizard.
	 * 
	 * @param stepName
	 *            the name of the step that was called for execution
	 */
	public void executeStep(String stepName) {
		currentProgress = 0;
		if (stepName.equals("Documents")) {

			progressMonitor = new ProgressMonitor(null, "Loading Documents",
					"", 0, documentsStepPanel.getDocuments().size() + 1);
			incProgress();
			progressMonitor.setMillisToDecideToPopup(1);
			progressMonitor.setMillisToPopup(2);

			// gets and sets the selected language
			try {
				driver.setLanguage(this.menuBar.getSelectedLanguage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			canonicizeStepPanel.clearAll();
			canonicizeStepPanel.addDocuments(driver.getDocuments());
		}
		if (stepName.equals("Canonicize")) {
			System.out.println(" ===== CANONICIZE =====");

		}
		if (stepName.equals("Event Set")) {
			System.out.println(" ===== GENERATE EVENT SET =====");
			driver.removeAllEventDrivers();
			try {
				driver.addEventDriver(eventSetStepPanel.getRadioSelection());
				System.out.println("Event Set: "
						+ eventSetStepPanel.getRadioSelection());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (stepName.equals("Analyze")) {
			DivergenceType currentDivergenceMethod;
			System.out.println(" ===== ANALYZE =====");
			currentDivergenceMethod = analyzeStepPanel
					.getDivergenceTypeSelection();
			driver.removeAllAnalysisDrivers();
			driver.clearResults();
			try {
				AnalysisDriver analysisDriver = driver
						.addAnalysisDriver(analyzeStepPanel.getRadioSelection());
				if (analysisDriver instanceof NeighborAnalysisDriver) {
					((NeighborAnalysisDriver) analysisDriver)
							.getDistanceFunction().setParameter(
									"divergenceOption",
									currentDivergenceMethod.ordinal());
				}
				driver.execute();
				List<Document> unknowns = driver.getUnknownDocuments();
				StringBuffer buffer = new StringBuffer();
				for (Document unknown : unknowns) {
					buffer.append(unknown.getResult());
				}
				reportStepPanel.displayResult(buffer.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * Initializes all the GUI components.
	 */
	private void initComponents() {

		setMinimumSize(new Dimension(400, 500));
		// setPreferredSize(new Dimension(600, 550));
		setPreferredSize(new Dimension(800, 600));
		setLayout(new BorderLayout());

		menuBar = new guiMenuBar(this);
		this.add(menuBar, BorderLayout.NORTH);

		// -- The StepWizard and its StepPanels --
		stepWizard = new StepWizard();
		documentsStepPanel = new DocumentsStepPanel();
		documentsStepPanel.addStepListener(this);
		stepWizard.addStep(documentsStepPanel);

		canonicizeStepPanel = new CanonicizeStepPanel(new IconGlassPane(),
				driver);
		canonicizeStepPanel.addStepListener(this);
		stepWizard.addStep(canonicizeStepPanel);

		eventSetStepPanel = new EventSetStepPanel();
		eventSetStepPanel.addStepListener(this);
		stepWizard.addStep(eventSetStepPanel);

		analyzeStepPanel = new AnalyzeStepPanel();
		analyzeStepPanel.addStepListener(this);
		stepWizard.addStep(analyzeStepPanel);

		reportStepPanel = new ReportStepPanel();
		stepWizard.addStep(reportStepPanel);

		this.add(stepWizard, BorderLayout.CENTER);
		setGlassPane(canonicizeStepPanel.getGlassPane());
	}

	public void saveDocuments(String command) {
		List<Document> documents = new ArrayList<Document>();
		if (command.equalsIgnoreCase("save all")) {
			documents.addAll(driver.getDocuments());
		} else if (command.equalsIgnoreCase("save known")) {
			documents.addAll(driver.getKnownDocuments());
		} else if (command.equalsIgnoreCase("save unknown")) {
			documents.addAll(driver.getUnknownDocuments());
		}
		if(saveFile.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
			Utils.saveDocumentsToCSV(documents, saveFile.getSelectedFile());
		}
	}

	/**
	 * Load a demo into the documents panel.
	 * 
	 * @param prob
	 *            partial file name of the demo to load
	 */
	public void loadDemo(String prob) {
		documentsStepPanel.clearDocuments();
		File file = new File(jgaapConstants.docsDir() + "/aaac/Demos/load"
				+ prob + ".csv");
		addDocumentsToGUI(CSVIO.readCSV(file));
	}
}
