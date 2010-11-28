/**
 *   JGAAP -- Java Graphical Authorship Attribution Program
 *   Copyright (C) 2009 Patrick Juola
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation under version 3 of the License.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/
package com.jgaap.backend;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import com.jgaap.jgaap;
import com.jgaap.jgaapConstants;

/**
 * Experiment Engine This class takes a csv file of experiments and then will
 * run them one after the other and generates result files in the tmp directory
 * 
 * @author Mike Ryan
 */
public class ExperimentEngine {
/**
 * This method generates unique file names and a directory structure to save the results of an experiment run
 * @param canon the canonicizors used
 * @param event the event used
 * @param analysis the analysis method or distance function used
 * @param experimentName the given name of this experiment specified on the top line of the experiment csv file
 * @param number the identifier given to this experiment
 * @return the location of where the file will be written
 */
    private static String fileNameGen(Vector<String> canon, String event,
            String analysis, String experimentName, String number) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd(HH:mm:ss)");
        java.util.Date date = new java.util.Date();
        String canonName = "none";
        String tmpCanon = new String();
        for (String iterator : canon) {
            tmpCanon = tmpCanon + iterator;
        }
        if (!tmpCanon.equals("")) {
            canonName = tmpCanon;
        }
        File file = new File(jgaapConstants.tmpDir() + canonName + "/" + event + "/"
                + analysis + "/");
        if(!file.mkdirs()) {
            System.err.println("Error creating experiment directory");
            System.exit(1);
        }
        return (jgaapConstants.tmpDir() + canonName + "/" + event + "/" + analysis + "/"
                + experimentName + dateFormat.format(date) + number + ".txt");
    }
/**
 * This method will iterate a the rows of a csv file of experiments running jgaap on each one and then generate a results file for it
 * @param listPath the location of the csv file of experiments
 */
    
    public static void runExperiment(String listPath){
    	runExperiment(CSVIO.readCSV(listPath));
    	
    }
    
    public static void runExperiment(Vector<Vector<String>> experimentList) {
        String experimentName = experimentList.remove(0).elementAt(0);
        while (!experimentList.isEmpty()) {
            Vector<String> currentExperiment = experimentList.remove(0);
            String number = currentExperiment.remove(0);
            String[] canonicizers = currentExperiment.remove(0).split(" ");
            if (canonicizers.length % 2 != 0) {
                System.out.println("Error in cononicizer formation");
                continue;
            }
            Vector<String> canonBuff = new Vector<String>();
            for (int i = 0; i < canonicizers.length; i += 2) {
                canonBuff.add(canonicizers[i] + " " + canonicizers[i + 1]);
            }
            String eventSet = currentExperiment.remove(0);
            String analysis = currentExperiment.remove(0);
            String[] flags = currentExperiment.remove(0).split(" ");
            String corpus = currentExperiment.remove(0);
            String resultFileName = fileNameGen(canonBuff, eventSet, analysis,
                    experimentName, number);
            String[] run = new String[20];
            run[0] = "-es";
            run[1] = eventSet;
            run[2] = "-a";
            run[3] = analysis;
            run[4] = "-l";
            run[5] = corpus;
            run[6] = "-s";
            run[7] = resultFileName;
            int i = 8;
            for (String itt : canonBuff) {
                run[i] = "-c";
                i++;
                run[i] = itt;
                i++;
            }
            for (int j = 0; j < flags.length; j++) {
                if (!flags[j].equals("")) {
                    run[i + j] = "-" + flags[j];
                }
            }
            jgaap.main(run);
        }
    }
}
