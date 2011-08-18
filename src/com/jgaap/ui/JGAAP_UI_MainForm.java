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
package com.jgaap.ui;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JGAAP_UI_MainForm.java
 *
 * Created on Nov 2, 2010, 1:14:56 PM
 */

/**
 *
 * @author Patrick Brennan
 */


//Package Imports
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.tree.TreePath;
import javax.swing.table.DefaultTableModel;
//import java.awt.event.*;

import com.jgaap.generics.*;
import com.jgaap.jgaapConstants;
import com.jgaap.backend.API;
import com.jgaap.backend.CSVIO;
import com.jgaap.backend.Utils;

import java.awt.Color;
import java.io.IOException;

public class JGAAP_UI_MainForm extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;

    JGAAP_UI_NotesDialog NotesPage = new JGAAP_UI_NotesDialog(JGAAP_UI_MainForm.this, true);
    JGAAP_UI_ResultsDialog ResultsPage = new JGAAP_UI_ResultsDialog(JGAAP_UI_MainForm.this, false);
    String[] Notes = new String [5];


    DefaultListModel AnalysisMethodListBox_Model = new DefaultListModel();
    DefaultListModel SelectedAnalysisMethodListBox_Model = new DefaultListModel();
    DefaultListModel CanonicizerListBox_Model = new DefaultListModel();
    DefaultListModel SelectedCanonicizerListBox_Model = new DefaultListModel();
    DefaultListModel EventCullingListBox_Model = new DefaultListModel();
    DefaultListModel SelectedEventCullingListBox_Model = new DefaultListModel();
    DefaultListModel EventSetsListBox_Model = new DefaultListModel();
    DefaultListModel SelectedEventSetsListBox_Model = new DefaultListModel();
    DefaultListModel DistanceFunctionsListBox_Model = new DefaultListModel();

    DefaultComboBoxModel LanguageComboBox_Model = new DefaultComboBoxModel();
    
    DefaultTreeModel KnownAuthorsTree_Model = new DefaultTreeModel(new DefaultMutableTreeNode("Authors"));

    DefaultTableModel UnknownAuthorDocumentsTable_Model = new DefaultTableModel() {
    	private static final long serialVersionUID = 1L;
        @Override
        public boolean isCellEditable(int row, int column) {
            if (column == 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    };
    DefaultTableModel DocumentsTable_Model = new DefaultTableModel() {
    	private static final long serialVersionUID = 1L;
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    API JGAAP_API = API.getInstance();

    JFileChooser  FileChoser;
    String filepath = "..";

    List<Canonicizer> CanonicizerMasterList =  new ArrayList<Canonicizer>();
    List<EventDriver> EventDriverMasterList = new ArrayList<EventDriver>();
    List<AnalysisDriver> AnalysisDriverMasterList = new ArrayList<AnalysisDriver>();
    List<DistanceFunction> DistanceFunctionsMasterList = new ArrayList<DistanceFunction>();
    List<EventCuller> EventCullersMasterList = new ArrayList<EventCuller>();
    List<Language> LanguagesMasterList = new ArrayList<Language>();

    List<EventDriver> SelectedEventDriverList = new ArrayList<EventDriver>();
    List<EventCuller> SelectedEventCullersList = new ArrayList<EventCuller>();
    List<AnalysisDriver> SelectedAnalysisDriverList  = new ArrayList<AnalysisDriver>();
    List<Canonicizer> SelectedCanonicizerList  = new ArrayList<Canonicizer>();
    List<Document> UnknownDocumentList = new ArrayList<Document>();
    List<Document> KnownDocumentList = new ArrayList<Document>();
    List<Document> DocumentList = new ArrayList<Document>();
    List<String> AuthorList = new ArrayList<String>();

    /** Creates new form JGAAP_UI_MainForm */
    public JGAAP_UI_MainForm() {
        initComponents();
        SanatizeMasterLists();
        SetAnalysisMethodList();
        SetDistanceFunctionList();
        SetCanonicizerList();
        SetEventSetList();
        SetEventCullingList();
        SetUnknownDocumentColumns();
        SetKnownDocumentTree();
        SetDocumentColumns();
        SetLanguagesList();
        SelectedEventDriverList.clear();
        SelectedAnalysisDriverList.clear();
        CheckMinimumRequirements();
      
       // DefaultMutableTreeNode top = new DefaultMutableTreeNode("The Java Series");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings({ "deprecation" })
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        helpDialog = new javax.swing.JDialog();
        helpCloseButton = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        JGAAP_TabbedPane = new javax.swing.JTabbedPane();
        JGAAP_DocumentsPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        DocumentsPanel_UnknownAuthorsTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        DocumentsPanel_KnownAuthorsTree = new javax.swing.JTree();
        DocumentsPanel_AddDocumentsButton = new javax.swing.JButton();
        DocumentsPanel_RemoveDocumentsButton = new javax.swing.JButton();
        DocumentsPanel_AddAuthorButton = new javax.swing.JButton();
        DocumentsPanel_EditAuthorButton = new javax.swing.JButton();
        DocumentsPanel_RemoveAuthorButton = new javax.swing.JButton();
        DocumentsPanel_NotesButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        DocumentsPanel_LanguageComboBox = new javax.swing.JComboBox();
        JGAAP_CanonicizerPanel = new javax.swing.JPanel();
        CanonicizersPanel_RemoveCanonicizerButton = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        CanonicizersPanel_NotesButton = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        CanonicizersPanel_DocumentsCanonicizerDescriptionTextBox = new javax.swing.JTextArea();
        CanonicizersPanel_AddCanonicizerButton = new javax.swing.JButton();
        CanonicizersPanel_AddAllCanonicizersButton = new javax.swing.JButton();
        jScrollPane12 = new javax.swing.JScrollPane();
        CanonicizersPanel_CanonicizerListBox = new javax.swing.JList();
        jScrollPane13 = new javax.swing.JScrollPane();
        CanonicizersPanel_SelectedCanonicizerListBox = new javax.swing.JList();
        CanonicizersPanel_RemoveAllCanonicizersButton = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane20 = new javax.swing.JScrollPane();
        CanonicizersPanel_DocumentsTable = new javax.swing.JTable();
        jScrollPane21 = new javax.swing.JScrollPane();
        CanonicizersPanel_DocumentsCurrentCanonicizersTextBox = new javax.swing.JTextArea();
        CanonicizersPanel_SetToDocumentButton = new javax.swing.JButton();
        CanonicizersPanel_SetToDocumentTypeButton = new javax.swing.JButton();
        CanonicizersPanel_SetToAllDocuments = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        JGAAP_EventSetsPanel = new javax.swing.JPanel();
        EventSetsPanel_NotesButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        EventSetsPanel_EventSetListBox = new javax.swing.JList();
        jScrollPane10 = new javax.swing.JScrollPane();
        EventSetsPanel_SelectedEventSetListBox = new javax.swing.JList();
        EventSetsPanel_ParametersPanel = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        EventSetsPanel_EventSetDescriptionTextBox = new javax.swing.JTextArea();
        EventSetsPanel_AddEventSetButton = new javax.swing.JButton();
        EventSetsPanel_RemoveEventSetButton = new javax.swing.JButton();
        EventSetsPanel_AddAllEventSetsButton = new javax.swing.JButton();
        EventSetsPanel_RemoveAllEventSetsButton = new javax.swing.JButton();
        JGAAP_EventCullingPanel = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        EventCullingPanel_NotesButton = new javax.swing.JButton();
        jScrollPane14 = new javax.swing.JScrollPane();
        EventCullingPanel_SelectedEventCullingListBox = new javax.swing.JList();
        EventCullingPanel_AddEventCullingButton = new javax.swing.JButton();
        EventCullingPanel_RemoveEventCullingButton = new javax.swing.JButton();
        EventCullingPanel_AddAllEventCullingButton = new javax.swing.JButton();
        EventCullingPanel_RemoveAllEventCullingButton = new javax.swing.JButton();
        EventCullingPanel_ParametersPanel = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        EventCullingPanel_EventCullingListBox = new javax.swing.JList();
        jScrollPane16 = new javax.swing.JScrollPane();
        EventCullingPanel_EventCullingDescriptionTextbox = new javax.swing.JTextArea();
        jLabel18 = new javax.swing.JLabel();
        JGAAP_AnalysisMethodPanel = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        AnalysisMethodPanel_NotesButton = new javax.swing.JButton();
        jScrollPane17 = new javax.swing.JScrollPane();
        AnalysisMethodPanel_SelectedAnalysisMethodsListBox = new javax.swing.JList();
        AnalysisMethodPanel_AddAnalysisMethodButton = new javax.swing.JButton();
        AnalysisMethodPanel_RemoveAnalysisMethodsButton = new javax.swing.JButton();
        AnalysisMethodPanel_AddAllAnalysisMethodsButton = new javax.swing.JButton();
        AnalysisMethodPanel_RemoveAllAnalysisMethodsButton = new javax.swing.JButton();
        AnalysisMethodPanel_AMParametersPanel = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        AnalysisMethodPanel_AnalysisMethodsListBox = new javax.swing.JList();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane19 = new javax.swing.JScrollPane();
        AnalysisMethodPanel_AnalysisMethodDescriptionTextBox = new javax.swing.JTextArea();
        jScrollPane22 = new javax.swing.JScrollPane();
        AnalysisMethodPanel_DistanceFunctionsListBox = new javax.swing.JList();
        jLabel35 = new javax.swing.JLabel();
        jScrollPane23 = new javax.swing.JScrollPane();
        AnalysisMethodPanel_DistanceFunctionDescriptionTextBox = new javax.swing.JTextArea();
        jLabel36 = new javax.swing.JLabel();
        AnalysisMethodPanel_DFParametersPanel = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        JGAAP_ReviewPanel = new javax.swing.JPanel();
        ReviewPanel_ProcessButton = new javax.swing.JButton();
        ReviewPanel_DocumentsLabel = new javax.swing.JLabel();
        jScrollPane24 = new javax.swing.JScrollPane();
        ReviewPanel_DocumentsTable = new javax.swing.JTable();
        ReviewPanel_SelectedEventSetLabel = new javax.swing.JLabel();
        ReviewPanel_SelectedEventCullingLabel = new javax.swing.JLabel();
        ReviewPanel_SelectedAnalysisMethodsLabel = new javax.swing.JLabel();
        jScrollPane25 = new javax.swing.JScrollPane();
        ReviewPanel_SelectedEventSetListBox = new javax.swing.JList();
        jScrollPane26 = new javax.swing.JScrollPane();
        ReviewPanel_SelectedEventCullingListBox = new javax.swing.JList();
        jScrollPane27 = new javax.swing.JScrollPane();
        ReviewPanel_SelectedAnalysisMethodsListBox = new javax.swing.JList();
        Next_Button = new javax.swing.JButton();
        Review_Button = new javax.swing.JButton();
        JGAAP_MenuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        BatchSaveMenuItem = new javax.swing.JMenuItem();
        BatchLoadMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        ProblemAMenuItem = new javax.swing.JMenuItem();
        ProblemBMenuItem = new javax.swing.JMenuItem();
        ProblemCMenuItem = new javax.swing.JMenuItem();
        ProblemDMenuItem = new javax.swing.JMenuItem();
        ProblemEMenuItem = new javax.swing.JMenuItem();
        ProblemFMenuItem = new javax.swing.JMenuItem();
        ProblemGMenuItem = new javax.swing.JMenuItem();
        ProblemHMenuItem = new javax.swing.JMenuItem();
        ProblemIMenuItem = new javax.swing.JMenuItem();
        ProblemJMenuItem = new javax.swing.JMenuItem();
        ProblemKMenuItem = new javax.swing.JMenuItem();
        ProblemLMenuItem = new javax.swing.JMenuItem();
        ProblemMMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        helpDialog.setTitle("About");
        helpDialog.setMinimumSize(new java.awt.Dimension(520, 300));
        helpDialog.setResizable(false);

        helpCloseButton.setText("close");
        helpCloseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpCloseButtonActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Lucida Grande", 0, 24));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("JGAAP 5.0");

        jLabel12.setText("<html> JGAAP, the Java Graphical Authorship Attribution Program, <br/>is an opensource author attribution / text classification tool <br/>Developed by the EVL lab (Evaluating Variation in Language Labratory) <br/> Released by Patrick Juola under the GPL v3.0");

        jLabel13.setText("Â©2011 EVL lab");

        jLabel23.setForeground(new java.awt.Color(0, 0, 255));
        jLabel23.setText("http://evllabs.com");
        jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel23MouseClicked(evt);
            }
        });

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jgaap/ui/resources/jgaap_icon.png"))); // NOI18N

        jLabel25.setForeground(new java.awt.Color(0, 0, 255));
        jLabel25.setText("http://jgaap.com");
        jLabel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel25MouseClicked(evt);
            }
        });

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jgaap/ui/resources/EVLlab_icon.png"))); // NOI18N

        javax.swing.GroupLayout helpDialogLayout = new javax.swing.GroupLayout(helpDialog.getContentPane());
        helpDialog.getContentPane().setLayout(helpDialogLayout);
        helpDialogLayout.setHorizontalGroup(
            helpDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(helpDialogLayout.createSequentialGroup()
                .addGroup(helpDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(helpDialogLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(helpDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(helpDialogLayout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel26))
                            .addComponent(helpCloseButton, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(helpDialogLayout.createSequentialGroup()
                        .addGap(199, 199, 199)
                        .addGroup(helpDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addGroup(helpDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel13)
                                .addComponent(jLabel25))))
                    .addGroup(helpDialogLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        helpDialogLayout.setVerticalGroup(
            helpDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(helpDialogLayout.createSequentialGroup()
                .addGroup(helpDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(helpDialogLayout.createSequentialGroup()
                        .addGroup(helpDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addGroup(helpDialogLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, helpDialogLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addGap(44, 44, 44)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(helpDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(helpCloseButton)
                    .addComponent(jLabel13))
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JGAAP");

        JGAAP_TabbedPane.setName("JGAAP_TabbedPane"); // NOI18N

        jLabel1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
        jLabel1.setText("Unknown Authors");

        jLabel2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
        jLabel2.setText("Known Authors");

        DocumentsPanel_UnknownAuthorsTable.setModel(UnknownAuthorDocumentsTable_Model);
        DocumentsPanel_UnknownAuthorsTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        DocumentsPanel_UnknownAuthorsTable.setColumnSelectionAllowed(true);
        DocumentsPanel_UnknownAuthorsTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(DocumentsPanel_UnknownAuthorsTable);
        DocumentsPanel_UnknownAuthorsTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        DocumentsPanel_KnownAuthorsTree.setModel(KnownAuthorsTree_Model);
        DocumentsPanel_KnownAuthorsTree.setShowsRootHandles(true);
        jScrollPane2.setViewportView(DocumentsPanel_KnownAuthorsTree);

        DocumentsPanel_AddDocumentsButton.setText("Add Document");
        DocumentsPanel_AddDocumentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DocumentsPanel_AddDocumentsButtonActionPerformed(evt);
            }
        });

        DocumentsPanel_RemoveDocumentsButton.setText("Remove Document");
        DocumentsPanel_RemoveDocumentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DocumentsPanel_RemoveDocumentsButtonActionPerformed(evt);
            }
        });

        DocumentsPanel_AddAuthorButton.setLabel("Add Author");
        DocumentsPanel_AddAuthorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DocumentsPanel_AddAuthorButtonActionPerformed(evt);
            }
        });

        DocumentsPanel_EditAuthorButton.setLabel("Edit Author");
        DocumentsPanel_EditAuthorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DocumentsPanel_EditAuthorButtonActionPerformed(evt);
            }
        });

        DocumentsPanel_RemoveAuthorButton.setLabel("Remove Author");
        DocumentsPanel_RemoveAuthorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DocumentsPanel_RemoveAuthorButtonActionPerformed(evt);
            }
        });

        DocumentsPanel_NotesButton.setLabel("Notes");
        DocumentsPanel_NotesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DocumentsPanel_NotesButtonActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
        jLabel10.setText("Language");

        DocumentsPanel_LanguageComboBox.setModel(LanguageComboBox_Model);
        DocumentsPanel_LanguageComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DocumentsPanel_LanguageComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JGAAP_DocumentsPanelLayout = new javax.swing.GroupLayout(JGAAP_DocumentsPanel);
        JGAAP_DocumentsPanel.setLayout(JGAAP_DocumentsPanelLayout);
        JGAAP_DocumentsPanelLayout.setHorizontalGroup(
            JGAAP_DocumentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JGAAP_DocumentsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JGAAP_DocumentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
                    .addGroup(JGAAP_DocumentsPanelLayout.createSequentialGroup()
                        .addGroup(JGAAP_DocumentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(DocumentsPanel_LanguageComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 656, Short.MAX_VALUE)
                        .addComponent(DocumentsPanel_NotesButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JGAAP_DocumentsPanelLayout.createSequentialGroup()
                        .addGroup(JGAAP_DocumentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(JGAAP_DocumentsPanelLayout.createSequentialGroup()
                                .addComponent(DocumentsPanel_AddDocumentsButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DocumentsPanel_RemoveDocumentsButton))
                            .addComponent(jLabel2))
                        .addGap(512, 512, 512))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JGAAP_DocumentsPanelLayout.createSequentialGroup()
                        .addComponent(DocumentsPanel_AddAuthorButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DocumentsPanel_EditAuthorButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DocumentsPanel_RemoveAuthorButton)))
                .addContainerGap())
        );
        JGAAP_DocumentsPanelLayout.setVerticalGroup(
            JGAAP_DocumentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JGAAP_DocumentsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JGAAP_DocumentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DocumentsPanel_NotesButton)
                    .addGroup(JGAAP_DocumentsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DocumentsPanel_LanguageComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JGAAP_DocumentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DocumentsPanel_RemoveDocumentsButton)
                    .addComponent(DocumentsPanel_AddDocumentsButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JGAAP_DocumentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DocumentsPanel_RemoveAuthorButton)
                    .addComponent(DocumentsPanel_EditAuthorButton)
                    .addComponent(DocumentsPanel_AddAuthorButton))
                .addContainerGap())
        );

        JGAAP_TabbedPane.addTab("Documents", JGAAP_DocumentsPanel);

        CanonicizersPanel_RemoveCanonicizerButton.setText("\u2190");
        CanonicizersPanel_RemoveCanonicizerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CanonicizersPanel_RemoveCanonicizerButtonActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        jLabel27.setText("Documents");

        CanonicizersPanel_NotesButton.setLabel("Notes");
        CanonicizersPanel_NotesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CanonicizersPanel_NotesButtonActionPerformed(evt);
            }
        });

        CanonicizersPanel_DocumentsCanonicizerDescriptionTextBox.setColumns(20);
        CanonicizersPanel_DocumentsCanonicizerDescriptionTextBox.setLineWrap(true);
        CanonicizersPanel_DocumentsCanonicizerDescriptionTextBox.setRows(5);
        CanonicizersPanel_DocumentsCanonicizerDescriptionTextBox.setWrapStyleWord(true);
        jScrollPane11.setViewportView(CanonicizersPanel_DocumentsCanonicizerDescriptionTextBox);

        CanonicizersPanel_AddCanonicizerButton.setText("\u2192");
        CanonicizersPanel_AddCanonicizerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CanonicizersPanel_AddCanonicizerButtonActionPerformed(evt);
            }
        });

        CanonicizersPanel_AddAllCanonicizersButton.setText("All");
        CanonicizersPanel_AddAllCanonicizersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CanonicizersPanel_AddAllCanonicizersButtonActionPerformed(evt);
            }
        });

        CanonicizersPanel_CanonicizerListBox.setModel(CanonicizerListBox_Model);
        CanonicizersPanel_CanonicizerListBox.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        CanonicizersPanel_CanonicizerListBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CanonicizersPanel_CanonicizerListBoxMouseClicked(evt);
            }
        });
        CanonicizersPanel_CanonicizerListBox.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                CanonicizersPanel_CanonicizerListBoxMouseMoved(evt);
            }
        });
        jScrollPane12.setViewportView(CanonicizersPanel_CanonicizerListBox);

        CanonicizersPanel_SelectedCanonicizerListBox.setModel(SelectedCanonicizerListBox_Model);
        CanonicizersPanel_SelectedCanonicizerListBox.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        CanonicizersPanel_SelectedCanonicizerListBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CanonicizersPanel_SelectedCanonicizerListBoxMouseClicked(evt);
            }
        });
        CanonicizersPanel_SelectedCanonicizerListBox.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                CanonicizersPanel_SelectedCanonicizerListBoxMouseMoved(evt);
            }
        });
        jScrollPane13.setViewportView(CanonicizersPanel_SelectedCanonicizerListBox);

        CanonicizersPanel_RemoveAllCanonicizersButton.setText("Clear");
        CanonicizersPanel_RemoveAllCanonicizersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CanonicizersPanel_RemoveAllCanonicizersButtonActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
        jLabel31.setText("Document's Current Canonicizers");

        CanonicizersPanel_DocumentsTable.setModel(DocumentsTable_Model);
        jScrollPane20.setViewportView(CanonicizersPanel_DocumentsTable);

        CanonicizersPanel_DocumentsCurrentCanonicizersTextBox.setColumns(20);
        CanonicizersPanel_DocumentsCurrentCanonicizersTextBox.setLineWrap(true);
        CanonicizersPanel_DocumentsCurrentCanonicizersTextBox.setRows(5);
        CanonicizersPanel_DocumentsCurrentCanonicizersTextBox.setWrapStyleWord(true);
        jScrollPane21.setViewportView(CanonicizersPanel_DocumentsCurrentCanonicizersTextBox);

        CanonicizersPanel_SetToDocumentButton.setText("Set to Doc");
        CanonicizersPanel_SetToDocumentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CanonicizersPanel_SetToDocumentButtonActionPerformed(evt);
            }
        });

        CanonicizersPanel_SetToDocumentTypeButton.setText("Set to Doc Type");
        CanonicizersPanel_SetToDocumentTypeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CanonicizersPanel_SetToDocumentTypeButtonActionPerformed(evt);
            }
        });

        CanonicizersPanel_SetToAllDocuments.setText("Set to All Docs");
        CanonicizersPanel_SetToAllDocuments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CanonicizersPanel_SetToAllDocumentsActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
        jLabel29.setText("Selected");

        jLabel30.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
        jLabel30.setText("Canonicizers");

        jLabel32.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
        jLabel32.setText("Canonicizer Description");

        jLabel33.setText("Note: These buttons are used to add");

        jLabel34.setText("selected canonicizers to documents.");

        javax.swing.GroupLayout JGAAP_CanonicizerPanelLayout = new javax.swing.GroupLayout(JGAAP_CanonicizerPanel);
        JGAAP_CanonicizerPanel.setLayout(JGAAP_CanonicizerPanelLayout);
        JGAAP_CanonicizerPanelLayout.setHorizontalGroup(
            JGAAP_CanonicizerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JGAAP_CanonicizerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JGAAP_CanonicizerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JGAAP_CanonicizerPanelLayout.createSequentialGroup()
                        .addGroup(JGAAP_CanonicizerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JGAAP_CanonicizerPanelLayout.createSequentialGroup()
                                .addGroup(JGAAP_CanonicizerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel30)
                                    .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(JGAAP_CanonicizerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(CanonicizersPanel_AddCanonicizerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(CanonicizersPanel_RemoveCanonicizerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(CanonicizersPanel_AddAllCanonicizersButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(CanonicizersPanel_RemoveAllCanonicizersButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addComponent(jLabel33)
                            .addComponent(jLabel34))
                        .addGroup(JGAAP_CanonicizerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CanonicizersPanel_SetToDocumentTypeButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                            .addComponent(jLabel29)
                            .addComponent(CanonicizersPanel_SetToDocumentButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                            .addComponent(jScrollPane13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                            .addComponent(CanonicizersPanel_SetToAllDocuments, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(JGAAP_CanonicizerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JGAAP_CanonicizerPanelLayout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 255, Short.MAX_VALUE)
                                .addComponent(CanonicizersPanel_NotesButton))))
                    .addGroup(JGAAP_CanonicizerPanelLayout.createSequentialGroup()
                        .addGroup(JGAAP_CanonicizerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane21, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JGAAP_CanonicizerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32)
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE))))
                .addContainerGap())
        );
        JGAAP_CanonicizerPanelLayout.setVerticalGroup(
            JGAAP_CanonicizerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JGAAP_CanonicizerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JGAAP_CanonicizerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JGAAP_CanonicizerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel27)
                        .addComponent(jLabel30)
                        .addComponent(jLabel29))
                    .addGroup(JGAAP_CanonicizerPanelLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(CanonicizersPanel_NotesButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JGAAP_CanonicizerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                    .addGroup(JGAAP_CanonicizerPanelLayout.createSequentialGroup()
                        .addGroup(JGAAP_CanonicizerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JGAAP_CanonicizerPanelLayout.createSequentialGroup()
                                .addComponent(CanonicizersPanel_AddCanonicizerButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CanonicizersPanel_RemoveCanonicizerButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CanonicizersPanel_AddAllCanonicizersButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CanonicizersPanel_RemoveAllCanonicizersButton))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JGAAP_CanonicizerPanelLayout.createSequentialGroup()
                                .addGroup(JGAAP_CanonicizerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                                    .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CanonicizersPanel_SetToDocumentButton)))
                        .addGap(6, 6, 6)
                        .addGroup(JGAAP_CanonicizerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(JGAAP_CanonicizerPanelLayout.createSequentialGroup()
                                .addComponent(jLabel33)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel34)
                                .addGap(18, 18, 18))
                            .addGroup(JGAAP_CanonicizerPanelLayout.createSequentialGroup()
                                .addComponent(CanonicizersPanel_SetToDocumentTypeButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(CanonicizersPanel_SetToAllDocuments)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JGAAP_CanonicizerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JGAAP_CanonicizerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        JGAAP_TabbedPane.addTab("Canonicizers", JGAAP_CanonicizerPanel);

        EventSetsPanel_NotesButton.setLabel("Notes");
        EventSetsPanel_NotesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EventSetsPanel_NotesButtonActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        jLabel6.setText("Event Drivers");

        jLabel7.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
        jLabel7.setText("Parameters");

        jLabel8.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
        jLabel8.setText("Event Driver Description");

        jLabel9.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
        jLabel9.setText("Selected");

        EventSetsPanel_EventSetListBox.setModel(EventSetsListBox_Model);
        EventSetsPanel_EventSetListBox.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        EventSetsPanel_EventSetListBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EventSetsPanel_EventSetListBoxMouseClicked(evt);
            }
        });
        EventSetsPanel_EventSetListBox.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                EventSetsPanel_EventSetListBoxMouseMoved(evt);
            }
        });
        jScrollPane9.setViewportView(EventSetsPanel_EventSetListBox);

        EventSetsPanel_SelectedEventSetListBox.setModel(SelectedEventSetsListBox_Model);
        EventSetsPanel_SelectedEventSetListBox.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        EventSetsPanel_SelectedEventSetListBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EventSetsPanel_SelectedEventSetListBoxMouseClicked(evt);
            }
        });
        EventSetsPanel_SelectedEventSetListBox.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                EventSetsPanel_SelectedEventSetListBoxMouseMoved(evt);
            }
        });
        jScrollPane10.setViewportView(EventSetsPanel_SelectedEventSetListBox);

        EventSetsPanel_ParametersPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout EventSetsPanel_ParametersPanelLayout = new javax.swing.GroupLayout(EventSetsPanel_ParametersPanel);
        EventSetsPanel_ParametersPanel.setLayout(EventSetsPanel_ParametersPanelLayout);
        EventSetsPanel_ParametersPanelLayout.setHorizontalGroup(
            EventSetsPanel_ParametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 344, Short.MAX_VALUE)
        );
        EventSetsPanel_ParametersPanelLayout.setVerticalGroup(
            EventSetsPanel_ParametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        EventSetsPanel_EventSetDescriptionTextBox.setColumns(20);
        EventSetsPanel_EventSetDescriptionTextBox.setLineWrap(true);
        EventSetsPanel_EventSetDescriptionTextBox.setRows(5);
        EventSetsPanel_EventSetDescriptionTextBox.setWrapStyleWord(true);
        jScrollPane6.setViewportView(EventSetsPanel_EventSetDescriptionTextBox);

        EventSetsPanel_AddEventSetButton.setText("\u2192");
        EventSetsPanel_AddEventSetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EventSetsPanel_AddEventSetButtonActionPerformed(evt);
            }
        });

        EventSetsPanel_RemoveEventSetButton.setText("\u2190");
        EventSetsPanel_RemoveEventSetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EventSetsPanel_RemoveEventSetButtonActionPerformed(evt);
            }
        });

        EventSetsPanel_AddAllEventSetsButton.setText("All");
        EventSetsPanel_AddAllEventSetsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EventSetsPanel_AddAllEventSetsButtonActionPerformed(evt);
            }
        });

        EventSetsPanel_RemoveAllEventSetsButton.setText("Clear");
        EventSetsPanel_RemoveAllEventSetsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EventSetsPanel_RemoveAllEventSetsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JGAAP_EventSetsPanelLayout = new javax.swing.GroupLayout(JGAAP_EventSetsPanel);
        JGAAP_EventSetsPanel.setLayout(JGAAP_EventSetsPanelLayout);
        JGAAP_EventSetsPanelLayout.setHorizontalGroup(
            JGAAP_EventSetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JGAAP_EventSetsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JGAAP_EventSetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JGAAP_EventSetsPanelLayout.createSequentialGroup()
                        .addGroup(JGAAP_EventSetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JGAAP_EventSetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(EventSetsPanel_RemoveEventSetButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(EventSetsPanel_AddAllEventSetsButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(EventSetsPanel_AddEventSetButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(EventSetsPanel_RemoveAllEventSetsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JGAAP_EventSetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JGAAP_EventSetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JGAAP_EventSetsPanelLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 163, Short.MAX_VALUE)
                                .addComponent(EventSetsPanel_NotesButton))
                            .addComponent(EventSetsPanel_ParametersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        JGAAP_EventSetsPanelLayout.setVerticalGroup(
            JGAAP_EventSetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JGAAP_EventSetsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JGAAP_EventSetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JGAAP_EventSetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel9))
                    .addGroup(JGAAP_EventSetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(EventSetsPanel_NotesButton)
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JGAAP_EventSetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EventSetsPanel_ParametersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                    .addGroup(JGAAP_EventSetsPanelLayout.createSequentialGroup()
                        .addComponent(EventSetsPanel_AddEventSetButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EventSetsPanel_RemoveEventSetButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EventSetsPanel_AddAllEventSetsButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EventSetsPanel_RemoveAllEventSetsButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        JGAAP_TabbedPane.addTab("Event Drivers", JGAAP_EventSetsPanel);

        jLabel15.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        jLabel15.setText("Event Culling");

        jLabel16.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
        jLabel16.setText("Parameters");

        jLabel17.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
        jLabel17.setText("Selected");

        EventCullingPanel_NotesButton.setLabel("Notes");
        EventCullingPanel_NotesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EventCullingPanel_NotesButtonActionPerformed(evt);
            }
        });

        EventCullingPanel_SelectedEventCullingListBox.setModel(SelectedEventCullingListBox_Model);
        EventCullingPanel_SelectedEventCullingListBox.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        EventCullingPanel_SelectedEventCullingListBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EventCullingPanel_SelectedEventCullingListBoxMouseClicked(evt);
            }
        });
        EventCullingPanel_SelectedEventCullingListBox.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                EventCullingPanel_SelectedEventCullingListBoxMouseMoved(evt);
            }
        });
        jScrollPane14.setViewportView(EventCullingPanel_SelectedEventCullingListBox);

        EventCullingPanel_AddEventCullingButton.setText("\u2192");
        EventCullingPanel_AddEventCullingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EventCullingPanel_AddEventCullingButtonActionPerformed(evt);
            }
        });

        EventCullingPanel_RemoveEventCullingButton.setText("\u2190");
        EventCullingPanel_RemoveEventCullingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EventCullingPanel_RemoveEventCullingButtonActionPerformed(evt);
            }
        });

        EventCullingPanel_AddAllEventCullingButton.setText("All");
        EventCullingPanel_AddAllEventCullingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EventCullingPanel_AddAllEventCullingButtonActionPerformed(evt);
            }
        });

        EventCullingPanel_RemoveAllEventCullingButton.setText("Clear");
        EventCullingPanel_RemoveAllEventCullingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EventCullingPanel_RemoveAllEventCullingButtonActionPerformed(evt);
            }
        });

        EventCullingPanel_ParametersPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout EventCullingPanel_ParametersPanelLayout = new javax.swing.GroupLayout(EventCullingPanel_ParametersPanel);
        EventCullingPanel_ParametersPanel.setLayout(EventCullingPanel_ParametersPanelLayout);
        EventCullingPanel_ParametersPanelLayout.setHorizontalGroup(
            EventCullingPanel_ParametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 343, Short.MAX_VALUE)
        );
        EventCullingPanel_ParametersPanelLayout.setVerticalGroup(
            EventCullingPanel_ParametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        EventCullingPanel_EventCullingListBox.setModel(EventCullingListBox_Model);
        EventCullingPanel_EventCullingListBox.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        EventCullingPanel_EventCullingListBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EventCullingPanel_EventCullingListBoxMouseClicked(evt);
            }
        });
        EventCullingPanel_EventCullingListBox.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                EventCullingPanel_EventCullingListBoxMouseMoved(evt);
            }
        });
        jScrollPane15.setViewportView(EventCullingPanel_EventCullingListBox);

        EventCullingPanel_EventCullingDescriptionTextbox.setColumns(20);
        EventCullingPanel_EventCullingDescriptionTextbox.setLineWrap(true);
        EventCullingPanel_EventCullingDescriptionTextbox.setRows(5);
        EventCullingPanel_EventCullingDescriptionTextbox.setWrapStyleWord(true);
        jScrollPane16.setViewportView(EventCullingPanel_EventCullingDescriptionTextbox);

        jLabel18.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
        jLabel18.setText("Event Culling Description");

        javax.swing.GroupLayout JGAAP_EventCullingPanelLayout = new javax.swing.GroupLayout(JGAAP_EventCullingPanel);
        JGAAP_EventCullingPanel.setLayout(JGAAP_EventCullingPanelLayout);
        JGAAP_EventCullingPanelLayout.setHorizontalGroup(
            JGAAP_EventCullingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JGAAP_EventCullingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JGAAP_EventCullingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JGAAP_EventCullingPanelLayout.createSequentialGroup()
                        .addGroup(JGAAP_EventCullingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JGAAP_EventCullingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(EventCullingPanel_RemoveEventCullingButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(EventCullingPanel_AddAllEventCullingButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(EventCullingPanel_AddEventCullingButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(EventCullingPanel_RemoveAllEventCullingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JGAAP_EventCullingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JGAAP_EventCullingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(JGAAP_EventCullingPanelLayout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 162, Short.MAX_VALUE)
                                .addComponent(EventCullingPanel_NotesButton))
                            .addComponent(EventCullingPanel_ParametersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        JGAAP_EventCullingPanelLayout.setVerticalGroup(
            JGAAP_EventCullingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JGAAP_EventCullingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JGAAP_EventCullingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JGAAP_EventCullingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(jLabel17)
                        .addComponent(jLabel16))
                    .addComponent(EventCullingPanel_NotesButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JGAAP_EventCullingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                    .addComponent(EventCullingPanel_ParametersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                    .addGroup(JGAAP_EventCullingPanelLayout.createSequentialGroup()
                        .addComponent(EventCullingPanel_AddEventCullingButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EventCullingPanel_RemoveEventCullingButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EventCullingPanel_AddAllEventCullingButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EventCullingPanel_RemoveAllEventCullingButton)
                        .addGap(107, 107, 107)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        JGAAP_TabbedPane.addTab("Event Culling", JGAAP_EventCullingPanel);

        jLabel20.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        jLabel20.setText("Analysis Methods");

        jLabel21.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
        jLabel21.setText("AM Parameters");

        jLabel22.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
        jLabel22.setText("Selected");

        AnalysisMethodPanel_NotesButton.setLabel("Notes");
        AnalysisMethodPanel_NotesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnalysisMethodPanel_NotesButtonActionPerformed(evt);
            }
        });

        AnalysisMethodPanel_SelectedAnalysisMethodsListBox.setModel(SelectedAnalysisMethodListBox_Model);
        AnalysisMethodPanel_SelectedAnalysisMethodsListBox.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        AnalysisMethodPanel_SelectedAnalysisMethodsListBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AnalysisMethodPanel_SelectedAnalysisMethodsListBoxMouseClicked(evt);
            }
        });
        AnalysisMethodPanel_SelectedAnalysisMethodsListBox.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                AnalysisMethodPanel_SelectedAnalysisMethodsListBoxMouseMoved(evt);
            }
        });
        jScrollPane17.setViewportView(AnalysisMethodPanel_SelectedAnalysisMethodsListBox);

        AnalysisMethodPanel_AddAnalysisMethodButton.setText("\u2192");
        AnalysisMethodPanel_AddAnalysisMethodButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnalysisMethodPanel_AddAnalysisMethodButtonActionPerformed(evt);
            }
        });

        AnalysisMethodPanel_RemoveAnalysisMethodsButton.setText("\u2190");
        AnalysisMethodPanel_RemoveAnalysisMethodsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnalysisMethodPanel_RemoveAnalysisMethodsButtonActionPerformed(evt);
            }
        });

        AnalysisMethodPanel_AddAllAnalysisMethodsButton.setText("All");
        AnalysisMethodPanel_AddAllAnalysisMethodsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnalysisMethodPanel_AddAllAnalysisMethodsButtonActionPerformed(evt);
            }
        });

        AnalysisMethodPanel_RemoveAllAnalysisMethodsButton.setText("Clear");
        AnalysisMethodPanel_RemoveAllAnalysisMethodsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnalysisMethodPanel_RemoveAllAnalysisMethodsButtonActionPerformed(evt);
            }
        });

        AnalysisMethodPanel_AMParametersPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout AnalysisMethodPanel_AMParametersPanelLayout = new javax.swing.GroupLayout(AnalysisMethodPanel_AMParametersPanel);
        AnalysisMethodPanel_AMParametersPanel.setLayout(AnalysisMethodPanel_AMParametersPanelLayout);
        AnalysisMethodPanel_AMParametersPanelLayout.setHorizontalGroup(
            AnalysisMethodPanel_AMParametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 355, Short.MAX_VALUE)
        );
        AnalysisMethodPanel_AMParametersPanelLayout.setVerticalGroup(
            AnalysisMethodPanel_AMParametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 125, Short.MAX_VALUE)
        );

        AnalysisMethodPanel_AnalysisMethodsListBox.setModel(AnalysisMethodListBox_Model);
        AnalysisMethodPanel_AnalysisMethodsListBox.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        AnalysisMethodPanel_AnalysisMethodsListBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AnalysisMethodPanel_AnalysisMethodsListBoxMouseClicked(evt);
            }
        });
        AnalysisMethodPanel_AnalysisMethodsListBox.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                AnalysisMethodPanel_AnalysisMethodsListBoxMouseMoved(evt);
            }
        });
        jScrollPane18.setViewportView(AnalysisMethodPanel_AnalysisMethodsListBox);

        jLabel28.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        jLabel28.setText("Distance Function Description");

        AnalysisMethodPanel_AnalysisMethodDescriptionTextBox.setColumns(20);
        AnalysisMethodPanel_AnalysisMethodDescriptionTextBox.setLineWrap(true);
        AnalysisMethodPanel_AnalysisMethodDescriptionTextBox.setRows(5);
        AnalysisMethodPanel_AnalysisMethodDescriptionTextBox.setWrapStyleWord(true);
        jScrollPane19.setViewportView(AnalysisMethodPanel_AnalysisMethodDescriptionTextBox);

        AnalysisMethodPanel_DistanceFunctionsListBox.setModel(DistanceFunctionsListBox_Model);
        AnalysisMethodPanel_DistanceFunctionsListBox.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        AnalysisMethodPanel_DistanceFunctionsListBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AnalysisMethodPanel_DistanceFunctionsListBoxMouseClicked(evt);
            }
        });
        AnalysisMethodPanel_DistanceFunctionsListBox.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                AnalysisMethodPanel_DistanceFunctionsListBoxMouseMoved(evt);
            }
        });
        jScrollPane22.setViewportView(AnalysisMethodPanel_DistanceFunctionsListBox);

        jLabel35.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        jLabel35.setText("Distance Functions");

        AnalysisMethodPanel_DistanceFunctionDescriptionTextBox.setColumns(20);
        AnalysisMethodPanel_DistanceFunctionDescriptionTextBox.setLineWrap(true);
        AnalysisMethodPanel_DistanceFunctionDescriptionTextBox.setRows(5);
        AnalysisMethodPanel_DistanceFunctionDescriptionTextBox.setWrapStyleWord(true);
        jScrollPane23.setViewportView(AnalysisMethodPanel_DistanceFunctionDescriptionTextBox);

        jLabel36.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        jLabel36.setText("Analysis Method Description");

        AnalysisMethodPanel_DFParametersPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout AnalysisMethodPanel_DFParametersPanelLayout = new javax.swing.GroupLayout(AnalysisMethodPanel_DFParametersPanel);
        AnalysisMethodPanel_DFParametersPanel.setLayout(AnalysisMethodPanel_DFParametersPanelLayout);
        AnalysisMethodPanel_DFParametersPanelLayout.setHorizontalGroup(
            AnalysisMethodPanel_DFParametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 355, Short.MAX_VALUE)
        );
        AnalysisMethodPanel_DFParametersPanelLayout.setVerticalGroup(
            AnalysisMethodPanel_DFParametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 128, Short.MAX_VALUE)
        );

        jLabel37.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
        jLabel37.setText("DF Parameters");

        javax.swing.GroupLayout JGAAP_AnalysisMethodPanelLayout = new javax.swing.GroupLayout(JGAAP_AnalysisMethodPanel);
        JGAAP_AnalysisMethodPanel.setLayout(JGAAP_AnalysisMethodPanelLayout);
        JGAAP_AnalysisMethodPanelLayout.setHorizontalGroup(
            JGAAP_AnalysisMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JGAAP_AnalysisMethodPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JGAAP_AnalysisMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(JGAAP_AnalysisMethodPanelLayout.createSequentialGroup()
                        .addGroup(JGAAP_AnalysisMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JGAAP_AnalysisMethodPanelLayout.createSequentialGroup()
                                .addGroup(JGAAP_AnalysisMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                                    .addComponent(jScrollPane18, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(JGAAP_AnalysisMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(AnalysisMethodPanel_RemoveAnalysisMethodsButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(AnalysisMethodPanel_AddAllAnalysisMethodsButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(AnalysisMethodPanel_AddAnalysisMethodButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(AnalysisMethodPanel_RemoveAllAnalysisMethodsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JGAAP_AnalysisMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JGAAP_AnalysisMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AnalysisMethodPanel_DFParametersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(AnalysisMethodPanel_AMParametersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(JGAAP_AnalysisMethodPanelLayout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                                .addComponent(AnalysisMethodPanel_NotesButton))
                            .addComponent(jLabel37)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JGAAP_AnalysisMethodPanelLayout.createSequentialGroup()
                        .addGroup(JGAAP_AnalysisMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JGAAP_AnalysisMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(jScrollPane23, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE))))
                .addContainerGap())
        );
        JGAAP_AnalysisMethodPanelLayout.setVerticalGroup(
            JGAAP_AnalysisMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JGAAP_AnalysisMethodPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JGAAP_AnalysisMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22)
                    .addComponent(AnalysisMethodPanel_NotesButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JGAAP_AnalysisMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                    .addGroup(JGAAP_AnalysisMethodPanelLayout.createSequentialGroup()
                        .addComponent(AnalysisMethodPanel_AMParametersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AnalysisMethodPanel_DFParametersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(JGAAP_AnalysisMethodPanelLayout.createSequentialGroup()
                        .addGroup(JGAAP_AnalysisMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JGAAP_AnalysisMethodPanelLayout.createSequentialGroup()
                                .addComponent(AnalysisMethodPanel_AddAnalysisMethodButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(AnalysisMethodPanel_RemoveAnalysisMethodsButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(AnalysisMethodPanel_AddAllAnalysisMethodsButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(AnalysisMethodPanel_RemoveAllAnalysisMethodsButton))
                            .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane22, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JGAAP_AnalysisMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel36))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JGAAP_AnalysisMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        JGAAP_TabbedPane.addTab("Analysis Methods", JGAAP_AnalysisMethodPanel);

        ReviewPanel_ProcessButton.setLabel("Process");
        ReviewPanel_ProcessButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReviewPanel_ProcessButtonActionPerformed(evt);
            }
        });

        ReviewPanel_DocumentsLabel.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        ReviewPanel_DocumentsLabel.setText("Documents");
        ReviewPanel_DocumentsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ReviewPanel_DocumentsLabelMouseClicked(evt);
            }
        });

        ReviewPanel_DocumentsTable.setModel(DocumentsTable_Model);
        ReviewPanel_DocumentsTable.setEnabled(false);
        ReviewPanel_DocumentsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ReviewPanel_DocumentsTableMouseClicked(evt);
            }
        });
        jScrollPane24.setViewportView(ReviewPanel_DocumentsTable);

        ReviewPanel_SelectedEventSetLabel.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        ReviewPanel_SelectedEventSetLabel.setText("Event Driver");
        ReviewPanel_SelectedEventSetLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ReviewPanel_SelectedEventSetLabelMouseClicked(evt);
            }
        });

        ReviewPanel_SelectedEventCullingLabel.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        ReviewPanel_SelectedEventCullingLabel.setText("Event Culling");
        ReviewPanel_SelectedEventCullingLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ReviewPanel_SelectedEventCullingLabelMouseClicked(evt);
            }
        });

        ReviewPanel_SelectedAnalysisMethodsLabel.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        ReviewPanel_SelectedAnalysisMethodsLabel.setText("Analysis Methods");
        ReviewPanel_SelectedAnalysisMethodsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ReviewPanel_SelectedAnalysisMethodsLabelMouseClicked(evt);
            }
        });

        ReviewPanel_SelectedEventSetListBox.setModel(SelectedEventSetsListBox_Model);
        ReviewPanel_SelectedEventSetListBox.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ReviewPanel_SelectedEventSetListBox.setEnabled(false);
        ReviewPanel_SelectedEventSetListBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ReviewPanel_SelectedEventSetListBoxMouseClicked(evt);
            }
        });
        jScrollPane25.setViewportView(ReviewPanel_SelectedEventSetListBox);

        ReviewPanel_SelectedEventCullingListBox.setModel(SelectedEventCullingListBox_Model);
        ReviewPanel_SelectedEventCullingListBox.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ReviewPanel_SelectedEventCullingListBox.setEnabled(false);
        ReviewPanel_SelectedEventCullingListBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ReviewPanel_SelectedEventCullingListBoxMouseClicked(evt);
            }
        });
        jScrollPane26.setViewportView(ReviewPanel_SelectedEventCullingListBox);

        ReviewPanel_SelectedAnalysisMethodsListBox.setModel(SelectedAnalysisMethodListBox_Model);
        ReviewPanel_SelectedAnalysisMethodsListBox.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ReviewPanel_SelectedAnalysisMethodsListBox.setEnabled(false);
        ReviewPanel_SelectedAnalysisMethodsListBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ReviewPanel_SelectedAnalysisMethodsListBoxMouseClicked(evt);
            }
        });
        jScrollPane27.setViewportView(ReviewPanel_SelectedAnalysisMethodsListBox);

        javax.swing.GroupLayout JGAAP_ReviewPanelLayout = new javax.swing.GroupLayout(JGAAP_ReviewPanel);
        JGAAP_ReviewPanel.setLayout(JGAAP_ReviewPanelLayout);
        JGAAP_ReviewPanelLayout.setHorizontalGroup(
            JGAAP_ReviewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JGAAP_ReviewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JGAAP_ReviewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
                    .addComponent(ReviewPanel_DocumentsLabel, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ReviewPanel_ProcessButton)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JGAAP_ReviewPanelLayout.createSequentialGroup()
                        .addGroup(JGAAP_ReviewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ReviewPanel_SelectedEventSetLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JGAAP_ReviewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ReviewPanel_SelectedEventCullingLabel)
                            .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JGAAP_ReviewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ReviewPanel_SelectedAnalysisMethodsLabel)
                            .addComponent(jScrollPane27, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE))))
                .addContainerGap())
        );
        JGAAP_ReviewPanelLayout.setVerticalGroup(
            JGAAP_ReviewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JGAAP_ReviewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ReviewPanel_DocumentsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JGAAP_ReviewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ReviewPanel_SelectedEventSetLabel)
                    .addComponent(ReviewPanel_SelectedEventCullingLabel)
                    .addComponent(ReviewPanel_SelectedAnalysisMethodsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JGAAP_ReviewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane27, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                    .addComponent(jScrollPane26, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                    .addComponent(jScrollPane25, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ReviewPanel_ProcessButton)
                .addContainerGap())
        );

        JGAAP_TabbedPane.addTab("Review & Process", JGAAP_ReviewPanel);

        Next_Button.setText("Next \u2192");
        Next_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Next_ButtonActionPerformed(evt);
            }
        });

        Review_Button.setText("Finish & Review");
        Review_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Review_ButtonActionPerformed(evt);
            }
        });

        jMenu1.setText("File");

        jMenu4.setText("Batch Documents");

        BatchSaveMenuItem.setText("Save Documents");
        BatchSaveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BatchSaveMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(BatchSaveMenuItem);

        BatchLoadMenuItem.setText("Load Documents");
        BatchLoadMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BatchLoadMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(BatchLoadMenuItem);

        jMenu1.add(jMenu4);

        jMenu2.setText("AAAC Problems");

        ProblemAMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, (java.awt.event.InputEvent.CTRL_MASK) | InputEvent.SHIFT_MASK));
        ProblemBMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, (java.awt.event.InputEvent.CTRL_MASK) | InputEvent.SHIFT_MASK));
        ProblemCMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, (java.awt.event.InputEvent.CTRL_MASK) | InputEvent.SHIFT_MASK));
        ProblemDMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, (java.awt.event.InputEvent.CTRL_MASK) | InputEvent.SHIFT_MASK));
        ProblemEMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_E, (java.awt.event.InputEvent.CTRL_MASK) | InputEvent.SHIFT_MASK));
        ProblemFMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, (java.awt.event.InputEvent.CTRL_MASK) | InputEvent.SHIFT_MASK));
        ProblemGMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, (java.awt.event.InputEvent.CTRL_MASK) | InputEvent.SHIFT_MASK));
        ProblemHMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, (java.awt.event.InputEvent.CTRL_MASK) | InputEvent.SHIFT_MASK));
        ProblemIMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, (java.awt.event.InputEvent.CTRL_MASK) | InputEvent.SHIFT_MASK));
        ProblemJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_J, (java.awt.event.InputEvent.CTRL_MASK) | InputEvent.SHIFT_MASK));
        ProblemKMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_K, (java.awt.event.InputEvent.CTRL_MASK) | InputEvent.SHIFT_MASK));
        ProblemLMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, (java.awt.event.InputEvent.CTRL_MASK) | InputEvent.SHIFT_MASK));
        ProblemMMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, (java.awt.event.InputEvent.CTRL_MASK) | InputEvent.SHIFT_MASK));



        ProblemAMenuItem.setText("Problem A");
        ProblemAMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProblemAMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(ProblemAMenuItem);

        ProblemBMenuItem.setText("Problem B");
        ProblemBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProblemBMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(ProblemBMenuItem);

        ProblemCMenuItem.setText("Problem C");
        ProblemCMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProblemCMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(ProblemCMenuItem);

        ProblemDMenuItem.setText("Problem D");
        ProblemDMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProblemDMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(ProblemDMenuItem);

        ProblemEMenuItem.setText("Problem E");
        ProblemEMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProblemEMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(ProblemEMenuItem);

        ProblemFMenuItem.setText("Problem F");
        ProblemFMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProblemFMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(ProblemFMenuItem);

        ProblemGMenuItem.setText("Problem G");
        ProblemGMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProblemGMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(ProblemGMenuItem);

        ProblemHMenuItem.setText("Problem H");
        ProblemHMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProblemHMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(ProblemHMenuItem);

        ProblemIMenuItem.setText("Problem I");
        ProblemIMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProblemIMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(ProblemIMenuItem);

        ProblemJMenuItem.setText("Problem J");
        ProblemJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProblemJMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(ProblemJMenuItem);

        ProblemKMenuItem.setText("Problem K");
        ProblemKMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProblemKMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(ProblemKMenuItem);

        ProblemLMenuItem.setText("Problem L");
        ProblemLMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProblemLMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(ProblemLMenuItem);

        ProblemMMenuItem.setText("Problem M");
        ProblemMMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProblemMMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(ProblemMMenuItem);

        jMenu1.add(jMenu2);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(exitMenuItem);

        JGAAP_MenuBar.add(jMenu1);

        helpMenu.setText("Help");

        aboutMenuItem.setText("About..");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        JGAAP_MenuBar.add(helpMenu);

        setJMenuBar(JGAAP_MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(JGAAP_TabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Review_Button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Next_Button)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JGAAP_TabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Next_Button)
                    .addComponent(Review_Button))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ReviewPanel_ProcessButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReviewPanel_ProcessButtonActionPerformed
        try {
            JGAAP_API.clearResults();
            JGAAP_API.execute();
            List<Document> unknowns = JGAAP_API.getUnknownDocuments();
            StringBuffer buffer = new StringBuffer();
            for (Document unknown : unknowns) {
                buffer.append(unknown.getResult());
            }
            //ResultsPage.DisplayResults(buffer.toString());
            ResultsPage.AddResults(buffer.toString());
            ResultsPage.setVisible(true);
        } catch (Exception e){
        }
    }//GEN-LAST:event_ReviewPanel_ProcessButtonActionPerformed

    private void BatchLoadMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BatchLoadMenuItemActionPerformed
        FileChoser = new JFileChooser(filepath);
        int choice = FileChoser.showOpenDialog(JGAAP_UI_MainForm.this);
        if (choice == JFileChooser.APPROVE_OPTION)
        {
            try
            {
                filepath = FileChoser.getSelectedFile().getCanonicalPath();
                List<List<String>> DocumentCSVs = CSVIO.readCSV(filepath);
                for (int i = 0; i < DocumentCSVs.size(); i++)
                {
                	JGAAP_API.addDocument(DocumentCSVs.get(i).get(1),DocumentCSVs.get(i).get(0),(DocumentCSVs.get(i).size()>2?DocumentCSVs.get(i).get(2):null));
                }
                UpdateKnownDocumentsTree();
                UpdateUnknownDocumentsTable();
            }
            catch (Exception e)
            {

            }
        }
    }//GEN-LAST:event_BatchLoadMenuItemActionPerformed

    private void BatchSaveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BatchSaveMenuItemActionPerformed
        FileChoser = new JFileChooser(filepath);
        int choice = FileChoser.showSaveDialog(JGAAP_UI_MainForm.this);
        if (choice == JFileChooser.APPROVE_OPTION)
        {
            try 
            {
            	JGAAP_API.loadDocuments();
                DocumentList = JGAAP_API.getDocuments();
                Utils.saveDocumentsToCSV(DocumentList, FileChoser.getSelectedFile());
            }
            catch (Exception e)
            {

            }
        }
    }//GEN-LAST:event_BatchSaveMenuItemActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        dispose();
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void helpCloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpCloseButtonActionPerformed
        toggleHelpDialog();
    }//GEN-LAST:event_helpCloseButtonActionPerformed

    private void jLabel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseClicked
        browseToURL("http://evllabs.com");
    }//GEN-LAST:event_jLabel23MouseClicked

    private void jLabel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseClicked
        if(!browseToURL("http://jgaap.com")){
            browseToURL("http://server8.mathcomp.duq.edu/jgaap/w");
        }
    }//GEN-LAST:event_jLabel25MouseClicked

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        toggleHelpDialog();
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void AnalysisMethodPanel_AnalysisMethodsListBoxMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AnalysisMethodPanel_AnalysisMethodsListBoxMouseMoved
        JList theList = (JList) evt.getSource();
        int index = theList.locationToIndex(evt.getPoint());
        if (index > -1)
        {
            String text = AnalysisDriverMasterList.get(index).tooltipText();
            theList.setToolTipText(text);
        } 
}//GEN-LAST:event_AnalysisMethodPanel_AnalysisMethodsListBoxMouseMoved

    private void AnalysisMethodPanel_AnalysisMethodsListBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AnalysisMethodPanel_AnalysisMethodsListBoxMouseClicked
        AnalysisMethodPanel_AnalysisMethodDescriptionTextBox.setText(AnalysisDriverMasterList.get(AnalysisMethodPanel_AnalysisMethodsListBox.getSelectedIndex()).longDescription());

        if (evt.getClickCount() == 2)
        {
            AddAnalysisMethodSelection();
        }
}//GEN-LAST:event_AnalysisMethodPanel_AnalysisMethodsListBoxMouseClicked

    private void AnalysisMethodPanel_RemoveAllAnalysisMethodsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnalysisMethodPanel_RemoveAllAnalysisMethodsButtonActionPerformed
        JGAAP_API.removeAllAnalysisDrivers();
        UpdateSelectedAnalysisMethodListBox();
}//GEN-LAST:event_AnalysisMethodPanel_RemoveAllAnalysisMethodsButtonActionPerformed

    private void AnalysisMethodPanel_AddAllAnalysisMethodsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnalysisMethodPanel_AddAllAnalysisMethodsButtonActionPerformed
        try{
            for (int i = 0; i < AnalysisDriverMasterList.size(); i++) {
                JGAAP_API.addAnalysisDriver(AnalysisDriverMasterList.get(i).displayName());
            }
            UpdateSelectedAnalysisMethodListBox();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
}//GEN-LAST:event_AnalysisMethodPanel_AddAllAnalysisMethodsButtonActionPerformed

    private void AnalysisMethodPanel_RemoveAnalysisMethodsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnalysisMethodPanel_RemoveAnalysisMethodsButtonActionPerformed
        RemoveAnalysisMethodSelection();
}//GEN-LAST:event_AnalysisMethodPanel_RemoveAnalysisMethodsButtonActionPerformed

    private void RemoveAnalysisMethodSelection() {
        SelectedAnalysisDriverList = JGAAP_API.getAnalysisDrivers();
        JGAAP_API.removeAnalysisDriver(SelectedAnalysisDriverList.get(AnalysisMethodPanel_SelectedAnalysisMethodsListBox.getSelectedIndex()));
        UpdateSelectedAnalysisMethodListBox();
    }

    private void AnalysisMethodPanel_AddAnalysisMethodButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnalysisMethodPanel_AddAnalysisMethodButtonActionPerformed
        AddAnalysisMethodSelection();
}//GEN-LAST:event_AnalysisMethodPanel_AddAnalysisMethodButtonActionPerformed

    private void CheckMinimumRequirements() {
        boolean OK = true;
        if (DocumentList.isEmpty())
        {
            OK = false;
            ReviewPanel_DocumentsLabel.setForeground(Color.RED);
        }
        else
        {
            ReviewPanel_DocumentsLabel.setForeground(Color.GREEN.darker());
        }

        if (SelectedEventDriverList.isEmpty())
        {
            OK = false;
            ReviewPanel_SelectedEventSetLabel.setForeground(Color.RED);
        }
        else
        {
            ReviewPanel_SelectedEventSetLabel.setForeground(Color.GREEN.darker());
        }

        ReviewPanel_SelectedEventCullingLabel.setForeground(Color.GREEN.darker());

        if (SelectedAnalysisDriverList.isEmpty())
        {
            OK = false;
            ReviewPanel_SelectedAnalysisMethodsLabel.setForeground(Color.RED);
        }
        else
        {
            ReviewPanel_SelectedAnalysisMethodsLabel.setForeground(Color.GREEN.darker());
        }

        ReviewPanel_ProcessButton.setEnabled(OK);
    }

    private void AddAnalysisMethodSelection() {
        try{
            AnalysisDriver temp = JGAAP_API.addAnalysisDriver(AnalysisMethodPanel_AnalysisMethodsListBox.getSelectedValue().toString());
            if (temp instanceof NeighborAnalysisDriver)
            {
                JGAAP_API.addDistanceFunction(AnalysisMethodPanel_DistanceFunctionsListBox.getSelectedValue().toString(), temp);
            }
            UpdateSelectedAnalysisMethodListBox();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void AnalysisMethodPanel_SelectedAnalysisMethodsListBoxMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AnalysisMethodPanel_SelectedAnalysisMethodsListBoxMouseMoved
        JList theList = (JList) evt.getSource();
        int index = theList.locationToIndex(evt.getPoint());
        if (index > -1) {
            String text = SelectedAnalysisDriverList.get(index).tooltipText();
            theList.setToolTipText(text);
        }
}//GEN-LAST:event_AnalysisMethodPanel_SelectedAnalysisMethodsListBoxMouseMoved

    private void AnalysisMethodPanel_SelectedAnalysisMethodsListBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AnalysisMethodPanel_SelectedAnalysisMethodsListBoxMouseClicked
        AnalysisDriver temp = SelectedAnalysisDriverList.get(AnalysisMethodPanel_SelectedAnalysisMethodsListBox.getSelectedIndex());
        AnalysisMethodPanel_AMParametersPanel.removeAll();
        AnalysisMethodPanel_DFParametersPanel.removeAll();
        AnalysisMethodPanel_AMParametersPanel.setLayout(temp.getGUILayout(AnalysisMethodPanel_AMParametersPanel));
        if (temp instanceof NeighborAnalysisDriver)
        {
            AnalysisMethodPanel_DFParametersPanel.setLayout(((NeighborAnalysisDriver)temp).getDistanceFunction().getGUILayout(AnalysisMethodPanel_DFParametersPanel));
        }
        AnalysisMethodPanel_AnalysisMethodDescriptionTextBox.setText(temp.longDescription());
        if (temp instanceof NeighborAnalysisDriver)
        {
            AnalysisMethodPanel_DistanceFunctionDescriptionTextBox.setText(((NeighborAnalysisDriver)temp).getDistanceFunction().longDescription());
        }
        if (evt!=null && evt.getClickCount() == 2)
        {
            RemoveAnalysisMethodSelection();
        }
}//GEN-LAST:event_AnalysisMethodPanel_SelectedAnalysisMethodsListBoxMouseClicked

    private void EventCullingPanel_EventCullingListBoxMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EventCullingPanel_EventCullingListBoxMouseMoved
        JList theList = (JList) evt.getSource();
        int index = theList.locationToIndex(evt.getPoint());
        if (index > -1) {
            String text = EventCullersMasterList.get(index).tooltipText();
            theList.setToolTipText(text);
        }
}//GEN-LAST:event_EventCullingPanel_EventCullingListBoxMouseMoved

    private void EventCullingPanel_EventCullingListBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EventCullingPanel_EventCullingListBoxMouseClicked
        EventCullingPanel_EventCullingDescriptionTextbox.setText(EventCullersMasterList.get(EventCullingPanel_EventCullingListBox.getSelectedIndex()).longDescription());
        
        if (evt.getClickCount() == 2)
        {
            AddEventCullerSelection();
        }
}//GEN-LAST:event_EventCullingPanel_EventCullingListBoxMouseClicked

    private void EventCullingPanel_RemoveAllEventCullingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EventCullingPanel_RemoveAllEventCullingButtonActionPerformed
        JGAAP_API.removeAllEventCullers();
        UpdateSelectedEventCullingListBox();
}//GEN-LAST:event_EventCullingPanel_RemoveAllEventCullingButtonActionPerformed

    private void EventCullingPanel_AddAllEventCullingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EventCullingPanel_AddAllEventCullingButtonActionPerformed
        try{
            for (int i = 0; i < EventCullersMasterList.size(); i++) {
                JGAAP_API.addEventCuller(EventCullersMasterList.get(i).displayName());
            }
            UpdateSelectedEventCullingListBox();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }        // TODO add your handling code here:
}//GEN-LAST:event_EventCullingPanel_AddAllEventCullingButtonActionPerformed

    private void EventCullingPanel_RemoveEventCullingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EventCullingPanel_RemoveEventCullingButtonActionPerformed
        RemoveEventCullerSelection();
}//GEN-LAST:event_EventCullingPanel_RemoveEventCullingButtonActionPerformed

    private void RemoveEventCullerSelection() {
        SelectedEventCullersList = JGAAP_API.getEventCullers();
        JGAAP_API.removeEventCuller(SelectedEventCullersList.get(EventCullingPanel_SelectedEventCullingListBox.getSelectedIndex()));
        UpdateSelectedEventCullingListBox();
    }

    private void EventCullingPanel_AddEventCullingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EventCullingPanel_AddEventCullingButtonActionPerformed
        AddEventCullerSelection();
}//GEN-LAST:event_EventCullingPanel_AddEventCullingButtonActionPerformed

    private void AddEventCullerSelection() {
        try{
            JGAAP_API.addEventCuller(EventCullingPanel_EventCullingListBox.getSelectedValue().toString());
            UpdateSelectedEventCullingListBox();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void EventCullingPanel_SelectedEventCullingListBoxMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EventCullingPanel_SelectedEventCullingListBoxMouseMoved
        JList theList = (JList) evt.getSource();
        int index = theList.locationToIndex(evt.getPoint());
        if (index > -1) {
            String text = SelectedEventCullersList.get(index).tooltipText();
            theList.setToolTipText(text);
        }
}//GEN-LAST:event_EventCullingPanel_SelectedEventCullingListBoxMouseMoved

    private void EventCullingPanel_SelectedEventCullingListBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EventCullingPanel_SelectedEventCullingListBoxMouseClicked
        EventCullingPanel_ParametersPanel.removeAll();
        EventCullingPanel_ParametersPanel.setLayout(SelectedEventCullersList.get(EventCullingPanel_SelectedEventCullingListBox.getSelectedIndex()).getGUILayout(EventCullingPanel_ParametersPanel));

        EventCullingPanel_EventCullingDescriptionTextbox.setText(SelectedEventCullersList.get(EventCullingPanel_SelectedEventCullingListBox.getSelectedIndex()).longDescription());
        if (evt!=null && evt.getClickCount() == 2)
        {
            RemoveEventCullerSelection();
        }
}//GEN-LAST:event_EventCullingPanel_SelectedEventCullingListBoxMouseClicked

    private void EventSetsPanel_RemoveAllEventSetsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EventSetsPanel_RemoveAllEventSetsButtonActionPerformed
        JGAAP_API.removeAllEventDrivers();
        UpdateSelectedEventSetListBox();
}//GEN-LAST:event_EventSetsPanel_RemoveAllEventSetsButtonActionPerformed

    private void EventSetsPanel_AddAllEventSetsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EventSetsPanel_AddAllEventSetsButtonActionPerformed
        try{
            for (int i = 0; i < EventDriverMasterList.size(); i++) {
                JGAAP_API.addEventDriver(EventDriverMasterList.get(i).displayName());
            }
            UpdateSelectedEventSetListBox();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
}//GEN-LAST:event_EventSetsPanel_AddAllEventSetsButtonActionPerformed

    private void EventSetsPanel_RemoveEventSetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EventSetsPanel_RemoveEventSetButtonActionPerformed
        RemoveEventSetSelection();
}//GEN-LAST:event_EventSetsPanel_RemoveEventSetButtonActionPerformed

    private void RemoveEventSetSelection()
    {
        SelectedEventDriverList = JGAAP_API.getEventDrivers();
        JGAAP_API.removeEventDriver(SelectedEventDriverList.get(EventSetsPanel_SelectedEventSetListBox.getSelectedIndex()));
        UpdateSelectedEventSetListBox();
    }

    private void EventSetsPanel_AddEventSetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EventSetsPanel_AddEventSetButtonActionPerformed
        AddEventSetSelection();
}//GEN-LAST:event_EventSetsPanel_AddEventSetButtonActionPerformed

    private void AddEventSetSelection()
    {
        try{
            JGAAP_API.addEventDriver(EventSetsPanel_EventSetListBox.getSelectedValue().toString());
            UpdateSelectedEventSetListBox();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void EventSetsPanel_SelectedEventSetListBoxMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EventSetsPanel_SelectedEventSetListBoxMouseMoved
        JList theList = (JList) evt.getSource();
        int index = theList.locationToIndex(evt.getPoint());
        if (index > -1) {
            String text = SelectedEventDriverList.get(index).tooltipText();
            theList.setToolTipText(text);
        }
}//GEN-LAST:event_EventSetsPanel_SelectedEventSetListBoxMouseMoved

    private void EventSetsPanel_SelectedEventSetListBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EventSetsPanel_SelectedEventSetListBoxMouseClicked
        EventSetsPanel_ParametersPanel.removeAll();
        EventSetsPanel_ParametersPanel.setLayout(SelectedEventDriverList.get(EventSetsPanel_SelectedEventSetListBox.getSelectedIndex()).getGUILayout(EventSetsPanel_ParametersPanel));
        EventSetsPanel_EventSetDescriptionTextBox.setText(SelectedEventDriverList.get(EventSetsPanel_SelectedEventSetListBox.getSelectedIndex()).longDescription());
        if (evt != null && evt.getClickCount() == 2)
        {
            RemoveEventSetSelection();
        }
}//GEN-LAST:event_EventSetsPanel_SelectedEventSetListBoxMouseClicked

    private void EventSetsPanel_EventSetListBoxMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EventSetsPanel_EventSetListBoxMouseMoved
        JList theList = (JList) evt.getSource();
        int index = theList.locationToIndex(evt.getPoint());
        if (index > -1) {
            String text = EventDriverMasterList.get(index).tooltipText();
            theList.setToolTipText(text);
        }
}//GEN-LAST:event_EventSetsPanel_EventSetListBoxMouseMoved

    private void EventSetsPanel_EventSetListBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EventSetsPanel_EventSetListBoxMouseClicked
        EventSetsPanel_EventSetDescriptionTextBox.setText(EventDriverMasterList.get(EventSetsPanel_EventSetListBox.getSelectedIndex()).longDescription());
        if (evt.getClickCount() == 2)
        {
            AddEventSetSelection();
        }
}//GEN-LAST:event_EventSetsPanel_EventSetListBoxMouseClicked

    private void CanonicizersPanel_RemoveAllCanonicizersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CanonicizersPanel_RemoveAllCanonicizersButtonActionPerformed
        SelectedCanonicizerList.clear();
        UpdateSelectedCanonicizerListBox();
}//GEN-LAST:event_CanonicizersPanel_RemoveAllCanonicizersButtonActionPerformed

    private void CanonicizersPanel_AddAllCanonicizersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CanonicizersPanel_AddAllCanonicizersButtonActionPerformed
        try{
            for (int i = 0; i < CanonicizerMasterList.size(); i++){
                Canonicizer temp = CanonicizerMasterList.get(i).getClass().newInstance();
                SelectedCanonicizerList.add(temp);
            }
            UpdateSelectedCanonicizerListBox();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
}//GEN-LAST:event_CanonicizersPanel_AddAllCanonicizersButtonActionPerformed

    private void CanonicizersPanel_RemoveCanonicizerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CanonicizersPanel_RemoveCanonicizerButtonActionPerformed
        RemoveCanonicizerSelection();
}//GEN-LAST:event_CanonicizersPanel_RemoveCanonicizerButtonActionPerformed

    private void RemoveCanonicizerSelection() {
        SelectedCanonicizerList.remove(CanonicizersPanel_SelectedCanonicizerListBox.getSelectedIndex());
        UpdateSelectedCanonicizerListBox();
    }

    private void CanonicizersPanel_AddCanonicizerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CanonicizersPanel_AddCanonicizerButtonActionPerformed
        AddCanonicizerSelection();
}//GEN-LAST:event_CanonicizersPanel_AddCanonicizerButtonActionPerformed

    private void AddCanonicizerSelection()
    {
           try{
            for (int i = 0; i < CanonicizerMasterList.size(); i++){
                if (CanonicizerMasterList.get(i).displayName().equals(CanonicizersPanel_CanonicizerListBox.getSelectedValue().toString())){
                    Canonicizer temp = CanonicizerMasterList.get(i).getClass().newInstance();
                    SelectedCanonicizerList.add(temp);
                    UpdateSelectedCanonicizerListBox();
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void CanonicizersPanel_SelectedCanonicizerListBoxMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CanonicizersPanel_SelectedCanonicizerListBoxMouseMoved
        JList theList = (JList) evt.getSource();
        int index = theList.locationToIndex(evt.getPoint());
        if (index > -1) {
            String text = SelectedCanonicizerList.get(index).tooltipText();
            theList.setToolTipText(text);
        }
}//GEN-LAST:event_CanonicizersPanel_SelectedCanonicizerListBoxMouseMoved

    private void CanonicizersPanel_SelectedCanonicizerListBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CanonicizersPanel_SelectedCanonicizerListBoxMouseClicked
        CanonicizersPanel_DocumentsCanonicizerDescriptionTextBox.setText(SelectedCanonicizerList.get(CanonicizersPanel_SelectedCanonicizerListBox.getSelectedIndex()).longDescription());
        if (evt != null && evt.getClickCount() == 2)
        {
            RemoveCanonicizerSelection();
        }
}//GEN-LAST:event_CanonicizersPanel_SelectedCanonicizerListBoxMouseClicked

    private void CanonicizersPanel_CanonicizerListBoxMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CanonicizersPanel_CanonicizerListBoxMouseMoved
        JList theList = (JList) evt.getSource();
        int index = theList.locationToIndex(evt.getPoint());
        if (index > -1) {
            String text = CanonicizerMasterList.get(index).tooltipText();
            theList.setToolTipText(text);
        }
}//GEN-LAST:event_CanonicizersPanel_CanonicizerListBoxMouseMoved

    private void CanonicizersPanel_CanonicizerListBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CanonicizersPanel_CanonicizerListBoxMouseClicked
        CanonicizersPanel_DocumentsCanonicizerDescriptionTextBox.setText(CanonicizerMasterList.get(CanonicizersPanel_CanonicizerListBox.getSelectedIndex()).longDescription());
        if (evt.getClickCount() == 2)
        {
            AddCanonicizerSelection();
        }
}//GEN-LAST:event_CanonicizersPanel_CanonicizerListBoxMouseClicked

    private void DocumentsPanel_LanguageComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DocumentsPanel_LanguageComboBoxActionPerformed
        try {
            JGAAP_API.setLanguage(DocumentsPanel_LanguageComboBox.getSelectedItem().toString());
            SanatizeMasterLists();
            SetAnalysisMethodList();
            SetCanonicizerList();
            SetDistanceFunctionList();
            SetEventCullingList();
            SetEventSetList();
        } catch (Exception e) {
        	e.printStackTrace();
        }
}//GEN-LAST:event_DocumentsPanel_LanguageComboBoxActionPerformed

    private void DocumentsPanel_RemoveAuthorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DocumentsPanel_RemoveAuthorButtonActionPerformed
        TreePath Path = DocumentsPanel_KnownAuthorsTree.getSelectionPath();
        String AuthorName;
        if(Path.getPathCount() != 1) {
            AuthorName = Path.getPathComponent(1).toString();
            KnownDocumentList = JGAAP_API.getDocumentsByAuthor(AuthorName);
            for (int i = KnownDocumentList.size() - 1; i >= 0; i--) {
                JGAAP_API.removeDocument(KnownDocumentList.get(i));
            }
            UpdateKnownDocumentsTree();
        } else {

        }
}//GEN-LAST:event_DocumentsPanel_RemoveAuthorButtonActionPerformed

    private void DocumentsPanel_EditAuthorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DocumentsPanel_EditAuthorButtonActionPerformed
        TreePath Path = DocumentsPanel_KnownAuthorsTree.getSelectionPath();
        String AuthorName;
        if(Path.getPathCount() != 1) {
            AuthorName = Path.getPathComponent(1).toString();
            JGAAP_UI_AddAuthorDialog  AddAuthorDialog= new JGAAP_UI_AddAuthorDialog(JGAAP_UI_MainForm.this, true, AuthorName, filepath);
            AddAuthorDialog.setVisible(true);
            UpdateKnownDocumentsTree();
        } else {

        }
}//GEN-LAST:event_DocumentsPanel_EditAuthorButtonActionPerformed

    private void DocumentsPanel_AddAuthorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DocumentsPanel_AddAuthorButtonActionPerformed
        JGAAP_UI_AddAuthorDialog  AddAuthorDialog= new JGAAP_UI_AddAuthorDialog(JGAAP_UI_MainForm.this, true,"",filepath);
        AddAuthorDialog.setVisible(true);
        filepath = AddAuthorDialog.getFilePath();
        UpdateKnownDocumentsTree();
}//GEN-LAST:event_DocumentsPanel_AddAuthorButtonActionPerformed

    private void DocumentsPanel_RemoveDocumentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DocumentsPanel_RemoveDocumentsButtonActionPerformed
        UnknownDocumentList = JGAAP_API.getUnknownDocuments();
        JGAAP_API.removeDocument(UnknownDocumentList.get(DocumentsPanel_UnknownAuthorsTable.getSelectedRow()));
        UpdateUnknownDocumentsTable();
}//GEN-LAST:event_DocumentsPanel_RemoveDocumentsButtonActionPerformed

    private void DocumentsPanel_AddDocumentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DocumentsPanel_AddDocumentsButtonActionPerformed

        FileChoser = new JFileChooser(filepath);
        FileChoser.setMultiSelectionEnabled(true);
        int choice = FileChoser.showOpenDialog(JGAAP_UI_MainForm.this);
        if (choice == JFileChooser.APPROVE_OPTION) {
            for(File file : FileChoser.getSelectedFiles()){
                try {
                    JGAAP_API.addDocument(file.getCanonicalPath(), "","");
                    filepath = file.getCanonicalPath();
                } catch (Exception e) {
                    //TODO: add error dialog here
                }
                UpdateUnknownDocumentsTable();
            }
        }
}//GEN-LAST:event_DocumentsPanel_AddDocumentsButtonActionPerformed

    private void CanonicizersPanel_SetToDocumentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CanonicizersPanel_SetToDocumentButtonActionPerformed
        int row = CanonicizersPanel_DocumentsTable.getSelectedRow();
        if (row >= 0) {
            DocumentList = JGAAP_API.getDocuments();
            Document temp = DocumentList.get(row);
            temp.clearCanonicizers();
            for (int i = 0; i < SelectedCanonicizerList.size(); i++) {
                try {
                    JGAAP_API.addCanonicizer(SelectedCanonicizerList.get(i).displayName(),temp);
                } catch (Exception e) {
                }
            }
            UpdateCurrentCanonicizerBox();
            UpdateDocumentsTable();
        }
    }//GEN-LAST:event_CanonicizersPanel_SetToDocumentButtonActionPerformed

        private void CanonicizersPanel_SetToDocumentTypeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CanonicizersPanel_SetToDocumentTypeButtonActionPerformed
    int row = CanonicizersPanel_DocumentsTable.getSelectedRow();
        if (row >= 0) {
            DocumentList = JGAAP_API.getDocuments();
            Document temp = DocumentList.get(row);
            temp.clearCanonicizers();
            for (int i = 0; i < SelectedCanonicizerList.size(); i++) {
                try {
                    JGAAP_API.addCanonicizer(SelectedCanonicizerList.get(i).displayName(),temp);
                } catch (Exception e) {
                }
            }
            UpdateCurrentCanonicizerBox();
            UpdateDocumentsTable();
        }
        }//GEN-LAST:event_CanonicizersPanel_SetToDocumentTypeButtonActionPerformed

        private void CanonicizersPanel_SetToAllDocumentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CanonicizersPanel_SetToAllDocumentsActionPerformed
            JGAAP_API.removeAllCanonicizers();
            for (int i = 0; i < SelectedCanonicizerList.size(); i++) {
                try {
                    JGAAP_API.addCanonicizer(SelectedCanonicizerList.get(i).displayName());
                } catch (Exception e) {
                }
            }
            UpdateCurrentCanonicizerBox();
            UpdateDocumentsTable();
        }//GEN-LAST:event_CanonicizersPanel_SetToAllDocumentsActionPerformed

        private void DocumentsPanel_NotesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DocumentsPanel_NotesButtonActionPerformed
            try {
                NotesPage.DisplayNote(Notes[0]);
                NotesPage.setVisible(true);
                if (NotesPage.SavedNote != null)
                {
                    Notes[0] = NotesPage.SavedNote;
                }
            } catch (Exception e){
            }
        }//GEN-LAST:event_DocumentsPanel_NotesButtonActionPerformed

        private void CanonicizersPanel_NotesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CanonicizersPanel_NotesButtonActionPerformed
            try {
                NotesPage.DisplayNote(Notes[1]);
                NotesPage.setVisible(true);
                if (NotesPage.SavedNote != null)
                {
                    Notes[1] = NotesPage.SavedNote;
                }
            } catch (Exception e){
            }
        }//GEN-LAST:event_CanonicizersPanel_NotesButtonActionPerformed

        private void EventSetsPanel_NotesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EventSetsPanel_NotesButtonActionPerformed
            try {
                NotesPage.DisplayNote(Notes[2]);
                NotesPage.setVisible(true);
                if (NotesPage.SavedNote != null)
                {
                    Notes[2] = NotesPage.SavedNote;
                }
            } catch (Exception e){
            }
        }//GEN-LAST:event_EventSetsPanel_NotesButtonActionPerformed

        private void EventCullingPanel_NotesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EventCullingPanel_NotesButtonActionPerformed
            try {
                NotesPage.DisplayNote(Notes[3]);
                NotesPage.setVisible(true);
                if (NotesPage.SavedNote != null)
                {
                    Notes[3] = NotesPage.SavedNote;
                }
            } catch (Exception e){
            }
        }//GEN-LAST:event_EventCullingPanel_NotesButtonActionPerformed

        private void AnalysisMethodPanel_NotesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnalysisMethodPanel_NotesButtonActionPerformed
            try {
                NotesPage.DisplayNote(Notes[4]);
                NotesPage.setVisible(true);
                if (NotesPage.SavedNote != null)
                {
                    Notes[4] = NotesPage.SavedNote;
                }
            } catch (Exception e){
            }
        }//GEN-LAST:event_AnalysisMethodPanel_NotesButtonActionPerformed

        private void AnalysisMethodPanel_DistanceFunctionsListBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AnalysisMethodPanel_DistanceFunctionsListBoxMouseClicked
            AnalysisMethodPanel_DistanceFunctionDescriptionTextBox.setText(DistanceFunctionsMasterList.get(AnalysisMethodPanel_DistanceFunctionsListBox.getSelectedIndex()).longDescription());

            if (evt.getClickCount() == 2)
            {
                AddAnalysisMethodSelection();
            }
        }//GEN-LAST:event_AnalysisMethodPanel_DistanceFunctionsListBoxMouseClicked

        private void AnalysisMethodPanel_DistanceFunctionsListBoxMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AnalysisMethodPanel_DistanceFunctionsListBoxMouseMoved
            JList theList = (JList) evt.getSource();
            int index = theList.locationToIndex(evt.getPoint());
            if (index > -1)
            {
                String text = DistanceFunctionsMasterList.get(index).tooltipText();
                theList.setToolTipText(text);
            }
        }//GEN-LAST:event_AnalysisMethodPanel_DistanceFunctionsListBoxMouseMoved

        private void Next_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Next_ButtonActionPerformed
            int count = JGAAP_TabbedPane.getSelectedIndex();
            if (count < 5)
            {
                JGAAP_TabbedPane.setSelectedIndex(count + 1);
            }
        }//GEN-LAST:event_Next_ButtonActionPerformed

        private void Review_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Review_ButtonActionPerformed
            JGAAP_TabbedPane.setSelectedIndex(5);
        }//GEN-LAST:event_Review_ButtonActionPerformed

        private void loadAAACProblem(String problem){
        	filepath = jgaapConstants.docsDir()+"aaac/Demos/load"+problem+".csv";
        	List<Document> documents = Collections.emptyList();
        	try {
				documents = Utils.getDocumentsFromCSV(CSVIO.readCSV(filepath));
			} catch (Exception e) {
				e.printStackTrace();
			}
			for(Document document : documents){
				JGAAP_API.addDocument(document);
			}
			UpdateKnownDocumentsTree();
			UpdateUnknownDocumentsTable();
        	
        }
        
        private void ProblemAMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProblemAMenuItemActionPerformed
        	loadAAACProblem("A");
        }//GEN-LAST:event_ProblemAMenuItemActionPerformed

        private void ProblemBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProblemBMenuItemActionPerformed
        	loadAAACProblem("B");
        }//GEN-LAST:event_ProblemBMenuItemActionPerformed

        private void ProblemCMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProblemCMenuItemActionPerformed
        	loadAAACProblem("C");
        }//GEN-LAST:event_ProblemCMenuItemActionPerformed

        private void ProblemDMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProblemDMenuItemActionPerformed
        	loadAAACProblem("D");
        }//GEN-LAST:event_ProblemDMenuItemActionPerformed

        private void ProblemEMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProblemEMenuItemActionPerformed
        	loadAAACProblem("E");
        }//GEN-LAST:event_ProblemEMenuItemActionPerformed

        private void ProblemFMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProblemFMenuItemActionPerformed
        	loadAAACProblem("F");
        }//GEN-LAST:event_ProblemFMenuItemActionPerformed

        private void ProblemGMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProblemGMenuItemActionPerformed
        	loadAAACProblem("G");
        }//GEN-LAST:event_ProblemGMenuItemActionPerformed

        private void ProblemHMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProblemHMenuItemActionPerformed
        	loadAAACProblem("H");
        }//GEN-LAST:event_ProblemHMenuItemActionPerformed

        private void ProblemIMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProblemIMenuItemActionPerformed
        	loadAAACProblem("I");
        }//GEN-LAST:event_ProblemIMenuItemActionPerformed

        private void ProblemJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProblemJMenuItemActionPerformed
        	loadAAACProblem("J");
        }//GEN-LAST:event_ProblemJMenuItemActionPerformed

        private void ProblemKMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProblemKMenuItemActionPerformed
        	loadAAACProblem("K");
        }//GEN-LAST:event_ProblemKMenuItemActionPerformed

        private void ProblemLMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProblemLMenuItemActionPerformed
        	loadAAACProblem("L");
        }//GEN-LAST:event_ProblemLMenuItemActionPerformed

        private void ProblemMMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProblemMMenuItemActionPerformed
        	loadAAACProblem("M");
        }//GEN-LAST:event_ProblemMMenuItemActionPerformed

        private void ReviewPanel_DocumentsLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReviewPanel_DocumentsLabelMouseClicked
            JGAAP_TabbedPane.setSelectedIndex(0);
        }//GEN-LAST:event_ReviewPanel_DocumentsLabelMouseClicked

        private void ReviewPanel_DocumentsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReviewPanel_DocumentsTableMouseClicked
            //JGAAP_TabbedPane.setSelectedIndex(0);
        }//GEN-LAST:event_ReviewPanel_DocumentsTableMouseClicked

        private void ReviewPanel_SelectedEventSetLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReviewPanel_SelectedEventSetLabelMouseClicked
            JGAAP_TabbedPane.setSelectedIndex(2);
        }//GEN-LAST:event_ReviewPanel_SelectedEventSetLabelMouseClicked

        private void ReviewPanel_SelectedEventSetListBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReviewPanel_SelectedEventSetListBoxMouseClicked
            //JGAAP_TabbedPane.setSelectedIndex(2);
        }//GEN-LAST:event_ReviewPanel_SelectedEventSetListBoxMouseClicked

        private void ReviewPanel_SelectedEventCullingLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReviewPanel_SelectedEventCullingLabelMouseClicked
            JGAAP_TabbedPane.setSelectedIndex(3);
        }//GEN-LAST:event_ReviewPanel_SelectedEventCullingLabelMouseClicked

        private void ReviewPanel_SelectedEventCullingListBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReviewPanel_SelectedEventCullingListBoxMouseClicked
            //JGAAP_TabbedPane.setSelectedIndex(3);
        }//GEN-LAST:event_ReviewPanel_SelectedEventCullingListBoxMouseClicked

        private void ReviewPanel_SelectedAnalysisMethodsLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReviewPanel_SelectedAnalysisMethodsLabelMouseClicked
            JGAAP_TabbedPane.setSelectedIndex(4);
        }//GEN-LAST:event_ReviewPanel_SelectedAnalysisMethodsLabelMouseClicked

        private void ReviewPanel_SelectedAnalysisMethodsListBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReviewPanel_SelectedAnalysisMethodsListBoxMouseClicked
            //JGAAP_TabbedPane.setSelectedIndex(4);
        }//GEN-LAST:event_ReviewPanel_SelectedAnalysisMethodsListBoxMouseClicked

    private void toggleHelpDialog(){
        helpDialog.setVisible(!helpDialog.isVisible());
    }

    public boolean browseToURL(String url){
        boolean succees = false;
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
            succees = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return succees;
    }

    private void UpdateSelectedAnalysisMethodListBox()
    {
        SelectedAnalysisMethodListBox_Model.clear();
        SelectedAnalysisDriverList = JGAAP_API.getAnalysisDrivers();
        for (int i = 0; i < SelectedAnalysisDriverList.size(); i++)
        {
            SelectedAnalysisMethodListBox_Model.addElement(SelectedAnalysisDriverList.get(i).displayName());
        }
        CheckMinimumRequirements();
        if(!SelectedAnalysisDriverList.isEmpty()){
        	AnalysisMethodPanel_SelectedAnalysisMethodsListBox.setSelectedIndex(SelectedAnalysisDriverList.size()-1);
        	AnalysisMethodPanel_SelectedAnalysisMethodsListBoxMouseClicked(null);
        }
    }

    private void UpdateSelectedEventSetListBox()
    {
        SelectedEventSetsListBox_Model.clear();
        SelectedEventDriverList = JGAAP_API.getEventDrivers();
        for (int i = 0; i < SelectedEventDriverList.size(); i++)
        {
            SelectedEventSetsListBox_Model.addElement(SelectedEventDriverList.get(i).displayName());
        }
        CheckMinimumRequirements();
        if(!SelectedEventDriverList.isEmpty()){
        	EventSetsPanel_SelectedEventSetListBox.setSelectedIndex(SelectedEventDriverList.size()-1);
        	EventSetsPanel_SelectedEventSetListBoxMouseClicked(null);
        }
    }

    private void UpdateSelectedEventCullingListBox()
    {
        SelectedEventCullingListBox_Model.clear();
        SelectedEventCullersList = JGAAP_API.getEventCullers();
        for (int i = 0; i < SelectedEventCullersList.size(); i++)
        {
            SelectedEventCullingListBox_Model.addElement(SelectedEventCullersList.get(i).displayName());
        }
        if(!SelectedEventCullersList.isEmpty()){
        	EventCullingPanel_SelectedEventCullingListBox.setSelectedIndex(SelectedEventCullersList.size()-1);
        	EventCullingPanel_SelectedEventCullingListBoxMouseClicked(null);
        }
    }

    private void UpdateSelectedCanonicizerListBox() {
        SelectedCanonicizerListBox_Model.clear();
        for (int i = 0; i < SelectedCanonicizerList.size(); i++)
        {
            SelectedCanonicizerListBox_Model.addElement(SelectedCanonicizerList.get(i).displayName());
        }
        if(!SelectedCanonicizerList.isEmpty()){
        	CanonicizersPanel_SelectedCanonicizerListBox.setSelectedIndex(SelectedCanonicizerList.size()-1);
        	CanonicizersPanel_SelectedCanonicizerListBoxMouseClicked(null);
        }
}

    private void UpdateUnknownDocumentsTable() {
        UnknownAuthorDocumentsTable_Model.setRowCount(0);
        UnknownDocumentList = JGAAP_API.getUnknownDocuments();
        for (int i = 0; i < UnknownDocumentList.size(); i++)
        {
            Object RowData[] = {UnknownDocumentList.get(i).getTitle(), UnknownDocumentList.get(i).getFilePath()};
            UnknownAuthorDocumentsTable_Model.addRow(RowData);
        }
        UpdateDocumentsTable();
}

    private void UpdateKnownDocumentsTree() {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) KnownAuthorsTree_Model.getRoot();
        for (int i = root.getChildCount() - 1; i >= 0; i--)
        {
            KnownAuthorsTree_Model.removeNodeFromParent((DefaultMutableTreeNode)root.getChildAt(i));
        }
        AuthorList = JGAAP_API.getAuthors();
        for (int i = 0; i < AuthorList.size(); i++)
        {
            String AuthorName = AuthorList.get(i);
            DefaultMutableTreeNode author = new DefaultMutableTreeNode(AuthorName);
            KnownAuthorsTree_Model.insertNodeInto(author, root, i);
            //root.add(author);
            KnownDocumentList = JGAAP_API.getDocumentsByAuthor(AuthorName);
            for (int j = 0; j < KnownDocumentList.size(); j++)
            {
                //author.add(new DefaultMutableTreeNode(KnownDocumentList.get(j).getTitle() + " - " + KnownDocumentList.get(j).getFilePath()));
                DefaultMutableTreeNode temp = new DefaultMutableTreeNode(KnownDocumentList.get(j).getTitle() + " - " + KnownDocumentList.get(j).getFilePath());
                KnownAuthorsTree_Model.insertNodeInto(temp, author, j);
            }
        }

        UpdateDocumentsTable();
}

    private void UpdateDocumentsTable() {
        String CanonPresent = "No";
        DocumentsTable_Model.setRowCount(0);
        DocumentList = JGAAP_API.getDocuments();
        for (int i = 0; i < DocumentList.size(); i++)
        {
            if (DocumentList.get(i).getCanonicizers().isEmpty())
            {
                CanonPresent = "No";
            }
            else
            {
                CanonPresent = "Yes";
            }

            Object RowData[] = {DocumentList.get(i).getTitle(), DocumentList.get(i).isAuthorKnown(), DocumentList.get(i).getAuthor(), DocumentList.get(i).getDocType(), CanonPresent};
            DocumentsTable_Model.addRow(RowData);
        }
        CheckMinimumRequirements();
}

    private void UpdateCurrentCanonicizerBox() {
        int row = CanonicizersPanel_DocumentsTable.getSelectedRow();
        if (row >= 0)
        {
            DocumentList = JGAAP_API.getDocuments();
            List<Canonicizer> tempList = DocumentList.get(row).getCanonicizers();
            String tempString = "";
            for (int i = 0; i < tempList.size(); i++)
            {
                tempString = tempString + tempList.get(i).displayName() + "\r\n";
            }
            CanonicizersPanel_DocumentsCurrentCanonicizersTextBox.setText(tempString);
        }
}


	private void SanatizeMasterLists() {
		AnalysisDriverMasterList = new ArrayList<AnalysisDriver>();
		CanonicizerMasterList = new ArrayList<Canonicizer>();
		DistanceFunctionsMasterList = new ArrayList<DistanceFunction>();
		EventCullersMasterList = new ArrayList<EventCuller>();
		EventDriverMasterList = new ArrayList<EventDriver>();
		LanguagesMasterList = new ArrayList<Language>();

		for (AnalysisDriver analysisDriver : JGAAP_API.getAllAnalysisDrivers()) {
			if (analysisDriver.showInGUI())
				AnalysisDriverMasterList.add(analysisDriver);
		}
		for (Canonicizer canonicizer : JGAAP_API.getAllCanonicizers()) {
			if (canonicizer.showInGUI())
				CanonicizerMasterList.add(canonicizer);
		}
		for (DistanceFunction distanceFunction : JGAAP_API.getAllDistanceFunctions()) {
			if (distanceFunction.showInGUI())
				DistanceFunctionsMasterList.add(distanceFunction);
		}
		for (EventCuller eventCuller : JGAAP_API.getAllEventCullers()) {
			if (eventCuller.showInGUI())
				EventCullersMasterList.add(eventCuller);
		}
		for (EventDriver eventDriver : JGAAP_API.getAllEventDrivers()) {
			if (eventDriver.showInGUI())
				EventDriverMasterList.add(eventDriver);
		}
		for (Language language : JGAAP_API.getAllLanguages()) {
			if (language.showInGUI())
				LanguagesMasterList.add(language);
		}
	}

    private void SetAnalysisMethodList() {
    	AnalysisMethodListBox_Model.removeAllElements();
        for (int i = 0; i < AnalysisDriverMasterList.size(); i++)
        {
            AnalysisMethodListBox_Model.addElement(AnalysisDriverMasterList.get(i).displayName());
        }
        if (!AnalysisDriverMasterList.isEmpty())
        {
            AnalysisMethodPanel_AnalysisMethodsListBox.setSelectedIndex(0);
            AnalysisMethodPanel_AnalysisMethodDescriptionTextBox.setText(AnalysisDriverMasterList.get(0).longDescription());
        }
    }
    private void SetDistanceFunctionList() {
    	DistanceFunctionsListBox_Model.removeAllElements();
        for (int i = 0; i < DistanceFunctionsMasterList.size(); i++)
        {
            DistanceFunctionsListBox_Model.addElement(DistanceFunctionsMasterList.get(i).displayName());
        }
        if (!DistanceFunctionsMasterList.isEmpty())
        {
            AnalysisMethodPanel_DistanceFunctionsListBox.setSelectedIndex(0);
            AnalysisMethodPanel_DistanceFunctionDescriptionTextBox.setText(DistanceFunctionsMasterList.get(0).longDescription());
        }
    }

    private void SetCanonicizerList() {
    	CanonicizerListBox_Model.removeAllElements();
        for (int i = 0; i < CanonicizerMasterList.size(); i++)
        {
            CanonicizerListBox_Model.addElement(CanonicizerMasterList.get(i).displayName());
        }
        if (!CanonicizerMasterList.isEmpty())
        {
            CanonicizersPanel_CanonicizerListBox.setSelectedIndex(0);
            //CanonicizersPanel_DocumentsCurrentCanonicizersTextBox.setText(CanonicizerMasterList.get(0).longDescription());
        }
}

    private void SetEventCullingList() {
    	EventCullingListBox_Model.removeAllElements();
        for (int i = 0; i < EventCullersMasterList.size(); i++)
        {
            EventCullingListBox_Model.addElement(EventCullersMasterList.get(i).displayName());
        }
        if (!EventCullersMasterList.isEmpty())
        {
            EventCullingPanel_EventCullingListBox.setSelectedIndex(0);
            EventCullingPanel_EventCullingDescriptionTextbox.setText(EventCullersMasterList.get(0).longDescription());
        }
}

    private void SetLanguagesList() {
        int englishIndex = -1;
        for (int i = 0; i < LanguagesMasterList.size(); i++)
        {
            LanguageComboBox_Model.addElement(LanguagesMasterList.get(i).displayName());
            if (LanguagesMasterList.get(i).displayName().equalsIgnoreCase("English"))
            {
                englishIndex = i;
            }

        }
        if (englishIndex > -1)
        {
            DocumentsPanel_LanguageComboBox.setSelectedIndex(englishIndex);
        }

}

    private void SetEventSetList() {
    	EventSetsListBox_Model.clear();
        for (int i = 0; i < EventDriverMasterList.size(); i++)
        {
            EventSetsListBox_Model.addElement(EventDriverMasterList.get(i).displayName());
        }
        if (!EventDriverMasterList.isEmpty())
        {
            EventSetsPanel_EventSetListBox.setSelectedIndex(0);
            EventSetsPanel_EventSetDescriptionTextBox.setText(EventDriverMasterList.get(0).longDescription());
        }

}

    private void SetUnknownDocumentColumns() {
        UnknownAuthorDocumentsTable_Model.addColumn("Title");
        UnknownAuthorDocumentsTable_Model.addColumn("Filepath");
        DocumentsPanel_UnknownAuthorsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        DocumentsPanel_UnknownAuthorsTable.setColumnSelectionAllowed(false);
        DocumentsPanel_UnknownAuthorsTable.setRowSelectionAllowed(true);
        
        DocumentsPanel_UnknownAuthorsTable.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e)
            {
                System.out.println("Unknown Documents Table Row: " + e.getFirstRow() + ", Column: " + e.getColumn());
                if ((e.getColumn() >= 0) && (e.getFirstRow() >= 0))
                {
                    UnknownDocumentList = JGAAP_API.getUnknownDocuments();
                    if (e.getColumn() == 0)
                    {
                        UnknownDocumentList.get(e.getFirstRow()).setTitle((String)DocumentsPanel_UnknownAuthorsTable.getValueAt(e.getFirstRow(), 0));
                    }
                    UpdateDocumentsTable();
                }
            }
        });
}

    private void SetKnownDocumentTree() {
        DocumentsPanel_KnownAuthorsTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        DocumentsPanel_KnownAuthorsTree.setShowsRootHandles(true);
}

    private void SetDocumentColumns() {
        DocumentsTable_Model.addColumn("Title");
        DocumentsTable_Model.addColumn("Known");
        DocumentsTable_Model.addColumn("Author");
        DocumentsTable_Model.addColumn("Doc Type");
        DocumentsTable_Model.addColumn("Canonicizers?");

        CanonicizersPanel_DocumentsTable.setColumnSelectionAllowed(false);
        CanonicizersPanel_DocumentsTable.setRowSelectionAllowed(true);

         CanonicizersPanel_DocumentsTable.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e)
                {
                    UpdateCurrentCanonicizerBox();
                }
            });
}

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JGAAP_UI_MainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AnalysisMethodPanel_AMParametersPanel;
    private javax.swing.JButton AnalysisMethodPanel_AddAllAnalysisMethodsButton;
    private javax.swing.JButton AnalysisMethodPanel_AddAnalysisMethodButton;
    private javax.swing.JTextArea AnalysisMethodPanel_AnalysisMethodDescriptionTextBox;
    private javax.swing.JList AnalysisMethodPanel_AnalysisMethodsListBox;
    private javax.swing.JPanel AnalysisMethodPanel_DFParametersPanel;
    private javax.swing.JTextArea AnalysisMethodPanel_DistanceFunctionDescriptionTextBox;
    private javax.swing.JList AnalysisMethodPanel_DistanceFunctionsListBox;
    private javax.swing.JButton AnalysisMethodPanel_NotesButton;
    private javax.swing.JButton AnalysisMethodPanel_RemoveAllAnalysisMethodsButton;
    private javax.swing.JButton AnalysisMethodPanel_RemoveAnalysisMethodsButton;
    private javax.swing.JList AnalysisMethodPanel_SelectedAnalysisMethodsListBox;
    private javax.swing.JMenuItem BatchLoadMenuItem;
    private javax.swing.JMenuItem BatchSaveMenuItem;
    private javax.swing.JButton CanonicizersPanel_AddAllCanonicizersButton;
    private javax.swing.JButton CanonicizersPanel_AddCanonicizerButton;
    private javax.swing.JList CanonicizersPanel_CanonicizerListBox;
    private javax.swing.JTextArea CanonicizersPanel_DocumentsCanonicizerDescriptionTextBox;
    private javax.swing.JTextArea CanonicizersPanel_DocumentsCurrentCanonicizersTextBox;
    private javax.swing.JTable CanonicizersPanel_DocumentsTable;
    private javax.swing.JButton CanonicizersPanel_NotesButton;
    private javax.swing.JButton CanonicizersPanel_RemoveAllCanonicizersButton;
    private javax.swing.JButton CanonicizersPanel_RemoveCanonicizerButton;
    private javax.swing.JList CanonicizersPanel_SelectedCanonicizerListBox;
    private javax.swing.JButton CanonicizersPanel_SetToAllDocuments;
    private javax.swing.JButton CanonicizersPanel_SetToDocumentButton;
    private javax.swing.JButton CanonicizersPanel_SetToDocumentTypeButton;
    private javax.swing.JButton DocumentsPanel_AddAuthorButton;
    private javax.swing.JButton DocumentsPanel_AddDocumentsButton;
    private javax.swing.JButton DocumentsPanel_EditAuthorButton;
    private javax.swing.JTree DocumentsPanel_KnownAuthorsTree;
    private javax.swing.JComboBox DocumentsPanel_LanguageComboBox;
    private javax.swing.JButton DocumentsPanel_NotesButton;
    private javax.swing.JButton DocumentsPanel_RemoveAuthorButton;
    private javax.swing.JButton DocumentsPanel_RemoveDocumentsButton;
    private javax.swing.JTable DocumentsPanel_UnknownAuthorsTable;
    private javax.swing.JButton EventCullingPanel_AddAllEventCullingButton;
    private javax.swing.JButton EventCullingPanel_AddEventCullingButton;
    private javax.swing.JTextArea EventCullingPanel_EventCullingDescriptionTextbox;
    private javax.swing.JList EventCullingPanel_EventCullingListBox;
    private javax.swing.JButton EventCullingPanel_NotesButton;
    private javax.swing.JPanel EventCullingPanel_ParametersPanel;
    private javax.swing.JButton EventCullingPanel_RemoveAllEventCullingButton;
    private javax.swing.JButton EventCullingPanel_RemoveEventCullingButton;
    private javax.swing.JList EventCullingPanel_SelectedEventCullingListBox;
    private javax.swing.JButton EventSetsPanel_AddAllEventSetsButton;
    private javax.swing.JButton EventSetsPanel_AddEventSetButton;
    private javax.swing.JTextArea EventSetsPanel_EventSetDescriptionTextBox;
    private javax.swing.JList EventSetsPanel_EventSetListBox;
    private javax.swing.JButton EventSetsPanel_NotesButton;
    private javax.swing.JPanel EventSetsPanel_ParametersPanel;
    private javax.swing.JButton EventSetsPanel_RemoveAllEventSetsButton;
    private javax.swing.JButton EventSetsPanel_RemoveEventSetButton;
    private javax.swing.JList EventSetsPanel_SelectedEventSetListBox;
    private javax.swing.JPanel JGAAP_AnalysisMethodPanel;
    private javax.swing.JPanel JGAAP_CanonicizerPanel;
    private javax.swing.JPanel JGAAP_DocumentsPanel;
    private javax.swing.JPanel JGAAP_EventCullingPanel;
    private javax.swing.JPanel JGAAP_EventSetsPanel;
    private javax.swing.JMenuBar JGAAP_MenuBar;
    private javax.swing.JPanel JGAAP_ReviewPanel;
    private javax.swing.JTabbedPane JGAAP_TabbedPane;
    private javax.swing.JButton Next_Button;
    private javax.swing.JMenuItem ProblemAMenuItem;
    private javax.swing.JMenuItem ProblemBMenuItem;
    private javax.swing.JMenuItem ProblemCMenuItem;
    private javax.swing.JMenuItem ProblemDMenuItem;
    private javax.swing.JMenuItem ProblemEMenuItem;
    private javax.swing.JMenuItem ProblemFMenuItem;
    private javax.swing.JMenuItem ProblemGMenuItem;
    private javax.swing.JMenuItem ProblemHMenuItem;
    private javax.swing.JMenuItem ProblemIMenuItem;
    private javax.swing.JMenuItem ProblemJMenuItem;
    private javax.swing.JMenuItem ProblemKMenuItem;
    private javax.swing.JMenuItem ProblemLMenuItem;
    private javax.swing.JMenuItem ProblemMMenuItem;
    private javax.swing.JLabel ReviewPanel_DocumentsLabel;
    private javax.swing.JTable ReviewPanel_DocumentsTable;
    private javax.swing.JButton ReviewPanel_ProcessButton;
    private javax.swing.JLabel ReviewPanel_SelectedAnalysisMethodsLabel;
    private javax.swing.JList ReviewPanel_SelectedAnalysisMethodsListBox;
    private javax.swing.JLabel ReviewPanel_SelectedEventCullingLabel;
    private javax.swing.JList ReviewPanel_SelectedEventCullingListBox;
    private javax.swing.JLabel ReviewPanel_SelectedEventSetLabel;
    private javax.swing.JList ReviewPanel_SelectedEventSetListBox;
    private javax.swing.JButton Review_Button;
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JButton helpCloseButton;
    private javax.swing.JDialog helpDialog;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane9;
    // End of variables declaration//GEN-END:variables

}
