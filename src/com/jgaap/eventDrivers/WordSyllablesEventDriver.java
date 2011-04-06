// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
package com.jgaap.eventDrivers;

import com.jgaap.generics.*;
import javax.swing.*;


/**
 * This event set is the number of syllables in a given word, defined (naively)
 * by the number of vowel clusters. This will not work well for words like
 * "react" or "safes," but should be a decent approximation. Improvements
 * welcome, of course
 */
public class WordSyllablesEventDriver extends NumericEventDriver {

	@Override
	public String displayName() {
		return "Syllables Per Word";
	}

	@Override
	public String tooltipText() {
		return "Number of vowel clusters/word (min 1)";
	}

	@Override
	public String longDescription() {
		return "Simplified syllable counter; each vowel cluster is assumed to be one syllable (as in 'beater' 'queue' or 'candelabrae').   Will fail with words like 'sometimes' or 'coopt'.";
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

	public EventDriver wordtokenizer = new NaiveWordEventDriver();

	/* define vowels for cluster elements */
	public String vowellist = "aeiouyAEIOUY";

	@Override
	public NumericEventSet createEventSet(Document ds) {
		EventSet es = wordtokenizer.createEventSet(ds);
		NumericEventSet newEs = new NumericEventSet();
		newEs.setAuthor(es.getAuthor());
		newEs.setNewEventSetID(es.getAuthor());

		for (int i = 0; i < es.size(); i++) {
			String s = (es.eventAt(i)).toString();
			int l = 0;
			for (int j = 0; j < s.length(); j++) {
				if ((vowellist.indexOf(s.charAt(j)) != -1)
						&& ((j == s.length() - 1) || (vowellist.indexOf(s.charAt(j + 1)) == -1))) {
					l++;
				}
			}
			if (l == 0) {
				l = 1; // handle words like "Dr" by setting to 1
			}
			newEs.addEvent(new Event(String.valueOf(l)));
		}
		return newEs;
	}

}
