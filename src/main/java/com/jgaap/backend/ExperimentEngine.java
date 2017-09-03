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

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.jgaap.JGAAPConstants;
import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.ValidationDriver;
import com.jgaap.util.Document;

/**
 * Experiment Engine This class takes a csv file of experiments and then will
 * run them one after the other and generates result files in the tmp directory
 * 
 * @author Mike Ryan
 */
public class ExperimentEngine {

	static Logger logger = Logger.getLogger(ExperimentEngine.class);

	private static final int workers = 1;
	
	private static String language = "english";

	/**
	 * This method generates unique file names and a directory structure to save
	 * the results of an experiment run
	 * 
	 * @param canons
	 *            the canonicizors used
	 * @param events
	 *            the events used
	 * @param analysis
	 *            the analysis method or distance function used
	 * @param experimentName
	 *            the given name of this experiment specified on the top line of
	 *            the experiment csv file
	 * @param number
	 *            the identifier given to this experiment
	 * @return the location of where the file will be written
	 */
	public static String fileNameGen(List<String> canons, String[] events, String analysis, String experimentName,
			String number) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Iterator<String> iterator = canons.iterator();
		StringBuilder canonNameBuilder = new StringBuilder();
		while (iterator.hasNext()) {
			canonNameBuilder.append(iterator.next().trim()).append(" ");
		}
		String canonName = canonNameBuilder.toString().trim();
		if (canonName.isEmpty())
			canonName = "none";
		iterator = Arrays.asList(events).iterator();
		StringBuilder eventNameBuilder = new StringBuilder();
		while (iterator.hasNext()) {
			eventNameBuilder.append(iterator.next().trim()).append(" ");
		}
		String eventName = eventNameBuilder.toString().trim();
		String path = JGAAPConstants.JGAAP_TMPDIR + canonName.replace("/", "\\/") + JGAAPConstants.separator
				+ eventName.trim().replace("/", "\\/") + JGAAPConstants.separator + analysis.trim().replace("/", "\\/") + JGAAPConstants.separator;
		File file = new File(path);
		boolean newDirs = file.mkdirs();
		if (!newDirs) {
			; // Nothing (check added to satisfy static analysis / show we are
				// aware of this)
		}
		return (path + experimentName + number + dateFormat.format(date) + ".txt");
	}

	/**
	 * This method will iterate a the rows of a csv file of experiments running
	 * jgaap on each one and then generate a results file for it
	 * 
	 * @param listPath
	 *            the location of the csv file of experiments
	 */

	public static void runExperiment(String listPath, String lang) {
		try {
			runExperiment(CSVIO.readCSV(listPath), lang);
		} catch (IOException e) {
			logger.fatal("Problem processing experiment file: " + listPath, e);
		}

	}

	public static void runExperiment(List<List<String>> experimentTable, String lang) {
		if (lang != null)
			language = lang;
		final String experimentName = experimentTable.remove(0).get(0);
		ExecutorService experimentExecutor = Executors.newFixedThreadPool(workers);
		List<Future<String>> runningExperiments = new ArrayList<Future<String>>(experimentTable.size());
		for (final List<String> experimentRow : experimentTable) {
			if (experimentRow.isEmpty()) {
				continue;
			} else if (experimentRow.size() >= 6) {
				String number = experimentRow.get(0);
				String[] canonicizers = experimentRow.get(1).trim().split("\\s*&\\s*");
				String[] events = experimentRow.get(2).trim().split("\\s*&\\s*");
				String analysis = experimentRow.get(3).trim();
				String distance = experimentRow.get(4).trim();
				String documentsPath = experimentRow.get(5).trim();
				String fileName = fileNameGen(Arrays.asList(canonicizers), events, analysis
						+ (distance.isEmpty() ? "" : "-" + distance), experimentName, number);

				runningExperiments.add(experimentExecutor.submit(new Experiment(canonicizers, events, analysis,
						distance, documentsPath, fileName)));
			} else {
				logger.error("Experiment " + experimentRow.toString() + " missing " + (6 - experimentRow.size())
						+ " column(s)");
			}
		}
		experimentExecutor.shutdown();

		while (!experimentExecutor.isTerminated()) {
			Iterator<Future<String>> iterator = runningExperiments.iterator();
			while (iterator.hasNext()) {
				Future<String> current = iterator.next();
				if (current.isDone()) {
					try {
						logger.info("Experiment: " + current.get() + " has finished.");
					} catch (InterruptedException e) {
						logger.error("Problem printing experiment completion", e);
					} catch (ExecutionException e) {
						logger.error("Problem printing experiment completion", e);
					}
					iterator.remove();
					System.gc(); //I know this is terrible and should be removed MVR (Dear Future Me, Please forgive me.)
				}
			}
		}
	}

	private static class Experiment implements Callable<String> {

		private String[] canonicizers;
		private String[] events;
		private String analysis;
		private String distance;
		private String documentsPath;
		private String fileName;

		public Experiment(String[] canonicizers, String[] events, String analysis, String distance,
				String documentsPath, String fileName) {
			this.canonicizers = canonicizers;
			this.events = events;
			this.analysis = analysis;
			this.distance = distance;
			this.documentsPath = documentsPath;
			this.fileName = fileName;
		}

		@Override
		public String call() throws Exception {
			API experiment = API.getPrivateInstance();
			experiment.setLanguage(language);
			try {
				List<List<String>> tmp;
				if (documentsPath.startsWith(JGAAPConstants.JGAAP_RESOURCE_PACKAGE)) {
					tmp = CSVIO.readCSV(com.jgaap.JGAAP.class.getResourceAsStream(documentsPath));
				} else {
					tmp = CSVIO.readCSV(documentsPath);
				}
				List<Document> documents = Utils.getDocumentsFromCSV(tmp);
				for (Document document : documents) {
					experiment.addDocument(document);
				}
				for (String canonicizer : canonicizers) {
					if (!canonicizer.isEmpty())
						experiment.addCanonicizer(canonicizer);
				}
				for (String event : events) {
					String[] canons = null;
					String[] cullers = null;
					String[] splitCanon = event.split("@", 2);
					if(splitCanon.length > 1){
						event = splitCanon[0];
						String[] splitCuller = splitCanon[1].split("#",2);
						canons = splitCuller[0].split("@");
						if(splitCuller.length > 1){
							cullers = splitCuller[1].split("#");
						}
					} else {
						String[] splitCuller = event.split("#",2);
						if(splitCuller.length > 1){
							event = splitCuller[0];
							cullers = splitCuller[1].split("#");
						}
					}
					EventDriver eventDriver = experiment.addEventDriver(event.trim());
					if(canons != null) {
						for(String canon : canons) {
							experiment.addCanonicizer(canon.trim(), eventDriver);
						}
					}
					if(cullers != null) {
						for(String culler : cullers) {
							experiment.addEventCuller(culler.trim(), eventDriver);
						}
					}
				}
				AnalysisDriver analysisDriver = experiment.addAnalysisDriver(analysis);
				if (!distance.isEmpty()) {
					experiment.addDistanceFunction(distance, analysisDriver);
				}
				experiment.execute();
				List<Document> resultDocuments;
				if (analysisDriver instanceof ValidationDriver) {
					resultDocuments = experiment.getDocuments();
				} else {
					resultDocuments = experiment.getUnknownDocuments();
				}
				Path filePath = FileSystems.getDefault().getPath(fileName);
				Writer writer = Files.newBufferedWriter(filePath, Charset.defaultCharset(), StandardOpenOption.CREATE);
				for (Document resultDocument : resultDocuments) {
					writer.append(resultDocument.getFormattedResult(analysisDriver));
				}
				writer.close();
			} catch (Exception e) {
				logger.error("Could not run experiment " + fileName, e);
			}
			return fileName;
		}

	}

}
