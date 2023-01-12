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
 * @author Patrick Brennan, Michael Fang
 */

//Package Imports
import java.awt.Color;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.apache.log4j.Logger;

import com.jgaap.JGAAPConstants;
import com.jgaap.backend.API;
import com.jgaap.backend.AnalysisDrivers;
import com.jgaap.backend.CSVIO;
import com.jgaap.backend.Canonicizers;
import com.jgaap.backend.DistanceFunctions;
import com.jgaap.backend.EventCullers;
import com.jgaap.backend.EventDrivers;
import com.jgaap.backend.Languages;
import com.jgaap.backend.Utils;
import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.Canonicizer;
import com.jgaap.generics.Displayable;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.EventCuller;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.Language;
import com.jgaap.generics.NeighborAnalysisDriver;
import com.jgaap.generics.NonDistanceDependentAnalysisDriver;
import com.jgaap.util.Document;
import com.jgaap.util.Pair;
//import java.awt.event.*;
@SuppressWarnings({"rawtypes", "unchecked"})
public class JGAAP_UI_MainForm extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(JGAAP_UI_MainForm.class);

	JGAAP_UI_NotesDialog NotesPage = new JGAAP_UI_NotesDialog(
			JGAAP_UI_MainForm.this, true);
	JGAAP_UI_ResultsDialog ResultsPage = new JGAAP_UI_ResultsDialog(
			JGAAP_UI_MainForm.this, false);
	JGAAP_UI_AboutDialog AboutPage = new JGAAP_UI_AboutDialog();
	String[] Notes = new String[5];

	DefaultListModel AnalysisMethodListBox_Model = new DefaultListModel();
	DefaultListModel SelectedAnalysisMethodListBox_Model = new DefaultListModel();
	DefaultListModel CanonicizerListBox_Model = new DefaultListModel();
	DefaultListModel SelectedCanonicizerListBox_Model = new DefaultListModel();
	DefaultListModel EventCullingListBox_Model = new DefaultListModel();
	DefaultListModel SelectedEventCullingListBox_Model = new DefaultListModel();
	DefaultListModel EventSetsListBox_Model = new DefaultListModel();
	DefaultListModel SelectedEventSetsListBox_Model = new DefaultListModel();
	DefaultListModel DistanceFunctionsListBox_Model = new DefaultListModel();
	DefaultListModel AnalysisMethodsNoDistanceListBox_Model = new DefaultListModel();

	DefaultComboBoxModel LanguageComboBox_Model = new DefaultComboBoxModel();
	DefaultComboBoxModel CanonicizerComboBoxModel = new DefaultComboBoxModel();

	DefaultTreeModel KnownAuthorsTree_Model = new DefaultTreeModel(
			new DefaultMutableTreeNode("Authors"));

	DefaultTableModel UnknownAuthorDocumentsTable_Model = new DefaultTableModel() {
		private static final long serialVersionUID = 1L;

	};

	API JGAAP_API = API.getInstance();

	JFileChooser FileChoser;
	String filepath = "..";
	
	boolean distanceFormulasDisplayed = false;

	List<Canonicizer> CanonicizerMasterList = new ArrayList<Canonicizer>();
	List<EventDriver> EventDriverMasterList = new ArrayList<EventDriver>();
	List<AnalysisDriver> AnalysisDriverMasterList = new ArrayList<AnalysisDriver>();
	List<DistanceFunction> DistanceFunctionsMasterList = new ArrayList<DistanceFunction>();
	List<EventCuller> EventCullersMasterList = new ArrayList<EventCuller>();
	List<Language> LanguagesMasterList = new ArrayList<Language>();

	List<EventDriver> SelectedEventDriverList = new ArrayList<EventDriver>();
	List<EventCuller> SelectedEventCullersList = new ArrayList<EventCuller>();
	List<AnalysisDriver> SelectedAnalysisDriverList = new ArrayList<AnalysisDriver>();
	List<Pair<Canonicizer, Object>> SelectedCanonicizerList = new ArrayList<Pair<Canonicizer, Object>>();
	List<Object> docTypesList = new ArrayList<Object>();
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
		SetLanguagesList();
		SelectedEventDriverList.clear();
		SelectedAnalysisDriverList.clear();
		CheckMinimumRequirements();
		UpdateCanonicizerDocTypeComboBox();
		updateDistanceListUseability();
		SetAnalysisMethodNoDistanceList();

		// DefaultMutableTreeNode top = new
		// DefaultMutableTreeNode("The Java Series");
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings({ "deprecation" })
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		
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
		CanonicizersPanel_NotesButton = new javax.swing.JButton();
		jScrollPane11 = new javax.swing.JScrollPane();
		CanonicizersPanel_SearchField = new javax.swing.JTextField();
		CanonicizersPanel_DocumentsCanonicizerDescriptionTextBox = new javax.swing.JTextArea();
		CanonicizersPanel_AddCanonicizerButton = new javax.swing.JButton();
		CanonicizersPanel_DocTypeComboBox = new javax.swing.JComboBox();
		jScrollPane12 = new javax.swing.JScrollPane();
		CanonicizersPanel_CanonicizerListBox = new javax.swing.JList();
		jScrollPane13 = new javax.swing.JScrollPane();
		CanonicizersPanel_SelectedCanonicizerListBox = new javax.swing.JList();
		CanonicizersPanel_RemoveAllCanonicizersButton = new javax.swing.JButton();
		jLabel29 = new javax.swing.JLabel();
		jLabel30 = new javax.swing.JLabel();
		jLabel32 = new javax.swing.JLabel();
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
		EventSetsPanel_SearchField = new javax.swing.JTextField();
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
		EventCullingPanel_SearchField = new javax.swing.JTextField();
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
		lblDistanceFunctions = new javax.swing.JLabel();
		jScrollPane23 = new javax.swing.JScrollPane();
		AnalysisMethodPanel_AMDFSearchField = new javax.swing.JTextField();
		AnalysisMethodPanel_DistanceFunctionDescriptionTextBox = new javax.swing.JTextArea();
		jLabel36 = new javax.swing.JLabel();
		AnalysisMethodPanel_DFParametersPanel = new javax.swing.JPanel();
		jLabel37 = new javax.swing.JLabel();
		JGAAP_ReviewPanel = new javax.swing.JPanel();
		ReviewPanel_ProcessButton = new javax.swing.JButton();
		ReviewPanel_DocumentsLabel = new javax.swing.JLabel();
		jScrollPane24 = new javax.swing.JScrollPane();
		ReviewPanel_CanonicizersListBox = new javax.swing.JList();
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

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("JGAAP "+JGAAPConstants.VERSION);

		JGAAP_TabbedPane.setName("JGAAP_TabbedPane"); // NOI18N

		jLabel1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
		jLabel1.setText("Unknown Authors");

		jLabel2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
		jLabel2.setText("Known Authors");

		DocumentsPanel_UnknownAuthorsTable
				.setModel(UnknownAuthorDocumentsTable_Model);
		DocumentsPanel_UnknownAuthorsTable
				.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
		DocumentsPanel_UnknownAuthorsTable.setColumnSelectionAllowed(true);
		DocumentsPanel_UnknownAuthorsTable.getTableHeader()
				.setReorderingAllowed(false);
		jScrollPane1.setViewportView(DocumentsPanel_UnknownAuthorsTable);
		DocumentsPanel_UnknownAuthorsTable
				.getColumnModel()
				.getSelectionModel()
				.setSelectionMode(
						javax.swing.ListSelectionModel.SINGLE_SELECTION);

		DocumentsPanel_KnownAuthorsTree.setModel(KnownAuthorsTree_Model);
		DocumentsPanel_KnownAuthorsTree.setShowsRootHandles(true);
		jScrollPane2.setViewportView(DocumentsPanel_KnownAuthorsTree);

		DocumentsPanel_AddDocumentsButton.setText("Add Document");
		DocumentsPanel_AddDocumentsButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						DocumentsPanel_AddDocumentsButtonActionPerformed(evt);
					}
				});

		DocumentsPanel_RemoveDocumentsButton.setText("Remove Document");
		DocumentsPanel_RemoveDocumentsButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						DocumentsPanel_RemoveDocumentsButtonActionPerformed(evt);
					}
				});

		DocumentsPanel_AddAuthorButton.setLabel("Add Author");
		DocumentsPanel_AddAuthorButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						DocumentsPanel_AddAuthorButtonActionPerformed(evt);
					}
				});

		DocumentsPanel_EditAuthorButton.setLabel("Edit Author");
		DocumentsPanel_EditAuthorButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						DocumentsPanel_EditAuthorButtonActionPerformed(evt);
					}
				});

		DocumentsPanel_RemoveAuthorButton.setLabel("Remove Author");
		DocumentsPanel_RemoveAuthorButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						DocumentsPanel_RemoveAuthorButtonActionPerformed(evt);
					}
				});

		DocumentsPanel_NotesButton.setLabel("Notes");
		DocumentsPanel_NotesButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						DocumentsPanel_NotesButtonActionPerformed(evt);
					}
				});

		jLabel10.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
		jLabel10.setText("Language");

		DocumentsPanel_LanguageComboBox.setModel(LanguageComboBox_Model);
		DocumentsPanel_LanguageComboBox
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						DocumentsPanel_LanguageComboBoxActionPerformed(evt);
					}
				});

		javax.swing.GroupLayout JGAAP_DocumentsPanelLayout = new javax.swing.GroupLayout(
				JGAAP_DocumentsPanel);
		JGAAP_DocumentsPanel.setLayout(JGAAP_DocumentsPanelLayout);
		JGAAP_DocumentsPanelLayout
				.setHorizontalGroup(JGAAP_DocumentsPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								JGAAP_DocumentsPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												JGAAP_DocumentsPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jScrollPane2,
																javax.swing.GroupLayout.Alignment.LEADING,
																200, // originally javax.swing.GroupLayout.DEFAULT_SIZE,
																824,
																Short.MAX_VALUE)
														.addGroup(
																JGAAP_DocumentsPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				JGAAP_DocumentsPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jLabel10)
																						.addComponent(
																								DocumentsPanel_LanguageComboBox,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				656,
																				Short.MAX_VALUE)
																		.addComponent(
																				DocumentsPanel_NotesButton))
														.addComponent(
																jScrollPane1,
																javax.swing.GroupLayout.Alignment.LEADING,
																200, // originally javax.swing.GroupLayout.DEFAULT_SIZE
																824,
																Short.MAX_VALUE)
														.addGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																JGAAP_DocumentsPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				JGAAP_DocumentsPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jLabel1)
																						.addGroup(
																								JGAAP_DocumentsPanelLayout
																										.createSequentialGroup()
																										.addComponent(
																												DocumentsPanel_AddDocumentsButton)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												DocumentsPanel_RemoveDocumentsButton))
																						.addComponent(
																								jLabel2))
																		.addGap(100,
																				512,
																				512))
														.addGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																JGAAP_DocumentsPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				DocumentsPanel_AddAuthorButton)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				DocumentsPanel_EditAuthorButton)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				DocumentsPanel_RemoveAuthorButton)))
										.addContainerGap()));
		JGAAP_DocumentsPanelLayout
				.setVerticalGroup(JGAAP_DocumentsPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								JGAAP_DocumentsPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												JGAAP_DocumentsPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																DocumentsPanel_NotesButton)
														.addGroup(
																JGAAP_DocumentsPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				jLabel10)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				DocumentsPanel_LanguageComboBox,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jLabel1)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane1,
												50,
												// pref: originally 91
												91,
												Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												JGAAP_DocumentsPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																DocumentsPanel_RemoveDocumentsButton)
														.addComponent(
																DocumentsPanel_AddDocumentsButton))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jLabel2)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane2,
												50,
												91, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												JGAAP_DocumentsPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																DocumentsPanel_RemoveAuthorButton)
														.addComponent(
																DocumentsPanel_EditAuthorButton)
														.addComponent(
																DocumentsPanel_AddAuthorButton))
										.addContainerGap()));

		JGAAP_TabbedPane.addTab("Documents", JGAAP_DocumentsPanel);

		CanonicizersPanel_RemoveCanonicizerButton.setText("\u2190");
		CanonicizersPanel_RemoveCanonicizerButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						CanonicizersPanel_RemoveCanonicizerButtonActionPerformed(evt);
					}
				});

		CanonicizersPanel_NotesButton.setLabel("Notes");
		CanonicizersPanel_NotesButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						CanonicizersPanel_NotesButtonActionPerformed(evt);
					}
				});

		CanonicizersPanel_DocumentsCanonicizerDescriptionTextBox.setColumns(20);
		CanonicizersPanel_DocumentsCanonicizerDescriptionTextBox
				.setLineWrap(true);
		CanonicizersPanel_DocumentsCanonicizerDescriptionTextBox.setRows(5);
		CanonicizersPanel_DocumentsCanonicizerDescriptionTextBox
				.setWrapStyleWord(true);
		jScrollPane11
				.setViewportView(CanonicizersPanel_DocumentsCanonicizerDescriptionTextBox);

		CanonicizersPanel_AddCanonicizerButton.setText("\u2192");
		CanonicizersPanel_AddCanonicizerButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						CanonicizersPanel_AddCanonicizerButtonActionPerformed(evt);
					}
				});
		
		CanonicizersPanel_DocTypeComboBox.setModel(CanonicizerComboBoxModel);

		CanonicizersPanel_CanonicizerListBox.setModel(CanonicizerListBox_Model);
		CanonicizersPanel_CanonicizerListBox
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		CanonicizersPanel_CanonicizerListBox
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						CanonicizersPanel_CanonicizerListBoxMouseClicked(evt);
					}
				});
		CanonicizersPanel_CanonicizerListBox
				.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
					public void mouseMoved(java.awt.event.MouseEvent evt) {
						CanonicizersPanel_CanonicizerListBoxMouseMoved(evt);
					}
				});
		jScrollPane12.setViewportView(CanonicizersPanel_CanonicizerListBox);

		CanonicizersPanel_SelectedCanonicizerListBox
				.setModel(SelectedCanonicizerListBox_Model);
		CanonicizersPanel_SelectedCanonicizerListBox
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		CanonicizersPanel_SelectedCanonicizerListBox
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						CanonicizersPanel_SelectedCanonicizerListBoxMouseClicked(evt);
					}
				});
		CanonicizersPanel_SelectedCanonicizerListBox
				.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
					public void mouseMoved(java.awt.event.MouseEvent evt) {
						CanonicizersPanel_SelectedCanonicizerListBoxMouseMoved(evt);
					}
				});
		jScrollPane13
				.setViewportView(CanonicizersPanel_SelectedCanonicizerListBox);

		CanonicizersPanel_RemoveAllCanonicizersButton.setText("Clear");
		CanonicizersPanel_RemoveAllCanonicizersButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						CanonicizersPanel_RemoveAllCanonicizersButtonActionPerformed(evt);
					}
				});

		jLabel29.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
		jLabel29.setText("Selected");

		jLabel30.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
		jLabel30.setText("Canonicizers");

		jLabel32.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
		jLabel32.setText("Canonicizer Description");

		CanonicizersPanel_SearchField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent evt){} // responds to style change. not applicable here.
			public void removeUpdate(DocumentEvent evt){
				searchModuleList("Canonicizer");
			}
			public void insertUpdate(DocumentEvent evt){
				searchModuleList("Canonicizer");
			}
		});

		javax.swing.GroupLayout JGAAP_CanonicizerPanelLayout = new javax.swing.GroupLayout(
				JGAAP_CanonicizerPanel);
		JGAAP_CanonicizerPanel.setLayout(JGAAP_CanonicizerPanelLayout);
		JGAAP_CanonicizerPanelLayout
				.setHorizontalGroup(JGAAP_CanonicizerPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								JGAAP_CanonicizerPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												JGAAP_CanonicizerPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																JGAAP_CanonicizerPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				JGAAP_CanonicizerPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								JGAAP_CanonicizerPanelLayout
																										.createSequentialGroup()
																										.addGroup(
																												JGAAP_CanonicizerPanelLayout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.LEADING)
																														.addComponent(
																																jLabel30)
																														.addComponent(
																																jScrollPane12,
																																listboxMinWidth, // originally 200
																																listboxPrefWidth,
																																listboxMaxWidth)
																														.addComponent(
																																CanonicizersPanel_SearchField,
																																listboxMinWidth,
																																listboxPrefWidth,
																																listboxMaxWidth))
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addGroup(
																												JGAAP_CanonicizerPanelLayout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.LEADING,
																																false)
																														//.addGroup(JGAAP_CanonicizerPanelLayout.createSequentialGroup()
																																
																														
																															.addComponent(
																															CanonicizersPanel_DocTypeComboBox,
																															javax.swing.GroupLayout.DEFAULT_SIZE,
																															javax.swing.GroupLayout.DEFAULT_SIZE,
																															Short.MAX_VALUE)
																															
																															.addComponent(
																															CanonicizersPanel_AddCanonicizerButton,
																															javax.swing.GroupLayout.DEFAULT_SIZE,
																															javax.swing.GroupLayout.DEFAULT_SIZE,
																															Short.MAX_VALUE)
																														//)
																																
																														.addComponent(
																																CanonicizersPanel_RemoveCanonicizerButton,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE)
																														.addComponent(
																																CanonicizersPanel_RemoveAllCanonicizersButton,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE))
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
																		.addGroup(
																				JGAAP_CanonicizerPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jLabel29)
																						.addComponent(
																								jScrollPane13,
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								200,
																								600,
																								Short.MAX_VALUE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addGroup(
																				JGAAP_CanonicizerPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								JGAAP_CanonicizerPanelLayout
																										.createSequentialGroup()
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																												10,
																												10)
																										.addComponent(
																												CanonicizersPanel_NotesButton))))
														.addGroup(
																JGAAP_CanonicizerPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				JGAAP_CanonicizerPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								false))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				JGAAP_CanonicizerPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jLabel32)
																						.addComponent(
																								jScrollPane11,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								463,
																								Short.MAX_VALUE))))
										.addContainerGap()));
		JGAAP_CanonicizerPanelLayout
				.setVerticalGroup(JGAAP_CanonicizerPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								JGAAP_CanonicizerPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												JGAAP_CanonicizerPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																JGAAP_CanonicizerPanelLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				jLabel30)
																		.addComponent(
																				jLabel29))
														.addGroup(
																JGAAP_CanonicizerPanelLayout
																		.createSequentialGroup()
																		.addGap(5,
																				5,
																				5)
																		.addComponent(
																				CanonicizersPanel_NotesButton)))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												JGAAP_CanonicizerPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																JGAAP_CanonicizerPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				JGAAP_CanonicizerPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								JGAAP_CanonicizerPanelLayout
																										.createSequentialGroup()
																										// .addGroup(
																										// 	JGAAP_CanonicizerPanelLayout.createBaselineGroup(true, false)
																												.addComponent(
																												CanonicizersPanel_DocTypeComboBox,
																												27,27,27)	
																												.addComponent(
																												CanonicizersPanel_AddCanonicizerButton)
																											//)
