package com.jgaap.distances;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

/**
 * 
 * This was implemented because the Kendall tau rank correlation showed promise
 * This runs in N long N time unlike the simple implementation
 * This version also takes ties of X Y and (X,Y) into account making to closest to the Tau-B
 * The original impl calculated the Tau-A 
 * 
 * @author Michael Ryan
 * @since 5.0.2
 */
public class KendallCorrelationTauBDistance extends DistanceFunction {

	@Override
	public String displayName() {
		return "Kendall Correlation TauB";
	}

	@Override
	public String tooltipText() {
		return "Method for calculating tau as suggested by William Knight in A Computer Method for Calculating Kendall's Tau with Ungrouped Data (1966)";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public double distance(List<Double> v1, List<Double> v2){
		List<Pair<String, Double>> unknownList = new ArrayList<Pair<String,Double>>();
		List<Pair<String, Double>> knownList = new ArrayList<Pair<String,Double>>();
		for(int i=0; i<v1.size();i++){
			unknownList.add(new Pair<String, Double>(Integer.toString(i), v1.get(i), 2));
			knownList.add(new Pair<String, Double>(Integer.toString(i), v2.get(i), 2));
		}
		return tauDistance(unknownList, knownList);
	}
	
	@Override
	public double distance(EventSet es1, EventSet es2) {
		
		EventHistogram unknownHistogram = es1.getHistogram();
		EventHistogram knownHistogram = es2.getHistogram();
		//System.err.println("1");
		List<Pair<String, Double>> unknownList = new ArrayList<Pair<String, Double>>(unknownHistogram.getNTypes());
		List<Pair<String, Double>> knownList = new ArrayList<Pair<String, Double>>(knownHistogram.getNTypes());
		for(Event e : unknownHistogram){
			unknownList.add(new Pair<String, Double>(e.getEvent(), unknownHistogram.getRelativeFrequency(e),2));
		}
		for(Event e : knownHistogram){
			knownList.add(new Pair<String, Double>(e.getEvent(), knownHistogram.getRelativeFrequency(e), 2));
		}
		return tauDistance(unknownList, knownList);
	}
	private double tauDistance(List<Pair<String, Double>> unknownList, List<Pair<String, Double>>knownList){
		//System.err.println("2");
		Collections.sort(unknownList);
		Collections.reverse(unknownList);
		Collections.sort(knownList);
		Collections.reverse(knownList);
		//System.err.println("3");
		Map<String, Integer> unknownRanks = new HashMap<String, Integer>(unknownList.size());
		//ti
		List<Integer> unknownTies = new ArrayList<Integer>();
		int rank = 0;
		int count = 0;
		int ties = 0;
		double previousFrequency = 0.0;
		for (Pair<String, Double> current : unknownList) {
			count++;
			if (!current.getSecond().equals(previousFrequency)) {
				if(ties!=0){
					unknownTies.add(ties);
					ties =0;
				}
				rank = count;
				previousFrequency = current.getSecond();
			} else {
				ties++;
			}
			unknownRanks.put(current.getFirst(), rank);
		}
		
		Map<String, Integer> knownRanks = new HashMap<String, Integer>(knownList.size());
		//ui
		List<Integer> knownTies = new ArrayList<Integer>();
		rank = 0;
		count = 0;
		ties = 0;
		previousFrequency = 0.0;
		for (Pair<String, Double> current : knownList) {
			count++;
			if (!current.getSecond().equals(previousFrequency)) {
				if(ties !=0 ){
					knownTies.add(ties);
					ties = 0;
				}
				rank = count;
				previousFrequency = current.getSecond();
			} else {
				ties++;
			}
			knownRanks.put(current.getFirst(), rank);
		}
		
		//System.err.println("4");
		
		Set<String> allEvents = new LinkedHashSet<String>(unknownList.size());
		for(Pair<String, Double> element : unknownList){
			allEvents.add(element.getFirst());
		}
		for(Pair<String, Double> element : knownList){
			allEvents.add(element.getFirst());
		}
		
		List<Pair<Integer, Integer>> ranks = new ArrayList<Pair<Integer,Integer>>(allEvents.size());
		Integer unknownDefault = unknownRanks.size()+1;
		Integer knownDefault = knownRanks.size()+1;
		Integer tmp;
		Integer tmp2;
		for(String event : allEvents){
			
			ranks.add(new Pair<Integer, Integer>((tmp=unknownRanks.get(event))==null ? unknownDefault : tmp, 
					(tmp2=knownRanks.get(event))==null ? knownDefault : tmp2,
							new Comparator<Pair<Integer, Integer>>() {

								@Override
								public int compare(Pair<Integer, Integer> o1,
										Pair<Integer, Integer> o2) {
									if(o1.getFirst()==null || o1.getSecond()==null)
										return Integer.MAX_VALUE;
									int result = o1.getFirst().compareTo(o2.getFirst());
									if(result == 0){
										result = o1.getSecond().compareTo(o2.getSecond());
									}
									return result;
								}
							}));
		}
		//System.err.println("5");
		List<Integer> y = new ArrayList<Integer>(ranks.size());
		for(Pair<Integer, Integer> current : ranks){
			y.add(current.getSecond());
		}
		//System.err.println("5.1");
		Collections.sort(ranks);
		//System.err.println("5.1.1");
		Iterator<Pair<Integer, Integer>> iterator = ranks.iterator();
		Pair<Integer, Integer> previous = iterator.next();
		List<Integer> pairTies = new ArrayList<Integer>();
		ties = 0;
		//System.err.println("5.2");
		while(iterator.hasNext()){
			Pair<Integer, Integer> current = iterator.next();
			if(current.equals(previous)){
				ties++;
			}else{
				if(ties!=0){
					pairTies.add(ties);
					ties = 0;
				}
			}
		}
		//System.err.println("6");
		double n0 = allEvents.size()*(allEvents.size()-1)/2.0;
		double n1 = 0;
		for(Integer t : unknownTies){
			n1 += t*(t-1)/2.0;
		}
		double n2 = 0;
		for(Integer u : knownTies){
			n2 += u*(u-1)/2.0;
		}
		double n3 = 0;
		for(Integer v : pairTies){
			n3 += v*(v-1)/2.0;
		}

		//System.err.println("n0 "+n0+" n1 "+n1+" n2 "+n2+" n3"+n3);
//		int swaps = swaps(y);
		//System.err.println(swaps);
		double result = (n0-n1-n2+n3-2*swaps(y));
		result = result/Math.sqrt((n0-n1)*(n0-n2));
		return 1-result;
	}
	
//	private int swapCounter = 0;
//	private int mergeCounter = 0;
	
	private int swaps(List<Integer> list){
//		swapCounter++;
		if (list.size()<=1){
			return 0;
		}
		int middle = list.size()/2;

		List<Integer> left = list.subList(0, middle);
		List<Integer> right = list.subList(middle, list.size());
		//System.err.println("a");
		int tmp = swaps(left)+swaps(right);
		Collections.sort(left);
		Collections.sort(right);
		//System.err.println("b");
		int merge = merge(left, right);
		//System.err.println("mergers:"+merge);
		//System.err.println(merge+tmp);
		return tmp+merge;
	}
	
	private int merge(List<Integer> left, List<Integer> right){
//		mergeCounter++;
		int totalLength = left.size()+right.size();
		int i =0;
		int j =0;
		int swaps = 0;
		//System.err.println("I");
		//System.err.println("left:"+left.size()+" right:"+right.size()+" i:"+i+" j:"+j+" swaps:"+swaps);
		while((i+j)<totalLength&&j<right.size()){
			//System.out.println(right.get(j));
			if(i>=left.size() 
					|| right.get(j)
					<left.get(i)){
				swaps += left.size()-(i);
				j++;
			} else {
				i++;
			}
			//System.err.println("left:"+left.size()+" right:"+right.size()+" i:"+i+" j:"+j+" swaps:"+swaps);
		}
		//System.err.println("II");
		//System.err.println("left:"+left.size()+" right:"+right.size()+" i:"+i+" j:"+j+" swaps:"+swaps);
		return swaps;
	}
	
}
