/**
 * 
 */
package com.jgaap.classifiers;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

/**
 * @author Darren Vescovi
 *
 */
public class MarkovChainAnalysis extends AnalysisDriver{

	
	@Override
	public String displayName() {
		
		return "Markov Chain Analysis";
	}

	@Override
	public boolean showInGUI() {
		
		return true;
	}

	@Override
	public String tooltipText() {
		
		return "First Order Markov Chain Analysis";
	}
	
	@Override
	public List<Pair<String, Double>> analyze(EventSet unknown, List<EventSet> known) {
		
		
		List<Pair<String, Double>> results = new ArrayList<Pair<String, Double>>();
		
		Iterator<EventSet> setIt = known.iterator();
		while(setIt.hasNext()){
			Hashtable<Event, Hashtable<Event, Double>> matrix = new Hashtable<Event, Hashtable<Event, Double>>();
			EventSet ev = setIt.next();
			
			//Iterate over events to create a matrix with 
			//counts of each time a event pair sequence 
			//appears.
			Iterator<Event> eventIt = ev.iterator();
			if(eventIt.hasNext()){
				//get the first event
				Event e1 = eventIt.next();
				while(eventIt.hasNext()){
					//get the next event
					Event e2 = eventIt.next();
					if(matrix.containsKey(e1)){
						if(matrix.get(e1).containsKey(e2)){
							//find out if the event sequence is already in the matrix
							//if so increment the count by 1;
							double tmp = matrix.get(e1).get(e2).doubleValue();
							matrix.get(e1).remove(e2);
							matrix.get(e1).put(e2, new Double(tmp+1));
						}
						else{
							//add the new sequence provided the first event is already
							//in the matrix
							matrix.get(e1).put(e2, new Double(1));
						}
					}else{
						//add the new sequence to matrix
						matrix.put(e1, new Hashtable<Event, Double>());
						matrix.get(e1).put(e2, new Double(1));
					}
					//reassign e1 to be e2				
					e1=e2;
					
				}	
			}
			
			
			
		}
		
		
		
		return null;//TODO make this return something
	}


}
