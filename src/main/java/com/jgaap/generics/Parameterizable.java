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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jgaap.util.Pair;

/**
 * A class of things-that-can-take-(label:value)-parameters.
 * 
 * @author Juola
 * @since 1.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class Parameterizable {

    /** Parameters are stored using pairs of Strings in a HashMap */
    private Map<String, String> Parameters;

    /** Store parameter GUI settings representations (label, dropdown box pair) */
	private List<Pair<JLabel, JComboBox>> paramGUI;

    /** Construct new Parameterizable with empty set */
    public Parameterizable() {
        Parameters = new HashMap<String, String>();
        paramGUI = new ArrayList<Pair<JLabel, JComboBox>>();
    }
    
    private Parameterizable(Map<String, String> parameters) {
        Parameters = parameters;
        paramGUI = Collections.emptyList();
    }

    /** Removes all label and their associated values */
    public void clearParameterSet() {
        Parameters.clear();
    }
    
    static public Parameterizable converToParameters(String parametersString) {
    	String[] parameters = parametersString.split("\\|");
    	Map<String, String> parametersMap = new HashMap<String, String>(parameters.length);
    	for(String parameter : parameters){
    		String[] tmp = parameter.split(":", 2);
    		parametersMap.put(tmp[0].trim(), tmp[1].trim());
    	}
    	return new Parameterizable(parametersMap);
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
            return "";
        }
    }
    
    /**
     * return the value associated with label
     * 
     * @param label
     *            the label to set
     * @param val the default value
     * @return the appropriate value stored in the parameter set
     */
    public String getParameter(String label, String val) {
        if (Parameters.containsKey(label.toLowerCase())) {
            return Parameters.get(label.toLowerCase());
        } else {
            return val;
        }
    }
    
    /**
     * return the value associated with label
     * 
     * @param label
     *            the label to set
     * @param val the default value
     * @return the appropriate value stored in the parameter set
     */
    public int getParameter(String label, int val) {
    	if (Parameters.containsKey(label.toLowerCase())) {
            String tmp = Parameters.get(label.toLowerCase());
            try{
            	return Integer.parseInt(tmp);
            } catch(NumberFormatException e) {
            	return val;
            }
        } else {
            return val;
        }
    }
    
    /**
     * return the value associated with label
     * 
     * @param label
     *            the label to set
     * @param val the default value
     * @return the appropriate value stored in the parameter set
     */
    public double getParameter(String label, double val) {
    	if (Parameters.containsKey(label.toLowerCase())) {
            String tmp = Parameters.get(label.toLowerCase());
            try{
            	return Double.parseDouble(tmp);
            } catch(NumberFormatException e) {
            	return val;
            }
        } else {
            return val;
        }
    }
    
    public String getParameters() {
    	StringBuilder builder = new StringBuilder();
    	Set<Entry<String,String>> entries = Parameters.entrySet();
    	for(Entry<String, String> entry : entries){
    		builder.append(entry.getKey()).append(" : ").append(entry.getValue()).append(", ");
    	}
    	if(builder.length()>0)
    		builder.delete(builder.length()-2, builder.length()-1);
    	return builder.toString();
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
    
    public void setParameters(String parametersString) {
    	if(!parametersString.equals("")) {
	    	String[] parameters = parametersString.split("\\|");
	    	for(String parameter : parameters){
	    		String[] tmp = parameter.split(":", 2);
	    		setParameter(tmp[0].trim(), tmp[1].trim());
	    	}
    	}
    }

    public void setParameters(Parameterizable parameterizable) {
    	this.Parameters.putAll(parameterizable.Parameters);
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

    protected void changeParam(java.awt.event.ActionEvent evt) {
        for(Pair<JLabel, JComboBox> p : paramGUI) {
            this.setParameter(p.getSecond().getName(), (String)p.getSecond().getSelectedItem());
        }
    }
    
    /**
     * Return the List that makes the parameter GUI. This
     * is useful for sharing parameter requirements of
     * dependencies with the drivers that depend on them.
     * 
     * @return The List that makes the parameter GUI.
     */
    public List<Pair<JLabel, JComboBox>> getParamGUI() {
    	return paramGUI;
    }
    
    /**
     * Sets parameters based on the parameter GUI list that is passed in.
     * 
     * @param incomingParamGUI - the List that contains the parameter GUI
     * 							 information that is needed
     */
    public void setParamGUI(List<Pair<JLabel, JComboBox>> incomingParamGUI) {
    	// For each label and box pair in the list, extract the information
		// that is needed to add a parameter and pass it addParams().
    	for(Pair<JLabel, JComboBox> guiPair : incomingParamGUI) {
    		JLabel label = guiPair.getFirst();
    		JComboBox box = guiPair.getSecond();
    		String paramName = box.getName();
    		String displayName = label.getText();
    		String defaultValue = String.valueOf(box.getSelectedItem());
    		String[] possibleValues = new String[box.getModel().getSize()];
    		boolean editable = box.isEditable();
    		
    		for(int x = 0; x < possibleValues.length; x++)
    			possibleValues[x] = String.valueOf(box.getItemAt(x));
    		
    		addParams(paramName, displayName, defaultValue, possibleValues, editable);
    	}
    }
}
