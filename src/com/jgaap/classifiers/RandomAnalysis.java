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
import java.util.Random;

import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

/** 
 * Return a random authorship label from the list of known authors.
 * @author John Noecker Jr.
 *
 */
public class RandomAnalysis extends AnalysisDriver {
	
	private List<EventSet> knowns;
	private Random random;
	
	public String displayName(){
	    return "Random Analysis";
	}

	public String tooltipText(){
	    return "Assign authorship randomly (useful to establish various baseline results";
	}

	public boolean showInGUI(){
	    return false;
	}
	
	public void train(List<EventSet> knowns){
		random = new Random();
		this.knowns = knowns;
	}
	
    @Override
    public List<Pair<String, Double>> analyze(EventSet unknown) {
        EventSet s = knowns.get(random.nextInt(knowns.size()));
        List<Pair<String,Double>> results = new ArrayList<Pair<String,Double>>();
        results.add(new Pair<String, Double>(s.getAuthor(), 0.0));
        return results;
    }
}
