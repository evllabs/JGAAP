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

import java.util.*;

import org.apache.commons.cli.*;

import com.jgaap.*;
import com.jgaap.generics.*;

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
	static Option help = OptionBuilder
						.withArgName("command")
						.hasOptionalArg()
						.withDescription("print this message.")
						.withLongOpt("help").create('h');
	static Option version = OptionBuilder
						.withDescription("print version information")
						.withLongOpt("version").create('v');
	static Option canonicizers = OptionBuilder
						.withArgName("canonicizer")
						.hasArgs()
						.withDescription("A list of the canonicizers to use")
						.withLongOpt("canonicizers").create('c');
	static Option eventDriver = OptionBuilder
						.withArgName("event-driver")
						.hasArg()
						.withDescription("The method of dividing the document into quantifyable features")
						.withLongOpt("eventset").create("es");
	static Option eventCullers = OptionBuilder
						.withArgName("event-culler")
						.hasArgs()
						.withDescription("A list of the EventCullers to use")
						.withLongOpt("eventcullers").create("ec");
	static Option analysis = OptionBuilder
						.withArgName("analysis")
						.hasArg()
						.withDescription("Method of Statistical analysis of the document")
						.withLongOpt("analysis").create('a');
	static Option distanceFunction = OptionBuilder
						.withArgName("distancefunction")
						.hasArg()
						.withDescription("A method of wuantifying distance between documents, required for some analysis methods")
						.withLongOpt("distance").create('d');
	static Option language = OptionBuilder.withArgName("language")
						.hasArg()
						.withDescription("The language the working documents are in, also set the charset files will be read in")
						.withLongOpt("language").create("lang");
	static Option load = OptionBuilder
						.withArgName("file")
						.hasArg()
						.withDescription("A csv file of the documents to operate on. The file use the columns Author,FilePath,Title")
						.withLongOpt("load").create('l');
	static Option save = OptionBuilder
						.withArgName("file")
						.hasArg()
						.withDescription("Write JGAAP's output to a specified file")
						.withLongOpt("save").create('s');
	static Option experimentEngine = OptionBuilder
						.withArgName("file")
						.hasArg()
						.withDescription("Batch processing, pass in a csv file of experiments")
						.withLongOpt("experimentengine").create("ee");

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
		CommandLineParser parser = new GnuParser();
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
								options, "Copyright 2011 Evaluating Variation in Language Lab, Duquesne University");
			} else {
				List<Displayable> list = new ArrayList<Displayable>();
				if (command.equalsIgnoreCase("c")) {
					list.addAll(AutoPopulate.getCanonicizers());
				} else if (command.equalsIgnoreCase("es")) {
					list.addAll(AutoPopulate.getEventDrivers());
				} else if (command.equalsIgnoreCase("ec")) {
					list.addAll(AutoPopulate.getEventCullers());
				} else if (command.equalsIgnoreCase("a")) {
					list.addAll(AutoPopulate.getAnalysisDrivers());
				} else if (command.equalsIgnoreCase("d")) {
					list.addAll(AutoPopulate.getDistanceFunctions());
				} else if (command.equalsIgnoreCase("lang")) {
					list.addAll(AutoPopulate.getLanguages());
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
			System.out.println("Java Graphical Authorship Attribution Program version 5.2.0");
		} else if (cmd.hasOption("ee")) {
			String eeFile = cmd.getOptionValue("ee");
			ExperimentEngine.runExperiment(eeFile);
			System.exit(0);
		} else {
			JGAAP.commandline = true;
			API api = API.getPrivateInstance();
			String documentsFilePath = cmd.getOptionValue('l');
			if (documentsFilePath == null) {
				throw new Exception("No Documents CSV specified");
			}
			List<Document> documents = Utils.getDocumentsFromCSV(CSVIO.readCSV(documentsFilePath));
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
			String event = cmd.getOptionValue("es");
			if (event == null) {
				throw new Exception("No EventDriver specified");
			}
			EventDriver eventDriver = api.addEventDriver(event);
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
			StringBuffer buffer = new StringBuffer();
			for (Document unknown : unknowns) {
				buffer.append(unknown.getFormattedResult(analysisDriver, eventDriver));
			}
			String saveFile = cmd.getOptionValue('s');
			if (saveFile == null) {
				System.out.println(buffer.toString());
			} else {
				Utils.saveFile(saveFile, buffer.toString());
			}
		}
	}
}
