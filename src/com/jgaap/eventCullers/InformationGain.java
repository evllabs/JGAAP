package com.jgaap.eventCullers;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventCuller;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Sort out the N most informative events across all documents.
 * Uses smoothing to make sure that a divide by 0 error doesn't occur.
 * IG = log(i = 1 to n Product(mi!)/((i=1 to n Sum(mi))!(i=1 to n Product(Pi^mi))))
 * 
 * @author Christine Gray
 */
public class InformationGain extends EventCuller {
	 public InformationGain() {
	        super();
	        addParams("numEvents", "N", "50", new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "15", "20", "25", "30", "40", "45", "50", "75", "100", "150", "200" }, true);
	        addParams("informative" , "I", "Most", new String[] {"Most", "Least"}, true);
	        addParams("smoothing", "S", "Yes", new String[]{"Yes" , "No"}, true);
	 }

	@Override
	public List<EventSet> cull(List<EventSet> eventSets) {
	     List<EventSet> results = new ArrayList<EventSet>();
	     int minPos, numEvents;
	     String informative, smoothing;

	        if(!getParameter("minPos").equals("")) {
	            minPos = Integer.parseInt(getParameter("minPos"));
	        }
	        else {
	            minPos = 0;
	        }

	        if(!getParameter("numEvents").equals("")) {
	            numEvents = Integer.parseInt(getParameter("numEvents"));
	        }
	        else {
	            numEvents = 50;
	        }
	        
	        if(!getParameter("informative").equals("")){
	        	informative = getParameter("informative");
	        }
	        else {
	        	informative = "Most";
	        }
	        if(!getParameter("smoothing").equals("")){
	        	smoothing = getParameter("smoothing");
	        }
	        else{
	        	smoothing = "Yes";
	        }

	        EventHistogram hist = new EventHistogram();

	        for(EventSet oneSet : eventSets) {
	            for(Event e : oneSet) {
	                hist.add(e);
	            }
	        }
	        List<Pair<Event, Integer>> eventFrequencies = hist.getSortedHistogram();
	        
	        /*
	         * Find the total number of events
	         */
	        Double total = 0.0;
	        /* This number is used for smoothing so that nothing can be divided by 0 later */
	        Double tenth = 0.0; 
	        /* Counts total numbers of events across all documents */
	        for(int i = 0; i<eventFrequencies.size();i++){
	        	total += eventFrequencies.get(i).getSecond();  
	        }
	        tenth = total * .1;

	       /*
	        * Find the percentage that each event occurs
	        * This is Pi in the formula
	        */
	       List<Pair<Event, Double>> eventPercentage=new ArrayList<Pair<Event, Double>>(eventFrequencies.size());
	       for(int i = 0; i<eventFrequencies.size();i++){
	    	   Event token = eventFrequencies.get(i).getFirst();
	    	   Integer frequency = eventFrequencies.get(i).getSecond();
	    	   
	    	   /*
	    	    * If the frequency occurs less than 10% its frequency is adjusted to be equal to 10%. 
	    	    * This avoids the possibility of dividing by 0 later on
	    	    */
	    	   if(smoothing.equals("Yes")){
		    	   if(frequency < tenth){
		    		   frequency = tenth.intValue();		    		  
		    	   }
	    	   }
	    	   Double prob = frequency/total;
	    	   Pair<Event,Double> pair = new Pair<Event,Double>(token,prob);
	    	   eventPercentage.add(pair);
	       }
	       List<Pair<Event, Double>> infoGain = new ArrayList<Pair<Event, Double>>(eventFrequencies.size());
	       Double percentage = 0.0;
	       BigDecimal numerator = new BigDecimal(1.0);
	       BigDecimal denom1 = new BigDecimal(0.0);
	       BigDecimal denom2 = new BigDecimal(1.0);
	       
	       /* 
	        * The list count keeps track of the frequency of each event in the individual documents
	        * This is mi in the formula
	        */
		   List<Integer> count = new ArrayList<Integer>();
	       for(int i = 0; i<eventPercentage.size();i++){
	    	   Event tmp = null;
	    	   for(EventSet oneSet: eventSets){	    		   
	    		   int c = 0;	//count of events
	    		   for(Event e: oneSet){	    			   
	    			   if(e.equals(eventPercentage.get(i).getFirst())){
	    				   percentage = eventPercentage.get(i).getSecond();
	    				   c++;
	    				   tmp = e;
	    			   }	    			 	    			   
	    		   }
	    		   count.add(c);
    			   c = 0;
	    	   }
	    	   
	    	   for(int k = 0; k<count.size();k++){
	    		 /* 
	    		  * Calculates numerator 
	    		  * i = 0 to n Product of mi!
	    		  */
	    		   numerator = numerator.multiply(factorial(count.get(k))); 
	    		   BigDecimal countK = new BigDecimal(count.get(k));
	    		 /* 
	    		  * Calculates first term of the denominator 
	    		  * i = 0 to n Sum of mi
	    		  */
	    		   denom1 = denom1.add(countK);  
	    		   BigDecimal power = new BigDecimal(Math.pow(percentage, count.get(k)));
	    		   
	    		   if(smoothing.equals("Yes")){
	    			   if(power.doubleValue()==0.0){
	    				   /* 
	    				    * Calculates the power that percentage needs to be raised to in order to equal 0.1E-300
	    				    * This avoids Pi^mi = 0
	    				    */
	    				   Double newCount = Math.log(0.1E-300)/Math.log(percentage);
	    				   power = new BigDecimal( Math.pow(percentage,newCount));
	    			   }
	    		   }
	    		 /*
	    		  * Calculates second term of the denominator
	    		  * i = 0 to n Product Pi^mi
	    		  */
	    		   denom2 = denom2.multiply(power); 
	    	   }
	    	   denom1 = factorial(denom1); 
	    	   denom1 = denom1.multiply(denom2);  
	    	   numerator = numerator.divide(denom1, 4);
	    	   Double res = Math.log(numerator.doubleValue());
	    	   infoGain.add(new Pair<Event,Double>(tmp, res));	 
	    	   count.clear();
	    	   BigDecimal Temp1 = new BigDecimal(1.0);
	    	   BigDecimal Temp0 = new BigDecimal(0.0);
	    	   numerator = Temp1;
	    	   denom1 = Temp0;
	    	   denom2 = Temp1;
	       }
	       infoGain = getSorted(infoGain, informative);  
	       for(EventSet oneSet : eventSets) {
	            EventSet newSet = new EventSet();
	            for(Event e : oneSet) {
	                for(int i = minPos; i < minPos + numEvents; i++) {
	                    if(e.equals(infoGain.get(i).getFirst())) {
	                        newSet.addEvent(e);
	                    }
	                }
	            }
	            results.add(newSet);
	        }
		return results;
	}

	@Override
	public String displayName() {
		return "Information Gain";
	}

	@Override
	public String tooltipText() {
		return "Analyze only the N most or least informative events across all documents";
	}
	
	@Override
	public String longDescription() {
		return "Information Gain \n" +"Analyze only the N most or least informative events across all documents\n"+
				"IG = log(i = 1 to n \u03A0mi!/((i=1 to n \u03A3mi)!(i=1 to n \u03A0Pi^mi)))\n" +
				"Choose 'Yes' for S to use smoothing.  This prevents a divide by 0 error from occuring when Pi^mi = 0";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	
	/**
	 * 
	 * @param n 
	 * 				
	 * @return n!
	 */
	public BigDecimal factorial(double n){
		BigDecimal num = new BigDecimal(n);
		BigDecimal result = new BigDecimal(1.0);
		BigDecimal tmp = new BigDecimal(1.0);
		if(!(n==0.0)){
			for(int i = num.intValue(); i>0; i--){
				result = result.multiply(num);
				num = num.subtract(tmp);
			}
		}
		return result;
	}
	/**
	 * 
	 * @param n 
	 * 				
	 * @return n!
	 */
	public BigDecimal factorial(BigDecimal n){
		BigDecimal result = new BigDecimal(1.0);
		BigDecimal tmp = new BigDecimal(1.0);
		if(!n.equals(0)){
			for(int i = n.intValue(); i>0; i--){
				result = result.multiply(n);
				n = n.subtract(tmp);
			}
		}
		return result;
	}
	
	/**
	 * This method determines whether to sort based on most informative or least informative
	 * @param list 
	 * 				the list of events to be sorted
	 * @param informative 
	 * 				determines whether to sort by most informative or least informative
	 * @return the sorted list
	 */
	public List<Pair<Event, Double>> getSorted(List<Pair<Event, Double>> list, String informative){
		if(informative.equals("Most")){
			return getMostInformative(list);
		}
		else return getLeastInformative(list);
	}
	
	/**
	 * Sorts in order of most informative to least informative
	 * informative = "Most"
	 * @param list 
	 * 				the list of events to be sorted
	 * @return the list sorted from most informative to least informative
	 */
	 public List<Pair<Event, Double>>  getMostInformative(List<Pair<Event, Double>> list) {
		List<Pair<Event,Double>> result = new ArrayList<Pair<Event,Double>>();
		boolean added = false;
		for(int i = 0; i<list.size();i++){
			Pair<Event, Double> tmp = list.get(i);
			if(result.isEmpty()){
				result.add(tmp);
				added = true;
			}
			else{
				for (int j = 0; j<result.size();j++){
					if ((tmp.getSecond().toString()).compareTo(result.get(j).getSecond().toString())>0 && !added){
						result.add(j, tmp);
						added = true;
					}
						
				}
			}
			if(added == false){
				result.add(tmp);
			}
			added = false;		
		}
		
		return result;
	}
	 
	 /**
	  * Sorts in order of least informative to most informative
	  * informative = "Least" 
	  * @param list 
	  * 			the list of events to be sorted
	  * @return the list sorted from least informative to most informative
	  */
	 public List<Pair<Event, Double>>  getLeastInformative(List<Pair<Event, Double>> list) {
		List<Pair<Event,Double>> result = new ArrayList<Pair<Event,Double>>();
		boolean added = false;
		for(int i = 0; i<list.size();i++){
			Pair<Event, Double> tmp = list.get(i);
			if(result.isEmpty()){
				result.add(tmp);
				added = true;
			}
			else{
				for (int j = 0; j<result.size();j++){
					if ((tmp.getSecond().toString()).compareTo(result.get(j).getSecond().toString())<0 && !added){
						result.add(j, tmp);
						added = true;
					}						
				}
			}
			if(added == false){
				result.add(tmp);
			}
			added = false;		
		}		
		return result;
	}
}
