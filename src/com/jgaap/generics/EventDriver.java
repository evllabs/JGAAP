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
package com.jgaap.generics;
import javax.swing.*;

/**
 * Class for EventSet factories. As an abstract class, can only be instantiated
 * through subclasses. Legacy code inherited from WAY back.
 * 
 * @author unknown
 * @since 1.0
 */
public abstract class EventDriver extends Parameterizable implements Comparable<EventDriver>, Displayable {
	
	public abstract String displayName();

	public abstract String tooltipText();

	public abstract boolean showInGUI();

    /**
     * Creates an EventSet from a given DocumentSet after preprocessing.
     * 
     * @since 1.0
     * @param doc
     *            the DocumentSet to be Event-ified
     * @return the EventSet containing the Events from the document(s)
     */

    abstract public EventSet createEventSet(Document doc);

    
    public int compareTo(EventDriver o){
    	return displayName().compareTo(o.displayName());
    }
    
    public GroupLayout getGUILayout(JPanel panel){
    	JLabel label = new JLabel();
    	JComboBox box = new JComboBox();
    	
    	//label.setFont(new java.awt.Font("Lucida Grande", 0, 24)); 
    	label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    	label.setText(displayName());

    	box.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        	
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
        layout.setHorizontalGroup(
        		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(label)
                        .addComponent(box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(275, Short.MAX_VALUE))
            );	
        
        layout.setVerticalGroup(
        		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(255, Short.MAX_VALUE))
            );
        return layout;
    }
}


