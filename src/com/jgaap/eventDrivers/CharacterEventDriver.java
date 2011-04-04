/**
 **/
package com.jgaap.eventDrivers;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;
import javax.swing.*;


/**
 * This event set is all individual characters, as determined by the
 * preprocessing applied in the previous stage.
 **/
public class CharacterEventDriver extends EventDriver {
	/**
	 * test using files named on command line
	 * 
	 * @param args
	 *            files to process
	 */

	@Override
	public String displayName() {
		return "Characters";
	}

	@Override
	public String tooltipText() {
		return "UNICODE Characters";
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

	/**
	 * Create sequence of characters from document set.
	 * 
	 * @param document
	 *            document of interest
	 */
	@Override
	public EventSet createEventSet(Document document) {
		EventSet es = new EventSet(document.getAuthor());
		char[] cd = document.getProcessedText();
		for (int j = 0; j < cd.length; j++) {
			es.addEvent(new Event(cd[j]));
		}
		return es;
	}

}
