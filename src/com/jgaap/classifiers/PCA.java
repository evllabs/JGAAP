/**
 **/
package com.jgaap.classifiers;


import static org.math.array.LinearAlgebra.eigen;
import static org.math.array.LinearAlgebra.times;
import static org.math.array.StatisticSample.covariance;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.math.array.linearalgebra.EigenvalueDecomposition;
import org.math.plot.FrameView;
import org.math.plot.Plot2DPanel;
import org.math.plot.PlotPanel;

import com.jgaap.backend.KernelMethodMatrix;
import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;
import com.jgaap.jgaap;

/**
 * PCA - Principle Component Analysis Written by: Sandy Speer 10/2008 /
 *
 * NOTE: difference b/t PCA & SPCA -- SPCA normalizes the data set with respect
 * to its variance converts data to z-score 
 */
public class PCA extends AnalysisDriver {

	public String displayName(){
	    return "PCA";
	}

	public String tooltipText(){
	    return "Principle Component Anaysis (Graphs Only - No Conclusions)";
	}

	public boolean showInGUI(){
	    return true;
	}

    @Override
    public List<Pair<String, Double>> analyze(EventSet unknown, List<EventSet> known) {

        TreeSet<Event> vocab = new TreeSet<Event>(); // list of all events
        // (known & unknown)
        KernelMethodMatrix matrixFactory = new KernelMethodMatrix(); //
        double[][] eventMatrix;
        double totalSum = 0.0;
        double[] empiricalMean;
        double[][] covarianceMatrix;
        double[][] basis;
        double[] eigenValue;
        double[] eigenValueDsort;
        double[][] eigenVectors;
        EigenvalueDecomposition e;
        double[][] pBasis;
        double[][] finalMatrix;
        Plot2DPanel plot = new Plot2DPanel();

        String maxAuthor = "unknown";

        // *****************************************
        // create TreeSet of events
        // *****************************************
        for (int i = 0; i < known.size(); i++) {
            for (int j = 0; j < known.get(i).size(); j++) {
                vocab.add(known.get(i).eventAt(j));
            }
        }
        for (int j = 0; j < unknown.size(); j++) {
            vocab.add(unknown.eventAt(j));
        }

        // System.out.println("vocab size = " + vocab.size() +
        // "   known size = "+ known.size());
        // *****************************************
        // create relative freq matrix [document][event]
        // *****************************************
        eventMatrix = new double[known.size() + 1][vocab.size()];

        for (int i = 0; i < known.size(); i++) {
            eventMatrix[i] = matrixFactory.getRow(known.get(i), vocab);
        }
        eventMatrix[known.size()] = matrixFactory.getRow(unknown, vocab);
        /***************************************************************
         * System.out.println("Event Matrix" ); for(int i=0;
         * i<eventMatrix.length; i++){ for(int j=0; j<eventMatrix[0].length;
         * j++){ System.out.print(eventMatrix[i][j] +" "); }
         * System.out.println(); } System.out.println(); /
         **************************************************************/
        // *****************************************
        // create mean vectors
        // *****************************************
        empiricalMean = new double[eventMatrix.length];

        for (int i = 0; i < eventMatrix.length; i++) {
            for (int j = 0; j < eventMatrix[i].length; j++) {
                totalSum += eventMatrix[i][j];
            }
            empiricalMean[i] = totalSum / eventMatrix[0].length;
            totalSum = 0.0;
        }

        /***************************************************************
         * System.out.println("Mean Vector  -- orig data matrix" ); for(int i=0;
         * i<empiricalMean.length; i++){ System.out.println(empiricalMean[i]
         * +" "); } System.out.println(); /
         **************************************************************/

        // *****************************************
        // create deviations from means matrix
        // *****************************************
        for (int i = 0; i < eventMatrix.length; i++) {
            for (int j = 0; j < eventMatrix[i].length; j++) {
                eventMatrix[i][j] = eventMatrix[i][j] - empiricalMean[i];
            }
        }
        /***************************************************************
         * System.out.println("Event Matrix  -- MEAN ADJ matrix" ); for(int i=0;
         * i<eventMatrix.length; i++){ for(int j=0; j<eventMatrix[0].length;
         * j++){ System.out.print(eventMatrix[i][j] +" "); }
         * System.out.println(); } System.out.println(); /
         **************************************************************/

        // *****************************************
        // create covariance matrix
        // *****************************************

        covarianceMatrix = covariance(eventMatrix);

        /***************************************************************
         * System.out.println("covariance matrix" ); for(int i=0;
         * i<covarianceMatrix.length; i++){ for(int j=0;
         * j<covarianceMatrix[0].length; j++){
         * System.out.print(covarianceMatrix[i][j] +" "); }
         * System.out.println(); } System.out.println(); /
         **************************************************************/

        // *******************************************************************
        // find the eigenvalues & eigenvectors of the covariance matrix
        // *******************************************************************
        eigenValue = new double[covarianceMatrix[0].length];
        eigenValueDsort = new double[eigenValue.length];
        basis = new double[eigenValue.length][eigenValue.length];
        pBasis = new double[eigenValue.length][2];

        e = eigen(covarianceMatrix);
        eigenVectors = e.getV();
        eigenValue = e.get1DRealD(); // covariance matrix is symetric, so only
        // real eigenvalues...

        /***************************************************************
         * System.out.println("EIGEN VALUES"); for(int i=0; i<eigenValue.length;
         * i++){ System.out.println(eigenValue[i]); } System.out.println(); /
         **************************************************************/

        // put eigen values in decending order
        for (int i = 0; i < eigenValue.length; i++) {
            eigenValueDsort[i] = eigenValue[eigenValue.length - i - 1];
        }

        /***************************************************************
         * System.out.println("EIGEN VALUES DECENDING ORDER"); for(int i=0;
         * i<eigenValueDsort.length; i++){
         * System.out.println(eigenValueDsort[i]); } System.out.println(); /
         **************************************************************/

        /***************************************************************
         * System.out.println("EIGEN VECTORS"); for(int i=0;
         * i<eigenVectors.length; i++){ for(int j=0; j<eigenVectors.length;
         * j++){ System.out.print(eigenVectors[i][j] + " "); }
         * System.out.println(); } System.out.println(); /
         **************************************************************/

        // sort eign vectors in correspond w/ decending eigen values
        for (int i = 0; i < eigenValue.length; i++) {
            for (int j = 0; j < eigenValue.length; j++) {
                if (eigenValue[i] == eigenValueDsort[j]) {
                    for (int r = 0; r < eigenVectors.length; r++) {
                        basis[r][j] = eigenVectors[r][i];
                    }
                }
            }
        }

        /***************************************************************
         * System.out.println("EIGEN VECTORS SORTED"); for(int i=0;
         * i<eigenVectors.length; i++){ for(int j=0; j<eigenVectors.length;
         * j++){ System.out.print(basis[i][j] + " "); } System.out.println(); }
         * System.out.println(); /
         **************************************************************/

        // *****************************************
        // create matrix of 1st & 2nd principal components
        // to be used as a basis for projection
        // *****************************************
        System.out.println("BASIS   ");
        for (int i = 0; i < eigenVectors.length; i++) {
            for (int j = 0; j < 2; j++) {
                pBasis[i][j] = basis[i][j];

                System.out.print(pBasis[i][j] + " ");
            }
            System.out.println();
        }

        // *****************************************
        // project data onto new basis
        // *****************************************

        finalMatrix = times(eventMatrix, pBasis);

        System.out.println();
        for (int i = 0; i < finalMatrix.length; i++) {
            if (i < finalMatrix.length - 1) {
                System.out.print(known.get(i).getAuthor() + "     ");
            } else {
                System.out.print("Unknown     ");
            }
            for (int j = 0; j < 2; j++) {
                System.out.print(finalMatrix[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();
        
        /*
         * print graph if in the GUI otherwise(tests or CLI) do not print it
         */
        
        if(!(getParameter("printGraph").equals("false") || jgaap.commandline == true)){
        	setParameter("printGraph", "true");
        }
        
        if (getParameter("printGraph").equals("true")){
        /* Now, generate a plot of the new coordinates */
        // Keep track of the authors to correctly colour the graph - JIN
        // 10/27/08
        String currentAuthor = "";
        int matrixPosition = 0;
        int color = 0;
        // Loop through each eventset, adding the author's works to the plot.
        for (EventSet d : known) {
            if (!(d.getAuthor().equals(currentAuthor))) {
                currentAuthor = d.getAuthor();
                color = (color + 1) % PlotPanel.COLORLIST.length;
            }
            plot.addScatterPlot(currentAuthor, PlotPanel.COLORLIST[color],
                    KernelMethodMatrix
                            .transposeRow(finalMatrix[matrixPosition]));
            matrixPosition++;
        }

        plot.addScatterPlot(unknown.getDocumentName(), Color.BLACK,
                KernelMethodMatrix.transposeRow(finalMatrix[matrixPosition]));

        plot.addLegend("SOUTH");

        new FrameView(plot);
        }
        
        List<Pair<String,Double>> results = new ArrayList<Pair<String,Double>>();
        results.add(new Pair<String, Double>(maxAuthor, 0.0));
        return results;
    }

}
