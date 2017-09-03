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

import java.util.List;

import com.jgaap.util.Document;
import com.jgaap.util.Pair;

/**
 * Class for statistical analysis methods. As an abstract class, can only be
 * instantiated through subclasses. Legacy code inherited from WAY back.
 * 
 * @author unknown
 * @since 1.0
 */
public abstract class AnalysisDriver extends Parameterizable implements
		Comparable<AnalysisDriver>, Displayable {

	public String longDescription() {
		return tooltipText();
	}

	public abstract boolean showInGUI();

	/**
	 * Pass in samples of known author's writing
	 * Generate the models that unknown documents will be tested against
	 * 
	 * @param knownEventSets Samples of known authors writing
	 */
	
	abstract public void train(List<Document> knownDocuments) throws AnalyzeException;
	
	/**
	 * Compare the current document to the trained models
	 * 
	 * Only to be used after train()
	 * 
	 * @param unknownEventSet - sample of unknown authors work
	 * @return Sorted list of possible authors, sorted based on likelyhood they authored the document
	 * @throws AnalyzeException
	 */
	abstract public List<Pair<String, Double>> analyze(Document unknownDocument) throws AnalyzeException;

	public int compareTo(AnalysisDriver o) {
		return displayName().compareTo(o.displayName());
	}

}
