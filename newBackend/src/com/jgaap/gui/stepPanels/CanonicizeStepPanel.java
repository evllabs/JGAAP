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
 * CanonicizeStepPanel.java
 */
package com.jgaap.gui.stepPanels;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import java.util.Collection;

import com.jgaap.backend.API;
import com.jgaap.backend.AutoPopulate;
import com.jgaap.generics.Canonicizer;
import com.jgaap.generics.Document;
import com.jgaap.gui.dnd.*;
import com.jgaap.gui.generics.StepPanel;

/**
 * This panel represents a single step in a multi-step process, both graphically
 * and logically. It encapsulates all the necessary graphical elements and
 * program logic needed for this step, and provides access methods for necessary
 * data to be passed along to the next step. Specifically, it handles
 * Canonicizer processing for the JGAAP program.
 * 
 * @author Chuck Liddell Created 11/12/08 TODO: Complex Canonicizer data model
 *         and visual representation. Drag and Drop, the works.
 */
public class CanonicizeStepPanel extends StepPanel implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    /**
	 * Reference to an IconGlassPane that will be given to CanonicizerLabels
	 */
    private final IconGlassPane glassPane;
    
    /**
     * Reference to a DnDManager that will be the interface between drag and
     * drop functionality and the GUI backend (guiDriver).
     */
    private final DnDManager dndManager;
    
    // debugging option
    public static final boolean borders = false;
    
    private Border redBorder = BorderFactory.createLineBorder( Color.RED );
    private Border orangeBorder = BorderFactory.createLineBorder( Color.ORANGE );
    private Border greenBorder = BorderFactory.createLineBorder( Color.GREEN );
    private Border blueBorder = BorderFactory.createLineBorder( Color.BLUE );
    private Border grayBorder = BorderFactory.createLineBorder( Color.GRAY );
    private Border dropTargetBorder = BorderFactory.createTitledBorder( grayBorder, "Drop Targets", TitledBorder.CENTER, TitledBorder.TOP );
    private Border canonicizersBorder = BorderFactory.createTitledBorder( grayBorder, "Canonicizers", TitledBorder.CENTER, TitledBorder.TOP );
        
    private JTable              	canonicizersTable, docTypesTable;
    private CanonicizersTableModel 	canonicizersTableModel;
    private CanonicizersSidebarTableModel	docTypesTableModel;
    private JButton             	clearAllButton;
    
    // ---------------------------------------
    // -------- CANONICIZER SETTINGS ---------
    // ---------------------------------------
    // These settings are used to search for Canonicizers to automatically load into the GUI.
    public final String canonSearchDirectory     = "../../canonicizers/"; 
    public final String canonParentQualifiedName = "com.jgaap.generics.Canonicizer";
	public final String canonQualifiedNamePrefix = "com.jgaap.canonicizers.";
