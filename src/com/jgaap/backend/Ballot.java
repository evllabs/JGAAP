/*
 * JGAAP -- a graphical program for stylometric authorship attribution
 * Copyright (C) 2009,2011 by Patrick Juola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jgaap.backend;


import com.jgaap.generics.Pair;

import java.util.*;

@SuppressWarnings("rawtypes")
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
