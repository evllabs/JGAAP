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
package com.jgaap.generics;

import org.apache.tools.ant.taskdefs.Java;

import javax.swing.*;
import java.awt.*;
import java.awt.Event;
import java.util.HashMap;
import java.util.Vector;

/**
 * A class of things-that-can-take-(label:value)-parameters.
 * 
 * @author Juola
 * @since 1.0
 */
public class Parameterizable {

    /** Parameters are stored using pairs of Strings in a HashMap */
    private HashMap<String, String> Parameters;

    /** Store parameter GUI settings representations (label, dropdown box pair) */
    private Vector<Pair<JLabel, JComboBox>> paramGUI;

    /** Construct new Parameterizable with empty set */
    public Parameterizable() {
        Parameters = new HashMap<String, String>();
        paramGUI = new Vector<Pair<JLabel, JComboBox>>();
    }

    /** Removes all label and their associated values */
    public void clearParameterSet() {
        Parameters.clear();
    }

    /**
     * Removes a label and its associated value
     * 
     * @param label
     *            the label to set
     */
    public void deleteParameter(String label) {
        Parameters.remove(label);
    }

    /**
     * return the value associated with label
     * 
     * @param label
     *            the label to set
     * @return the appropriate value stored in the parameter set
     */
    public String getParameter(String label) {
        if (Parameters.containsKey(label.toLowerCase())) {
            return Parameters.get(label.toLowerCase());
        } else {
            return new String("");
        }
    }

    /**
     * Set label=String.valueOf(value) (persistantly)
     * 
     * @param label
     *            the label to set
     * @param value
     *            the (double) value to set the label to
     */
    public void setParameter(String label, double value) {
        Parameters.put(label.toLowerCase(), String.valueOf(value));
    }

    /**
     * Set label=String.valueOf(value) (persistantly)
     * 
     * @param label
     *            the label to set
     * @param value
     *            the (integer) value to set the label to
     */
    public void setParameter(String label, int value) {
        Parameters.put(label.toLowerCase(), String.valueOf(value));
    }

    // do we need things that return Integers and so forth?

    /**
     * Set label=String.valueOf(value) (persistantly)
     * 
     * @param label
     *            the label to set
     * @param value
     *            the (long) value to set the label to
     */
    public void setParameter(String label, long value) {
        Parameters.put(label.toLowerCase(), String.valueOf(value));
    }

    /**
     * Set label=value (persistantly)
     * 
     * @param label
     *            the label to set
     * @param value
     *            the value to set the label to
     */
    public void setParameter(String label, String value) {
        Parameters.put(label.toLowerCase(), value);
    }

    public void addParams(String paramName, String displayName, String defaultValue, String[] possibleValues, boolean editable) {
        JLabel label = new JLabel();
        JComboBox box = new JComboBox();

        setParameter(paramName, defaultValue);

        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setText(displayName);

        box.setModel(new javax.swing.DefaultComboBoxModel(possibleValues));
        box.setEditable(editable);
        box.setName(paramName);
        box.setSelectedItem(defaultValue);
        box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeParam(evt);
            }
        });

        paramGUI.add(new Pair<JLabel, JComboBox>(label, box));
    }

    public GroupLayout getGUILayout(JPanel panel){

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        GroupLayout.ParallelGroup labels = layout.createParallelGroup();
        GroupLayout.ParallelGroup boxes = layout.createParallelGroup();
        for(Pair<JLabel, JComboBox> p : paramGUI) {
            labels.addComponent(p.getFirst());
            boxes.addComponent(p.getSecond());
        }
        hGroup.addGroup(labels);
        hGroup.addGroup(boxes);
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        for(Pair<JLabel, JComboBox> p: paramGUI) {
            vGroup.addGroup(layout.createParallelGroup().addComponent(p.getFirst()).addComponent(p.getSecond()));
        }
        layout.setVerticalGroup(vGroup);

        return layout;
    }

    private void changeParam(java.awt.event.ActionEvent evt) {
        for(Pair<JLabel, JComboBox> p : paramGUI) {
            this.setParameter(p.getSecond().getName(), (String)p.getSecond().getSelectedItem());
        }
    }
}
