// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
package com.jgaap.backend;


import com.jgaap.generics.Pair;

import java.util.*;

public class Ballot<T> {
    TreeMap<T, Double> votes;
    Comparator comparator;

    public Ballot() {
        votes = new TreeMap<T, Double>();
    }

    public Ballot(Comparator comparator) {
        this.comparator = comparator;
        votes = new TreeMap<T, Double>();
    }

    public void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }


    public void vote(T candidate, Double numVotes) {
        if(votes.containsKey(candidate)) {
            double tmp = votes.remove(candidate);
            votes.put(candidate, numVotes + tmp);
        }
        else {
            votes.put(candidate, numVotes);
        }
    }

    public void vote(T candidate) {
        vote(candidate, 1.0);
    }

    public List<Pair<T, Double> > getResults() {
        List<Pair<T, Double> > list = new ArrayList<Pair<T, Double> >();
        for(Map.Entry<T, Double> e : votes.entrySet()) {
            if(comparator == null) {
                list.add(new Pair<T, Double>(e.getKey(), e.getValue(), 2));
            }
            else {
                list.add(new Pair<T, Double>(e.getKey(), e.getValue(), comparator));
            }
        }
        Collections.sort(list);
        Collections.reverse(list);
        return list;
    }

}
