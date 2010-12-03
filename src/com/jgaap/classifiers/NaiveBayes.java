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
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.Iterator;
import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

/**
 * Naive Bayes classifier performs bayesian probability model with maximum a
 * posterior or MAP rule analysis on a set of data. 
 *         author: Sandy Speer  9/2008 
 *
 * ASSUMPTIONS: Prior is the number of documents by each author. If the
 * probability of an unknown event is 0.0 then the probability of said event is
 * an arbitrarily small number(ie. 0.00001). This is done to keep any arbitrary
 * events found in the unknown eventset from causing the posteriors, or the
 * product of the event probability, to become 0.0. This is especially useful
 * for unknown events which are rarely found in text. 
 */
public class NaiveBayes extends AnalysisDriver {
	public String displayName(){
	    return "Naive Bayes Classifier";
	}

	public String tooltipText(){
	    return "Naive Bayes Probability Model with Maximum A Posterior Rule Analysis";
	}

	public boolean showInGUI(){
	    return true;
	}

    @Override
 	public List<Pair<String, Double>> analyze(EventSet unknown, List<EventSet> known) {

	// probability that a given event w1 in the EventSet is author A1 is
	// the avg probability - standard deviation of w1 over all documents by A1

	    EventHistogram hKnown = new EventHistogram();   // histogram of known eventset
	    Event theEvent;
	    
	    int flag;                      // indicator of author found
  	    int authorCount=0;             // total # of authors in vector of known eventsets
 	    String[] authors = new String[50]; // This can be increased if we need more than 50 authors.
	    int[] tenCount = new int[authors.length-1]; //# times posterior is multiplied by 1000000000 or # docs per author 
	                                                // ***** need for normalization ******
	    //  double[] posteriorA = new double[authors.length-1]; //posterior probability for each known author
	    double[] posteriorA = new double[50];
	    double temp;
        double count;                  // # of documents in known by author A
	    double sum;                    // sum of relative frequency of an unknown event in eventset by known author A
	    double sum2;                   // sum of square relative frequency 
	    double prob;                   // mu - standard deviation -- probability of event set by author A over all docs by author A
	    double posterior;              // posterior probability -- product of prob for each unknown eventset
	    double max = 0.0;              // maximum posterior probability 

	    List<Pair<String,Double>> results = new ArrayList<Pair<String,Double>>();
	    
	    TreeSet<Event> vocab = new TreeSet<Event>();  


//  *****************************************
//		create array of author names
//  *****************************************
		for(int q=0; q<known.size(); q++){
			flag = 0;
			for(int i= 0; i< authors.length; i++){  // check if author is in array authors
				if(known.get(q).getAuthor().equals(authors[i])){
					flag = 1;
					break;
				}
			}

			if(flag ==0){  // if author is not in array authors find empty place for it
				for(int i=0; i<authors.length; i++){
					if(authors[i] == null){
						authors[i] = known.get(q).getAuthor();
						authorCount = i+1;
						break;
					}
				}
			}
		}


//  *****************************************
//		create TreeSet of events in unknown document
//  *****************************************	 

	    for(int j=0; j<unknown.size(); j++){
		vocab.add(unknown.eventAt(j));
	    }

//  *****************************************
//	create relative frequency  Matrix [document][event]
//  *****************************************
	    double[][]eMatrix = new double[known.size()][vocab.size()];

		for(int i=0; i<known.size(); i++){
		    for (int l=0; l < known.get(i).size(); l++){
			hKnown.add(known.get(i).eventAt(l));
		    }
		    //EventList = hKnown.events();
		    Iterator<Event> it = vocab.iterator();
		    for(int b=0; it.hasNext(); b++) {
			theEvent = it.next();
			//			System.out.println("theEvent of interest = "+theEvent);
			eMatrix[i][b] = hKnown.getRelativeFrequency(theEvent);
			//			System.out.println("relative frequency = "+eMatrix[i][b]);
		    }
		    
		    hKnown = new EventHistogram();
		}


//  *****************************************
//	determine posterior for each author
//  *****************************************
		
	  //for each known author
	for(int i=0; i<authorCount; i++){  
	    posterior = 1.0;

	    //for each event
	    for(int j=0; j<vocab.size(); j++){	
		sum =0;
		count = 0;
		sum2 = 0;

		// for each document of author A
		for(int k=0; k<known.size(); k++){
		    if(known.get(k).getAuthor().equals(authors[i])){
			sum = sum + eMatrix[k][j];
			count = count +1;
			sum2 = sum2 +eMatrix[k][j]*eMatrix[k][j];
		 
		    }		    
		}
		
		prob = (sum/count)-Math.sqrt((1/count)*sum2 - (sum/count)*(sum/count));
		//	System.out.println("prob = " + prob);

		if (prob <= 0.0){
		    prob = .00001;  
		}
		
		temp = posterior * prob;
		if(temp <= 0.0){
		    //   System.out.println("############posterior issue######### prob = " + prob + "  posterior = " + posterior);
		    posterior = posterior*1000000000*prob;
		    tenCount[i] = tenCount[i]+1;
		    //    System.out.println("############posterior issue######### temp = " + temp + "  posterior = " + posterior);
		    if(posterior<=0.0)
			break;
		}
		else{
		    posterior = temp;
		}
	    }

	    //normalize posterior
 //	     posteriorA[i]=(prior*posterior)/normalizing;
	    //  posteriorA[i]=count*posterior;
	    posteriorA[i]=posterior;
	}

	// adjust posteriors - make on same scale since mult by 10000 if posterior became too close to zero
	for(int i=0; i<authorCount; i++){ 
	    //	    System.out.println("Author "+ i + "  Posterior= " + posteriorA[i] + " tenCount= " + tenCount[i]);
	    if(tenCount[i] > max){
		max = tenCount[i];
	    }
	}
	for(int i=0; i<authorCount; i++){
	    if(tenCount[i]!= max){
		posteriorA[i] = posteriorA[i]*1000000000*(max-tenCount[i]);
	    }
	    results.add(new Pair<String, Double>(authors[i], posteriorA[i], 2));
	}
	
	Collections.sort(results);
	Collections.reverse(results);
	return results;

	}

}