//	private URI canonSearchURI = null; // This URI solves the problem where the directory
	// you started the program from would have an effect on whether canonicizers could be found
    
    /**
     * Default constructor.
     */
    public CanonicizeStepPanel( IconGlassPane glassPane, API driver ) {
        super("Canonicize");
        this.glassPane = glassPane;
        this.setLayout( new GridBagLayout() );
        if( borders ) this.setBorder( greenBorder );
        setNextButtonText("Canonicize");
        
        dndManager = new DnDManager( driver );
       /* 
        try { // Try to set up the URI for automated canonicizer loading
        	canonSearchURI = getClass().getResource( canonSearchDirectory ).toURI();
        }catch( URISyntaxException e){ e.printStackTrace(); }
        */
        buildComponents();
    }
    
    /**
     * Adds all the components to this panel.
     */
    private void buildComponents(){
    	
    	this.add( buildTopBar(),
        		new GridBagConstraints(0, 0, 2, 1,
                100, 10, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
    	
    	this.add( buildCanonTable(),
        		new GridBagConstraints(0, 1, 1, 1,
                80, 90, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    	
    	this.add( buildSidebar(),
        		new GridBagConstraints(1, 1, 1, 1,
                20, 90, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));    	
    }
    
    /**
     * Build the top bar, which has instructions and all canonicizers.
     * @return
     * 		A JPanel containing instructions and the top CanonicizerLabelPanel.
     */
    private Component buildTopBar(){
        JPanel topComponentsPanel = new JPanel( new GridBagLayout() );
        if( borders ) topComponentsPanel.setBorder( blueBorder );
        
        //  - InstructionsTextArea -
        JTextArea instructionsTextArea = new JTextArea();
        instructionsTextArea.setText("Drag and drop Canonicizers to rows in the documents table or to the drop targets table on the sidebar " +
        		"to apply them to a single document (main table) or a set of documents (sidebar). Drag Canonicizers to the Trash to get rid of them. Tip: " +
        		"hold the CTRL key while dragging to duplicate a Canonicizer.");
        instructionsTextArea.setEditable( false );
        instructionsTextArea.setOpaque( false );
        instructionsTextArea.setLineWrap( true );
        instructionsTextArea.setWrapStyleWord( true );
        if( borders ) instructionsTextArea.setBorder( orangeBorder );
        topComponentsPanel.add( instructionsTextArea,
        		new GridBagConstraints(0, 0, 2, 1,
                    100, 30, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(0, 15, 5, 5), 0, 0));
                
        //  - Canonicizers Panel / CanonicizerLabels -
        CanonicizerLabelPanel canonicizersPanel = new CanonicizerLabelPanel( dndManager,
        											   						 CanonicizerLabelPanel.ActionGroup.NONE,
        											   						 false);
        canonicizersPanel.setBorder( canonicizersBorder );
        int canonIndex = 0;
        /* This code replaced 4/27/09 (JN) to use the new, more
         * consistent AutoPopulate class
        // Iterate through all the subclasses of Canonicizer found in the canonicizers package
        for( Class<?> subClass : findClasses.getSubClasses( canonSearchURI, canonParentQualifiedName, canonQualifiedNamePrefix ) )
        {
        	try {
        		Canonicizer canon = (Canonicizer)subClass.newInstance(); //instantiate the canonicizer
        	
	        	if( canon.showInGUI() ) //if the canonicizer should be shown in the GUI, keep it
	        	{
		        	CanonicizerLabel temp = new CanonicizerLabel( dndManager, canon, canonicizersPanel, 
		        												  glassPane, canon.guiColor(), canon.displayName() );
		        	temp.setToolTipText( canon.tooltipText() );
		        	if( borders ) temp.setBorder( redBorder );        	
		        	canonicizersPanel.add( temp,
		        			new GridBagConstraints(canonIndex, 0, 1, 1,
		                    20, 100, GridBagConstraints.CENTER,
		                    GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
		        	canonIndex++;
	        	}
        	}catch(Exception e){e.printStackTrace();}
        }  */
        
        // Iterate through all subclasses of Canonicizer found in the canonicizers package
        for(Canonicizer canon : AutoPopulate.getCanonicizers()) {
        	
        	if( canon.showInGUI() ) // if the canonicizer should be shown in the GUI, keep it 
        	{
        		CanonicizerLabel temp = new CanonicizerLabel( dndManager, canon, canonicizersPanel,
        													  glassPane, canon.guiColor(), canon.displayName() );
        		temp.setToolTipText( canon.tooltipText() );
        		if( borders ) temp.setBorder( redBorder );
        		canonicizersPanel.add(temp,
        				new GridBagConstraints(canonIndex, 0, 1, 1,
        				20, 100, GridBagConstraints.CENTER,
        				GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        		canonIndex++;
        	}
        }
        topComponentsPanel.add( canonicizersPanel,
        		new GridBagConstraints(0, 1, 1, 1,
                80, 70, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 10), 0, 0));
             
        //  - ClearAllButton -
        clearAllButton = new JButton( "Clear All" );
        clearAllButton.addActionListener( this );        
        topComponentsPanel.add( clearAllButton,
        		new GridBagConstraints( 1, 1, 1, 1,
                0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 15));
        
        return topComponentsPanel;
    }
    
    /**
     * Build the main table.
     * @return
     * 		A JScrollPane containing the main canonicizer table
     */
    private Component buildCanonTable() {    	
        canonicizersTableModel = new CanonicizersTableModel( dndManager );
        canonicizersTable = new CanonicizersTable( canonicizersTableModel ); 
        canonicizersTable.setDefaultRenderer( CanonicizerLabelPanel.class, new CanonicizerLabelPanelRenderer() );
        canonicizersTable.setDefaultEditor( CanonicizerLabelPanel.class, new CanonicizerLabelPanelRenderer() );
        dndManager.addCanonClearListener( canonicizersTable );
        
        JScrollPane canonicizersScrollPane = new JScrollPane( canonicizersTable );
        if( borders ) canonicizersScrollPane.setBorder( blueBorder );
        return canonicizersScrollPane;
    }
    
    /**
     * Build the sidebar that appears on the right.
     * @return
     * 		A JPanel containing the sidebar components
     */
    private Component buildSidebar(){
        JPanel rightSidebarPanel = new JPanel( new GridBagLayout() );
        rightSidebarPanel.setBorder( dropTargetBorder );
        docTypesTableModel = new CanonicizersSidebarTableModel( dndManager );
        docTypesTable = new CanonicizersSidebarTable( docTypesTableModel );
        //docTypesTable.setDefaultRenderer( CanonicizerLabelPanel.class, new CanonicizerLabelPanelRenderer() );
        //docTypesTable.setDefaultEditor( CanonicizerLabelPanel.class, new CanonicizerLabelPanelRenderer() );
        dndManager.addCanonClearListener( docTypesTable );
        
        JScrollPane docTypesScrollPane = new JScrollPane( docTypesTable );
        rightSidebarPanel.add( docTypesScrollPane,
        		new GridBagConstraints(0, 0, 1, 1,
                100, 70, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
                
        TrashCan trashCan = new TrashCan();
        if( borders ) trashCan.setBorder( orangeBorder );
        rightSidebarPanel.add( trashCan,
        		new GridBagConstraints(0, 1, 1, 1,
                100, 10, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));        
        
        return rightSidebarPanel;        
    }
    
    /**
     * Add documents to the Canonicizers table in this panel.
     * 
     * @param documents
     * 		Documents to be added.
     */
    public void addDocuments( Collection<Document> documents ){
    	canonicizersTableModel.addDocuments( documents );
    }
    
    /**
     * Inherited method for evaluating ActionEvents.
     */
    public void actionPerformed( ActionEvent e ){
    	if( e.getSource() == clearAllButton )
    	{
    		dndManager.clearAllCanonicizers();
    	}
    }
    
    /**
     * Clear anything that has state (tables, etc)
     */
    public void clearAll(){
    	canonicizersTableModel.removeAllRows();
    	dndManager.clearAllCanonicizers();
    }

	/**
	 * @return the glassPane
	 */
	public IconGlassPane getGlassPane() {
		return glassPane;
	}
}
