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

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.jgaap.jgaap;
import com.jgaap.jgaapConstants;
import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.Displayable;
import com.jgaap.generics.DivergenceType;
import com.jgaap.generics.NeighborAnalysisDriver;

/**
 * Command Line Interface This is version 3 of the command line interface.
 * Version 3 implements a new tag based system to allow for more descriptive
 * names of event sets and analysis methods it also adds flags for enforced
 * commutativity and most common events. For information on how to use the CLI
 * visit http://www.jgaap.com
 * 
 * @author michael ryan
 */
public class CLI {

	/**
	 * Parses the arguments passed to jgaap from the command line. Will either
	 * display the help or run jgaap on an experiment.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		if (args[0].equals("help")) {
			if (args.length == 1) {
				System.out
						.println("\nWelcome to JGAAP"
								+ "(Java Graphical Aurthorship Attribution Program) "
								+ "\nCommandLine Interface: "
								+ "\n\n\tjgaap [-c Canonicizer] [-lang Language] [-es EventSet] [-ec EventCuller] [-a AnalysisName] "
								+ "\n\t      [-l CorpusCSV] [-mc] [-avg] [-rev] [-max] [-min] [-ad] [-s FileName]"
								+ "\n\n\tTo view a list of valid arguments for any tag simply type jgaap help [tag]"
								+ "\n\t\te.g. jgaap help a or jgaap help canonicizer"
								+ "\n\n\tLoad Corpus: [-l CORPUSNAME]"
								+ "\n\t\tThis feature takes the name of a csv file of the authors names and the "
								+ "\n\t\tfiles paths of the associated documents and loads them into JGAAP."
								+ "\n\n\tSave Results: [-s FILENAME]"
								+ "\n\t\tThis will save the results the the specified file."
								+ "\n\n\tLanguage: [-lang LANGUAGE]"
								+ "\n\t\tThis feature will allow the use to add laguages to JGAAP by adding a "
								+ "\n\t\tlanguage class to the com.jgaap.languages package."
								+ "\n\t\tEnter a known language or one you have found/created there."
								+ "\n\n\tFlags: [-avg, -rev, -max, -min]"
								+ "\n\t\tMost Common [-mc]: only uses the 50 most common words accorss the documents."
								+ "\n\t\tAverage Divergence [-avg]: the divergence in both directions averaged together."
								+ "\n\t\tReverse Divergence [-rev]: the divergence is taken from the unknown document to the known."
								+ "\n\t\tMax Divergence [-max]: the divergence is taken in both directions and the max is returned."
								+ "\n\t\tMin Divergence [-min]: the divergence is taken in both directions and the min is returned."
								+ "\n\t\tAuthor Distance [-ad]: uses the average distance of an author's work against an unknown."
								+ "\n\n\tA fully qualified JGAAP command will look like the following:"
								+ "\n\t\tjava -jar jgaap.jar -c Smash Case -es Words -a cross entropy -l ../docs/aaac/Demos/loadA.csv\n"
								+ "\nMore information can be found at www.jgaap.com");
			} else {
				StringBuffer topic = new StringBuffer();
				List<Displayable> list = new ArrayList<Displayable>();
				topic.append(args[1]);
				for (int i = 2; i < args.length; i++) {
					topic.append(" " + args[i]);
				}
				String current = topic.toString();
				if (current.equalsIgnoreCase("c")) {
					list.addAll(AutoPopulate.getCanonicizers());
				} else if (current.equalsIgnoreCase("es")) {
					list.addAll(AutoPopulate.getEventDrivers());
				} else if (current.equalsIgnoreCase("ec")){
					list.addAll(AutoPopulate.getEventCullers());
				}else if (current.equalsIgnoreCase("a")) {
					list.addAll(AutoPopulate.getAnalysisDrivers());
				} else if (current.equalsIgnoreCase("d")) {
					list.addAll(AutoPopulate.getDistanceFunctions());
				} else if (current.equalsIgnoreCase("lang")){
					list.addAll(AutoPopulate.getLanguages());
				}
				for(Displayable display : list){
					if(display.showInGUI())
						System.out.println(display.displayName()+" - "+display.tooltipText());
				}
				if(list.isEmpty()){
					System.out.println("Option "+current+" was not found.");
					System.out.println("Please use c, es, d, a, or lang");
				}
			}

		} else {
			jgaap.commandline = true;
			API commandDriver = new API();
			String eventSelected = "";
			List<String> eventCullersSelected = new ArrayList<String>();
			String analyzerSelected = "";
			String distanceSelected = "";
			String saveFilePath = "";
			String language = "english";
			DivergenceType currentDivergenceMethod = DivergenceType.Standard;
			boolean saveResults = false;
			Vector<Vector<String>> commandInput = new Vector<Vector<String>>();
			List<List<String>> documentMatrix = new Vector<List<String>>();
			Vector<String> canonicizers = new Vector<String>();
			for (int i = 0; i < args.length; i++) {
				Vector<String> nextFlag;
				if (args[i] == null) {
					break;
				}
				if ((args[i].charAt(0) == '-') || (args[i].charAt(0) == '+')) {
					nextFlag = new Vector<String>();
					nextFlag.add(args[i]);
					commandInput.add(nextFlag);
				} else {
					commandInput.lastElement().add(args[i]);
				}
			}
			System.out.println(commandInput);
			while (!commandInput.isEmpty()) {
				Vector<String> currentTagSet = commandInput.remove(0);
				String currentArg = currentTagSet.remove(0);
				if (currentArg.equalsIgnoreCase("-c")) {
					String canonSelected = optionBuilder(currentTagSet);
					canonicizers.add(canonSelected);
				} else if (currentArg.equalsIgnoreCase("-es")) {
					eventSelected = optionBuilder(currentTagSet);
				} else if (currentArg.equalsIgnoreCase("-ec")) {
					eventCullersSelected.add(optionBuilder(currentTagSet));
				} else if (currentArg.equalsIgnoreCase("-a")) {
					analyzerSelected = optionBuilder(currentTagSet);
				} else if (currentArg.equalsIgnoreCase("-d")) {
					distanceSelected = optionBuilder(currentTagSet);
				} else if (currentArg.equalsIgnoreCase("-avg")) {
					currentDivergenceMethod = DivergenceType.Average;
				} else if (currentArg.equalsIgnoreCase("-max")) {
					currentDivergenceMethod = DivergenceType.Max;
				} else if (currentArg.equalsIgnoreCase("-min")) {
					currentDivergenceMethod = DivergenceType.Min;
				} else if (currentArg.equalsIgnoreCase("-rev")) {
					currentDivergenceMethod = DivergenceType.Reverse;
				} else if (currentArg.equalsIgnoreCase("-cross")) {
					currentDivergenceMethod = DivergenceType.Cross;
				} else if (currentArg.equalsIgnoreCase("-l")) {
					String csvFilePath = optionBuilder(currentTagSet);
					documentMatrix = CSVIO.readCSV(csvFilePath);
				} else if (currentArg.equalsIgnoreCase("-s")) {
					saveResults = true;
					saveFilePath = optionBuilder(currentTagSet);
				} else if (currentArg.equalsIgnoreCase("-ee")) {
					String loadEngineFilePath = optionBuilder(currentTagSet);
					ExperimentEngine.runExperiment(loadEngineFilePath);
					System.exit(0);
				} else if (currentArg.equalsIgnoreCase("-lang")) {
					language = optionBuilder(currentTagSet);
				} else if (currentArg.equalsIgnoreCase("-ws")) {
					String wSize = optionBuilder(currentTagSet);
					System.out.println(wSize + " " + Integer.parseInt(wSize));
					jgaapConstants.globalParams.setParameter("windowSize",
							Integer.parseInt(wSize));
				} else {
					System.out.println("The following command"
							+ (currentTagSet.size() > 1 ? "s" : "") + " "
							+ (currentTagSet.size() > 1 ? "were" : "was")
							+ " not recognized.");
					System.out.println(currentArg);
					for (int i = 0; i < currentTagSet.size(); i++) {
						System.out.println(currentTagSet.remove(0));
					}
					System.exit(1);
				}
			}
			try {
				commandDriver.setLanguage(language);
				while (!documentMatrix.isEmpty()) {
					commandDriver.addDocument(
							(documentMatrix.get(0).get(1)),
							documentMatrix.get(0).get(0), null);
					documentMatrix.remove(0);
				}
				for (String c : canonicizers) {
					commandDriver.addCanonicizer(c);
				}
				commandDriver.addEventDriver(eventSelected);
				for (String eventCuller : eventCullersSelected){
					commandDriver.addEventCuller(eventCuller);
				}
				AnalysisDriver analysisDriver = commandDriver.addAnalysisDriver(analyzerSelected);
				if (!distanceSelected.equalsIgnoreCase("")){
					commandDriver.addDistanceFunction(distanceSelected, analysisDriver);
				}
				
				if (analysisDriver instanceof NeighborAnalysisDriver) {
					((NeighborAnalysisDriver) analysisDriver)
							.getDistanceFunction().setParameter(
									"divergenceOption",
									currentDivergenceMethod.ordinal());
				}
				StringBuffer finalResults = new StringBuffer();
				jgaapConstants.globalParams.setParameter("divergenceOption",
						currentDivergenceMethod.ordinal());
				commandDriver.execute();
				String results = finalResults.toString();
				System.out.println(results);
				if(saveResults){
					Utils.saveFile(saveFilePath, results);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	private static String optionBuilder(List<String> tagSet) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(tagSet.remove(0));
		while (!tagSet.isEmpty()) {
			String s = tagSet.remove(0);
			buffer.append(" " + s);
		}
		return buffer.toString();
	}
}
