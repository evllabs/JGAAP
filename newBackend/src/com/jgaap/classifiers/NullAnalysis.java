/**
 *   JGAAP -- Java Graphical Authorship Attribution Program
 *   Copyright (C) 2009 Patrick Juola
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation under version 3 of the License.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/
package com.jgaap.classifiers;

import java.util.ArrayList;
import java.util.List;

import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

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

    @Override
    public List<Pair<String, Double>> analyze(EventSet unknown, List<EventSet> known) {
        int i;

        // When we start using a useful logging function, change the
        // print(ln) lines below.
        System.out.println("--- Unknown Event Set ---");
        System.out.println(unknown.toString());

        for (i = 0; i < known.size(); i++) {
            System.out.println("--- Known Event Set #" + i + " ---");
            System.out.println(known.get(i).toString());
        }

        List<Pair<String,Double>> results = new ArrayList<Pair<String,Double>>();
        results.add(new Pair<String, Double>("No analysis performed.\n", 0.0));
        return results;
    }

}
