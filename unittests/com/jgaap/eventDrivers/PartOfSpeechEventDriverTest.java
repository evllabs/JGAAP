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
 * 
 */
package com.jgaap.eventDrivers;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;

/**
 * @author darrenvescovi
 *
 */
public class PartOfSpeechEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.PartOfSpeechEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 */
	@Test
	public void testCreateEventSetDocumentSet() {
			String text = ("Today the fox jumped over the lazy dog "
		    		+"While the fox jumped over the lazy dog a cat ran under a truck "
		    		+"The truck missed the cat and the lazy dog was not so lazy and caught the cat");
		    
		    EventSet sampleSet = new PartOfSpeechEventDriver().createEventSet(text.toCharArray());
		    System.out.println(sampleSet.size());
		    
		    
		    EventSet expectedSet = new EventSet();
		    Vector<Event> tmp = new Vector<Event>();
		    tmp.add(new Event("NN", null));
		    tmp.add(new Event("DT", null));
		    tmp.add(new Event("NN", null));
		    tmp.add(new Event("VBD", null));
		    tmp.add(new Event("IN", null));
		    tmp.add(new Event("DT", null));
		    tmp.add(new Event("JJ", null));
		    tmp.add(new Event("NN", null));
		    
		    tmp.add(new Event("IN", null));
		    tmp.add(new Event("DT", null));
		    tmp.add(new Event("NN", null));
		    tmp.add(new Event("VBD", null));
		    tmp.add(new Event("IN", null));
		    tmp.add(new Event("DT", null));
		    tmp.add(new Event("JJ", null));
		    tmp.add(new Event("NN", null));
		    tmp.add(new Event("DT", null));
		    tmp.add(new Event("NN", null));
		    tmp.add(new Event("VBD", null));//
		    tmp.add(new Event("IN", null));
		    tmp.add(new Event("DT", null));
		    tmp.add(new Event("NN", null));
		   
		    tmp.add(new Event("DT", null));
		    tmp.add(new Event("NN", null));
		    tmp.add(new Event("VBD", null));//
		    tmp.add(new Event("DT", null));
		    tmp.add(new Event("NN", null));
		    tmp.add(new Event("CC", null));
		    tmp.add(new Event("DT", null));
		    tmp.add(new Event("JJ", null));
		    tmp.add(new Event("NN", null));
		    tmp.add(new Event("VBD", null)); //33
		    tmp.add(new Event("RB", null));
		    tmp.add(new Event("RB", null));
		    tmp.add(new Event("JJ", null));
		    tmp.add(new Event("CC", null));
		    tmp.add(new Event("VBN", null));
		    tmp.add(new Event("DT", null));
		    tmp.add(new Event("NN", null));
		    

		     expectedSet.addEvents(tmp);
		     
		     for(int i=0; i<sampleSet.size(); i++)
			    {
			    	System.out.println(sampleSet.eventAt(i).toString()+" "+expectedSet.eventAt(i).toString());
			    	System.out.println(sampleSet.eventAt(i).toString().equals(expectedSet.eventAt(i).toString()));
			    }
		     System.out.println(expectedSet.size());
		     
		     
			    assertTrue(expectedSet.equals(sampleSet));
	}

}
