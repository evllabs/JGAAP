/**
 **/
package com.jgaap.classifiers;

import java.util.ArrayList;
import java.util.List;

import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

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

    @Override
    public List<Pair<String, Double>> analyze(EventSet unknown, List<EventSet> known) {
        int i;

        EventHistogram h1 = new EventHistogram();
        EventHistogram h2;

        for (i = 0; i < unknown.size(); i++) {
            h1.add(unknown.eventAt(i));
        }

        // for (Event e : unknown)
        // h1.add(e);

        System.out.println("--- Unknown Event Set ---");
        //System.out.println("%hash = (");
        for(Event event : h1){
        	System.out.println("'"+event.getEvent().replaceAll("'", "\\'")+"','"+h1.getRelativeFrequency(event)+"',");
        }
        //System.out.println(h1);

        for (i = 0; i < known.size(); i++) {
            h2 = new EventHistogram();
            for (int j = 0; j < known.get(i).size(); j++) {
                h2.add(known.get(i).eventAt(j));
            }
            // for(Event e : known.elementAt(i))
            // h2.add(e);
            System.out.println("--- Known Event Set #" + i + " ---");
            for(Event event : h2){
            	System.out.println("'"+event.getEvent().replaceAll("'", "\\'")+"','"+h1.getRelativeFrequency(event)+"',");
            }
        }

        List<Pair<String,Double>> results = new ArrayList<Pair<String,Double>>();
        results.add(new Pair<String, Double>("No analysis performed.\n", 0.0));
        return results;
    }
}
