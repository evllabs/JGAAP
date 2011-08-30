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
/**
 **/
package com.jgaap.eventDrivers;

import com.jgaap.generics.Document;
import com.jgaap.generics.EventSet;
import javax.swing.*;

/**
 * Extract vowel-initial words with between M and N letters as features
 * @author Patrick Juola
 * @since 5.0
 *
 */
public class VowelMNLetterWordEventDriver extends MNLetterWordEventDriver {

    JLabel MLabel = new JLabel();
    JComboBox MBox = new JComboBox();
    JLabel NLabel = new JLabel();
    JComboBox NBox = new JComboBox();

    @Override
    public String displayName(){
    	String m = (getParameter("M").isEmpty() ? "M" : getParameter("M"));
    	String n = (getParameter("N").isEmpty() ? "N" : getParameter("N"));
    	return "Vowel "+m+"--"+n+" letter Words";
    }
    
    @Override
    public String tooltipText(){
    	return "Vowel-initial Words with between M and N letters";
    }
    
    @Override
    public boolean showInGUI(){
    	return true;
    }

    @Override
    public GroupLayout getGUILayout(JPanel panel){

        MLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MLabel.setText("M");

        MBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50" }));
        String temp = this.getParameter("M");
        if (temp.equals(""))
        {
            this.setParameter("M", 2);
        }
        MBox.setSelectedIndex(Integer.parseInt(this.getParameter("M")) - 1);
        MBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MBoxActionPerformed(evt);
                }
        });

        NLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NLabel.setText("N");

        NBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50" }));
        temp = this.getParameter("N");
        if (temp.equals(""))
        {
            this.setParameter("N", 3);
        }
        NBox.setSelectedIndex(Integer.parseInt(this.getParameter("N")) - 1);
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
                    .addComponent(MLabel)
                    .addComponent(MBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NLabel)
                    .addComponent(NBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(423, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(366, Short.MAX_VALUE))
        );
        return layout;
    }

    private void NBoxActionPerformed(java.awt.event.ActionEvent evt) {
        this.setParameter("N", NBox.getSelectedIndex()+1);
    }

    private void MBoxActionPerformed(java.awt.event.ActionEvent evt) {
        this.setParameter("M", MBox.getSelectedIndex()+1);
    }

    private MNLetterWordEventDriver theDriver;

    @Override
    public EventSet createEventSet(Document ds) {
        theDriver = new MNLetterWordEventDriver();
        theDriver.setParameter("underlyingevents", "Vowel-initial Words");
        String temp = this.getParameter("M");
        if (temp.equals(""))
        {
            this.setParameter("M", 2);
        }
        theDriver.setParameter("M",  this.getParameter("M"));
        temp = this.getParameter("N");
        if (temp.equals(""))
        {
            this.setParameter("N", 3);
        }
        theDriver.setParameter("N",  this.getParameter("N"));
        return theDriver.createEventSet(ds);
    }
}
