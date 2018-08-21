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
package com.jgaap.eventDrivers;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

/**
 * 
 * @author David Berdik
 *
 */
public class KSkipNGramWordEventDriverTest {
	@Test
	public void testCreateEventDocumentSet() throws EventGenerationException {
		String sampleInput = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. "
				+ "Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar "
				+ "ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.";
		EventDriver eventDriver = new KSkipNGramWordEventDriver();
		eventDriver.setParameter("K", 3);
		eventDriver.setParameter("N", 4);
		EventSet actualSet = eventDriver.createEventSet(sampleInput.toCharArray());
		
		EventSet expectedSet = new EventSet();
		expectedSet.addEvent(new Event("Lorem amet, Maecenas Fusce", eventDriver));
		expectedSet.addEvent(new Event("ipsum consectetuer porttitor posuere,", eventDriver));
		expectedSet.addEvent(new Event("dolor adipiscing congue magna", eventDriver));
		expectedSet.addEvent(new Event("sit elit. massa. sed", eventDriver));
		expectedSet.addEvent(new Event("amet, Maecenas Fusce pulvinar", eventDriver));
		expectedSet.addEvent(new Event("consectetuer porttitor posuere, ultricies,", eventDriver));
		expectedSet.addEvent(new Event("adipiscing congue magna purus", eventDriver));
		expectedSet.addEvent(new Event("elit. massa. sed lectus", eventDriver));
		expectedSet.addEvent(new Event("Maecenas Fusce pulvinar malesuada", eventDriver));
		expectedSet.addEvent(new Event("porttitor posuere, ultricies, libero,", eventDriver));
		expectedSet.addEvent(new Event("congue magna purus sit", eventDriver));
		expectedSet.addEvent(new Event("massa. sed lectus amet", eventDriver));
		expectedSet.addEvent(new Event("Fusce pulvinar malesuada commodo", eventDriver));
		expectedSet.addEvent(new Event("posuere, ultricies, libero, magna", eventDriver));
		expectedSet.addEvent(new Event("magna purus sit eros", eventDriver));
		expectedSet.addEvent(new Event("sed lectus amet quis", eventDriver));
		expectedSet.addEvent(new Event("pulvinar malesuada commodo urna.", eventDriver));
		
		assertTrue(expectedSet.equals(actualSet));
	}
}