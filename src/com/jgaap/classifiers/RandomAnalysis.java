/**
 **/
package com.jgaap.classifiers;



import java.util.ArrayList;
import java.util.List;

import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

/** 
 * Return a random authorship label from the list of known authors.
 * @author John Noecker Jr.
 *
 */
public class RandomAnalysis extends AnalysisDriver {
	public String displayName(){
	    return "Random Analysis";
	}

	public String tooltipText(){
	    return "Assign authorship randomly (useful to establish various baseline results";
	}

	public boolean showInGUI(){
	    return false;
	}
    @Override
    public List<Pair<String, Double>> analyze(EventSet unknown, List<EventSet> known) {
        int numChoices = known.size();
        EventSet s = known.get((int) (Math.random() * numChoices));
        List<Pair<String,Double>> results = new ArrayList<Pair<String,Double>>();
        results.add(new Pair<String, Double>(s.getAuthor(), 0.0));
        return results;
    }
}
