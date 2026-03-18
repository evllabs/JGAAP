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
package com.jgaap.classifiers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.jgaap.util.Document;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;
import com.jgaap.util.Pair;

public class BurrowsDeltaTest {

	@Test
	public void testAnalyzeFiltersZeroVarianceFeatures() {
		BurrowsDelta burrowsDelta = new BurrowsDelta();
		List<Pair<String, Double>> results = runClassification(burrowsDelta);

		assertEquals("AuthorA", results.get(0).getFirst());
		for (Pair<String, Double> result : results) {
			assertFalse(Double.isNaN(result.getSecond()));
			assertFalse(Double.isInfinite(result.getSecond()));
		}
	}

	@Test
	public void testAnalyzeFiltersZeroVarianceFeaturesInCentroidMode() {
		BurrowsDelta burrowsDelta = new BurrowsDelta();
		burrowsDelta.setParameter("centroid", "true");
		List<Pair<String, Double>> results = runClassification(burrowsDelta);

		assertEquals("AuthorA", results.get(0).getFirst());
		for (Pair<String, Double> result : results) {
			assertFalse(Double.isNaN(result.getSecond()));
			assertFalse(Double.isInfinite(result.getSecond()));
		}
	}

	private List<Pair<String, Double>> runClassification(BurrowsDelta burrowsDelta) {
		List<Document> knowns = new ArrayList<Document>();
		knowns.add(documentFromSample("AuthorA", "AAB"));
		knowns.add(documentFromSample("AuthorB", "AAC"));

		Document unknown = documentFromSample(null, "AAB");

		burrowsDelta.train(knowns);
		return burrowsDelta.analyze(unknown);
	}

	private Document documentFromSample(String author, String sample) {
		EventSet eventSet = new EventSet();
		for (int i = 0; i < sample.length(); i++) {
			eventSet.addEvent(new Event(sample.charAt(i), null));
		}

		Document document = new Document();
		document.setAuthor(author);
		document.addEventSet(null, eventSet);
		return document;
	}
}
