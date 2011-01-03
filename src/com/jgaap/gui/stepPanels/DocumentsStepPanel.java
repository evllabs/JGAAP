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
 * DocumentsStepPanel.java
 */
package com.jgaap.gui.stepPanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.Collection;

import javax.swing.*;
import javax.swing.border.*;

import com.jgaap.generics.Document;
import com.jgaap.gui.generics.StepPanel;

/**
 * This panel represents a single step in a multi-step process, both graphically
 * and logically. It encapsulates all the necessary graphical elements and
 * program logic needed for this step, and provides access methods for necessary
 * data to be passed along to the next step. Specifically, it handles Documents
 * processing for the JGAAP program.
 * 
 * @author Chuck Liddell Created 11/12/08 TODO: Implement saving once the new
 *         package system allows for references to the needed classes TODO: Add
 *         some more commenting
 */
public class DocumentsStepPanel extends StepPanel implements ActionListener,
        FocusListener {

    /**
     *
     */
    private static final long   serialVersionUID = 1L;

    // ---- Global Variables ----
    private JFileChooser        chooseFile;
    private JTable              documentsTable;
    private DocumentsTableModel documentsTableModel;
    private JLabel              labelTitle, labelDocument, labelAuthor,
            errorMsg;
    private JTextField          docTitleField, filePathField, authorNameField;
    private JScrollPane         documentsScrollPane;
    private JRadioButton        radioKnown, radioUnknown;
    private ButtonGroup         buttonGroup;

    private JButton             buttonRemoveDocument, buttonRemoveAllDocuments,
            browseButton, addDocButton;

    /**
     * Default constructor.
     */
    public DocumentsStepPanel() {
        super("Documents");

        documentsTableModel = new DocumentsTableModel();
        documentsTable = new JTable(documentsTableModel){
        	/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			// Overriding this method stretches the table vertically
        	public boolean getScrollableTracksViewportHeight() {
        	    // fetch the table's parent
        	    Container viewport = getParent();

        	    // if the parent is not a viewport, calling this isn't useful
        	    if (!(viewport instanceof JViewport)) {
        	        return false;
        	    }

        	    // return true if the table's preferred height is smaller
        	    // than the viewport height, else false
        	    return getPreferredSize().height < viewport.getHeight();
        	}
        };
        documentsScrollPane = new JScrollPane();
        buttonRemoveDocument = new JButton();
        buttonRemoveAllDocuments = new JButton();

        // add document sub-panel
        labelTitle = new JLabel();
        labelDocument = new JLabel();
        labelAuthor = new JLabel();
        docTitleField = new JTextField();
        filePathField = new JTextField();
        browseButton = new JButton();
        chooseFile = new JFileChooser();
        errorMsg = new JLabel();
        radioKnown = new JRadioButton();
        radioUnknown = new JRadioButton();
        authorNameField = new JTextField();
        addDocButton = new JButton();

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));

        // ======== documentsTable panel ========
        JPanel docsPanel = new JPanel(new BorderLayout());

        // documentsTable.setFillsViewportHeight( true ); these do not exist
        // until we start using jdk 1.5
        // documentsTable.setAutoCreateRowSorter( true ); these do not exist
        // until we start using jdk 1.5 - sortable table by clicking column
        // header
        documentsScrollPane = new JScrollPane(documentsTable);
        docsPanel.add(documentsScrollPane, BorderLayout.CENTER);

        buttonRemoveDocument.setText("Remove Selected Document");
        buttonRemoveDocument.addActionListener(this);
        buttonRemoveAllDocuments.setText("Remove All Documents");
        buttonRemoveAllDocuments.addActionListener(this);
        JPanel docsPanelButtons = new JPanel();
        docsPanelButtons.add(buttonRemoveDocument);
        docsPanelButtons.add(buttonRemoveAllDocuments);

        docsPanel.add(docsPanelButtons, BorderLayout.SOUTH);

        add(docsPanel, BorderLayout.CENTER);

        // ======== add document panel ========
        Border lineBorder = BorderFactory.createLineBorder(Color.black);
        Border addDocBorder = BorderFactory.createTitledBorder(lineBorder,
                "Add Document");
        JPanel addDocPanel = new JPanel(new GridBagLayout());
        addDocPanel.setBorder(addDocBorder);
        GridBagConstraints constraints = new GridBagConstraints();

        labelTitle.setText("Title: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        addDocPanel.add(labelTitle, constraints);

        docTitleField.setText("Enter document title here (optional)...");
        docTitleField.addFocusListener(this);
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 2; // two columns wide
        constraints.fill = GridBagConstraints.BOTH;
        addDocPanel.add(docTitleField, constraints);

        labelDocument.setText("Document: ");
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        addDocPanel.add(labelDocument, constraints);

        filePathField.setText("Enter file name here...");
        filePathField.addFocusListener(this);
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 2; // two columns wide
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.5;
        addDocPanel.add(filePathField, constraints);

        browseButton.setText("Browse...");
        browseButton.addActionListener(this);
        constraints = new GridBagConstraints();
        constraints.gridx = 3;
        constraints.gridy = 1;
        addDocPanel.add(browseButton, constraints);

        errorMsg.setText("Error messages go here.");
        errorMsg.setForeground(Color.red);
        errorMsg.setVisible(false);
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 4;
        addDocPanel.add(errorMsg, constraints);

        labelAuthor.setText("Author: ");
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 3;
        addDocPanel.add(labelAuthor, constraints);

        radioKnown.setText("Known");
        radioKnown.setToolTipText("Author of paper is known (see text box)");
        radioKnown.addActionListener(this);
        radioKnown.setSelected(true);
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 3;
        addDocPanel.add(radioKnown, constraints);

        radioUnknown.setText("Unknown");
        radioUnknown.setToolTipText("Author of paper is not known");
        radioUnknown.addActionListener(this);
        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 3;
        addDocPanel.add(radioUnknown, constraints);

        authorNameField.setText("");
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        addDocPanel.add(authorNameField, constraints);

        addDocButton.setText("Add Document");
        addDocButton.addActionListener(this);
        constraints = new GridBagConstraints();
        constraints.gridx = 4;
        constraints.gridy = 4;
        addDocPanel.add(addDocButton, constraints);

        add(addDocPanel, BorderLayout.SOUTH);

        buttonGroup = new ButtonGroup();
        buttonGroup.add(radioKnown);
        buttonGroup.add(radioUnknown);
    }

    /**
     * Called by various buttons when they are activated.
     * 
     * @param event
     *            The event passed along to us by the event pipeline
     */
    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == radioKnown) {
            authorNameField.setText("");
            authorNameField.setVisible(true);
        }

        if (event.getSource() == radioUnknown) {
            authorNameField.setText("");
            authorNameField.setVisible(false);
        }

        if (event.getSource() == browseButton) {
            int returnVal = chooseFile.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = chooseFile.getSelectedFile();
                filePathField.setText(file.toString());
            }
        }

        if (event.getSource() == addDocButton) {
            // add document to table
            if (docTitleField.getText().equals(
                    "Enter document title here (optional)...")) {
                docTitleField.setText("");
            }
            try {
				addDocument( new Document( filePathField.getText(), authorNameField.getText(), docTitleField.getText() ));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

            // clear the add document panel fields
            docTitleField.setText("Enter document title here (optional)...");
            filePathField.setText("Enter file name here...");
            radioKnown.doClick();

        }

        if (event.getSource() == buttonRemoveDocument) {
            // convert selected rows to the model's coordinate space
            int[] selection = documentsTable.getSelectedRows();
            for (int i = 0; i < selection.length; i++) {
                documentsTableModel.removeRow(selection[i]);
                // documentsTable.convertRowIndexToModel(selection[i]) );
                documentsTable.updateUI();
            }
        }

        if (event.getSource() == buttonRemoveAllDocuments) {
            documentsTableModel.removeAllRows();
            documentsTable.updateUI();
        }

    }
    
    /**
     * Adds a Document to this StepPanel. Documents can be retrieved from this
     * panel by using the 'getDocuments()' method.
     * 
     * @param newDocument the Document to be added
     */
    public void addDocument( Document newDocument ){
    	documentsTableModel.addDocument( newDocument );
    }

//    /**
//     * Adds a Document to this StepPanel. Documents can be retrieved from this
//     * panel by using the 'getDocuments()' method.
//     * 
//     * @param title
//     *            the title of the document
//     * @param filePath
//     *            the file path to the document
//     * @param authorName
//     *            the name of the document's author
//     */
//    public void addDocument( String filePath, String authorName, String title ) {
//    	documentsTableModel.addDocument( new Document( filePath, authorName, title ));
//    }
    
    /**
     * Remove all current documents from this panel.
     */
    public void clearDocuments() {
        documentsTableModel.removeAllRows();
    }

    public void focusGained(FocusEvent event) {
        if (event.getSource() == docTitleField) {
            docTitleField.setText("");
        }

        if (event.getSource() == filePathField) {
            filePathField.setText("");
        }
    }

    public void focusLost(FocusEvent event) {

    }

    /**
     * Return a Vector of String arrays containing all the documents currently
     * referenced by this panel.
     * 
     * @return a Vector of Documents
     */
    public Collection<Document> getDocuments() {
        return documentsTableModel.getDocuments();
    }
}
