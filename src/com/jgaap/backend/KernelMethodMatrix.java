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
package com.jgaap.backend;

import static org.math.array.DoubleArray.transpose;

import java.util.Iterator;
import java.util.Set;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;

/**
 * KernelMethodDistance: Uses LDA, PCA, SVM as appropriate to classify unknowns
 * as appropriate.
 * This essentially calculates a histogram of the event set passed in, including only
 * those events from a specified vocabulary, and returns it as a vector to be used
 * in a classification matrix as described above.
 */
public class KernelMethodMatrix {

    // Special transposition command for row vectors
    public static double[][] transposeRow(double[] matrix) {
        double[][] newMatrix = new double[1][];
        newMatrix[0] = matrix;
        return transpose(newMatrix);
    }

    /*
     * Returns the normalized frequency of the vocab words 
     *   based on the frequencies in the histogram constructed 
     *   from the EventSet.
     */
    public double[] getRow(EventSet es1, Set<Event> vocab) {
    	//Convert EventSet to EventHistogram
        EventHistogram h1 = new EventHistogram(es1);
        
        //Row of frequencies to be returned
        double[] row = new double[vocab.size()];
        
        //For each Event in vocab, store the normalized
        //  frequency of it in row
        Event theEvent;
        Iterator<Event> it = vocab.iterator();
        for (int i = 0; it.hasNext(); i++) {
            theEvent = it.next();
            row[i] = h1.getNormalizedFrequency(theEvent);
        }
        
        return row;
    }
    
    /*
     * Same as getRow(EventSet, Set<Event>), except limits
     * the number of row/cols returned.
     */
    public double[] getRow(EventSet es1, Set<Event> vocab, int MAX_ELEMENTS) {
        EventHistogram h1 = new EventHistogram(es1);
        Event theEvent;

        double[] row = new double[((vocab.size() > MAX_ELEMENTS) ? MAX_ELEMENTS : vocab.size())];

        Iterator<Event> it = vocab.iterator();
        for (int i = 0; it.hasNext() && (i < MAX_ELEMENTS); i++) {
            theEvent = it.next();
            row[i] = h1.getNormalizedFrequency(theEvent);
        }
        
        return row;
    }

}
