// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
package com.jgaap.eventDrivers;

import com.jgaap.generics.*;
import javax.swing.*;


/**
 * Extract number of characters in each word as features.
 * 
 * @see com.jgaap.eventDrivers.NaiveWordEventDriver
 */
public class WordLengthEventDriver extends NumericEventDriver {

	@Override
	public String displayName() {
		return "Word Lengths";
	}

	@Override
	public String tooltipText() {
		return "Lengths of Word-Events";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

        @Override
        public GroupLayout getGUILayout(JPanel panel){
            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
            return layout;
        }

	private EventDriver wordTokenizer;

	@Override
	public NumericEventSet createEventSet(Document ds) {

		wordTokenizer = new NaiveWordEventDriver();
		EventSet es = wordTokenizer.createEventSet(ds);

		NumericEventSet newEs = new NumericEventSet();
		newEs.setAuthor(es.getAuthor());
		newEs.setNewEventSetID(es.getAuthor());

		for (int i = 0; i < es.size(); i++) {
			String s = (es.eventAt(i)).toString();
			if (s.equals("JGAAP:DOCUMENTBOUNDARY") == false) {
				int l = s.length();
				newEs.addEvent(new Event(String.valueOf(l)));
			}
		}
		return newEs;
	}

}
