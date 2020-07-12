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

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.jgaap.generics.Experiment;
import com.jgaap.util.ConfusionMatrix;
import org.apache.commons.cli.*;

import com.jgaap.JGAAP;
import com.jgaap.JGAAPConstants;
import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.Displayable;
import com.jgaap.util.Document;

/**
 * Command Line Interface This is version 4.0 of the command line interface.
 * Version 4.0 continues tag based system to allow for more descriptive names of
 * event sets and analysis methods. The difference is that 4.0 uses the apache
 * commons cli package to process input. This has been done in an attempt to
 * make updating the cli easier going forward. 4.0 keeps the ability to specify
 * parameters for part of jgaap that takes them (EventSet, EventCuller,
 * Analysis, Distance) simply place a bar (|) after the method and then give it
 * string pair separated by a colon (:) For information on how to use the CLI
 * visit http://www.jgaap.com
 * 
 * @author Michael Ryan
 */
@SuppressWarnings("static-access")
public class CLI {

	static Options options = new Options();
	static Option help = Option.builder("h")
						.argName("command")
						.optionalArg(true)
						.desc("print this message.")
						.longOpt("help").build();
	static Option version = Option.builder("v")
						.desc("print version information")
						.longOpt("version").build();
	static Option canonicizers = Option.builder("c")
						.argName("canonicizer")
						.hasArgs()
						.desc("A list of the canonicizers to use")
						.longOpt("canonicizers").build();
	static Option eventDriver = Option.builder("es")
						.argName("event-driver")
						.hasArg()
						.desc("The method of dividing the document into quantifyable features")
						.longOpt("eventset").build();
	static Option eventCullers = Option.builder("ec")
						.argName("event-culler")
						.hasArgs()
						.desc("A list of the EventCullers to use")
						.longOpt("eventcullers").build();
	static Option analysis = Option.builder("a")
						.argName("analysis")
						.hasArg()
						.desc("Method of Statistical analysis of the document")
						.longOpt("analysis").build();
	static Option distanceFunction = Option.builder("d")
						.argName("distancefunction")
						.hasArg()
						.desc("A method of quantifying distance between documents, required for some analysis methods")
						.longOpt("distance").build();
	static Option language = Option.builder("lang")
						.argName("language")
						.hasArg()
						.desc("The language the working documents are in, also set the charset files will be read in")
						.longOpt("language").build();
	static Option load = Option.builder("l")
						.argName("file")
						.hasArg()
						.desc("A csv file of the documents to operate on. The file use the columns Author,FilePath,Title")
						.longOpt("load").build();
	static Option save = Option.builder("s")
						.argName("file")
						.hasArg()
						.desc("Write JGAAP's output to a specified file")
						.longOpt("save").build();
	static Option experimentEngine = Option
						.builder("ee")
						.argName("file")
						.hasArg()
						.desc("Batch processing, pass in a csv file of experiments")
						.longOpt("experimentengine").build();
	static Option experiments = Option
						.builder("e")
						.longOpt("experiments")
						.hasArg()
						.argName("file")
						.desc("Batch processing, pass in a jsonl file of experiments")
						.build();

	static {
		options.addOption(help);
		options.addOption(version);
		options.addOption(canonicizers);
		options.addOption(eventDriver);
		options.addOption(eventCullers);
		options.addOption(analysis);
		options.addOption(distanceFunction);
		options.addOption(language);
		options.addOption(load);
		options.addOption(save);
		options.addOption(experimentEngine);
		options.addOption(experiments);
	}

