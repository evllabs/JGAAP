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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.jgaap.jgaapConstants;
import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.DivergenceType;
import com.jgaap.generics.Document;
import com.jgaap.generics.NeighborAnalysisDriver;

/**
 * Experiment Engine This class takes a csv file of experiments and then will
 * run them one after the other and generates result files in the tmp directory
 * 
 * @author Mike Ryan
 */
public class ExperimentEngine {
	/**
	 * This method generates unique file names and a directory structure to save
	 * the results of an experiment run
	 * 
	 * @param canons
	 *            the canonicizors used
	 * @param event
	 *            the event used
	 * @param analysis
	 *            the analysis method or distance function used
	 * @param experimentName
	 *            the given name of this experiment specified on the top line of
	 *            the experiment csv file
	 * @param number
	 *            the identifier given to this experiment
	 * @return the location of where the file will be written
	 */
	public static String fileNameGen(List<String> canons, String event,
			String analysis, String experimentName, String number) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = new java.util.Date();
		Iterator<String> iterator = canons.iterator();
		String canonName = (iterator.hasNext() ? iterator.next() : "none");
		while (iterator.hasNext()) {
			canonName = canonName + " " + iterator.next();
		}
		File file = new File(jgaapConstants.tmpDir() + canonName + "/" + event
				+ "/" + analysis + "/");
		file.mkdirs();
		// if (!file.mkdirs()) {
		// System.err.println("Error creating experiment directory");
		// System.exit(1);
		// }
		return (jgaapConstants.tmpDir() + canonName + "/" + event + "/"
				+ analysis + "/" + experimentName + number
				+ dateFormat.format(date) + ".txt");
	}

	/**
	 * This method will iterate a the rows of a csv file of experiments running
	 * jgaap on each one and then generate a results file for it
	 * 
	 * @param listPath
	 *            the location of the csv file of experiments
	 */

	public static void runExperiment(String listPath) {
		runExperiment(CSVIO.readCSV(listPath));

	}

	public static void runExperiment(List<List<String>> experimentTable) {
		final String experimentName = experimentTable.remove(0).get(0);
		List<Thread> threads = new ArrayList<Thread>();
		for (final List<String> experimentRow : experimentTable) {
			if (experimentRow.size() >= 5) {
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						String number = experimentRow.get(0);
						List<String> canons = new ArrayList<String>();
						if (!"".equalsIgnoreCase(experimentRow.get(1).trim())) {
							String[] canonicizers = experimentRow.get(1).split(
									"\\|");
							canons = new ArrayList<String>();
							for (String current : canonicizers) {
								canons.add(current.trim());
							}
						}
						String eventDriver = experimentRow.get(2);
						String analysis = experimentRow.get(3);
						String[] flags = experimentRow.get(4).split(" ");
						String documentsPath = experimentRow.get(5);
						String fileName = fileNameGen(canons, eventDriver,
								analysis, experimentName, number);
						DivergenceType divergenceType = DivergenceType.Standard;
						for (String flag : flags) {
							if (flag.equalsIgnoreCase("-avg")) {
								divergenceType = DivergenceType.Average;
							} else if (flag.equalsIgnoreCase("-max")) {
								divergenceType = DivergenceType.Max;
							} else if (flag.equalsIgnoreCase("-min")) {
								divergenceType = DivergenceType.Min;
							} else if (flag.equalsIgnoreCase("-rev")) {
								divergenceType = DivergenceType.Reverse;
							}
						}
						API experiment = new API();
						try {
							List<Document> documents = Utils
									.getDocumentsFromCSV(CSVIO
											.readCSV(documentsPath));
							for (Document document : documents) {
								experiment.addDocument(document);
							}
							for (String canonicizer : canons) {
								experiment.addCanonicizer(canonicizer);
							}
							experiment.addEventDriver(eventDriver);
							AnalysisDriver analysisDriver = experiment
									.addAnalysisDriver(analysis);
							if (analysisDriver instanceof NeighborAnalysisDriver) {
								((NeighborAnalysisDriver) analysisDriver)
										.getDistanceFunction().setParameter(
												"divergenceOption",
												divergenceType.ordinal());
							}
							experiment.execute();
							List<Document> unknowns = experiment
									.getUnknownDocuments();
							StringBuffer buffer = new StringBuffer();
							for (Document unknown : unknowns) {
								buffer.append(unknown.getResult());
							}
							Utils.saveFile(fileName, buffer.toString());
						} catch (Exception e) {
							Utils.appendToFile(jgaapConstants.tmpDir()
									+ "/EEerrors",
									Arrays.toString(experimentRow.toArray())
											+ "\n" + e.getMessage()
											+ "\n------------\n");
						}
					}
				});
				threads.add(thread);
			} else {
				System.out.println("Error wiht");
			}
		}
		if (threads.size() > 1)
			for (int i = 0; i < threads.size() / 2; i++) {
				try {
					Thread thread1 = threads.get(i * 2);
					thread1.start();
					if (i * 2 + 1 < threads.size()) {
						Thread thread2 = threads.get(i * 2 + 1);
						thread2.start();
						thread2.join();
					}
					thread1.join();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		else{
			threads.get(0).start();
			try {
				threads.get(0).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
