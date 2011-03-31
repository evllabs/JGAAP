package com.jgaap.backend;


import com.jgaap.generics.Pair;

import java.util.*;

public class Ballot<T> {
    TreeMap<T, Double> votes;

    public Ballot() {
        votes = new TreeMap<T, Double>();
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
            list.add(new Pair<T, Double>(e.getKey(), e.getValue(), 2));
        }
        Collections.sort(list);
        Collections.reverse(list);
        return list;
    }

}
