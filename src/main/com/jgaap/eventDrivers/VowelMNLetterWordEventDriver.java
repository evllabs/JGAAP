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

import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

/**
 * Extract vowel-initial words with between M and N letters as features
 * 
 * @author Patrick Juola
 * @since 5.0
 * 
 */
public class VowelMNLetterWordEventDriver extends MNLetterWordEventDriver {

	@Override
	public String displayName() {
		return "Vowel M--N letter Words";
	}

	@Override
	public String tooltipText() {
		return "Vowel-initial Words with between M and N letters";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}


	@Override
	public EventSet createEventSet(char[] text) {
		EventSet eventSet = super.createEventSet(text);
		EventSet finalEventSet = new EventSet(eventSet.size());
		for(Event event : eventSet){
			if("aeiouyAEIOUY".indexOf(event.toString().charAt(0))!=-1){
				finalEventSet.addEvent(event);
			}
		}
		return finalEventSet;
	}
}
