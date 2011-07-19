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

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;

/**
 * @author Joshua Booth
 *
 */

public class HapaxLegomenaEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.HapaxLegomenaEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
	 */

	@Test
	    public void testCreateEventSetDocumentSet(){

	    /*Note: Default underlying event set is NaiveWordEventDriver*/
	    /*HapaxLegomenaEventDriver - Returns only events that occurs once*/
	    /*Test One - Normal Text w/ one event to return*/

	    Document doc = new Document();
	    doc.readStringText("The Quick Brown Fox Jumped Over The Lazy Dog 3 3 3 4 4 4 4 5 5 5 5 5");

	    EventSet sampleSet = new HapaxLegomenaEventDriver().createEventSet(doc);
	    
	    EventSet expectedSet = new EventSet();
	    Vector<Event> tmp = new Vector<Event>();
	    tmp.add(new Event("Quick"));
	    tmp.add(new Event("Brown"));
	    tmp.add(new Event("Fox"));
	    tmp.add(new Event("Jumped"));
	    tmp.add(new Event("Over"));
	    tmp.add(new Event("Lazy"));
	    tmp.add(new Event("Dog"));

	     expectedSet.addEvents(tmp);
	     
		    assertTrue(expectedSet.equals(sampleSet));
		    }
}




