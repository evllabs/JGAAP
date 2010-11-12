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
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.ProgressMonitor;

import com.jgaap.DivergenceType;
import com.jgaap.jgaapConstants;
import com.jgaap.backend.CSVIO;
import com.jgaap.backend.guiDriver;
import com.jgaap.generics.Canonicizer;
import com.jgaap.generics.Document;
import com.jgaap.generics.Language;
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
    private static final long   serialVersionUID = 1L;

    // --------------------------
    // ---- Global Variables ----
    // --------------------------
    /**
     * The current program version.
     */
    public final static String  VERSION          = "3.4.0";
    
    /**
     * The current progress of the GUI's Progress Bar
     */
    public static int currentProgress = 0;

    /**
     * A flag for more verbose output in System.out.
     */
    public final boolean        DEBUG            = true;

    /**
     * The guiDriver class that provides a means of interfacing with the JGAAP
     * back-end.
     */
    private guiDriver           driver;

    /**
     * Encapsulates the menu instantiation and logic, and calls appropriate
     * methods in this class as needed.
     */
    private guiMenuBar          menuBar;

    /**
     * This is the main component that controls the different steps in our
     * process.
     */
    private StepWizard          stepWizard;
    
    private JFileChooser      saveFile         = new JFileChooser();
    
    private static ProgressMonitor progressMonitor;
    
    // ---- Various StepPanels ----
    private DocumentsStepPanel  documentsStepPanel;
    private CanonicizeStepPanel canonicizeStepPanel;
    private EventSetStepPanel   eventSetStepPanel;
    private AnalyzeStepPanel    analyzeStepPanel;
    private ReportStepPanel     reportStepPanel;

    /**
     * Default constructor.
     */
    public jgaapGUI() {
        driver = new guiDriver();
        initComponents();
        this.setTitle("Java Graphical Authorship Attribution");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo( null );
        this.setVisible(true);
    }
    
    /**
     * Increment the progress bar by one.
     */
    public static synchronized void incProgress() {
    	currentProgress++;
    	if(progressMonitor != null) {
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
    public void addDocumentsToGUI(Vector<Vector<String>> newDocuments) {
        for (Vector<String> docInfo : newDocuments) {
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
     * @param known
     *            boolean representing whether the author is known
     * @param authorName
     *            the name of the document's author
     */
    public void addDocumentToGUI(String filePath, String authorName, String title) {
        documentsStepPanel.addDocument( new Document( filePath, authorName, title ) );
    }

    /**
     * Overloaded method in to make it possible to add documents to the GUI as a
     * vector of Strings.
     * 
     * @param newDocument
     *            vector of Strings with document info
     */
    public void addDocumentToGUI(Vector<String> newDocument) {
        if ((newDocument.size() < 2)) {
            return; // If we don't have at least an author and a file, don't add
            // it
        }

        // Support old-style CSV files without the "Title" field. If the Title
        // field is present, load it. Otherwise, leave it blank       
        addDocumentToGUI(newDocument.get(1), newDocument.get(0),
		(newDocument.size() > 2 ? newDocument.get(2) : "") );
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
        	
        	progressMonitor = new ProgressMonitor(null, "Loading Documents", "", 0, documentsStepPanel.getDocuments().size()+1);
        	incProgress();
        	progressMonitor.setMillisToDecideToPopup(1);
        	progressMonitor.setMillisToPopup(2);

        	driver.clearAll(); // First, remove all documents that were already loaded
        	//gets and sets the selected language 
        	Language language = driver.selectedLanguage(this.menuBar.getSelectedLanguage());
        	language.apply();
            // get documents from the documents panel and add them to the guiDriver
            driver.addDocuments( documentsStepPanel.getDocuments() );
            //if the selected language requires parsing or other formatting  it is handled here
            driver.evaluateLanguage(language);
            canonicizeStepPanel.clearAll();
            canonicizeStepPanel.addDocuments( driver.getAllDocuments() );
        }
        if (stepName.equals("Canonicize")) {
            System.out.println(" ===== CANONICIZE =====");
        	progressMonitor = new ProgressMonitor(null, "Canonicization in Progress", "", 0, documentsStepPanel.getDocuments().size()+1);
        	incProgress();
        	progressMonitor.setMillisToDecideToPopup(1);
        	progressMonitor.setMillisToPopup(2);
            driver.canonicize();
        }
        if (stepName.equals("Event Set")) {
            System.out.println(" ===== GENERATE EVENT SET =====");
        	progressMonitor = new ProgressMonitor(null, "Generating Event Sets", "", 0, documentsStepPanel.getDocuments().size()+1);
        	incProgress();
        	progressMonitor.setMillisToDecideToPopup(1);
        	progressMonitor.setMillisToPopup(2);

            driver.createEventSet(eventSetStepPanel.getRadioSelection(),
                    eventSetStepPanel.getCheckboxSelection());
            System.out.println("Event Set: "
                    + eventSetStepPanel.getRadioSelection());
        }
        if (stepName.equals("Analyze")) {
        	driver.setAuthorDistance(menuBar.getAuthorDistance());
            String listResults = "";
            DivergenceType currentDivergenceMethod = DivergenceType.Standard;
            System.out.println(" ===== ANALYZE =====");
            listResults += "Canonicizers: ";
            // Commented out 1/24/09 CKL - will not work with new system - change or toss?
            /*JCheckBox[] canonicizers = canonicizeStepPanel.getCanonicizers();
            for (int i = 0; i < canonicizers.length; i++) {
                if (canonicizers[i].isSelected()) {
                    listResults += canonicizers[i].getText() + " ";
                }
            }*/
    		StringBuffer usedCanon = new StringBuffer();
    		for(Canonicizer c : ((Document)(jgaapConstants.globalObjects.get("hDoc"))).getCanonicizers()) {
    			usedCanon.append(c);
    			usedCanon.append(" ");
    		}
    		listResults += usedCanon.toString();
            listResults += "\nEvent Set: ";
            if (eventSetStepPanel.getCheckboxSelection()) {
                listResults += "Most Common ";
            }
            listResults += eventSetStepPanel.getRadioSelection();
            listResults += "\nAnalysis Method: ";
            listResults += analyzeStepPanel.getRadioSelection();
            currentDivergenceMethod = analyzeStepPanel.getDivergenceTypeSelection();
            switch (currentDivergenceMethod.ordinal()) {
				case 1:
					listResults+=(" (Average)");
					break;
				case 2:
					listResults+=(" (Max) ");
					break;
				case 3:
					listResults+=(" (Min)");
					break;
				case 4:
					listResults+=(" (Reverse)");
					break;
			}
            
            if (jgaapConstants.globalParams.getParameter("authorDistance").equalsIgnoreCase("true"))
            	listResults += " Author Distance";
            listResults += "\n\n";
            
        	progressMonitor = new ProgressMonitor(null, "Analyzing Documents", "", 0, documentsStepPanel.getDocuments().size()+1);
        	incProgress();
        	progressMonitor.setMillisToDecideToPopup(1);
        	progressMonitor.setMillisToPopup(2);

            jgaapConstants.globalParams.setParameter("divergenceOption", currentDivergenceMethod.ordinal());
            String results = driver.runStatisticalAnalysis(analyzeStepPanel.getRadioSelection());
            listResults += results;
            listResults += "\n---\n\n";
            if (analyzeStepPanel.getVerboseSelection()) {
                listResults += "Verbose Output:\n\nMove zig. For great justice.";
            }
            reportStepPanel.displayResult(listResults);
        }
    }

    /**
     * Initializes all the GUI components.
     */
    private void initComponents() {

        setMinimumSize(new Dimension(400, 500));
        //setPreferredSize(new Dimension(600, 550));
        setPreferredSize( new Dimension(800, 600 ));
        setLayout(new BorderLayout());
                
        menuBar = new guiMenuBar(this);
        this.add(menuBar, BorderLayout.NORTH);

        // -- The StepWizard and its StepPanels --
        stepWizard = new StepWizard();
        documentsStepPanel = new DocumentsStepPanel();
        documentsStepPanel.addStepListener(this);
        stepWizard.addStep(documentsStepPanel);

        canonicizeStepPanel = new CanonicizeStepPanel( new IconGlassPane(), driver );
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
        setGlassPane( canonicizeStepPanel.getGlassPane() );
    }
    
    public void saveFiles(String command){
    	driver.clearAll(); // First, remove all documents that were
        // already loaded 
        for( Document doc : documentsStepPanel.getDocuments() ) {
        	driver.addDocument( doc ); 
        } 
        int returnVal = saveFile.showSaveDialog(null); 
        if (returnVal==JFileChooser.APPROVE_OPTION){ 
        	File file = saveFile.getSelectedFile(); 
        	driver.saveDocuments(command, file);
        	System.out.println("Saving: " + file.getName() + "."); 
        } 
        else
        	System.out.println("User canceled save."); driver.clearAll();         
    }

    /**
     * Load a demo into the documents panel.
     * 
     * @param prob
     *            partial file name of the demo to load
     */
    public void loadDemo(String prob) {
        documentsStepPanel.clearDocuments();
        File file = new File(jgaapConstants.docsDir()+"/aaac/Demos/load" + prob + ".csv");
        addDocumentsToGUI(CSVIO.readCSV(file));
    }
}
