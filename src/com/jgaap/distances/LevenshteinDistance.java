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
/**
 **/
package com.jgaap.distances;

import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;

/**
 * LevenshteinDistance, the edit-distance between two sequences of Events. This
 * code is based on the implementation listed in
 * http://www.merriampark.com/ldjava.htm but modified for EventSets. In theory,
 * this should work for very long strings. (Code is submitted to commons-lang
 * project, so I assume there are no legal problems.) General theory;
 * Levenshtein distance (aka edit distance) measures the amount of changes it
 * takes to re-write one string as another via deletions, insertions and
 * single-character replacements. (I.e. "gumbo" becomes "gambol" by changing "u"
 * to "a" and adding "l."
 * 
 * @author Juola
 * @version 1.0
 */
public class LevenshteinDistance extends DistanceFunction {
	public String displayName(){
	    return "Levenshtein Distance";
	}

	public String tooltipText(){
	    return "Edit Distance (Levenshtein) Nearest Neighbor Classifier";
	}

	public boolean showInGUI(){
	    return true;
	}
    /**
     * Returns Levenshtein distance between event sets es1 and es2. Note that
     * Levenshtein distance is sequence-dependent, unlike many of the
     * histogram-based distances used elsewhere in JGAAP.
     * 
     * @param es1
     *            The first EventSet
     * @param es2
     *            The second EventSet
     * @return the Levenshtein distance between them
     */

    @Override
    public double distance(EventSet s, EventSet t) {
        /*
         * The difference between this impl. and the previous is that, rather
         * than creating and retaining a matrix of size s.size()+1 by
         * t.size()+1, we maintain two single-dimensional arrays of length
         * s.size()+1. The first, d, is the 'current working' distance array
         * that maintains the newest distance cost counts as we iterate through
         * the characters of String s. Each time we increment the index of
         * String t we are comparing, d is copied to p, the second int[]. Doing
         * so allows us to retain the previous cost counts as required by the
         * algorithm (taking the minimum of the cost count to the left, up one,
         * and diagonally up and to the left of the current cost count being
         * calculated). (Note that the arrays aren't really copied anymore, just
         * switched...this is clearly much better than cloning an array or doing
         * a System.arraycopy() each time through the outer loop.) Effectively,
         * the difference between the two implementations is this one does not
         * cause an out of memory condition when calculating the LD over two
         * very large strings.
         */

        int n = s.size(); // length of s
        int m = t.size(); // length of t

        // System.out.println("s is " + s.toString());
        // System.out.println("t is " + t.toString());

        if (n == 0) {
            return m;
        } else if (m == 0) {
            return n;
        }

        int p[] = new int[n + 1]; // 'previous' cost array, horizontally
        int d[] = new int[n + 1]; // cost array, horizontally
        int _d[]; // placeholder to assist in swapping p and d

        // indexes into strings s and t

        int i; // iterates through s
        int j; // iterates through t

        Event t_j; // jth "Event" of t

        int cost; // cost

        for (i = 0; i <= n; i++) {
            p[i] = i;
        }

        for (j = 1; j <= m; j++) {
            t_j = t.eventAt(j - 1);
            d[0] = j;

            for (i = 1; i <= n; i++) {
                cost = s.eventAt(i - 1).compareTo(t_j) == 0 ? 0 : 1;
                // minimum of cell to the left+1, to the top+1, diagonally left
                // and up +cost
                d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1]
                        + cost);
            }
            // copy current distance counts to 'previous row' distance counts
            _d = p;
            p = d;
            d = _d;
        }

        // our last action in the above loop was to switch d and p, so p now
        // actually has the most recent cost counts
        return p[n];
    }
}
