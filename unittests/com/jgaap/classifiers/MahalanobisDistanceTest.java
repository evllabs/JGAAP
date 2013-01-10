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
package com.jgaap.classifiers;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Vector;

import org.junit.Test;

import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

/**
 * @author darren vescovi
 * 
 * 
 */
public class MahalanobisDistanceTest {

	//This test currently fails because the method is broken and needs work
	
	@Test
	public void testAnalyze() throws AnalyzeException {
		EventSet known1 = new EventSet();
		EventSet known2 = new EventSet();
		EventSet unknown = new EventSet();

		known1.addEvent(new Event("Mary", null));
		known1.addEvent(new Event("had", null));
		known1.addEvent(new Event("a", null));
		known1.addEvent(new Event("little", null));
		known1.addEvent(new Event("lamb", null));
		known1.addEvent(new Event("whose", null));
		known1.addEvent(new Event("fleece", null));
		known1.addEvent(new Event("was", null));
		known1.addEvent(new Event("white", null));
		known1.addEvent(new Event("as", null));
		known1.addEvent(new Event("snow.", null));
		known1.addEvent(new Event("Mary", null));
		known1.addEvent(new Event("had", null));
		known1.addEvent(new Event("a", null));
		known1.addEvent(new Event("little", null));
		known1.addEvent(new Event("lamb", null));
		known1.addEvent(new Event("whose", null));
		known1.addEvent(new Event("fleece", null));
		known1.addEvent(new Event("was", null));
		known1.addEvent(new Event("white", null));
		known1.addEvent(new Event("as", null));
		known1.addEvent(new Event("snow.", null));
		known1.addEvent(new Event("Mary", null));
		known1.addEvent(new Event("had", null));
		known1.addEvent(new Event("a", null));
		known1.addEvent(new Event("little", null));
		known1.addEvent(new Event("lamb", null));
		known1.addEvent(new Event("whose", null));
		known1.addEvent(new Event("fleece", null));
		known1.addEvent(new Event("was", null));
		known1.addEvent(new Event("white", null));
		known1.addEvent(new Event("as", null));
		known1.addEvent(new Event("snow.", null));
		known1.addEvent(new Event("Mary", null));
		known1.addEvent(new Event("had", null));
		known1.addEvent(new Event("a", null));
		known1.addEvent(new Event("little", null));
		known1.addEvent(new Event("lamb", null));
		known1.addEvent(new Event("whose", null));
		known1.addEvent(new Event("fleece", null));
		known1.addEvent(new Event("was", null));
		known1.addEvent(new Event("white", null));
		known1.addEvent(new Event("as", null));
		known1.addEvent(new Event("snow.", null));
		known1.setAuthor("Mary");

		known2.addEvent(new Event("Peter", null));
		known2.addEvent(new Event("piper", null));
		known2.addEvent(new Event("picked", null));
		known2.addEvent(new Event("a", null));
		known2.addEvent(new Event("pack", null));
		known2.addEvent(new Event("of", null));
		known2.addEvent(new Event("pickled", null));
		known2.addEvent(new Event("peppers.", null));
		known2.addEvent(new Event("Peter", null));
		known2.addEvent(new Event("piper", null));
		known2.addEvent(new Event("picked", null));
		known2.addEvent(new Event("a", null));
		known2.addEvent(new Event("pack", null));
		known2.addEvent(new Event("of", null));
		known2.addEvent(new Event("pickled", null));
		known2.addEvent(new Event("peppers.", null));
		known2.addEvent(new Event("Peter", null));
		known2.addEvent(new Event("piper", null));
		known2.addEvent(new Event("picked", null));
		known2.addEvent(new Event("a", null));
		known2.addEvent(new Event("pack", null));
		known2.addEvent(new Event("of", null));
		known2.addEvent(new Event("pickled", null));
		known2.addEvent(new Event("peppers.", null));
		known2.addEvent(new Event("Peter", null));
		known2.addEvent(new Event("piper", null));
		known2.addEvent(new Event("picked", null));
		known2.addEvent(new Event("a", null));
		known2.addEvent(new Event("pack", null));
		known2.addEvent(new Event("of", null));
		known2.addEvent(new Event("pickled", null));
		known2.addEvent(new Event("peppers.", null));
		known2.addEvent(new Event("Peter", null));
		known2.addEvent(new Event("piper", null));
		known2.addEvent(new Event("picked", null));
		known2.addEvent(new Event("a", null));
		known2.addEvent(new Event("pack", null));
		known2.addEvent(new Event("of", null));
		known2.addEvent(new Event("pickled", null));
		known2.addEvent(new Event("peppers.", null));
		known2.setAuthor("Peter");

		unknown.addEvent(new Event("Mary", null));
		unknown.addEvent(new Event("had", null));
		unknown.addEvent(new Event("a", null));
		unknown.addEvent(new Event("little", null));
		unknown.addEvent(new Event("lambda", null));
		unknown.addEvent(new Event("whose", null));
		unknown.addEvent(new Event("syntax", null));
		unknown.addEvent(new Event("was", null));
		unknown.addEvent(new Event("white", null));
		unknown.addEvent(new Event("as", null));
		unknown.addEvent(new Event("snow.", null));
		unknown.addEvent(new Event("Mary", null));
		unknown.addEvent(new Event("had", null));
		unknown.addEvent(new Event("a", null));
		unknown.addEvent(new Event("little", null));
		unknown.addEvent(new Event("lambda", null));
		unknown.addEvent(new Event("whose", null));
		unknown.addEvent(new Event("syntax", null));
		unknown.addEvent(new Event("was", null));
		unknown.addEvent(new Event("white", null));
		unknown.addEvent(new Event("as", null));
		unknown.addEvent(new Event("snow.", null));
		unknown.addEvent(new Event("Mary", null));
		unknown.addEvent(new Event("had", null));
		unknown.addEvent(new Event("a", null));
		unknown.addEvent(new Event("little", null));
		unknown.addEvent(new Event("lambda", null));
		unknown.addEvent(new Event("whose", null));
		unknown.addEvent(new Event("syntax", null));
		unknown.addEvent(new Event("was", null));
		unknown.addEvent(new Event("white", null));
		unknown.addEvent(new Event("as", null));
		unknown.addEvent(new Event("snow.", null));
		unknown.addEvent(new Event("Mary", null));
		unknown.addEvent(new Event("had", null));
		unknown.addEvent(new Event("a", null));
		unknown.addEvent(new Event("little", null));
		unknown.addEvent(new Event("lambda", null));
		unknown.addEvent(new Event("whose", null));
		unknown.addEvent(new Event("syntax", null));
		unknown.addEvent(new Event("was", null));
		unknown.addEvent(new Event("white", null));
		unknown.addEvent(new Event("as", null));
		unknown.addEvent(new Event("snow.", null));

		Vector<EventSet> esv = new Vector<EventSet>();
		esv.add(known1);
		esv.add(known2);

		AnalysisDriver analysisDriver = new MahalanobisDistance();
		analysisDriver.train(esv);
		List<Pair<String, Double>> t = analysisDriver.analyze(unknown);
		for(Pair<String, Double> element : t){
			System.out.println(element.toString());
		}
		String r = t.get(0).getFirst();

		String s = "Mary";
		//TODO: this test has been neutered until someone fixes this distance 
		assertTrue(r.equals(s));
	}
}
