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
package com.jgaap.classifiers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jgaap.generics.AnalysisDriver;
import com.jgaap.util.*;

/**
 * NullAnalysis : no analysis, but prints histogram of event sets received
 */
public class NullHistAnalysis extends AnalysisDriver {
	public String displayName(){
	    return "Null Histogram Analysis";
	}

	public String tooltipText(){
	    return "Prints a Histogram of Event Sets";
	}

	public boolean showInGUI(){
	    return true;
	}

	public void train(List<Document> knowns){
		int count = 0;
		for(AbsoluteHistogram histogram : knowns.stream().map(document -> AbsoluteHistogram.builder().addDocument(document).build()).collect(Collectors.toList())){
			count++;
			System.out.println("--- Known Event Set #" + count + " ---");
            for(Event event : histogram.uniqueEvents()){
            	System.out.println(
            			"'"+event.getEvent().replaceAll("'", "\\'")+"','"+
						histogram.getRelativeFrequency(event)+"',");
            }
		}
	}
	
    @Override
    public List<Pair<String, Double>> analyze(Document unknown) {
        System.out.println("--- Unknown Event Set ---");
        AbsoluteHistogram histogram = AbsoluteHistogram.builder().addDocument(unknown).build();
        for(Event event : histogram.uniqueEvents()){
        	System.out.println(
        			"'"+event.getEvent().replaceAll("'", "\\'")+"','"+
					histogram.getRelativeFrequency(event)+"',");
        }

        List<Pair<String,Double>> results = new ArrayList<Pair<String,Double>>();
        results.add(new Pair<String, Double>("No analysis performed.\n", 0.0));
        return results;
    }
}