	/**
	 * Parses the arguments passed to jgaap from the command line. Will either
	 * display the help or run jgaap on an experiment.
	 * 
	 * @param args
	 *            command line arguments
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);
		if (cmd.hasOption('h')) {
			String command = cmd.getOptionValue('h');
			if (command == null) {
				HelpFormatter helpFormatter = new HelpFormatter();
				helpFormatter.setLeftPadding(5);
				helpFormatter.setWidth(100);
				helpFormatter.printHelp(
								"jgaap -c [canon canon ...] -es [event] -ec [culler culler ...] -a [analysis] <-d [distance]> -l [file] <-s [file]>",
								"Welcome to JGAAP the Java Graphical Authorship Attribution Program.\nMore information can be found at http://jgaap.com",
								options, "Copyright 2013 Evaluating Variation in Language Lab, Duquesne University");
			} else {
				List<Displayable> list = new ArrayList<Displayable>();
				if (command.equalsIgnoreCase("c")) {
					list.addAll(Canonicizers.getCanonicizers());
				} else if (command.equalsIgnoreCase("es")) {
					list.addAll(EventDrivers.getEventDrivers());
				} else if (command.equalsIgnoreCase("ec")) {
					list.addAll(EventCullers.getEventCullers());
				} else if (command.equalsIgnoreCase("a")) {
					list.addAll(AnalysisDrivers.getAnalysisDrivers());
				} else if (command.equalsIgnoreCase("d")) {
					list.addAll(DistanceFunctions.getDistanceFunctions());
				} else if (command.equalsIgnoreCase("lang")) {
					list.addAll(Languages.getLanguages());
				}
				for (Displayable display : list) {
					if (display.showInGUI())
						System.out.println(display.displayName() + " - "+ display.tooltipText());
				}
				if (list.isEmpty()) {
					System.out.println("Option " + command + " was not found.");
					System.out.println("Please use c, es, d, a, or lang");
				}
			}
		} else if (cmd.hasOption('v')) {
			System.out.println("Java Graphical Authorship Attribution Program version "+JGAAPConstants.VERSION);
		} else if (cmd.hasOption("ee")) {
			String eeFile = cmd.getOptionValue("ee");
			String lang = cmd.getOptionValue("lang");
			ExperimentEngine.runExperiment(eeFile, lang);
			System.exit(0);
		} else if (cmd.hasOption("e")) {
			String experimentsPath = cmd.getOptionValue("e");
			String documentsFilePath = cmd.getOptionValue('l');
			if (documentsFilePath == null) {
				throw new Exception("No Documents CSV specified");
			}
			List<Document> documents = Utils.getDocuments(documentsFilePath);
			List<Document> knownDocuments = new ArrayList<>();
			List<Document> unknownDocuments = new ArrayList<>();
			for (Document document : documents) {
				if (document.isAuthorKnown()) {
					knownDocuments.add(document);
				} else {
					unknownDocuments.add(document);
				}
			}
			System.out.println(String.format(
					"Training on %d known documents to identify %d unknowns documents.", knownDocuments.size(), unknownDocuments.size()));
			Scanner scanner = new Scanner(new File(experimentsPath));
			while (scanner.hasNextLine()) {
				String experimentJSON = scanner.nextLine();
				Experiment experiment = ExperimentJSON.fromJSON(experimentJSON).instanceExperiment();
				ConfusionMatrix confusionMatrix = experiment.train(knownDocuments);
				System.out.println(experimentJSON);
				System.out.println("PERFORMANCE");
				String resultFormat = "%s \tF1 Score %f \tPrecision %f \tRecall %f \tSupport %d";
				System.out.println(
					String.format(
							resultFormat, "Summary", confusionMatrix.getAverageF1Score(),
							confusionMatrix.getAveragePrecision(), confusionMatrix.getAverageRecall(),
							knownDocuments.size()));
				for (String label : confusionMatrix.getLabels()) {
					System.out.println(
						String.format(
								resultFormat, label, confusionMatrix.getF1Score(label),
								confusionMatrix.getPrecision(label), confusionMatrix.getRecall(label),
								confusionMatrix.getActualTotal(label)));
				}
				if (!unknownDocuments.isEmpty()) {
					System.out.println("RESULTS");
					String predictionFormat = "Title: %s \tPrediction: %s";
					for (Document unknown : unknownDocuments){
						String prediction = experiment.getModel().apply(unknown);
						System.out.println(String.format(predictionFormat, unknown.getTitle(), prediction));
					}
				}
			}

		} else {
			JGAAP.commandline = true;
			API api = API.getPrivateInstance();
			String documentsFilePath = cmd.getOptionValue('l');
			if (documentsFilePath == null) {
				throw new Exception("No Documents CSV specified");
			}
			List<Document> documents = Utils.getDocuments(documentsFilePath);
			for (Document document : documents) {
				api.addDocument(document);
			}
			String language = cmd.getOptionValue("lang", "english");
			api.setLanguage(language);
			String[] canonicizers = cmd.getOptionValues('c');
			if (canonicizers != null) {
				for (String canonicizer : canonicizers) {
					api.addCanonicizer(canonicizer);
				}
			}
			String[] events = cmd.getOptionValues("es");
			if (events == null) {
				throw new Exception("No EventDriver specified");
			}
			for(String event : events){
				api.addEventDriver(event);
			}
			String[] eventCullers = cmd.getOptionValues("ec");
			if (eventCullers != null) {
				for (String eventCuller : eventCullers) {
					api.addEventCuller(eventCuller);
				}
			}
			String analysis = cmd.getOptionValue('a');
			if (analysis == null) {
				throw new Exception("No AnalysisDriver specified");
			}
			AnalysisDriver analysisDriver = api.addAnalysisDriver(analysis);
			String distanceFunction = cmd.getOptionValue('d');
			if (distanceFunction != null) {
				api.addDistanceFunction(distanceFunction, analysisDriver);
			}
			api.execute();
			List<Document> unknowns = api.getUnknownDocuments();
			OutputStreamWriter outputStreamWriter;
			String saveFile = cmd.getOptionValue('s');
			if (saveFile == null) {
				outputStreamWriter = new OutputStreamWriter(System.out);
			} else {
				outputStreamWriter = new OutputStreamWriter(new FileOutputStream(saveFile));
			}
			Writer writer = new BufferedWriter(outputStreamWriter);
			for (Document unknown : unknowns) {
				writer.append(unknown.getFormattedResult(analysisDriver));
			}
			writer.append('\n');
		}
	}
}
