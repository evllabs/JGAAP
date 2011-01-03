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
package com.jgaap.gui.dnd;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceMotionListener;

import javax.swing.*;

import com.jgaap.generics.Canonicizer;
import com.jgaap.gui.generics.GradientIcon;

/**
 * This class is intended to be both a visual and model layer
 * for representing Canonicizers visually in the JGAAP GUI.
 *
 * @author Chuck Liddell
 *
 */
public class CanonicizerLabel extends JLabel implements DragSourceMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Reference to the DnDManager so we can pass drag and drop
	 * event info to it for processing.
	 */
	private final DnDManager dndManager;
	
	/**
	 * Internal reference to a Canonicizer instance that this
	 * CanonicizerLabel represents.
	 */
	private final Canonicizer canonicizer;
	
	/**
	 * Internal reference to a CanonicizerLabelPanel that will
	 * be responsible for packaging this label for transmission
	 * to other components.
	 */
	private final CanonicizerLabelPanel owner;
	
	/**
	 * A reference to the glass pane that drag icons will be
	 * drawn on.
	 */
	private final IconGlassPane glassPane;
	
	/**
	 * Internal reference to a color that will be displayed in the
	 * GUI and be a synonymous symbol for the canonicizer attached
	 * to this label.
	 */
	private final Color color;
	
	/**
	 * Dimensions of icons shown in CanonicizerLabels with text.
	 */
	private static final Dimension large = new Dimension(20, 20);
	
	/**
	 * Dimensions of icons shown in CanonicizerLabels without text.
	 */
	private static final Dimension small = new Dimension(14, 14);
	
	/**
	 * Dimensions of icons shown in CanonicizerLabels without text.
	 */
	private static final Dimension drag = new Dimension(17, 17);
	
	// TODO: Figure out color implementation scheme		
		
	
	// -----------------------------------------------------
	// -------------- CONSTRUCTORS -------------------------
	// -----------------------------------------------------		
	/**
	 * Small icon, no text constructor that accepts color as an instance of 
	 * java.awt.Color.
	 * 
	 * @param dndManager
	 * 		DnDManager instance that we will pass drag / drop results to
	 * @param canonicizer
	 * 		Canonicizer linked to this label
	 * @param owner
	 * 		CanonicizerLabelPanel responsible for transferring this label
	 * @param color
	 * 		Color used to render Canonicizer icon
	 */	
	public CanonicizerLabel( DnDManager dndManager, Canonicizer canonicizer, 
							 CanonicizerLabelPanel owner,
							 IconGlassPane glassPane, Color color){
		super( new GradientIcon( small.height, small.width, color) );
		this.dndManager = dndManager;
		this.canonicizer = canonicizer;
		this.owner = owner;
		this.glassPane = glassPane;
		this.color = color;
		initializeDragSupport();
	}	
	
	/**
	 * Large icon with text constructor, accepts color as an instance of
	 * java.awt.Color.
	 * 
	 * @param dndManager
	 * 		DnDManager instance that we will pass drag / drop results to
	 * @param canonicizer
	 * 		Canonicizer linked to this label
	 * @param color
	 * 		Color used to render Canonicizer icon
	 * @param owner
	 * 		CanonicizerLabelPanel responsible for transferring this label
	 * @param text
	 * 		Text to display next to the Canonicizer icon
	 */
	public CanonicizerLabel( DnDManager dndManager, Canonicizer canonicizer, 
							 CanonicizerLabelPanel owner,
							 IconGlassPane glassPane, Color color, String text ){
		super( text, new GradientIcon( large.height, large.width, color), JLabel.CENTER );
		this.dndManager = dndManager;
		this.canonicizer = canonicizer;
		this.owner = owner;
		this.glassPane = glassPane;
		this.color = color;
		initializeDragSupport();
	}
	
	/**
	 * Copy constructor, to break references when needed.
	 * 
	 * Note: This constructor always produce a small icon, no text instance.
	 * 
	 * @param toCopy
	 * 		The label to get data from
	 */
	public CanonicizerLabel( CanonicizerLabel toCopy ){
		this(toCopy.getDnDManager(), toCopy.getCanonicizer(), toCopy.getOwner(), 
				toCopy.getGlassPane(), toCopy.getColor() );
	}
	
	/**
	 * Copy constructor that accepts a new CanonicizerLabelPanel reference.
	 * 
	 * Note: This constructor always produce a small icon, no text instance.
	 * 
	 * @param toCopy
	 * 		The label to get data from
	 * @param owner
	 * 		A CanonicizerLabelPanel to replace the old one
	 */
	public CanonicizerLabel( CanonicizerLabel toCopy, CanonicizerLabelPanel owner ){
		this(toCopy.getDnDManager(), toCopy.getCanonicizer(), owner, 
				toCopy.getGlassPane(), toCopy.getColor() );
	}
	
	/**
	 * Take care of the drag listener setup
	 */
	private void initializeDragSupport(){
		
		this.setTransferHandler( new LabelTransferHandler() );
		MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                //System.out.println("mousePressed");
                JComponent c = (JComponent)e.getSource();
                TransferHandler th = c.getTransferHandler();
                CanonicizerLabel.this.glassPane.showIcon( new GradientIcon( drag.width, drag.height,
                		CanonicizerLabel.this.color ) );
                th.exportAsDrag(c, e, TransferHandler.MOVE);
                
            }
        };
        this.addMouseListener(ml);
        DragSource dragSource = DragSource.getDefaultDragSource();
        dragSource.addDragSourceMotionListener( this );
	}
	
	/**
	 * Compare two CanonicizerLabel instances for equality.
	 * CanonicizerLabels are considered equal if their related
	 * Canonicizers have the same display name.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
        if(obj instanceof CanonicizerLabel) {
			return this.getCanonicizer().displayName().equals(((CanonicizerLabel)obj).getCanonicizer().displayName());
        }
        return false;
	}	
	
	
	/**
	 * @return the canonicizer
	 */
	public Canonicizer getCanonicizer() {
		return canonicizer;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return the owner
	 */
	public CanonicizerLabelPanel getOwner() {
		return owner;
	}
	
	/**
	 * @return the glassPane
	 */
	public IconGlassPane getGlassPane() {
		return glassPane;
	}
	
	/**
	 * @return the dndManager
	 */
	public DnDManager getDnDManager() {
		return dndManager;
	}
	
	// ------------------------------------------------
	// ------ DRAG AND DROP FUNCTIONALITY -------------
	// ------------------------------------------------
    /**
     * Called repeatedly while the mouse is being dragged
     * after a drag and drop event has started on this component.
     * 
     * @param dsde Event describing the drag
     */
	public void dragMouseMoved(DragSourceDragEvent dsde) {
        if( this.getRootPane() != null )
         	glassPane.updateIconLocation();
    }
	
	/**
	 * Bundle this CanonicizerIcon inside a CanonicizerPacket
	 * so that it can be transported between components.
	 * 
	 * @return A transferable containing this label
	 */
	public Transferable createTransferable(){
		return new CanonicizerPacket( this, this.getOwner().getActionGroup() );
	}
}
