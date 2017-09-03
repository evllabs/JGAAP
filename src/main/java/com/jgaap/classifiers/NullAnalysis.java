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

import com.jgaap.generics.AnalysisDriver;
import com.jgaap.util.Document;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;
import com.jgaap.util.Pair;

/**
 * NullAnalysis : no analysis, but prints event sets received
 */
public class NullAnalysis extends AnalysisDriver {

	public String displayName(){
	    return"Null Analysis";
	}

	public String tooltipText(){
	    return "Prints all event sets received";
	}

	public boolean showInGUI(){
	    return true;
	}

	public void train(List<Document> knowns) {
		for (int i = 0; i < knowns.size(); i++) {
			System.out.println("--- Known Event Set #" + i + " ---");
			for(EventSet eventSet : knowns.get(i).getEventSets().values()){
				for(Event event : eventSet){
					System.out.print(event.toString()+" *** ");
				}
				System.out.println();
			}
		}
	}
	
    @Override
    public List<Pair<String, Double>> analyze(Document unknown) {

        // When we start using a useful logging function, change the
        // print(ln) lines below.
        System.out.println("--- Unknown Event Set ---");
        for(EventSet eventSet : unknown.getEventSets().values()){
			for(Event event : eventSet){
				System.out.print(event.getEvent()+" *** ");
			}
			System.out.println();
		};       

        List<Pair<String,Double>> results = new ArrayList<Pair<String,Double>>();
        results.add(new Pair<String, Double>("No analysis performed.\n", 0.0));
        return results;
    }

}
