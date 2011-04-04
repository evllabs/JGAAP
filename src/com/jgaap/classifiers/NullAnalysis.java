/**
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
