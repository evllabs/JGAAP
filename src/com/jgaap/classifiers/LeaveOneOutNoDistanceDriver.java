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

import java.util.ArrayList;
import java.util.List;

import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.NonDistanceDependentAnalysisDriver;
import com.jgaap.util.Document;
import com.jgaap.util.Pair;

/**
 * 
 * @author David Berdik
 * 
 */

public class LeaveOneOutNoDistanceDriver extends NonDistanceDependentAnalysisDriver {
	private List<Document> knownDocuments = new ArrayList<>();

	@Override
	public String displayName(){
	    return"Leave One Out No Distance" + getAnalysisDependencyName();
	}

	@Override
	public String tooltipText(){
	    return "";
	}

	@Override
	public boolean showInGUI(){
	    return true;
	}

	public void train(List<Document> knowns) {
		// Store known documents in a class-level list.
		knownDocuments = knowns;
		super.train(knowns);
	}
	
    @Override
    public List<Pair<String, Double>> analyze(Document fakeUnknown) throws AnalyzeException {
    	// Create a temporary list of the known documents that excludes the fake unknown
    	// document. We call this document a fake unknown because it is actually known,
    	// but we want to pretend that it isn't.
    	List<Document> knownsTemp = new ArrayList<>();
    	for(Document known : knownDocuments)
    		if(known != fakeUnknown)
    			knownsTemp.add(known);
    	
    	// Pass the temporary known list and the fake unknown to the analysis driver that this
    	// driver depends on, and return the result.
    	analysisDriver.train(knownsTemp);
    	return analysisDriver.analyze(fakeUnknown);
    }
}
