// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
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

    public double[] getRow(EventSet es1, Set<Event> vocab) {
        EventHistogram h1 = new EventHistogram();
        Event theEvent;

        for (int i = 0; i < es1.size(); i++) {
            h1.add(es1.eventAt(i));
        }

        double[] row = new double[vocab.size()];
             
        h1.events();
        Iterator<Event> it = vocab.iterator();
        for (int i = 0; it.hasNext(); i++) {
            theEvent = it.next();
            row[i] = h1.getNormalizedFrequency(theEvent);
        }
        return row;
    }
    
    // limit the number of row/cols returned by n
    public double[] getRow(EventSet es1, Set<Event> vocab, int n) {
        EventHistogram h1 = new EventHistogram();
        Event theEvent;
        int MAX_ELEMENTS = n;

        for (int i = 0; i < es1.size(); i++) {
            h1.add(es1.eventAt(i));
        }

        double[] row = new double[((vocab.size() > MAX_ELEMENTS) ? MAX_ELEMENTS : vocab.size())];

        h1.events();
        Iterator<Event> it = vocab.iterator();
        for (int i = 0; it.hasNext() && (i < MAX_ELEMENTS); i++) {
            theEvent = it.next();
            row[i] = h1.getNormalizedFrequency(theEvent);
        }
        return row;
    }

}