//																										.addComponent(
//																												CanonicizersPanel_AddCanonicizerButton)
//																										
//																										.addComponent(
//																												CanonicizersPanel_DocTypeComboBox)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												CanonicizersPanel_RemoveCanonicizerButton)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												CanonicizersPanel_RemoveAllCanonicizersButton))
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								JGAAP_CanonicizerPanelLayout
																										.createSequentialGroup()
																										.addGroup(
																												JGAAP_CanonicizerPanelLayout
																														.createParallelGroup(
																																//javax.swing.GroupLayout.Alignment.TRAILING
																																)
																														.addComponent(
																																jScrollPane13,
																																listboxMinHeight,
																																217,
																																Short.MAX_VALUE)
																														.addGroup(
																															JGAAP_CanonicizerPanelLayout
																																.createSequentialGroup()
																																.addComponent(
																																jScrollPane12,
																																listboxMinHeight,
																																217,
																																Short.MAX_VALUE)
																														.addGap(searchFieldGap)
																														.addComponent(
																																CanonicizersPanel_SearchField,
																																searchFieldMaxHeight,
																																searchFieldMaxHeight,
																																searchFieldMaxHeight)))
																						))
																		.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGap(6,
																				6,
																				6)
																		.addGroup(
																				JGAAP_CanonicizerPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								false)
																						.addGroup(
																								JGAAP_CanonicizerPanelLayout
																										.createSequentialGroup()
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addGap(18,
																												18,
																												18)))))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												JGAAP_CanonicizerPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel32))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												JGAAP_CanonicizerPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jScrollPane11,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																descriptionBoxPrefHeight,
																descriptionBoxMaxHeight))
										.addContainerGap()));

		JGAAP_TabbedPane.addTab("Canonicizers", JGAAP_CanonicizerPanel);

		EventSetsPanel_NotesButton.setLabel("Notes");
		EventSetsPanel_NotesButton
				.addActionListener(new java.awt.event.ActionListener() {
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
		EventSetsPanel_EventSetListBox
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		EventSetsPanel_EventSetListBox
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						EventSetsPanel_EventSetListBoxMouseClicked(evt);
					}
				});
		EventSetsPanel_EventSetListBox
				.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
					public void mouseMoved(java.awt.event.MouseEvent evt) {
						EventSetsPanel_EventSetListBoxMouseMoved(evt);
					}
				});
		jScrollPane9.setViewportView(EventSetsPanel_EventSetListBox);

		EventSetsPanel_SelectedEventSetListBox
				.setModel(SelectedEventSetsListBox_Model);
		EventSetsPanel_SelectedEventSetListBox
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		EventSetsPanel_SelectedEventSetListBox
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						EventSetsPanel_SelectedEventSetListBoxMouseClicked(evt);
					}
				});
		EventSetsPanel_SelectedEventSetListBox
				.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
					public void mouseMoved(java.awt.event.MouseEvent evt) {
						EventSetsPanel_SelectedEventSetListBoxMouseMoved(evt);
					}
				});
		jScrollPane10.setViewportView(EventSetsPanel_SelectedEventSetListBox);

		EventSetsPanel_ParametersPanel.setBorder(javax.swing.BorderFactory
				.createEtchedBorder());

		javax.swing.GroupLayout EventSetsPanel_ParametersPanelLayout = new javax.swing.GroupLayout(
				EventSetsPanel_ParametersPanel);
		EventSetsPanel_ParametersPanel
				.setLayout(EventSetsPanel_ParametersPanelLayout);
		EventSetsPanel_ParametersPanelLayout
				.setHorizontalGroup(EventSetsPanel_ParametersPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGap(0, 344, Short.MAX_VALUE));
		EventSetsPanel_ParametersPanelLayout
				.setVerticalGroup(EventSetsPanel_ParametersPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGap(0, 300, Short.MAX_VALUE));

		EventSetsPanel_EventSetDescriptionTextBox.setColumns(20);
		EventSetsPanel_EventSetDescriptionTextBox.setLineWrap(true);
		EventSetsPanel_EventSetDescriptionTextBox.setRows(5);
		EventSetsPanel_EventSetDescriptionTextBox.setWrapStyleWord(true);
		jScrollPane6.setViewportView(EventSetsPanel_EventSetDescriptionTextBox);

		EventSetsPanel_AddEventSetButton.setText("\u2192");
		EventSetsPanel_AddEventSetButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						EventSetsPanel_AddEventSetButtonActionPerformed(evt);
					}
				});

		EventSetsPanel_RemoveEventSetButton.setText("\u2190");
		EventSetsPanel_RemoveEventSetButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						EventSetsPanel_RemoveEventSetButtonActionPerformed(evt);
					}
				});

		EventSetsPanel_AddAllEventSetsButton.setText("All");
		EventSetsPanel_AddAllEventSetsButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						EventSetsPanel_AddAllEventSetsButtonActionPerformed(evt);
					}
				});

		EventSetsPanel_RemoveAllEventSetsButton.setText("Clear");
		EventSetsPanel_RemoveAllEventSetsButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						EventSetsPanel_RemoveAllEventSetsButtonActionPerformed(evt);
					}
				});

		EventSetsPanel_SearchField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent evt){} // responds to style change. not applicable here.
			public void removeUpdate(DocumentEvent evt){
				searchModuleList("EventDriver");
			}
			public void insertUpdate(DocumentEvent evt){
				searchModuleList("EventDriver");
			}
		});

		javax.swing.GroupLayout JGAAP_EventSetsPanelLayout = new javax.swing.GroupLayout(
				JGAAP_EventSetsPanel);
		JGAAP_EventSetsPanel.setLayout(JGAAP_EventSetsPanelLayout);
		JGAAP_EventSetsPanelLayout
				.setHorizontalGroup(JGAAP_EventSetsPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								JGAAP_EventSetsPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												JGAAP_EventSetsPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jScrollPane6,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																824,
																Short.MAX_VALUE)
														.addComponent(
																jLabel8,
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																JGAAP_EventSetsPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				JGAAP_EventSetsPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jScrollPane9,
																								listboxMinWidth, // originally 178
																								listboxPrefWidth,
																								listboxMaxWidth)
																						.addComponent(
																								EventSetsPanel_SearchField,
																								0,
																								listboxPrefWidth,
																								listboxMaxWidth)
																						.addComponent(
																								jLabel6))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				JGAAP_EventSetsPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								false)
																						.addComponent(
																								EventSetsPanel_RemoveEventSetButton,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								EventSetsPanel_AddAllEventSetsButton,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								EventSetsPanel_AddEventSetButton,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								EventSetsPanel_RemoveAllEventSetsButton,
																								buttonMinWidth,
																								buttonMinWidth, // 64
																								buttonMinWidth))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				JGAAP_EventSetsPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jLabel9)
																						.addComponent(
																								jScrollPane10,
																								listboxMinWidth, // originally 216
																								listboxPrefWidth,
																								listboxMaxWidth))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				JGAAP_EventSetsPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								JGAAP_EventSetsPanelLayout
																										.createSequentialGroup()
																										.addComponent(
																												jLabel7)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																												163,
																												Short.MAX_VALUE)
																										.addComponent(
																												EventSetsPanel_NotesButton))
																						.addComponent(
																								EventSetsPanel_ParametersPanel,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE))))
										.addContainerGap()));
		JGAAP_EventSetsPanelLayout
				.setVerticalGroup(JGAAP_EventSetsPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								JGAAP_EventSetsPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												JGAAP_EventSetsPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																JGAAP_EventSetsPanelLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				jLabel6)
																		.addComponent(
																				jLabel9))
														.addGroup(
																JGAAP_EventSetsPanelLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.TRAILING)
																		.addComponent(
																				EventSetsPanel_NotesButton)
																		.addComponent(
																				jLabel7)))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												JGAAP_EventSetsPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																EventSetsPanel_ParametersPanel,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jScrollPane10,
																listboxMinHeight, // originally same as default size
																304,
																Short.MAX_VALUE)
														.addGroup(
																JGAAP_EventSetsPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																			jScrollPane9,
																			listboxMinHeight,
																			304,
																			Short.MAX_VALUE)
																		.addGap(searchFieldGap)
																		.addComponent(
																				EventSetsPanel_SearchField,
																				searchFieldMaxHeight,
																				searchFieldMaxHeight,
																				searchFieldMaxHeight))
														.addGroup(
																JGAAP_EventSetsPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				EventSetsPanel_AddEventSetButton)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				EventSetsPanel_RemoveEventSetButton)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				EventSetsPanel_AddAllEventSetsButton)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				EventSetsPanel_RemoveAllEventSetsButton)))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jLabel8)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane6,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												descriptionBoxPrefHeight,
												descriptionBoxMaxHeight)
										.addContainerGap()));

		JGAAP_TabbedPane.addTab("Event Drivers", JGAAP_EventSetsPanel);

		jLabel15.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
		jLabel15.setText("Event Culling");

		jLabel16.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
		jLabel16.setText("Parameters");

		jLabel17.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
		jLabel17.setText("Selected");

		EventCullingPanel_NotesButton.setLabel("Notes");
		EventCullingPanel_NotesButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						EventCullingPanel_NotesButtonActionPerformed(evt);
					}
				});

		EventCullingPanel_SelectedEventCullingListBox
				.setModel(SelectedEventCullingListBox_Model);
		EventCullingPanel_SelectedEventCullingListBox
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		EventCullingPanel_SelectedEventCullingListBox
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						EventCullingPanel_SelectedEventCullingListBoxMouseClicked(evt);
					}
				});
		EventCullingPanel_SelectedEventCullingListBox
				.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
					public void mouseMoved(java.awt.event.MouseEvent evt) {
						EventCullingPanel_SelectedEventCullingListBoxMouseMoved(evt);
					}
				});
		jScrollPane14
				.setViewportView(EventCullingPanel_SelectedEventCullingListBox);

		EventCullingPanel_AddEventCullingButton.setText("\u2192");
		EventCullingPanel_AddEventCullingButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						EventCullingPanel_AddEventCullingButtonActionPerformed(evt);
					}
				});

		EventCullingPanel_RemoveEventCullingButton.setText("\u2190");
		EventCullingPanel_RemoveEventCullingButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						EventCullingPanel_RemoveEventCullingButtonActionPerformed(evt);
					}
				});

		EventCullingPanel_AddAllEventCullingButton.setText("All");
		EventCullingPanel_AddAllEventCullingButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						EventCullingPanel_AddAllEventCullingButtonActionPerformed(evt);
					}
				});

		EventCullingPanel_RemoveAllEventCullingButton.setText("Clear");
		EventCullingPanel_RemoveAllEventCullingButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						EventCullingPanel_RemoveAllEventCullingButtonActionPerformed(evt);
					}
				});

		EventCullingPanel_ParametersPanel.setBorder(javax.swing.BorderFactory
				.createEtchedBorder());

		javax.swing.GroupLayout EventCullingPanel_ParametersPanelLayout = new javax.swing.GroupLayout(
				EventCullingPanel_ParametersPanel);
		EventCullingPanel_ParametersPanel
				.setLayout(EventCullingPanel_ParametersPanelLayout);
		EventCullingPanel_ParametersPanelLayout
				.setHorizontalGroup(EventCullingPanel_ParametersPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGap(0, 343, Short.MAX_VALUE));
		EventCullingPanel_ParametersPanelLayout
				.setVerticalGroup(EventCullingPanel_ParametersPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGap(0, 300, Short.MAX_VALUE));

		EventCullingPanel_EventCullingListBox
				.setModel(EventCullingListBox_Model);
		EventCullingPanel_EventCullingListBox
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		EventCullingPanel_EventCullingListBox
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						EventCullingPanel_EventCullingListBoxMouseClicked(evt);
					}
				});
		EventCullingPanel_EventCullingListBox
				.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
					public void mouseMoved(java.awt.event.MouseEvent evt) {
						EventCullingPanel_EventCullingListBoxMouseMoved(evt);
					}
				});
		jScrollPane15.setViewportView(EventCullingPanel_EventCullingListBox);

		EventCullingPanel_EventCullingDescriptionTextbox.setColumns(20);
		EventCullingPanel_EventCullingDescriptionTextbox.setLineWrap(true);
		EventCullingPanel_EventCullingDescriptionTextbox.setRows(5);
		EventCullingPanel_EventCullingDescriptionTextbox.setWrapStyleWord(true);
		jScrollPane16
				.setViewportView(EventCullingPanel_EventCullingDescriptionTextbox);

		jLabel18.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
		jLabel18.setText("Event Culling Description");

		EventCullingPanel_SearchField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent evt){} // responds to style change. not applicable here.
			public void removeUpdate(DocumentEvent evt){
				searchModuleList("EventCulling");
			}
			public void insertUpdate(DocumentEvent evt){
				searchModuleList("EventCulling");
			}
		});
		

		javax.swing.GroupLayout JGAAP_EventCullingPanelLayout = new javax.swing.GroupLayout(
				JGAAP_EventCullingPanel);
		JGAAP_EventCullingPanel.setLayout(JGAAP_EventCullingPanelLayout);
		JGAAP_EventCullingPanelLayout
				.setHorizontalGroup(JGAAP_EventCullingPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								JGAAP_EventCullingPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												JGAAP_EventCullingPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jScrollPane16,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																824,
																Short.MAX_VALUE)
														.addComponent(
																jLabel18,
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																JGAAP_EventCullingPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				JGAAP_EventCullingPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jScrollPane15,
																								listboxMinWidth, // originall 178
																								listboxPrefWidth,
																								listboxMaxWidth)
																						.addComponent(
																									EventCullingPanel_SearchField,
																									0,
																									listboxPrefWidth,
																									listboxMaxWidth)
																						.addComponent(
																								jLabel15))
																		
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				JGAAP_EventCullingPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								false)
																						.addComponent(
																								EventCullingPanel_RemoveEventCullingButton,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								EventCullingPanel_AddAllEventCullingButton,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								EventCullingPanel_AddEventCullingButton,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								EventCullingPanel_RemoveAllEventCullingButton,
																								buttonMinWidth,
																								buttonMinWidth, // originally 64
																								buttonMinWidth))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				JGAAP_EventCullingPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jLabel17)
																						.addComponent(
																								jScrollPane14,
																								listboxMinWidth, // originall 217
																								listboxPrefWidth,
																								listboxMaxWidth))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				JGAAP_EventCullingPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addGroup(
																								JGAAP_EventCullingPanelLayout
																										.createSequentialGroup()
																										.addComponent(
																												jLabel16)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																												162,
																												Short.MAX_VALUE)
																										.addComponent(
																												EventCullingPanel_NotesButton))
																						.addComponent(
																								EventCullingPanel_ParametersPanel,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE))))
										.addContainerGap()));
		JGAAP_EventCullingPanelLayout
				.setVerticalGroup(JGAAP_EventCullingPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								JGAAP_EventCullingPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												JGAAP_EventCullingPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																JGAAP_EventCullingPanelLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				jLabel15)
																		.addComponent(
																				jLabel17)
																		.addComponent(
																				jLabel16))
														.addComponent(
																EventCullingPanel_NotesButton))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												JGAAP_EventCullingPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jScrollPane14,
																listboxMinHeight, // originally same as default
																304,
																Short.MAX_VALUE)
														.addComponent(
																EventCullingPanel_ParametersPanel,
																listboxMinHeight, // originally same as default
																304,
																Short.MAX_VALUE)
														.addGroup(JGAAP_EventCullingPanelLayout
																.createSequentialGroup()
																.addComponent(
																	jScrollPane15,
																	listboxMinHeight, // originally same as default
																	304,
																	Short.MAX_VALUE)
																.addGap(searchFieldGap)
																.addComponent(
																		EventCullingPanel_SearchField,
																		searchFieldMaxHeight,
																		searchFieldMaxHeight,
																		searchFieldMaxHeight)
														)
														.addGroup(
																JGAAP_EventCullingPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				EventCullingPanel_AddEventCullingButton)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				EventCullingPanel_RemoveEventCullingButton)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				EventCullingPanel_AddAllEventCullingButton)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				EventCullingPanel_RemoveAllEventCullingButton)
																		.addGap(107,
																				107,
																				107)))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jLabel18)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane16,
												descriptionBoxMinHeight,
												descriptionBoxPrefHeight,
												descriptionBoxMaxHeight)
										.addContainerGap()));

		JGAAP_TabbedPane.addTab("Event Culling", JGAAP_EventCullingPanel);

		jLabel20.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
		jLabel20.setText("Analysis Methods");

		jLabel21.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
		jLabel21.setText("AM Parameters");

		jLabel22.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
		jLabel22.setText("Selected");

		AnalysisMethodPanel_NotesButton.setLabel("Notes");
		AnalysisMethodPanel_NotesButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						AnalysisMethodPanel_NotesButtonActionPerformed(evt);
					}
				});

		AnalysisMethodPanel_SelectedAnalysisMethodsListBox
				.setModel(SelectedAnalysisMethodListBox_Model);
		AnalysisMethodPanel_SelectedAnalysisMethodsListBox
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		AnalysisMethodPanel_SelectedAnalysisMethodsListBox
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						AnalysisMethodPanel_SelectedAnalysisMethodsListBoxMouseClicked(evt);
					}
				});
		AnalysisMethodPanel_SelectedAnalysisMethodsListBox
				.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
					public void mouseMoved(java.awt.event.MouseEvent evt) {
						AnalysisMethodPanel_SelectedAnalysisMethodsListBoxMouseMoved(evt);
					}
				});
		jScrollPane17
				.setViewportView(AnalysisMethodPanel_SelectedAnalysisMethodsListBox);

		AnalysisMethodPanel_AddAnalysisMethodButton.setText("\u2192");
		AnalysisMethodPanel_AddAnalysisMethodButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						AnalysisMethodPanel_AddAnalysisMethodButtonActionPerformed(evt);
					}
				});

		AnalysisMethodPanel_RemoveAnalysisMethodsButton.setText("\u2190");
		AnalysisMethodPanel_RemoveAnalysisMethodsButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						AnalysisMethodPanel_RemoveAnalysisMethodsButtonActionPerformed(evt);
					}
				});

		AnalysisMethodPanel_AddAllAnalysisMethodsButton.setText("All");
		AnalysisMethodPanel_AddAllAnalysisMethodsButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						AnalysisMethodPanel_AddAllAnalysisMethodsButtonActionPerformed(evt);
					}
				});

		AnalysisMethodPanel_RemoveAllAnalysisMethodsButton.setText("Clear");
		AnalysisMethodPanel_RemoveAllAnalysisMethodsButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						AnalysisMethodPanel_RemoveAllAnalysisMethodsButtonActionPerformed(evt);
					}
				});

		AnalysisMethodPanel_AMParametersPanel
				.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		javax.swing.GroupLayout AnalysisMethodPanel_AMParametersPanelLayout = new javax.swing.GroupLayout(
				AnalysisMethodPanel_AMParametersPanel);
		AnalysisMethodPanel_AMParametersPanel
				.setLayout(AnalysisMethodPanel_AMParametersPanelLayout);
		AnalysisMethodPanel_AMParametersPanelLayout
				.setHorizontalGroup(AnalysisMethodPanel_AMParametersPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGap(0, 355, Short.MAX_VALUE));
		AnalysisMethodPanel_AMParametersPanelLayout
				.setVerticalGroup(AnalysisMethodPanel_AMParametersPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGap(0, 125, Short.MAX_VALUE));

		AnalysisMethodPanel_AnalysisMethodsListBox
				.setModel(AnalysisMethodListBox_Model);
		AnalysisMethodPanel_AnalysisMethodsListBox
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		AnalysisMethodPanel_AnalysisMethodsListBox
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						AnalysisMethodPanel_AnalysisMethodsListBoxMouseClicked(evt);
					}
				});
		AnalysisMethodPanel_AnalysisMethodsListBox
				.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
					public void mouseMoved(java.awt.event.MouseEvent evt) {
						AnalysisMethodPanel_AnalysisMethodsListBoxMouseMoved(evt);
					}
				});
		AnalysisMethodPanel_AnalysisMethodsListBox.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt){
				updateDistanceListUseability();
			}
		});
		AnalysisMethodPanel_AnalysisMethodsListBox.addKeyListener(new java.awt.event.KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				//do nothing
			}

			@Override
			public void keyReleased(KeyEvent e) {
				updateDistanceListUseability();
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// do nothing
			}
			
		});
		jScrollPane18
				.setViewportView(AnalysisMethodPanel_AnalysisMethodsListBox);

		jLabel28.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
		jLabel28.setText("Distance Function Description");

		AnalysisMethodPanel_AnalysisMethodDescriptionTextBox.setColumns(20);
		AnalysisMethodPanel_AnalysisMethodDescriptionTextBox.setLineWrap(true);
		AnalysisMethodPanel_AnalysisMethodDescriptionTextBox.setRows(5);
		AnalysisMethodPanel_AnalysisMethodDescriptionTextBox
				.setWrapStyleWord(true);
		jScrollPane19
				.setViewportView(AnalysisMethodPanel_AnalysisMethodDescriptionTextBox);

		AnalysisMethodPanel_DistanceFunctionsListBox
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		AnalysisMethodPanel_DistanceFunctionsListBox
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						AnalysisMethodPanel_DistanceFunctionsListBoxMouseClicked(evt);
					}
				});
		AnalysisMethodPanel_DistanceFunctionsListBox
				.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
					public void mouseMoved(java.awt.event.MouseEvent evt) {
						AnalysisMethodPanel_DistanceFunctionsListBoxMouseMoved(evt);
					}
				});
		jScrollPane22
				.setViewportView(AnalysisMethodPanel_DistanceFunctionsListBox);

		lblDistanceFunctions.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N

		AnalysisMethodPanel_DistanceFunctionDescriptionTextBox.setColumns(20);
		AnalysisMethodPanel_DistanceFunctionDescriptionTextBox
				.setLineWrap(true);
		AnalysisMethodPanel_DistanceFunctionDescriptionTextBox.setRows(5);
		AnalysisMethodPanel_DistanceFunctionDescriptionTextBox
				.setWrapStyleWord(true);
		jScrollPane23
				.setViewportView(AnalysisMethodPanel_DistanceFunctionDescriptionTextBox);

		jLabel36.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
		jLabel36.setText("Analysis Method Description");

		AnalysisMethodPanel_DFParametersPanel
				.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		javax.swing.GroupLayout AnalysisMethodPanel_DFParametersPanelLayout = new javax.swing.GroupLayout(
				AnalysisMethodPanel_DFParametersPanel);
		AnalysisMethodPanel_DFParametersPanel
				.setLayout(AnalysisMethodPanel_DFParametersPanelLayout);
		AnalysisMethodPanel_DFParametersPanelLayout
				.setHorizontalGroup(AnalysisMethodPanel_DFParametersPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGap(0, 355, Short.MAX_VALUE));
		AnalysisMethodPanel_DFParametersPanelLayout
				.setVerticalGroup(AnalysisMethodPanel_DFParametersPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGap(0, 128, Short.MAX_VALUE));

		jLabel37.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
		jLabel37.setText("DF Parameters");

		AnalysisMethodPanel_AMDFSearchField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent evt){} // responds to style change. not applicable here.
			public void removeUpdate(DocumentEvent evt){
				searchModuleList("AnalysisMethod");
			}
			public void insertUpdate(DocumentEvent evt){
				searchModuleList("AnalysisMethod");
			}
		});

		javax.swing.GroupLayout JGAAP_AnalysisMethodPanelLayout = new javax.swing.GroupLayout(
				JGAAP_AnalysisMethodPanel);
		JGAAP_AnalysisMethodPanel.setLayout(JGAAP_AnalysisMethodPanelLayout);
		JGAAP_AnalysisMethodPanelLayout
				.setHorizontalGroup(JGAAP_AnalysisMethodPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								JGAAP_AnalysisMethodPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												JGAAP_AnalysisMethodPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(
																JGAAP_AnalysisMethodPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				JGAAP_AnalysisMethodPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								lblDistanceFunctions,
																								listboxMinWidth,
																								listboxPrefWidth,
																								listboxMaxWidth)
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								JGAAP_AnalysisMethodPanelLayout
																										.createSequentialGroup()
																										.addGroup(
																												JGAAP_AnalysisMethodPanelLayout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.TRAILING)
																														.addComponent(
																																jScrollPane22,
																																javax.swing.GroupLayout.Alignment.LEADING,
																																listboxMinWidth,
																																listboxPrefWidth,
																																listboxMaxWidth)
																														.addComponent(
																																jScrollPane18,
																																javax.swing.GroupLayout.Alignment.LEADING,
																																listboxMinWidth,
																																listboxPrefWidth,
																																listboxMaxWidth)
																														.addComponent(
																																jLabel20,
																																javax.swing.GroupLayout.Alignment.LEADING,
																																listboxMinWidth,
																																listboxPrefWidth,
																																listboxMaxWidth)
																														.addComponent(
																																AnalysisMethodPanel_AMDFSearchField,
																																listboxMinWidth,
																																listboxPrefWidth,
																																listboxMaxWidth))
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addGroup(
																												JGAAP_AnalysisMethodPanelLayout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.TRAILING,
																																false)
																														.addComponent(
																																AnalysisMethodPanel_RemoveAnalysisMethodsButton,
																																javax.swing.GroupLayout.Alignment.LEADING,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE)
																														.addComponent(
																																AnalysisMethodPanel_AddAllAnalysisMethodsButton,
																																javax.swing.GroupLayout.Alignment.LEADING,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE)
																														.addComponent(
																																AnalysisMethodPanel_AddAnalysisMethodButton,
																																javax.swing.GroupLayout.Alignment.LEADING,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE)
																														.addComponent(
																																AnalysisMethodPanel_RemoveAllAnalysisMethodsButton,
																																buttonMinWidth,
																																buttonMinWidth, // originally 64
																																buttonMinWidth))))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				JGAAP_AnalysisMethodPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jLabel22)
																						.addComponent(
																								jScrollPane17,
																								listboxMinWidth, // originally 196
																								listboxPrefWidth,
																								listboxMaxWidth))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				JGAAP_AnalysisMethodPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								AnalysisMethodPanel_DFParametersPanel,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								AnalysisMethodPanel_AMParametersPanel,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addGroup(
																								JGAAP_AnalysisMethodPanelLayout
																										.createSequentialGroup()
																										.addComponent(
																												jLabel21)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																												133,
																												Short.MAX_VALUE)
																										.addComponent(
																												AnalysisMethodPanel_NotesButton))
																						.addComponent(
																								jLabel37)))
														.addGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																JGAAP_AnalysisMethodPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				JGAAP_AnalysisMethodPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jScrollPane19,
																								100,
																								descriptionBoxPrefHeight,
																								Short.MAX_VALUE)
																						.addComponent(
																								jLabel36))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				JGAAP_AnalysisMethodPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jLabel28)
																						.addComponent(
																								jScrollPane23,
																								100,
																								descriptionBoxPrefHeight,
																								Short.MAX_VALUE))))
										.addContainerGap()));
		JGAAP_AnalysisMethodPanelLayout
				.setVerticalGroup(JGAAP_AnalysisMethodPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								JGAAP_AnalysisMethodPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												JGAAP_AnalysisMethodPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel20)
														.addComponent(jLabel21)
														.addComponent(jLabel22)
														.addComponent(
																AnalysisMethodPanel_NotesButton))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												JGAAP_AnalysisMethodPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jScrollPane17,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																301,
																Short.MAX_VALUE)
														.addGroup(
																JGAAP_AnalysisMethodPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				AnalysisMethodPanel_AMParametersPanel,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jLabel37)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				AnalysisMethodPanel_DFParametersPanel,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE))
														.addGroup(
																JGAAP_AnalysisMethodPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				JGAAP_AnalysisMethodPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								JGAAP_AnalysisMethodPanelLayout
																										.createSequentialGroup()
																										.addComponent(
																												AnalysisMethodPanel_AddAnalysisMethodButton)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												AnalysisMethodPanel_RemoveAnalysisMethodsButton)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												AnalysisMethodPanel_AddAllAnalysisMethodsButton)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												AnalysisMethodPanel_RemoveAllAnalysisMethodsButton))
																						.addComponent(
																								jScrollPane18,
																								100,
																								130,
																								Short.MAX_VALUE // originally set to same as pref
																								))
																		.addGap(searchFieldGap)
																		.addComponent(
																				AnalysisMethodPanel_AMDFSearchField,
																				searchFieldMaxHeight,
																				searchFieldMaxHeight,
																				searchFieldMaxHeight)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				lblDistanceFunctions)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jScrollPane22,
																				100,
																				130,
																				Short.MAX_VALUE)))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												JGAAP_AnalysisMethodPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel28)
														.addComponent(jLabel36))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												JGAAP_AnalysisMethodPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jScrollPane19,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																descriptionBoxPrefHeight,
																descriptionBoxMaxHeight)
														.addComponent(
																jScrollPane23,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																descriptionBoxPrefHeight,
																descriptionBoxMaxHeight))
										.addContainerGap()));

		JGAAP_TabbedPane.addTab("Analysis Methods", JGAAP_AnalysisMethodPanel);

		ReviewPanel_ProcessButton.setText("Process");
		ReviewPanel_ProcessButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						ReviewPanel_ProcessButtonActionPerformed(evt);
					}
				});

		ReviewPanel_DocumentsLabel.setFont(new java.awt.Font(
				"Microsoft Sans Serif", 0, 24)); // NOI18N
		ReviewPanel_DocumentsLabel.setText("Canonicizers");
		ReviewPanel_DocumentsLabel
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						ReviewPanel_DocumentsLabelMouseClicked(evt);
					}
				});

		ReviewPanel_CanonicizersListBox.setModel(SelectedCanonicizerListBox_Model);
		jScrollPane24.setViewportView(ReviewPanel_CanonicizersListBox);
		
		ReviewPanel_SelectedEventSetLabel.setFont(new java.awt.Font(
				"Microsoft Sans Serif", 0, 24)); // NOI18N
		ReviewPanel_SelectedEventSetLabel.setText("Event Driver");
		ReviewPanel_SelectedEventSetLabel
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						ReviewPanel_SelectedEventSetLabelMouseClicked(evt);
					}
				});

		ReviewPanel_SelectedEventCullingLabel.setFont(new java.awt.Font(
				"Microsoft Sans Serif", 0, 24)); // NOI18N
		ReviewPanel_SelectedEventCullingLabel.setText("Event Culling");
		ReviewPanel_SelectedEventCullingLabel
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						ReviewPanel_SelectedEventCullingLabelMouseClicked(evt);
					}
				});

		ReviewPanel_SelectedAnalysisMethodsLabel.setFont(new java.awt.Font(
				"Microsoft Sans Serif", 0, 24)); // NOI18N
		ReviewPanel_SelectedAnalysisMethodsLabel.setText("Analysis Methods");
		ReviewPanel_SelectedAnalysisMethodsLabel
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						ReviewPanel_SelectedAnalysisMethodsLabelMouseClicked(evt);
					}
				});

		ReviewPanel_SelectedEventSetListBox
				.setModel(SelectedEventSetsListBox_Model);
		ReviewPanel_SelectedEventSetListBox
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		ReviewPanel_SelectedEventSetListBox.setEnabled(false);
		ReviewPanel_SelectedEventSetListBox
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						ReviewPanel_SelectedEventSetListBoxMouseClicked(evt);
					}
				});
		jScrollPane25.setViewportView(ReviewPanel_SelectedEventSetListBox);

		ReviewPanel_SelectedEventCullingListBox
				.setModel(SelectedEventCullingListBox_Model);
		ReviewPanel_SelectedEventCullingListBox
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		ReviewPanel_SelectedEventCullingListBox.setEnabled(false);
		ReviewPanel_SelectedEventCullingListBox
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						ReviewPanel_SelectedEventCullingListBoxMouseClicked(evt);
					}
				});
		jScrollPane26.setViewportView(ReviewPanel_SelectedEventCullingListBox);

		ReviewPanel_SelectedAnalysisMethodsListBox
				.setModel(SelectedAnalysisMethodListBox_Model);
		ReviewPanel_SelectedAnalysisMethodsListBox
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		ReviewPanel_SelectedAnalysisMethodsListBox.setEnabled(false);
		ReviewPanel_SelectedAnalysisMethodsListBox
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						ReviewPanel_SelectedAnalysisMethodsListBoxMouseClicked(evt);
					}
				});
		jScrollPane27
				.setViewportView(ReviewPanel_SelectedAnalysisMethodsListBox);

		javax.swing.GroupLayout JGAAP_ReviewPanelLayout = new javax.swing.GroupLayout(
				JGAAP_ReviewPanel);
		JGAAP_ReviewPanel.setLayout(JGAAP_ReviewPanelLayout);
		JGAAP_ReviewPanelLayout
				.setHorizontalGroup(JGAAP_ReviewPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								JGAAP_ReviewPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												JGAAP_ReviewPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jScrollPane24,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																824,
																Short.MAX_VALUE)
														.addComponent(
																ReviewPanel_DocumentsLabel,
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																ReviewPanel_ProcessButton)
														.addGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																JGAAP_ReviewPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				JGAAP_ReviewPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jScrollPane25,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								273,
																								Short.MAX_VALUE)
																						.addComponent(
																								ReviewPanel_SelectedEventSetLabel))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				JGAAP_ReviewPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								ReviewPanel_SelectedEventCullingLabel)
																						.addComponent(
																								jScrollPane26,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								268,
																								Short.MAX_VALUE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				JGAAP_ReviewPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								ReviewPanel_SelectedAnalysisMethodsLabel)
																						.addComponent(
																								jScrollPane27,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								271,
																								Short.MAX_VALUE))))
										.addContainerGap()));
		JGAAP_ReviewPanelLayout
				.setVerticalGroup(JGAAP_ReviewPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								JGAAP_ReviewPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												ReviewPanel_DocumentsLabel)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane24,
												50,
												118,
												Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												JGAAP_ReviewPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																ReviewPanel_SelectedEventSetLabel)
														.addComponent(
																ReviewPanel_SelectedEventCullingLabel)
														.addComponent(
																ReviewPanel_SelectedAnalysisMethodsLabel))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												JGAAP_ReviewPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jScrollPane27,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																258,
																Short.MAX_VALUE)
														.addComponent(
																jScrollPane26,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																258,
																Short.MAX_VALUE)
														.addComponent(
																jScrollPane25,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																258,
																Short.MAX_VALUE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(ReviewPanel_ProcessButton)
										.addContainerGap()));

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
		BatchSaveMenuItem
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						BatchSaveMenuItemActionPerformed(evt);
					}
				});
		jMenu4.add(BatchSaveMenuItem);

		BatchLoadMenuItem.setText("Load Documents");
		BatchLoadMenuItem
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						BatchLoadMenuItemActionPerformed(evt);
					}
				});
		jMenu4.add(BatchLoadMenuItem);

		jMenu1.add(jMenu4);

		jMenu2.setText("AAAC Problems");

		ProblemAMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_A,
				(java.awt.event.InputEvent.CTRL_MASK) | InputEvent.SHIFT_MASK));
		ProblemBMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_B,
				(java.awt.event.InputEvent.CTRL_MASK) | InputEvent.SHIFT_MASK));
		ProblemCMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_C,
				(java.awt.event.InputEvent.CTRL_MASK) | InputEvent.SHIFT_MASK));
		ProblemDMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_D,
				(java.awt.event.InputEvent.CTRL_MASK) | InputEvent.SHIFT_MASK));
		ProblemEMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				KeyEvent.VK_E, (java.awt.event.InputEvent.CTRL_MASK)
						| InputEvent.SHIFT_MASK));
		ProblemFMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_F,
				(java.awt.event.InputEvent.CTRL_MASK) | InputEvent.SHIFT_MASK));
		ProblemGMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_G,
				(java.awt.event.InputEvent.CTRL_MASK) | InputEvent.SHIFT_MASK));
		ProblemHMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_H,
				(java.awt.event.InputEvent.CTRL_MASK) | InputEvent.SHIFT_MASK));
		ProblemIMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_I,
				(java.awt.event.InputEvent.CTRL_MASK) | InputEvent.SHIFT_MASK));
		ProblemJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_J,
				(java.awt.event.InputEvent.CTRL_MASK) | InputEvent.SHIFT_MASK));
		ProblemKMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_K,
				(java.awt.event.InputEvent.CTRL_MASK) | InputEvent.SHIFT_MASK));
		ProblemLMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_L,
				(java.awt.event.InputEvent.CTRL_MASK) | InputEvent.SHIFT_MASK));
		ProblemMMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_M,
				(java.awt.event.InputEvent.CTRL_MASK) | InputEvent.SHIFT_MASK));

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

		exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_Q,
				java.awt.event.InputEvent.CTRL_MASK));
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

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(
														JGAAP_TabbedPane,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														849, Short.MAX_VALUE)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		Review_Button)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		Next_Button)))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(JGAAP_TabbedPane,
										0,
										javax.swing.GroupLayout.PREFERRED_SIZE, // original min and max size
										//529, original pref size
										Short.MAX_VALUE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(Next_Button)
												.addComponent(Review_Button))
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										// Short.MAX_VALUE <- original max size
										10
										)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void ReviewPanel_ProcessButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ReviewPanel_ProcessButtonActionPerformed
		try {
			JGAAP_API.clearData();
			JGAAP_API.clearCanonicizers();
			for(Pair<Canonicizer, Object> canonicizerPair : SelectedCanonicizerList){
				if(canonicizerPair.getSecond() instanceof Document.Type){
					JGAAP_API.addCanonicizer(canonicizerPair.getFirst().displayName(), (Document.Type)canonicizerPair.getSecond());
				} else if(canonicizerPair.getSecond() instanceof Document){
					JGAAP_API.addCanonicizer(canonicizerPair.getFirst().displayName(), (Document)canonicizerPair.getSecond());
				} else {
					JGAAP_API.addCanonicizer(canonicizerPair.getFirst().displayName());
				}
			}
			JGAAP_API.execute();
			List<Document> documents = JGAAP_API.getDocuments();
			StringBuilder buffer = new StringBuilder();
			for (Document document : documents) {
				String result = document.getResult();
				buffer.append(result);
			}
			// ResultsPage.DisplayResults(buffer.toString());
			ResultsPage.addResults(buffer.toString());
			ResultsPage.setVisible(true);
		} catch (Exception e) {
			if (e.getMessage() == null) {
				JOptionPane
						.showMessageDialog(this,
								"Experiment failed to complete.\nReview Error logs for more information.\n(Run JGAAP from the terminal to view logs using java -jar jgaap.jar or ant run-gui)",
								"JGAAP Error", JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this,
						"Experiment failed to complete.\nDetailed information is available below:\n"
								+ e.toString(), "JGAAP Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		CanonicizersPanel_SearchField.setText("");
		EventSetsPanel_SearchField.setText("");
		EventCullingPanel_SearchField.setText("");
		AnalysisMethodPanel_AMDFSearchField.setText("");
	}// GEN-LAST:event_ReviewPanel_ProcessButtonActionPerformed

	private void BatchLoadMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BatchLoadMenuItemActionPerformed
		FileChoser = new JFileChooser(filepath);
		int choice = FileChoser.showOpenDialog(JGAAP_UI_MainForm.this);
		if (choice == JFileChooser.APPROVE_OPTION) {
			try {
				filepath = FileChoser.getSelectedFile().getCanonicalPath();
				List<List<String>> DocumentCSVs = CSVIO.readCSV(filepath);
				for (int i = 0; i < DocumentCSVs.size(); i++) {
					JGAAP_API.addDocument(DocumentCSVs.get(i).get(1),
							DocumentCSVs.get(i).get(0), (DocumentCSVs.get(i)
									.size() > 2 ? DocumentCSVs.get(i).get(2)
									: null));
				}
				UpdateKnownDocumentsTree();
				UpdateUnknownDocumentsTable();
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, e.getMessage(),
						"JGAAP Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}// GEN-LAST:event_BatchLoadMenuItemActionPerformed

	private void BatchSaveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BatchSaveMenuItemActionPerformed
		FileChoser = new JFileChooser(filepath);
		int choice = FileChoser.showSaveDialog(JGAAP_UI_MainForm.this);
		if (choice == JFileChooser.APPROVE_OPTION) {
			try {
				JGAAP_API.loadDocuments();
				DocumentList = JGAAP_API.getDocuments();
				Utils.saveDocumentsToCSV(DocumentList,
						FileChoser.getSelectedFile());
			} catch (Exception e) {
				logger.error("Unable to save documents to csv", e);
				JOptionPane.showMessageDialog(this,
						"Unable to save documents to csv", "JGAAP Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}// GEN-LAST:event_BatchSaveMenuItemActionPerformed

	private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_exitMenuItemActionPerformed
		dispose();
		System.exit(0);
	}// GEN-LAST:event_exitMenuItemActionPerformed

	private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_aboutMenuItemActionPerformed
		AboutPage.setVisible(true);
	}// GEN-LAST:event_aboutMenuItemActionPerformed

	private void AnalysisMethodPanel_AnalysisMethodsListBoxMouseMoved(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_AnalysisMethodPanel_AnalysisMethodsListBoxMouseMoved
		JList theList = (JList) evt.getSource();
		int index = theList.locationToIndex(evt.getPoint());
		if (index > -1) {
			String text = AnalysisDriverMasterList.get(index).tooltipText();
			theList.setToolTipText(text);
		}
	}// GEN-LAST:event_AnalysisMethodPanel_AnalysisMethodsListBoxMouseMoved

	private void AnalysisMethodPanel_AnalysisMethodsListBoxMouseClicked(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_AnalysisMethodPanel_AnalysisMethodsListBoxMouseClicked
		try {
			AnalysisMethodPanel_AnalysisMethodDescriptionTextBox.setText(
				AnalysisDrivers.getAnalysisDriver(
					AnalysisMethodPanel_AnalysisMethodsListBox.getSelectedValue().toString()
				).longDescription()
			);
		} catch (Exception e) {
			logger.error("Failed to get selected analysis method");
			e.printStackTrace();
		}
		if (evt.getClickCount() == 2) {
			AddAnalysisMethodSelection();
		}
		updateDistanceListUseability();
	}// GEN-LAST:event_AnalysisMethodPanel_AnalysisMethodsListBoxMouseClicked

	private void AnalysisMethodPanel_RemoveAllAnalysisMethodsButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_AnalysisMethodPanel_RemoveAllAnalysisMethodsButtonActionPerformed
		JGAAP_API.removeAllAnalysisDrivers();
		UpdateSelectedAnalysisMethodListBox();
	}// GEN-LAST:event_AnalysisMethodPanel_RemoveAllAnalysisMethodsButtonActionPerformed

	private void AnalysisMethodPanel_AddAllAnalysisMethodsButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_AnalysisMethodPanel_AddAllAnalysisMethodsButtonActionPerformed
		try {
			// instead of drawing from master list, draw from what's shown in the available listbox,
			// this way the "all" button would add only those that matched user search terms
			for (int i = 0; i < AnalysisMethodListBox_Model.size(); i++) {
				JGAAP_API.addAnalysisDriver(AnalysisMethodListBox_Model.elementAt(i).toString());
			}
			UpdateSelectedAnalysisMethodListBox();
		} catch (Exception e) {
			logger.error("Problem adding all analysis drivers", e);
			JOptionPane.showMessageDialog(this,
					"Problem adding all anaysis drivers", "JGAAP Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}// GEN-LAST:event_AnalysisMethodPanel_AddAllAnalysisMethodsButtonActionPerformed

	private void AnalysisMethodPanel_RemoveAnalysisMethodsButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_AnalysisMethodPanel_RemoveAnalysisMethodsButtonActionPerformed
		RemoveAnalysisMethodSelection();
	}// GEN-LAST:event_AnalysisMethodPanel_RemoveAnalysisMethodsButtonActionPerformed

	private void RemoveAnalysisMethodSelection() {
		SelectedAnalysisDriverList = JGAAP_API.getAnalysisDrivers();
		JGAAP_API.removeAnalysisDriver(SelectedAnalysisDriverList
				.get(AnalysisMethodPanel_SelectedAnalysisMethodsListBox
						.getSelectedIndex()));
		UpdateSelectedAnalysisMethodListBox();
	}

	private void AnalysisMethodPanel_AddAnalysisMethodButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_AnalysisMethodPanel_AddAnalysisMethodButtonActionPerformed
		AddAnalysisMethodSelection();
	}// GEN-LAST:event_AnalysisMethodPanel_AddAnalysisMethodButtonActionPerformed

	private void CheckMinimumRequirements() {
		boolean OK = true;
		
		ReviewPanel_DocumentsLabel.setForeground(Color.GREEN.darker());

		if (SelectedEventDriverList.isEmpty()) {
			OK = false;
			ReviewPanel_SelectedEventSetLabel.setForeground(Color.RED);
		} else {
			ReviewPanel_SelectedEventSetLabel.setForeground(Color.GREEN
					.darker());
		}

		ReviewPanel_SelectedEventCullingLabel.setForeground(Color.GREEN
				.darker());

		if (SelectedAnalysisDriverList.isEmpty()) {
			OK = false;
			ReviewPanel_SelectedAnalysisMethodsLabel.setForeground(Color.RED);
		} else {
			ReviewPanel_SelectedAnalysisMethodsLabel.setForeground(Color.GREEN
					.darker());
		}

		ReviewPanel_ProcessButton.setEnabled(OK);
	}

	private void AddAnalysisMethodSelection() {
		try {
			AnalysisDriver temp = JGAAP_API
					.addAnalysisDriver(AnalysisMethodPanel_AnalysisMethodsListBox
							.getSelectedValue().toString());
			if (temp instanceof NeighborAnalysisDriver) {
				// If the analysis driver that was selected requires a distance,
				// add the selected distance function.
				JGAAP_API.addDistanceFunction(
						AnalysisMethodPanel_DistanceFunctionsListBox
								.getSelectedValue().toString(), temp);
			}
			else if (temp instanceof NonDistanceDependentAnalysisDriver) {
				// If the analysis driver that was selected is dependent on
				// another analysis driver being selected, add the one that
				// that is selected.
				JGAAP_API.addAnalysisDriverAsParamToOther(
						AnalysisMethodPanel_DistanceFunctionsListBox
						.getSelectedValue().toString(), (NonDistanceDependentAnalysisDriver) temp);
			}
			UpdateSelectedAnalysisMethodListBox();
		} catch (Exception e) {
			logger.error("Error adding analysis", e);
			JOptionPane.showMessageDialog(this, "Error adding analysis",
					"JGAAP Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void AnalysisMethodPanel_SelectedAnalysisMethodsListBoxMouseMoved(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_AnalysisMethodPanel_SelectedAnalysisMethodsListBoxMouseMoved
		JList theList = (JList) evt.getSource();
		int index = theList.locationToIndex(evt.getPoint());
		if (index > -1) {
			String text = SelectedAnalysisDriverList.get(index).tooltipText();
			theList.setToolTipText(text);
		}
	}// GEN-LAST:event_AnalysisMethodPanel_SelectedAnalysisMethodsListBoxMouseMoved

	private void AnalysisMethodPanel_SelectedAnalysisMethodsListBoxMouseClicked(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_AnalysisMethodPanel_SelectedAnalysisMethodsListBoxMouseClicked
		if (!SelectedAnalysisDriverList.isEmpty()) {
			AnalysisDriver temp = SelectedAnalysisDriverList
					.get(AnalysisMethodPanel_SelectedAnalysisMethodsListBox
							.getSelectedIndex());
			AnalysisMethodPanel_AMParametersPanel.removeAll();
			AnalysisMethodPanel_DFParametersPanel.removeAll();
			AnalysisMethodPanel_AMParametersPanel.setLayout(temp
					.getGUILayout(AnalysisMethodPanel_AMParametersPanel));
			if (temp instanceof NeighborAnalysisDriver) {
				AnalysisMethodPanel_DFParametersPanel
						.setLayout(((NeighborAnalysisDriver) temp)
								.getDistanceFunction().getGUILayout(
										AnalysisMethodPanel_DFParametersPanel));
			}
			AnalysisMethodPanel_AnalysisMethodDescriptionTextBox.setText(temp
					.longDescription());
			if (temp instanceof NeighborAnalysisDriver) {
				AnalysisMethodPanel_DistanceFunctionDescriptionTextBox
						.setText(((NeighborAnalysisDriver) temp)
								.getDistanceFunction().longDescription());
			}
			if (evt != null && evt.getClickCount() == 2) {
				RemoveAnalysisMethodSelection();
			}
		}
	}// GEN-LAST:event_AnalysisMethodPanel_SelectedAnalysisMethodsListBoxMouseClicked

	private void EventCullingPanel_EventCullingListBoxMouseMoved(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_EventCullingPanel_EventCullingListBoxMouseMoved
		JList theList = (JList) evt.getSource();
		int index = theList.locationToIndex(evt.getPoint());
		if (index > -1) {
			String text = EventCullersMasterList.get(index).tooltipText();
			theList.setToolTipText(text);
		}
	}// GEN-LAST:event_EventCullingPanel_EventCullingListBoxMouseMoved

	private void EventCullingPanel_EventCullingListBoxMouseClicked(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_EventCullingPanel_EventCullingListBoxMouseClicked
		try {
			EventCullingPanel_EventCullingDescriptionTextbox.setText(
				EventCullers.getEventCuller(
					EventCullingPanel_EventCullingListBox.getSelectedValue().toString()
				).longDescription()
			);
		} catch (Exception e) {
			logger.error("Failed to get selected event culler");
			e.printStackTrace();
		}
		if (evt.getClickCount() == 2) {
			AddEventCullerSelection();
		}
	}// GEN-LAST:event_EventCullingPanel_EventCullingListBoxMouseClicked

	private void EventCullingPanel_RemoveAllEventCullingButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_EventCullingPanel_RemoveAllEventCullingButtonActionPerformed
		JGAAP_API.removeAllEventCullers();
		UpdateSelectedEventCullingListBox();
	}// GEN-LAST:event_EventCullingPanel_RemoveAllEventCullingButtonActionPerformed

	private void EventCullingPanel_AddAllEventCullingButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_EventCullingPanel_AddAllEventCullingButtonActionPerformed
		try {
			for (int i = 0; i < EventCullingListBox_Model.size(); i++) {
				JGAAP_API.addEventCuller(EventCullingListBox_Model.elementAt(i).toString());
			}
			UpdateSelectedEventCullingListBox();
		} catch (Exception e) {
			logger.error("Error adding all EventCullers", e);
			JOptionPane.showMessageDialog(this,
					"Error adding all EventCullers", "JGAAP Error",
					JOptionPane.ERROR_MESSAGE);
		} 
	}// GEN-LAST:event_EventCullingPanel_AddAllEventCullingButtonActionPerformed

	private void EventCullingPanel_RemoveEventCullingButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_EventCullingPanel_RemoveEventCullingButtonActionPerformed
		RemoveEventCullerSelection();
	}// GEN-LAST:event_EventCullingPanel_RemoveEventCullingButtonActionPerformed

	private void RemoveEventCullerSelection() {
		SelectedEventCullersList = JGAAP_API.getEventCullers();
		JGAAP_API.removeEventCuller(SelectedEventCullersList
				.get(EventCullingPanel_SelectedEventCullingListBox
						.getSelectedIndex()));
		UpdateSelectedEventCullingListBox();
	}

	private void EventCullingPanel_AddEventCullingButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_EventCullingPanel_AddEventCullingButtonActionPerformed
		AddEventCullerSelection();
	}// GEN-LAST:event_EventCullingPanel_AddEventCullingButtonActionPerformed

	private void AddEventCullerSelection() {
		try {
			JGAAP_API.addEventCuller(EventCullingPanel_EventCullingListBox
					.getSelectedValue().toString());
			UpdateSelectedEventCullingListBox();
		} catch (Exception e) {
			logger.error("Error adding EventCuller "
					+ EventCullingPanel_EventCullingListBox.getSelectedValue()
							.toString(), e);
			JOptionPane.showMessageDialog(this, "Error adding EventCuller "
					+ EventCullingPanel_EventCullingListBox.getSelectedValue()
							.toString(), "JGAAP Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void EventCullingPanel_SelectedEventCullingListBoxMouseMoved(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_EventCullingPanel_SelectedEventCullingListBoxMouseMoved
		JList theList = (JList) evt.getSource();
		int index = theList.locationToIndex(evt.getPoint());
		if (index > -1) {
			String text = SelectedEventCullersList.get(index).tooltipText();
			theList.setToolTipText(text);
		}
	}// GEN-LAST:event_EventCullingPanel_SelectedEventCullingListBoxMouseMoved

	private void EventCullingPanel_SelectedEventCullingListBoxMouseClicked(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_EventCullingPanel_SelectedEventCullingListBoxMouseClicked
		EventCullingPanel_ParametersPanel.removeAll();
		EventCullingPanel_ParametersPanel.setLayout(SelectedEventCullersList
				.get(EventCullingPanel_SelectedEventCullingListBox
						.getSelectedIndex()).getGUILayout(
						EventCullingPanel_ParametersPanel));

		EventCullingPanel_EventCullingDescriptionTextbox
				.setText(SelectedEventCullersList.get(
						EventCullingPanel_SelectedEventCullingListBox
								.getSelectedIndex()).longDescription());
		if (evt != null && evt.getClickCount() == 2) {
			RemoveEventCullerSelection();
		}
	}// GEN-LAST:event_EventCullingPanel_SelectedEventCullingListBoxMouseClicked

	private void EventSetsPanel_RemoveAllEventSetsButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_EventSetsPanel_RemoveAllEventSetsButtonActionPerformed
		JGAAP_API.removeAllEventDrivers();
		UpdateSelectedEventSetListBox();
	}// GEN-LAST:event_EventSetsPanel_RemoveAllEventSetsButtonActionPerformed

	private void EventSetsPanel_AddAllEventSetsButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_EventSetsPanel_AddAllEventSetsButtonActionPerformed
		try {
			for (int i = 0; i < EventSetsListBox_Model.size(); i++) {
				JGAAP_API.addEventDriver(EventSetsListBox_Model.elementAt(i).toString());
			}
			UpdateSelectedEventSetListBox();
		} catch (Exception e) {
			logger.error("Error adding all EventDrivers", e);
			JOptionPane.showMessageDialog(this,
					"Error adding all EventDrivers", "JGAAP Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}// GEN-LAST:event_EventSetsPanel_AddAllEventSetsButtonActionPerformed

	private void EventSetsPanel_RemoveEventSetButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_EventSetsPanel_RemoveEventSetButtonActionPerformed
		RemoveEventSetSelection();
	}// GEN-LAST:event_EventSetsPanel_RemoveEventSetButtonActionPerformed

	private void RemoveEventSetSelection() {
		SelectedEventDriverList = JGAAP_API.getEventDrivers();
		JGAAP_API
				.removeEventDriver(SelectedEventDriverList
						.get(EventSetsPanel_SelectedEventSetListBox
								.getSelectedIndex()));
		EventSetsPanel_ParametersPanel.removeAll();
		EventSetsPanel_ParametersPanel.updateUI();
		UpdateSelectedEventSetListBox();
	}

	private void EventSetsPanel_AddEventSetButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_EventSetsPanel_AddEventSetButtonActionPerformed
		AddEventSetSelection();
	}// GEN-LAST:event_EventSetsPanel_AddEventSetButtonActionPerformed

	private void AddEventSetSelection() {
		try {
			JGAAP_API.addEventDriver(EventSetsPanel_EventSetListBox
					.getSelectedValue().toString());
			UpdateSelectedEventSetListBox();
		} catch (Exception e) {
			logger.error("Error adding EventDriver "
					+ EventSetsPanel_EventSetListBox.getSelectedValue()
							.toString(), e);
			JOptionPane.showMessageDialog(this, "Error adding EventDriver "
					+ EventSetsPanel_EventSetListBox.getSelectedValue()
							.toString(), "JGAAP Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void EventSetsPanel_SelectedEventSetListBoxMouseMoved(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_EventSetsPanel_SelectedEventSetListBoxMouseMoved
		if (!SelectedEventDriverList.isEmpty()) {
			JList theList = (JList) evt.getSource();
			int index = theList.locationToIndex(evt.getPoint());
			if (index > -1) {
				String text = SelectedEventDriverList.get(index).tooltipText();
				theList.setToolTipText(text);
			}
		}
	}// GEN-LAST:event_EventSetsPanel_SelectedEventSetListBoxMouseMoved

	private void EventSetsPanel_SelectedEventSetListBoxMouseClicked(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_EventSetsPanel_SelectedEventSetListBoxMouseClicked
		if (!SelectedEventDriverList.isEmpty()) {
			EventSetsPanel_ParametersPanel.removeAll();
			EventSetsPanel_ParametersPanel.setLayout(SelectedEventDriverList
					.get(EventSetsPanel_SelectedEventSetListBox
							.getSelectedIndex()).getGUILayout(
							EventSetsPanel_ParametersPanel));
			EventSetsPanel_EventSetDescriptionTextBox
					.setText(SelectedEventDriverList.get(
							EventSetsPanel_SelectedEventSetListBox
									.getSelectedIndex()).longDescription());
			if (evt != null && evt.getClickCount() == 2) {
				RemoveEventSetSelection();
			}
		}
	}// GEN-LAST:event_EventSetsPanel_SelectedEventSetListBoxMouseClicked

	private void EventSetsPanel_EventSetListBoxMouseMoved(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_EventSetsPanel_EventSetListBoxMouseMoved
		JList theList = (JList) evt.getSource();
		int index = theList.locationToIndex(evt.getPoint());
		if (index > -1) {
			String text = EventDriverMasterList.get(index).tooltipText();
			theList.setToolTipText(text);
		}
	}// GEN-LAST:event_EventSetsPanel_EventSetListBoxMouseMoved

	private void EventSetsPanel_EventSetListBoxMouseClicked(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_EventSetsPanel_EventSetListBoxMouseClicked
		try {
			EventSetsPanel_EventSetDescriptionTextBox.setText(
				EventDrivers.getEventDriver(
					EventSetsPanel_EventSetListBox.getSelectedValue().toString()
				).longDescription()
			);
		} catch (Exception e) {
			logger.error("Failed to get selected event driver");
			e.printStackTrace();
		}
		if (evt.getClickCount() == 2) {
			AddEventSetSelection();
		}
	}// GEN-LAST:event_EventSetsPanel_EventSetListBoxMouseClicked

	private void CanonicizersPanel_RemoveAllCanonicizersButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_CanonicizersPanel_RemoveAllCanonicizersButtonActionPerformed
		SelectedCanonicizerList.clear();
		UpdateSelectedCanonicizerListBox();
	}// GEN-LAST:event_CanonicizersPanel_RemoveAllCanonicizersButtonActionPerformed

	private void CanonicizersPanel_RemoveCanonicizerButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_CanonicizersPanel_RemoveCanonicizerButtonActionPerformed
		RemoveCanonicizerSelection();
	}// GEN-LAST:event_CanonicizersPanel_RemoveCanonicizerButtonActionPerformed

	private void RemoveCanonicizerSelection() {
		SelectedCanonicizerList
				.remove(CanonicizersPanel_SelectedCanonicizerListBox
						.getSelectedIndex());
		UpdateSelectedCanonicizerListBox();
	}

	private void CanonicizersPanel_AddCanonicizerButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_CanonicizersPanel_AddCanonicizerButtonActionPerformed
		AddCanonicizerSelection();
	}// GEN-LAST:event_CanonicizersPanel_AddCanonicizerButtonActionPerformed

	private void AddCanonicizerSelection() {
		try {
			Canonicizer temp = JGAAP_API.addCanonicizer(
				CanonicizersPanel_CanonicizerListBox.getSelectedValue().toString()
			);
			SelectedCanonicizerList.add(new Pair<Canonicizer, Object>(temp,
					docTypesList.get(CanonicizersPanel_DocTypeComboBox.getSelectedIndex())));
			UpdateSelectedCanonicizerListBox();
		} catch (Exception e) {
			logger.error("Error adding canonicizer "
					+ CanonicizersPanel_CanonicizerListBox.getSelectedValue()
							.toString(), e);
			JOptionPane.showMessageDialog(this, e.getMessage(), "JGAAP Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void CanonicizersPanel_SelectedCanonicizerListBoxMouseMoved(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_CanonicizersPanel_SelectedCanonicizerListBoxMouseMoved
		JList theList = (JList) evt.getSource();
		int index = theList.locationToIndex(evt.getPoint());
		if (index > -1) {
			String text = SelectedCanonicizerList.get(index).getFirst()
					.tooltipText();
			theList.setToolTipText(text);
		}
	}// GEN-LAST:event_CanonicizersPanel_SelectedCanonicizerListBoxMouseMoved

	private void CanonicizersPanel_SelectedCanonicizerListBoxMouseClicked(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_CanonicizersPanel_SelectedCanonicizerListBoxMouseClicked
		if (!SelectedCanonicizerList.isEmpty()) {
			CanonicizersPanel_DocumentsCanonicizerDescriptionTextBox
					.setText(SelectedCanonicizerList
							.get(CanonicizersPanel_SelectedCanonicizerListBox
									.getSelectedIndex()).getFirst()
							.longDescription());
			if (evt != null && evt.getClickCount() == 2) {
				RemoveCanonicizerSelection();
			}
		}
	}// GEN-LAST:event_CanonicizersPanel_SelectedCanonicizerListBoxMouseClicked

	private void CanonicizersPanel_CanonicizerListBoxMouseMoved(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_CanonicizersPanel_CanonicizerListBoxMouseMoved
		JList theList = (JList) evt.getSource();
		int index = theList.locationToIndex(evt.getPoint());
		if (index > -1) {
			String text = CanonicizerMasterList.get(index).tooltipText();
			theList.setToolTipText(text);
		}
	}// GEN-LAST:event_CanonicizersPanel_CanonicizerListBoxMouseMoved

	private void CanonicizersPanel_CanonicizerListBoxMouseClicked(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_CanonicizersPanel_CanonicizerListBoxMouseClicked
		try {
			CanonicizersPanel_DocumentsCanonicizerDescriptionTextBox.setText(
				Canonicizers.getCanonicizer(
					CanonicizersPanel_CanonicizerListBox.getSelectedValue().toString()
				).longDescription()
			);
		} catch (Exception e){
			logger.error("Failed to get selected canonicizer");
			e.printStackTrace();
		}

		if (evt.getClickCount() == 2) {
			AddCanonicizerSelection();
		}
	}// GEN-LAST:event_CanonicizersPanel_CanonicizerListBoxMouseClicked

	private void DocumentsPanel_LanguageComboBoxActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_DocumentsPanel_LanguageComboBoxActionPerformed
		try {
			JGAAP_API.setLanguage(DocumentsPanel_LanguageComboBox
					.getSelectedItem().toString());
			SanatizeMasterLists();
			SetAnalysisMethodList();
			SetCanonicizerList();
			SetDistanceFunctionList();
			SetEventCullingList();
			SetEventSetList();
			SetAnalysisMethodNoDistanceList();
		} catch (Exception e) {
			logger.error("Error changing language", e);
			JOptionPane.showMessageDialog(this, "Error changing language",
					"JGAAP Error", JOptionPane.ERROR_MESSAGE);
		}
	}// GEN-LAST:event_DocumentsPanel_LanguageComboBoxActionPerformed

	private void DocumentsPanel_RemoveAuthorButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_DocumentsPanel_RemoveAuthorButtonActionPerformed
		TreePath Path = DocumentsPanel_KnownAuthorsTree.getSelectionPath();
		String AuthorName;
		if (Path.getPathCount() != 1) {
			AuthorName = Path.getPathComponent(1).toString();
			KnownDocumentList = JGAAP_API.getDocumentsByAuthor(AuthorName);
			for (int i = KnownDocumentList.size() - 1; i >= 0; i--) {
				JGAAP_API.removeDocument(KnownDocumentList.get(i));
			}
			UpdateKnownDocumentsTree();
		} else {

		}
	}// GEN-LAST:event_DocumentsPanel_RemoveAuthorButtonActionPerformed

	private void DocumentsPanel_EditAuthorButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_DocumentsPanel_EditAuthorButtonActionPerformed
		TreePath Path = DocumentsPanel_KnownAuthorsTree.getSelectionPath();
		String AuthorName;
		if (Path.getPathCount() != 1) {
			AuthorName = Path.getPathComponent(1).toString();
			JGAAP_UI_AddAuthorDialog AddAuthorDialog = new JGAAP_UI_AddAuthorDialog(
					JGAAP_UI_MainForm.this, true, AuthorName, filepath);
			AddAuthorDialog.setVisible(true);
			UpdateKnownDocumentsTree();
		} else {

		}
	}// GEN-LAST:event_DocumentsPanel_EditAuthorButtonActionPerformed

	private void DocumentsPanel_AddAuthorButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_DocumentsPanel_AddAuthorButtonActionPerformed
		JGAAP_UI_AddAuthorDialog AddAuthorDialog = new JGAAP_UI_AddAuthorDialog(
				JGAAP_UI_MainForm.this, true, "", filepath);
		AddAuthorDialog.setVisible(true);
		filepath = AddAuthorDialog.getFilePath();
		UpdateKnownDocumentsTree();
	}// GEN-LAST:event_DocumentsPanel_AddAuthorButtonActionPerformed

	private void DocumentsPanel_RemoveDocumentsButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_DocumentsPanel_RemoveDocumentsButtonActionPerformed
		UnknownDocumentList = JGAAP_API.getUnknownDocuments();
		JGAAP_API.removeDocument(UnknownDocumentList
				.get(DocumentsPanel_UnknownAuthorsTable.getSelectedRow()));
		UpdateUnknownDocumentsTable();
	}// GEN-LAST:event_DocumentsPanel_RemoveDocumentsButtonActionPerformed

	private void DocumentsPanel_AddDocumentsButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_DocumentsPanel_AddDocumentsButtonActionPerformed

		FileChoser = new JFileChooser(filepath);
		FileChoser.setMultiSelectionEnabled(true);
		int choice = FileChoser.showOpenDialog(JGAAP_UI_MainForm.this);
		if (choice == JFileChooser.APPROVE_OPTION) {
			for (File file : FileChoser.getSelectedFiles()) {
				try {
					JGAAP_API.addDocument(file.getCanonicalPath(), "", "");
					filepath = file.getCanonicalPath();
				} catch (Exception e) {
					logger.error("Error adding document(s)", e);
					JOptionPane.showMessageDialog(this,
							"Error adding document(s)", "JGAAP Error",
							JOptionPane.ERROR_MESSAGE);
				}
				UpdateUnknownDocumentsTable();
			}
		}
	}// GEN-LAST:event_DocumentsPanel_AddDocumentsButtonActionPerformed

	private void DocumentsPanel_NotesButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_DocumentsPanel_NotesButtonActionPerformed
		try {
			NotesPage.displayNote(Notes[0]);
			NotesPage.setVisible(true);
			if (NotesPage.SavedNote != null) {
				Notes[0] = NotesPage.SavedNote;
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage(), "JGAAP Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}// GEN-LAST:event_DocumentsPanel_NotesButtonActionPerformed

	private void CanonicizersPanel_NotesButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_CanonicizersPanel_NotesButtonActionPerformed
		try {
			NotesPage.displayNote(Notes[1]);
			NotesPage.setVisible(true);
			if (NotesPage.SavedNote != null) {
				Notes[1] = NotesPage.SavedNote;
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage(), "JGAAP Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}// GEN-LAST:event_CanonicizersPanel_NotesButtonActionPerformed

	private void EventSetsPanel_NotesButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_EventSetsPanel_NotesButtonActionPerformed
		try {
			NotesPage.displayNote(Notes[2]);
			NotesPage.setVisible(true);
			if (NotesPage.SavedNote != null) {
				Notes[2] = NotesPage.SavedNote;
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage(), "JGAAP Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}// GEN-LAST:event_EventSetsPanel_NotesButtonActionPerformed

	private void EventCullingPanel_NotesButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_EventCullingPanel_NotesButtonActionPerformed
		try {
			NotesPage.displayNote(Notes[3]);
			NotesPage.setVisible(true);
			if (NotesPage.SavedNote != null) {
				Notes[3] = NotesPage.SavedNote;
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage(), "JGAAP Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}// GEN-LAST:event_EventCullingPanel_NotesButtonActionPerformed

	private void AnalysisMethodPanel_NotesButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_AnalysisMethodPanel_NotesButtonActionPerformed
		try {
			NotesPage.displayNote(Notes[4]);
			NotesPage.setVisible(true);
			if (NotesPage.SavedNote != null) {
				Notes[4] = NotesPage.SavedNote;
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage(), "JGAAP Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}// GEN-LAST:event_AnalysisMethodPanel_NotesButtonActionPerformed

	private void AnalysisMethodPanel_DistanceFunctionsListBoxMouseClicked(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_AnalysisMethodPanel_DistanceFunctionsListBoxMouseClicked
		try {
			AnalysisMethodPanel_DistanceFunctionDescriptionTextBox.setText(
				DistanceFunctions.getDistanceFunction(
					AnalysisMethodPanel_DistanceFunctionsListBox.getSelectedValue().toString()
				).longDescription()
			);
		} catch (Exception e) {
			logger.error("Failed to get selected distance function");
			e.getStackTrace();
		}

		if (evt.getClickCount() == 2) {
			AddAnalysisMethodSelection();
		}
	}// GEN-LAST:event_AnalysisMethodPanel_DistanceFunctionsListBoxMouseClicked

	private void AnalysisMethodPanel_DistanceFunctionsListBoxMouseMoved(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_AnalysisMethodPanel_DistanceFunctionsListBoxMouseMoved
		JList theList = (JList) evt.getSource();
		int index = theList.locationToIndex(evt.getPoint());
		if (index > -1) {
			String text = DistanceFunctionsMasterList.get(index).tooltipText();
			theList.setToolTipText(text);
		}
	}// GEN-LAST:event_AnalysisMethodPanel_DistanceFunctionsListBoxMouseMoved

	private void Next_ButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Next_ButtonActionPerformed
		int count = JGAAP_TabbedPane.getSelectedIndex();
		if (count < 5) {
			JGAAP_TabbedPane.setSelectedIndex(count + 1);
		}
	}// GEN-LAST:event_Next_ButtonActionPerformed

	private void Review_ButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Review_ButtonActionPerformed
		JGAAP_TabbedPane.setSelectedIndex(5);
	}// GEN-LAST:event_Review_ButtonActionPerformed

	private void loadAAACProblem(String problem) {
		filepath = JGAAPConstants.JGAAP_RESOURCE_PACKAGE + "aaac/problem"
				+ problem + "/load" + problem + ".csv";
		List<Document> documents = Collections.emptyList();
		try {
			documents = Utils.getDocumentsFromCSV(CSVIO
					.readCSV(com.jgaap.JGAAP.class
							.getResourceAsStream(filepath)));
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage(), "JGAAP Error",
					JOptionPane.ERROR_MESSAGE);
		}
		for (Document document : documents) {
			JGAAP_API.addDocument(document);
		}
		UpdateKnownDocumentsTree();
		UpdateUnknownDocumentsTable();

	}

	private void ProblemAMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ProblemAMenuItemActionPerformed
		loadAAACProblem("A");
	}// GEN-LAST:event_ProblemAMenuItemActionPerformed

	private void ProblemBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ProblemBMenuItemActionPerformed
		loadAAACProblem("B");
	}// GEN-LAST:event_ProblemBMenuItemActionPerformed

	private void ProblemCMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ProblemCMenuItemActionPerformed
		loadAAACProblem("C");
	}// GEN-LAST:event_ProblemCMenuItemActionPerformed

	private void ProblemDMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ProblemDMenuItemActionPerformed
		loadAAACProblem("D");
	}// GEN-LAST:event_ProblemDMenuItemActionPerformed

	private void ProblemEMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ProblemEMenuItemActionPerformed
		loadAAACProblem("E");
	}// GEN-LAST:event_ProblemEMenuItemActionPerformed

	private void ProblemFMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ProblemFMenuItemActionPerformed
		loadAAACProblem("F");
	}// GEN-LAST:event_ProblemFMenuItemActionPerformed

	private void ProblemGMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ProblemGMenuItemActionPerformed
		loadAAACProblem("G");
	}// GEN-LAST:event_ProblemGMenuItemActionPerformed

	private void ProblemHMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ProblemHMenuItemActionPerformed
		loadAAACProblem("H");
	}// GEN-LAST:event_ProblemHMenuItemActionPerformed

	private void ProblemIMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ProblemIMenuItemActionPerformed
		loadAAACProblem("I");
	}// GEN-LAST:event_ProblemIMenuItemActionPerformed

	private void ProblemJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ProblemJMenuItemActionPerformed
		loadAAACProblem("J");
	}// GEN-LAST:event_ProblemJMenuItemActionPerformed

	private void ProblemKMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ProblemKMenuItemActionPerformed
		loadAAACProblem("K");
	}// GEN-LAST:event_ProblemKMenuItemActionPerformed

	private void ProblemLMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ProblemLMenuItemActionPerformed
		loadAAACProblem("L");
	}// GEN-LAST:event_ProblemLMenuItemActionPerformed

	private void ProblemMMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ProblemMMenuItemActionPerformed
		loadAAACProblem("M");
	}// GEN-LAST:event_ProblemMMenuItemActionPerformed

	private void ReviewPanel_DocumentsLabelMouseClicked(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_ReviewPanel_DocumentsLabelMouseClicked
		JGAAP_TabbedPane.setSelectedIndex(1);
	}// GEN-LAST:event_ReviewPanel_DocumentsLabelMouseClicked

	private void ReviewPanel_SelectedEventSetLabelMouseClicked(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_ReviewPanel_SelectedEventSetLabelMouseClicked
		JGAAP_TabbedPane.setSelectedIndex(2);
	}// GEN-LAST:event_ReviewPanel_SelectedEventSetLabelMouseClicked

	private void ReviewPanel_SelectedEventSetListBoxMouseClicked(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_ReviewPanel_SelectedEventSetListBoxMouseClicked
		// JGAAP_TabbedPane.setSelectedIndex(2);
	}// GEN-LAST:event_ReviewPanel_SelectedEventSetListBoxMouseClicked

	private void ReviewPanel_SelectedEventCullingLabelMouseClicked(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_ReviewPanel_SelectedEventCullingLabelMouseClicked
		JGAAP_TabbedPane.setSelectedIndex(3);
	}// GEN-LAST:event_ReviewPanel_SelectedEventCullingLabelMouseClicked

	private void ReviewPanel_SelectedEventCullingListBoxMouseClicked(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_ReviewPanel_SelectedEventCullingListBoxMouseClicked
		// JGAAP_TabbedPane.setSelectedIndex(3);
	}// GEN-LAST:event_ReviewPanel_SelectedEventCullingListBoxMouseClicked

	private void ReviewPanel_SelectedAnalysisMethodsLabelMouseClicked(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_ReviewPanel_SelectedAnalysisMethodsLabelMouseClicked
		JGAAP_TabbedPane.setSelectedIndex(4);
	}// GEN-LAST:event_ReviewPanel_SelectedAnalysisMethodsLabelMouseClicked

	private void ReviewPanel_SelectedAnalysisMethodsListBoxMouseClicked(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_ReviewPanel_SelectedAnalysisMethodsListBoxMouseClicked
		// JGAAP_TabbedPane.setSelectedIndex(4);
	}// GEN-LAST:event_ReviewPanel_SelectedAnalysisMethodsListBoxMouseClicked
	
	private void UpdateCanonicizerDocTypeComboBox() { 
		CanonicizerComboBoxModel.removeAllElements();
		docTypesList.clear();
		docTypesList.add("All");
		docTypesList.add(Document.Type.GENERIC);
		docTypesList.add(Document.Type.DOC);
		docTypesList.add(Document.Type.PDF);
		docTypesList.add(Document.Type.HTML);
		for(Document document : JGAAP_API.getDocuments()){
			docTypesList.add(document);
		}
		for(Object obj : docTypesList){
			CanonicizerComboBoxModel.addElement(obj.toString());
		}
	}

	private void UpdateSelectedAnalysisMethodListBox() {
		SelectedAnalysisMethodListBox_Model.clear();
		SelectedAnalysisDriverList = JGAAP_API.getAnalysisDrivers();
		for (int i = 0; i < SelectedAnalysisDriverList.size(); i++) {
			SelectedAnalysisMethodListBox_Model
					.addElement(SelectedAnalysisDriverList.get(i).displayName());
		}
		CheckMinimumRequirements();
		if (!SelectedAnalysisDriverList.isEmpty()) {
			AnalysisMethodPanel_SelectedAnalysisMethodsListBox
					.setSelectedIndex(SelectedAnalysisDriverList.size() - 1);
			AnalysisMethodPanel_SelectedAnalysisMethodsListBoxMouseClicked(null);
		}
	}

	private void UpdateSelectedEventSetListBox() {
		SelectedEventSetsListBox_Model.clear();
		SelectedEventDriverList = JGAAP_API.getEventDrivers();
		for (int i = 0; i < SelectedEventDriverList.size(); i++) {
			SelectedEventSetsListBox_Model.addElement(SelectedEventDriverList
					.get(i).displayName());
		}
		CheckMinimumRequirements();
		if (!SelectedEventDriverList.isEmpty()) {
			EventSetsPanel_SelectedEventSetListBox
					.setSelectedIndex(SelectedEventDriverList.size() - 1);
			EventSetsPanel_SelectedEventSetListBoxMouseClicked(null);
		}
	}

	private void UpdateSelectedEventCullingListBox() {
		SelectedEventCullingListBox_Model.clear();
		SelectedEventCullersList = JGAAP_API.getEventCullers();
		for (int i = 0; i < SelectedEventCullersList.size(); i++) {
			SelectedEventCullingListBox_Model
					.addElement(SelectedEventCullersList.get(i).displayName());
		}
		if (!SelectedEventCullersList.isEmpty()) {
			EventCullingPanel_SelectedEventCullingListBox
					.setSelectedIndex(SelectedEventCullersList.size() - 1);
			EventCullingPanel_SelectedEventCullingListBoxMouseClicked(null);
		}
	}

	private void UpdateSelectedCanonicizerListBox() {
		SelectedCanonicizerListBox_Model.clear();
		for (int i = 0; i < SelectedCanonicizerList.size(); i++) {
			SelectedCanonicizerListBox_Model.addElement(SelectedCanonicizerList
					.get(i).getFirst().displayName()+" ["+SelectedCanonicizerList.get(i).getSecond().toString()+"]");
		}
		if (!SelectedCanonicizerList.isEmpty()) {
			CanonicizersPanel_SelectedCanonicizerListBox
					.setSelectedIndex(SelectedCanonicizerList.size() - 1);
			CanonicizersPanel_SelectedCanonicizerListBoxMouseClicked(null);
		}
	}

	private void UpdateUnknownDocumentsTable() {
		UnknownAuthorDocumentsTable_Model.setRowCount(0);
		UnknownDocumentList = JGAAP_API.getUnknownDocuments();
		for (int i = 0; i < UnknownDocumentList.size(); i++) {
			Object RowData[] = { UnknownDocumentList.get(i).getTitle(),
					UnknownDocumentList.get(i).getFilePath() };
			UnknownAuthorDocumentsTable_Model.addRow(RowData);
		}
		UpdateDocumentsTable();
	}

	private void UpdateKnownDocumentsTree() {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) KnownAuthorsTree_Model
				.getRoot();
		for (int i = root.getChildCount() - 1; i >= 0; i--) {
			KnownAuthorsTree_Model
					.removeNodeFromParent((DefaultMutableTreeNode) root
							.getChildAt(i));
		}
		AuthorList = JGAAP_API.getAuthors();
		for (int i = 0; i < AuthorList.size(); i++) {
			String AuthorName = AuthorList.get(i);
			DefaultMutableTreeNode author = new DefaultMutableTreeNode(
					AuthorName);
			KnownAuthorsTree_Model.insertNodeInto(author, root, i);
			// root.add(author);
			KnownDocumentList = JGAAP_API.getDocumentsByAuthor(AuthorName);
			for (int j = 0; j < KnownDocumentList.size(); j++) {
				// author.add(new
				// DefaultMutableTreeNode(KnownDocumentList.get(j).getTitle() +
				// " - " + KnownDocumentList.get(j).getFilePath()));
				DefaultMutableTreeNode temp = new DefaultMutableTreeNode(
						KnownDocumentList.get(j).getTitle() + " - "
								+ KnownDocumentList.get(j).getFilePath());
				KnownAuthorsTree_Model.insertNodeInto(temp, author, j);
			}
		}

		UpdateDocumentsTable();
	}

	private void UpdateDocumentsTable() {
		UpdateCanonicizerDocTypeComboBox();
		CheckMinimumRequirements();
	}

	private void SanatizeMasterLists() {
		AnalysisDriverMasterList = new ArrayList<AnalysisDriver>();
		CanonicizerMasterList = new ArrayList<Canonicizer>();
		DistanceFunctionsMasterList = new ArrayList<DistanceFunction>();
		EventCullersMasterList = new ArrayList<EventCuller>();
		EventDriverMasterList = new ArrayList<EventDriver>();
		LanguagesMasterList = new ArrayList<Language>();

		for (int i = 0; i < AnalysisDrivers.getAnalysisDrivers().size(); i++){
		//for (AnalysisDriver analysisDriver : AnalysisDrivers.getAnalysisDrivers()) {
			AnalysisDriver analysisDriver = AnalysisDrivers.getAnalysisDrivers().get(i);
			if (analysisDriver.showInGUI())
				AnalysisDriverMasterList.add(analysisDriver);
		}
		
		for (int i = 0; i < Canonicizers.getCanonicizers().size(); i++){
		//for (Canonicizer canonicizer : Canonicizers.getCanonicizers()) {
			Canonicizer canonicizer = Canonicizers.getCanonicizers().get(i);
			if (canonicizer.showInGUI())
				CanonicizerMasterList.add(canonicizer);
		}

		for (int i = 0; i < DistanceFunctions.getDistanceFunctions().size(); i++){
		//for (DistanceFunction distanceFunction : DistanceFunctions.getDistanceFunctions()) {
			DistanceFunction distanceFunction = DistanceFunctions.getDistanceFunctions().get(i);
			if (distanceFunction.showInGUI())
				DistanceFunctionsMasterList.add(distanceFunction);
		}

		for (int i = 0; i < EventCullers.getEventCullers().size(); i++){
		//for (EventCuller eventCuller : EventCullers.getEventCullers()) {
			EventCuller eventCuller = EventCullers.getEventCullers().get(i);
			if (eventCuller.showInGUI())
				EventCullersMasterList.add(eventCuller);
		}

		for (int i = 0; i < EventDrivers.getEventDrivers().size(); i++){
		//for (EventDriver eventDriver : EventDrivers.getEventDrivers()) {
			EventDriver eventDriver = EventDrivers.getEventDrivers().get(i);
			if (eventDriver.showInGUI())
				EventDriverMasterList.add(eventDriver);
		}


		for (Language language : Languages.getLanguages()) {
			if (language.showInGUI())
				LanguagesMasterList.add(language);
		}
	}

	private void SetAnalysisMethodList() {
		AnalysisMethodListBox_Model.removeAllElements();
		for (int i = 0; i < AnalysisDriverMasterList.size(); i++) {
			AnalysisMethodListBox_Model.addElement(AnalysisDriverMasterList
					.get(i).displayName());
		}
		if (!AnalysisDriverMasterList.isEmpty()) {
			AnalysisMethodPanel_AnalysisMethodsListBox.setSelectedIndex(0);
			AnalysisMethodPanel_AnalysisMethodDescriptionTextBox
					.setText(AnalysisDriverMasterList.get(0).longDescription());
		}
	}
	
	private void SetAnalysisMethodNoDistanceList() {
		// Create a list box model containing the analysis methods that do not require a distance
		// or a second analysis method to be selected.
		AnalysisMethodsNoDistanceListBox_Model.removeAllElements();
		for(AnalysisDriver analysisDriver : AnalysisDriverMasterList) {
			if(analysisDriver instanceof AnalysisDriver && !(analysisDriver instanceof NeighborAnalysisDriver)
					&& !(analysisDriver instanceof NonDistanceDependentAnalysisDriver)) {
				AnalysisMethodsNoDistanceListBox_Model.addElement(analysisDriver.displayName());
			}
		}
	}

	private void SetDistanceFunctionList() {
		DistanceFunctionsListBox_Model.removeAllElements();
		for (int i = 0; i < DistanceFunctionsMasterList.size(); i++) {
			DistanceFunctionsListBox_Model
					.addElement(DistanceFunctionsMasterList.get(i)
							.displayName());
		}
		if (!DistanceFunctionsMasterList.isEmpty()) {
			AnalysisMethodPanel_DistanceFunctionsListBox.setSelectedIndex(0);
			AnalysisMethodPanel_DistanceFunctionDescriptionTextBox
					.setText(DistanceFunctionsMasterList.get(0)
							.longDescription());
		}
	}

	private void SetCanonicizerList() {
		CanonicizerListBox_Model.removeAllElements();
		for (int i = 0; i < CanonicizerMasterList.size(); i++) {
			CanonicizerListBox_Model.addElement(CanonicizerMasterList.get(i)
					.displayName());
		}
		if (!CanonicizerMasterList.isEmpty()) {
			CanonicizersPanel_CanonicizerListBox.setSelectedIndex(0);
			// CanonicizersPanel_DocumentsCurrentCanonicizersTextBox.setText(CanonicizerMasterList.get(0).longDescription());
		}
	}

	private void SetEventCullingList() {
		EventCullingListBox_Model.removeAllElements();
		for (int i = 0; i < EventCullersMasterList.size(); i++) {
			EventCullingListBox_Model.addElement(EventCullersMasterList.get(i)
					.displayName());
		}
		if (!EventCullersMasterList.isEmpty()) {
			EventCullingPanel_EventCullingListBox.setSelectedIndex(0);
			EventCullingPanel_EventCullingDescriptionTextbox
					.setText(EventCullersMasterList.get(0).longDescription());
		}
	}

	private void SetLanguagesList() {
		int englishIndex = -1;
		for (int i = 0; i < LanguagesMasterList.size(); i++) {
			LanguageComboBox_Model.addElement(LanguagesMasterList.get(i)
					.displayName());
			if (LanguagesMasterList.get(i).displayName()
					.equalsIgnoreCase("English")) {
				englishIndex = i;
			}

		}
		if (englishIndex > -1) {
			DocumentsPanel_LanguageComboBox.setSelectedIndex(englishIndex);
		}

	}

	private void SetEventSetList() {
		EventSetsListBox_Model.clear();
		for (int i = 0; i < EventDriverMasterList.size(); i++) {
			EventSetsListBox_Model.addElement(EventDriverMasterList.get(i)
					.displayName());
		}
		if (!EventDriverMasterList.isEmpty()) {
			EventSetsPanel_EventSetListBox.setSelectedIndex(0);
			EventSetsPanel_EventSetDescriptionTextBox
					.setText(EventDriverMasterList.get(0).longDescription());
		}

	}

	private void searchModuleList(String moduleType) {
		// selectively display what's in the module listboxes depending on what's in the search field.
		// widgets ctrl-F: SearchField

		// abstract away the exact widgets on each panel.
		javax.swing.JTextField searchField;
		javax.swing.DefaultListModel listboxModel;
		javax.swing.JList listbox;
		javax.swing.JTextArea descriptionBox;
		//javax.swing.JButton theAllButton;
		List masterList;

		switch (moduleType) {
			// resolve abstract widgets
			case "Canonicizer":
				searchField = CanonicizersPanel_SearchField;
				listboxModel = CanonicizerListBox_Model;
				listbox = CanonicizersPanel_CanonicizerListBox;
				masterList = CanonicizerMasterList;
				descriptionBox = CanonicizersPanel_DocumentsCanonicizerDescriptionTextBox;
				//theAllButton = null;
				break;
			case "EventDriver":
				searchField = EventSetsPanel_SearchField;
				listboxModel = EventSetsListBox_Model;
				listbox = EventSetsPanel_EventSetListBox;
				masterList = EventDriverMasterList;
				descriptionBox = EventSetsPanel_EventSetDescriptionTextBox;
				//theAllButton = EventSetsPanel_AddAllEventSetsButton;
				break;
			case "EventCulling":
				searchField = EventCullingPanel_SearchField;
				listboxModel = EventCullingListBox_Model;
				listbox = EventCullingPanel_EventCullingListBox;
				masterList = EventCullersMasterList;
				descriptionBox = EventCullingPanel_EventCullingDescriptionTextbox;
				//theAllButton = EventCullingPanel_AddAllEventCullingButton;
				break;
			case "AnalysisMethod":
				searchField = AnalysisMethodPanel_AMDFSearchField;
				listboxModel = AnalysisMethodListBox_Model;
				listbox = AnalysisMethodPanel_AnalysisMethodsListBox;
				masterList = AnalysisDriverMasterList;
				descriptionBox = AnalysisMethodPanel_AnalysisMethodDescriptionTextBox;
				//theAllButton = AnalysisMethodPanel_AddAllAnalysisMethodsButton;
				break;
			case "DistanceFunction":
				searchField = AnalysisMethodPanel_AMDFSearchField;
				listboxModel = DistanceFunctionsListBox_Model;
				listbox = AnalysisMethodPanel_DistanceFunctionsListBox;
				masterList = DistanceFunctionsMasterList;
				descriptionBox = AnalysisMethodPanel_DistanceFunctionDescriptionTextBox;
				//theAllButton = AnalysisMethodPanel_AddAllAnalysisMethodsButton;
				break;
			default:
				return;
		}
		if (masterList.isEmpty()) return;

		String searchTerms = "";
		try { // retrieve search term from search field
			searchTerms = searchField.getDocument().getText(0, searchField.getDocument().getLength()).toLowerCase().replace('-', ' ');
		} catch (BadLocationException e){
			logger.error("Invalid character location encountered in search term retrieval");
			e.printStackTrace();
			return;
		}

		listboxModel.removeAllElements();
		if (searchTerms.length() == 0 ||
				(searchTerms.length() == 1 && searchTerms.charAt(0) == '"') ||
				(searchTerms.length() == 2 && searchTerms.equals("\"\""))
			) {
			// null search terms -> no need to match lol
			for (int i = 0; i < masterList.size(); i++) {
				listboxModel.addElement(((Displayable) masterList.get(i)).displayName());
			}
			//theAllButton.setText("All");
			searchField.setBackground(new Color(255, 255, 255));
			return;
		} else {
			boolean matchedAny = false;
			// make list of all alternative search terms to match module names to.
			ArrayList<String> allSearchTerms = new ArrayList<String>();
			allSearchTerms.add(searchTerms);
			for (String searchTermKey : JGAAPConstants.JGAAP_SEARCH_TABLE.keySet()){
				if (searchTermKey.contains(searchTerms)){
					for (String altTerm : JGAAPConstants.JGAAP_SEARCH_TABLE.get(searchTermKey))
						allSearchTerms.add(altTerm);
				}
			}
			// now match the names, including the text in the search bar.
			for (int i = 0; i < masterList.size(); i++) {
				// for every module, check the following:
				// 1. if their own names (lowercase, hypen -> space) match search terms
				// 2. if their own names match alternative search terms
				// 3. if their initials match search terms.
				Boolean match = false;
				String moduleName = ((Displayable) masterList.get(i)).displayName();
				String searchMatch = moduleName.toLowerCase().replace('-', ' ');

				if (searchTerms.charAt(0) == '"' &&
						searchTerms.charAt(searchTerms.length()-1) == '"' &&
						searchTerms.length() > 2) {
					match = searchMatch.contains(searchTerms.substring(1, searchTerms.length()-1).toString());

				} else {
					if (allSearchTerms.get(0).charAt(0) == '"')
						// ignore if first char is double quote. This is so the intermediate results
						// are more "stable" when the user is trying to perform an exact search
						// but haven't typed the double quote at the end yet.
						allSearchTerms.set(0, allSearchTerms.get(0).substring(1));
					for (String searchTerm : allSearchTerms){
						match = match || (searchMatch.contains(searchTerm));
					}
					String initials = "";
					for (String word : searchMatch.split(" +")) initials += word.charAt(0);

					match = match || ((initials.contains(searchTerms)) && searchTerms.length() > 1);
				}

				if (match) {
					listboxModel.addElement(moduleName);
					matchedAny = matchedAny || match;
				}
				if (matchedAny){
					searchField.setBackground(new Color(245, 255, 245));
				} else {
					searchField.setBackground(new Color(255, 220, 220));
				}
			}
		}
		listbox.setSelectedIndex(0);
		descriptionBox.setText("");
		// update description box with every search so user is not confused if the desc stays
		// but the selected module changes
		String description = "";
		if (listboxModel.size() == 0) return;
		String moduleName = listboxModel.getElementAt(0).toString();
		try {
			switch (moduleType) {
				case "Canonicizer":
					description = Canonicizers.getCanonicizer(moduleName).longDescription();
					break;
				case "EventDriver":
					description = EventDrivers.getEventDriver(moduleName).longDescription();
					break;
				case "EventCulling":
					description = EventCullers.getEventCuller(moduleName).longDescription();
					break;
				case "AnalysisMethod":
					description = AnalysisDrivers.getAnalysisDriver(moduleName).longDescription();
					break;
				case "DistanceFunction":
					description = DistanceFunctions.getDistanceFunction(moduleName).longDescription();
					break;
			}
		} catch (Exception e) {
			logger.error("Failed to get selected analysis method");
			e.printStackTrace();
		}
		descriptionBox.setText(description);
		//theAllButton.setText("Matched");
	}

	private void SetUnknownDocumentColumns() {
		UnknownAuthorDocumentsTable_Model.addColumn("Title");
		UnknownAuthorDocumentsTable_Model.addColumn("Filepath");
		DocumentsPanel_UnknownAuthorsTable
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		DocumentsPanel_UnknownAuthorsTable.setColumnSelectionAllowed(false);
		DocumentsPanel_UnknownAuthorsTable.setRowSelectionAllowed(true);

		DocumentsPanel_UnknownAuthorsTable.getModel().addTableModelListener(
				new TableModelListener() {
					public void tableChanged(TableModelEvent e) {
						// System.out.println("Unknown Documents Table Row: " +
						// e.getFirstRow() + ", Column: " + e.getColumn());
						if ((e.getColumn() >= 0) && (e.getFirstRow() >= 0)) {
							UnknownDocumentList = JGAAP_API
									.getUnknownDocuments();
							if (e.getColumn() == 0) {
								UnknownDocumentList
										.get(e.getFirstRow())
										.setTitle(
												(String) DocumentsPanel_UnknownAuthorsTable.getValueAt(
														e.getFirstRow(), 0));
							}
							UpdateDocumentsTable();
						}
					}
				});
	}

	private void SetKnownDocumentTree() {
		DocumentsPanel_KnownAuthorsTree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		DocumentsPanel_KnownAuthorsTree.setShowsRootHandles(true);
	}
	
	private void updateDistanceListUseability() {
		// Update the distance list's useability and contents based on various conditions.
		if(!distanceFormulasDisplayed) {
			// If the distance formulas are not currently displayed, then display them. If we need
			// something else to display instead, this will be changed by this method later.
			lblDistanceFunctions.setText("Distance Functions");
			AnalysisMethodPanel_DistanceFunctionsListBox.setModel(DistanceFunctionsListBox_Model);
			AnalysisMethodPanel_DistanceFunctionsListBox.setSelectedIndex(0);
			distanceFormulasDisplayed = true;
		}
		
		int i = AnalysisMethodPanel_AnalysisMethodsListBox.getSelectedIndex();
		if(AnalysisDriverMasterList.get(i) instanceof NeighborAnalysisDriver){
			// If the currently selected analysis driver requires a distance formula, enable the JList.
			this.AnalysisMethodPanel_DistanceFunctionsListBox.setEnabled(true);
		}
		else if(AnalysisDriverMasterList.get(i) instanceof NonDistanceDependentAnalysisDriver) {
			// If the currently selected analysis driver requires a second, non-distance analysis,
			// change the JList to show non-distance analysis drivers.
			lblDistanceFunctions.setText("Analyze with:");
			AnalysisMethodPanel_DistanceFunctionsListBox.setModel(AnalysisMethodsNoDistanceListBox_Model);
			AnalysisMethodPanel_DistanceFunctionsListBox.setSelectedIndex(0);
			this.AnalysisMethodPanel_DistanceFunctionsListBox.setEnabled(true);
			distanceFormulasDisplayed = false;
		}
		else {
			// If the currently selected analysis driver does not require a distance formula, disable
			// the JList.
			this.AnalysisMethodPanel_DistanceFunctionsListBox.setEnabled(false);
		}
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JPanel AnalysisMethodPanel_AMParametersPanel;
	private javax.swing.JButton AnalysisMethodPanel_AddAllAnalysisMethodsButton;
	private javax.swing.JButton AnalysisMethodPanel_AddAnalysisMethodButton;
	private javax.swing.JTextArea AnalysisMethodPanel_AnalysisMethodDescriptionTextBox;
	private javax.swing.JList AnalysisMethodPanel_AnalysisMethodsListBox;
	private javax.swing.JPanel AnalysisMethodPanel_DFParametersPanel;
	private javax.swing.JTextField AnalysisMethodPanel_AMDFSearchField;
	private javax.swing.JTextArea AnalysisMethodPanel_DistanceFunctionDescriptionTextBox;
	private javax.swing.JList AnalysisMethodPanel_DistanceFunctionsListBox;
	private javax.swing.JButton AnalysisMethodPanel_NotesButton;
	private javax.swing.JButton AnalysisMethodPanel_RemoveAllAnalysisMethodsButton;
	private javax.swing.JButton AnalysisMethodPanel_RemoveAnalysisMethodsButton;
	private javax.swing.JList AnalysisMethodPanel_SelectedAnalysisMethodsListBox;
	private javax.swing.JMenuItem BatchLoadMenuItem;
	private javax.swing.JMenuItem BatchSaveMenuItem;
	private javax.swing.JButton CanonicizersPanel_AddCanonicizerButton;
	private javax.swing.JList CanonicizersPanel_CanonicizerListBox;
	private javax.swing.JTextField CanonicizersPanel_SearchField;
	private javax.swing.JTextArea CanonicizersPanel_DocumentsCanonicizerDescriptionTextBox;
	private javax.swing.JButton CanonicizersPanel_NotesButton;
	private javax.swing.JButton CanonicizersPanel_RemoveAllCanonicizersButton;
	private javax.swing.JButton CanonicizersPanel_RemoveCanonicizerButton;
	private javax.swing.JList CanonicizersPanel_SelectedCanonicizerListBox;
	private javax.swing.JButton DocumentsPanel_AddAuthorButton;
	private javax.swing.JButton DocumentsPanel_AddDocumentsButton;
	private javax.swing.JButton DocumentsPanel_EditAuthorButton;
	private javax.swing.JTree DocumentsPanel_KnownAuthorsTree;
	private javax.swing.JComboBox DocumentsPanel_LanguageComboBox;
	private javax.swing.JComboBox CanonicizersPanel_DocTypeComboBox;
	private javax.swing.JButton DocumentsPanel_NotesButton;
	private javax.swing.JButton DocumentsPanel_RemoveAuthorButton;
	private javax.swing.JButton DocumentsPanel_RemoveDocumentsButton;
	private javax.swing.JTable DocumentsPanel_UnknownAuthorsTable;
	private javax.swing.JButton EventCullingPanel_AddAllEventCullingButton;
	private javax.swing.JButton EventCullingPanel_AddEventCullingButton;
	private javax.swing.JTextField EventCullingPanel_SearchField;
	private javax.swing.JTextArea EventCullingPanel_EventCullingDescriptionTextbox;
	private javax.swing.JList EventCullingPanel_EventCullingListBox;
	private javax.swing.JButton EventCullingPanel_NotesButton;
	private javax.swing.JPanel EventCullingPanel_ParametersPanel;
	private javax.swing.JButton EventCullingPanel_RemoveAllEventCullingButton;
	private javax.swing.JButton EventCullingPanel_RemoveEventCullingButton;
	private javax.swing.JList EventCullingPanel_SelectedEventCullingListBox;
	private javax.swing.JButton EventSetsPanel_AddAllEventSetsButton;
	private javax.swing.JButton EventSetsPanel_AddEventSetButton;
	private javax.swing.JTextField EventSetsPanel_SearchField;
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
	private javax.swing.JButton ReviewPanel_ProcessButton;
	private javax.swing.JLabel ReviewPanel_SelectedAnalysisMethodsLabel;
	private javax.swing.JList ReviewPanel_SelectedAnalysisMethodsListBox;
	private javax.swing.JLabel ReviewPanel_SelectedEventCullingLabel;
	private javax.swing.JList ReviewPanel_SelectedEventCullingListBox;
	private javax.swing.JLabel ReviewPanel_SelectedEventSetLabel;
	private javax.swing.JList ReviewPanel_SelectedEventSetListBox;
	private javax.swing.JList ReviewPanel_CanonicizersListBox;
	private javax.swing.JButton Review_Button;
	private javax.swing.JMenuItem aboutMenuItem;
	private javax.swing.JMenuItem exitMenuItem;
	private javax.swing.JMenu helpMenu;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel15;
	private javax.swing.JLabel jLabel16;
	private javax.swing.JLabel jLabel17;
	private javax.swing.JLabel jLabel18;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel20;
	private javax.swing.JLabel jLabel21;
	private javax.swing.JLabel jLabel22;
	private javax.swing.JLabel jLabel28;
	private javax.swing.JLabel jLabel29;
	private javax.swing.JLabel jLabel30;
	private javax.swing.JLabel jLabel32;
	private javax.swing.JLabel lblDistanceFunctions;
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
	private javax.swing.JScrollPane jScrollPane22;
	private javax.swing.JScrollPane jScrollPane23;
	private javax.swing.JScrollPane jScrollPane24;
	private javax.swing.JScrollPane jScrollPane25;
	private javax.swing.JScrollPane jScrollPane26;
	private javax.swing.JScrollPane jScrollPane27;
	private javax.swing.JScrollPane jScrollPane6;
	private javax.swing.JScrollPane jScrollPane9;
	// End of variables declaration//GEN-END:variables

	// reused UI element sizes
	private int descriptionBoxMinHeight = 25;
	private int descriptionBoxMaxHeight = 200;
	private int descriptionBoxPrefHeight = 100;
	private int listboxMinWidth = 200;
	private int listboxPrefWidth = 300;
	private int listboxMaxWidth = 750;
	private int listboxMinHeight = 70;
	private int searchFieldMaxHeight = 23;
	private int searchFieldGap = 5;
	private int buttonMinWidth = 90;
}
