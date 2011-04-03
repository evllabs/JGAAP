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
package com.jgaap.eventDrivers;

import com.jgaap.generics.Document;
import com.jgaap.generics.EventSet;
import javax.swing.*;

/**
 * Extract character N-grams as features.
 *
 */
public class WordNGramEventDriver extends NGramEventDriver {

    JLabel NLabel = new JLabel();
    JComboBox NBox = new JComboBox();
    int LocalN = 2;

    @Override
    public String displayName(){
    	return "Word NGrams";
    }

    @Override
    public String tooltipText(){
    	return "Groups of N Successive Words";
    }

    @Override
    public boolean showInGUI(){
    	return true;
    }

    @Override
    public GroupLayout getGUILayout(JPanel panel){

    	NLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    	NLabel.setText("N");

    	NBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50" }));
        NBox.setSelectedIndex(LocalN-1);
        NBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
        layout.setHorizontalGroup(
        		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(NLabel)
                        .addComponent(NBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(275, Short.MAX_VALUE))
            );

        layout.setVerticalGroup(
        		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(NLabel)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(NBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(255, Short.MAX_VALUE))
            );
        return layout;
    }

    private void NBoxActionPerformed(java.awt.event.ActionEvent evt) {
        LocalN = NBox.getSelectedIndex()+1;
    }

    private NGramEventDriver theDriver;


    @Override
    public EventSet createEventSet(Document ds) {
        theDriver = new NGramEventDriver();
        theDriver.setParameter("N", LocalN);
        return theDriver.createEventSet(ds);
    }
}
