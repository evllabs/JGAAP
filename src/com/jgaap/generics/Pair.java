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
package com.jgaap.generics;

import java.util.Comparator;

/**
 * A generic pair object (for when two values need to be passed simultaneously)
 * @author John Noecker Jr.
 * @since 5.0.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class Pair<First, Second> implements Comparable<Pair<First, Second> > {
    private First first;
    private Second second;
    Comparator myComparator;


    /**
     * Create a new pair object and specify how pairs should be sorted.
     * @param first
     * @param second
     * @param myComparator
     */
    public Pair(First first, Second second, Comparator myComparator) {
        this.first = first;
        this.second = second;
        this.myComparator = myComparator;
    }

    /**
     * Create a new pair object that will sort according to the first or second
     * component's natural ordering.
     * @param first
     * @param second
     * @param sortElement If 0 or 1, this pair sorts by the first element, otherwise it sorts by the second element.
     */
    public Pair(First first, Second second, int sortElement) {
        this.first = first;
        this.second = second;

        if(sortElement == 0 || sortElement == 1) {
            this.myComparator = new sortFirst();
        }
        else {
            this.myComparator = new sortSecond();
        }
    }

    /**
     * Create a new Pair object that sorts by the first element.
     * @param first
     * @param second
     */
    public Pair(First first, Second second) {
        this(first, second, 1);
    }

    public int compareTo(Pair<First, Second> o) {
        try {
            return myComparator.compare(this, o);
        } catch(ClassCastException e) {
            System.err.println("Inconsistency in class Pair.\n");
            return Integer.MAX_VALUE;
        }
    }

    public boolean equals(Object o) {
        if(!(o instanceof Pair)) {
            return false;
        }
        else {
            try {
                return (myComparator.compare(this, o) == 0);
            } catch(ClassCastException e) {
                return false;
            }
        }
    }

    private class sortFirst implements Comparator<Pair<? extends Comparable, Second> >  {

        public int compare(Pair<? extends Comparable, Second> x, Pair<? extends Comparable, Second> y) {
            if(x.first != null) {
                return x.first.compareTo(y.first);
            }
            System.err.println("Null pointer in pair first element.\n");
            return Integer.MAX_VALUE;
        }
    }

    private class sortSecond implements Comparator<Pair<First, ? extends Comparable> >  {

        public int compare(Pair<First, ? extends Comparable> x, Pair<First, ? extends Comparable> y) {
            if(x.second != null) {

                return x.second.compareTo(y.second);
            }
            System.err.println("Null pointer in pair second element.\n");
            return Integer.MAX_VALUE;
        }
    }

    public String toString() {
        return "[" + first.toString() + ":" + second.toString() + "]";
    }

    public First getFirst() {
        return first;
    }

    public Second getSecond() {
        return second;
    }
}
