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
 * 
 */
package com.jgaap.gui.dnd;

import com.jgaap.backend.API;
import com.jgaap.generics.Document;
import com.jgaap.generics.DocType;

import java.util.ArrayList;
import java.util.EnumMap;

import javax.swing.JTable;

/**
 * This class is the single point of contact between the guiDriver (GUI backend)
 * and all of the drag and drop functionality on the CanonicizeStepPanel. Any 
 * drag and drop code that will affect the backend has to come through here. No
 * drag and drop components should interact directly with guiDriver.
 * 
 * TODO: Update GUI after mass changes
 * 
 * @author Chuck Liddell
 *
 */
public class DnDManager {

	/**
	 * Reference to the guiDriver that powers the backend of the JGAAP GUI.
	 */
	private final API driver;
	
	/**
	 * List of CanonicizerLabelPanels that are registered for canonicizer changes.
	 */
	private ArrayList<CanonicizerLabelPanel> labelPanels = new ArrayList<CanonicizerLabelPanel>();
	
	/**
	 * List of JTables that are registered for canonicizer clears.
	 */
	private ArrayList<JTable> tables = new ArrayList<JTable>();
	
	/**
	 * List of CanonicizerLabelPanels that are registered for canonicizer changes,
	 * sorted by DocType.
	 */
	private EnumMap<DocType, ArrayList<CanonicizerLabelPanel>> panelsByDocType =
		new EnumMap<DocType, ArrayList<CanonicizerLabelPanel>>(DocType.class);
	
	/**
	 * Constructor takes a reference to the guiDriver, which it will use to
	 * pass information to the backend.
	 */
	public DnDManager( API driver ){
		this.driver = driver;
		for( DocType docType : DocType.values() )
			panelsByDocType.put( docType, new ArrayList<CanonicizerLabelPanel>() );
	}
	
	/*
	 * These methods provide the main functionality of this class, which
	 * is to receive and interpret drag and drop results funneled here
	 * by all of the classes in the dnd package. Components in the GUI
	 * that receive and initiate drag and drop transfers will call these
	 * methods as CanonicizerLabels are moved around the GUI interface
	 * by the user.
	 */
	
	/**
	 * Add a canonicizer to all documents.
	 * 
	 * @param canonicizer
	 * 		The canonicizer to add
	 */
	public void processAdd( CanonicizerLabel label ){
		//System.out.println("DnDManager add to all. (" + labelPanels.size() + ")");
		for( CanonicizerLabelPanel panel : labelPanels )
			panel.addCanonicizerLabel( new CanonicizerLabel( label, panel ) );
		try {
			driver.addCanonicizer( label.getCanonicizer().displayName().toLowerCase() );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Add a canonicizer to a set of documents based on docType.
	 * 
	 * @param canonicizer
	 * 		The canonicizer to add
	 * @param docType
	 * 		The docType to apply the canonicizer to
	 */
	public void processAdd( CanonicizerLabel label,
							DocType	docType ){
		//System.out.println("DnDManager add to docType: " + docType + "(" + panelsByDocType.get(docType) + ")");
		for( CanonicizerLabelPanel panel : panelsByDocType.get(docType) )
			panel.addCanonicizerLabel( new CanonicizerLabel( label, panel ) );
		try {
			driver.addCanonicizer( label.getCanonicizer().displayName(), docType );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Add a canonicizer to a specific document.
	 * 
	 * @param canonicizer
	 * 		The canonicizer to add
	 * @param document
	 * 		The document to apply the canonicizer to
	 */
	public void processAdd( CanonicizerLabel label,
							Document document ){
		//System.out.println("DnDManager add to document: " + document);
		try {
			driver.addCanonicizer( label.getCanonicizer().displayName(), document );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Remove a canonicizer from all documents.
	 * 
	 * @param canonicizer
	 * 		The canonicizer to remove
	 */
	public void processRemove( CanonicizerLabel label ){
		//System.out.println("DnDManager remove from all.");
		for( CanonicizerLabelPanel panel : labelPanels )
			panel.removeForeignCanonicizerLabel( new CanonicizerLabel( label, panel ) );
		driver.removeCanonicizer( label.getCanonicizer().displayName().toLowerCase() );
	}
	
	/**
	 * Remove a canonicizer from a set of documents based on docType.
	 * 
	 * @param canonicizer
	 * 		The canonicizer to remove
	 * @param docType
	 * 		The docType to remove the canonicizer from
	 */
	public void processRemove( CanonicizerLabel label,
							   DocType docType ){
		//System.out.println("DnDManager remove from docType: " + docType);
		for( CanonicizerLabelPanel panel : panelsByDocType.get(docType) )
			panel.removeForeignCanonicizerLabel( new CanonicizerLabel( label, panel ) );
		driver.removeCanonicizer( label.getCanonicizer().displayName().toLowerCase(), docType );
	}
	
	/**
	 * Remove a canonicizer from a single document.
	 * 
	 * @param canonicizer
	 * 		The canonicizer to remove
	 * @param docType
	 * 		The document to remove the canonicizer from
	 */
	public void processRemove( CanonicizerLabel label,
							   Document document ){
		//System.out.println("DnDManager remove from document: " + document);
		driver.removeCanonicizer( label.getCanonicizer().displayName().toLowerCase(), document );
	}
	
	/**
	 * Clear all canonicizers on all documents.
	 */
	public void clearAllCanonicizers(){
		//System.out.println("DnDManager clear all canonicizers.");
		for( CanonicizerLabelPanel panel : labelPanels )
			panel.removeAllCanonicizerLabels();
		for( JTable table : tables )
			table.repaint();
		driver.removeAllCanonicizers();
	}
	
	/**
	 * Allows CanonicizerLabelPanels to listen for global changes.
	 * 
	 * @param CanonicizerLabelPanel
	 * 		The new panel to notify of global canonicizer changes
	 */
	public void addCanonChangeListener( CanonicizerLabelPanel panel ){
		labelPanels.add( panel ); // add to global list
		if( panel.getDocType() != null ) // add to docType list
			panelsByDocType.get( panel.getDocType() ).add( panel ); 
	}
	
	/**
	 * Allows JTables to be repainted when canonicizers have cleared.
	 * 
	 * @param table
	 * 		The JTable that should be repainted
	 */
	public void addCanonClearListener( JTable table ){
		tables.add( table );
	}
}
