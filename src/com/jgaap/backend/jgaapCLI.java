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

import java.util.Vector;

import com.jgaap.DivergenceType;
import com.jgaap.jgaap;
import com.jgaap.jgaapConstants;
import com.jgaap.generics.Language;
import com.jgaap.languages.English;

/**
 * Command Line Interface This is version 3 of the command line interface.
 * Version 3 implements a new tag based system to allow for more descriptive
 * names of event sets and analysis methods it also adds flags for enforced
 * commutativity and most common events. For information on how to use the CLI
 * visit http://www.jgaap.com
 * 
 * @author michael ryan
 */
public class jgaapCLI {

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
								+ "\n\n\tjgaap [-c Canonicizer] [-lang Language] [-es EventSet] [-a AnalysisName] "
								+ "\n\t      [-l CorpusCSV] [-mc] [-avg] [-rev] [-max] [-min] [-ad] [-s FileName]"
								+ "\n\n\tTo view a list of valid arguments for any tag simply type jgaap help [tag]"
								+ "\n\t\te.g. jgaap help a or jgaap help canonicizer"
								+ "\n\n\tCanonicizers: [-c CANONICIZERNAME]"
								+ "\n\t\tSmash Case: is used to noramlize case duh "
								+ "\n\t\tNormalize Whitespace is used to normalize whitespace see a pattern "
								+ "\n\t\tStrip HTML: will remove the HTML from a document so analysis can be "
								+ "\n\t\tpreformed on what is written in that web document. "
								+ "\n\t\tStrip Punctuation: will remove all punctuation from a document, "
								+ "\n\t\tit will not replace it with anything."
								+ "\n\t\tThe -c tag must be used before each canonicizor you wish to use and "
								+ "\n\t\tthey are applied in the order they are typed."
								+ "\n\n\tEvent Set: [-es EVENTSETNAME]"
								+ "\n\t\tIn JGAAP you need to select an event set.  "
								+ "\n\t\tIf you want this to only contain the 50 most common use the additional flag -mc "
								+ "\n\t\tEvent Set Names: characters, character bigrams, character trigrams, "
								+ "\n\t\tcharacter tetragrams, word lengths, syllables, Words, word bigrams, "
								+ "\n\t\tword trigrams, word tetragrams."
								+ "\n\n\tAnalysis: [-a ANALYSISNAME]"
								+ "\n\t\tJGAAP currently has six methods "
								+ "\n\t\tLDA,Linear SVM, Gaussian SVM, Histogram Distance, KolmogorovShmirnov distance, and  Cross Entropy "
								+ "\n\t\t they can be called using the the names above on the command line."
								+ "\n\n\tLoad Corpus: [-l CORPUSNAME]"
								+ "\n\t\tThis feature takes the name of a csv file of the authors names and the "
								+ "\n\t\tfiles paths of the associated documents and loads them into JGAAP."
								+ "\n\n\tSave Results: [-s FILENAME]"
								+ "\n\t\tThis will save the results the the specified file."
								+ "\n\n\tLanguage: [-lang LANGUAGE]"
								+ "\n\t\tThis feature will allow the use to add laguages to JGAAP by adding a "
								+ "\n\t\tlanguage class to the com.jgaap.languages package."
								+ "\n\t\tEnter a known language or one you have found/created there."
								+ "\n\n\tFlags: [-mc, -avg, -rev, -max, -min, -ad]"
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
				guiDriver commandDriver = new guiDriver();
				StringBuffer topic = new StringBuffer();
				Vector<String> list;
				topic.append(args[1]);
				for (int i = 2; i < args.length; i++) {
					topic.append(" " + args[i]);
				}
				list = commandDriver.argumentList(topic.toString());
				for (String line : list) {
					System.out.println(line);
				}
			}

		} else {
			jgaap.commandline = true;
			/*
			 * MVR 9/7/2008
			 */
			guiDriver commandDriver = new guiDriver();
			String eventSelected = "";
			String analyzerSelected = "";
			String saveFilePath = "";
			DivergenceType currentDivergenceMethod = DivergenceType.Standard;
			boolean mostCommonEvents = false;
			boolean saveResults = false;
			boolean malformedError = false;
			Vector<Vector<String>> commandInput = new Vector<Vector<String>>();
			Vector<Vector<String>> documentMatrix = new Vector<Vector<String>>();
			Language selectedLanguage = new English();
			Vector<String> canonicizers = new Vector<String>();
			for (int i = 0; i < args.length; i++) {
				Vector<String> nextFlag = new Vector<String>();
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
				while (!currentTagSet.isEmpty()) {
					String currentArg = currentTagSet.remove(0);
					if (currentArg.equalsIgnoreCase("-c")) {
						String canonSelected = currentTagSet.remove(0);
						while (!currentTagSet.isEmpty()) {
							canonSelected = canonSelected + " "
									+ currentTagSet.remove(0);
						}
						canonicizers.add(canonSelected);
						//commandDriver.canonicizerProcessEngine(canonSelected);
					} else if (currentArg.equalsIgnoreCase("-es")) {
						eventSelected = currentTagSet.remove(0);
						while (!currentTagSet.isEmpty()) {
							eventSelected = eventSelected + " "
									+ currentTagSet.remove(0);
						}
					} else if (currentArg.equalsIgnoreCase("-a")) {
						analyzerSelected = currentTagSet.remove(0);
						while (!currentTagSet.isEmpty()) {
							analyzerSelected = analyzerSelected + " "
									+ currentTagSet.remove(0);
						}
					} else if (currentArg.equalsIgnoreCase("-ec")
							|| currentArg.equalsIgnoreCase("-avg")) {
						currentDivergenceMethod = DivergenceType.Average;
					} else if (currentArg.equalsIgnoreCase("-mc")) {
						mostCommonEvents = true;
					} else if (currentArg.equalsIgnoreCase("-max")) {
						currentDivergenceMethod = DivergenceType.Max;
					} else if (currentArg.equalsIgnoreCase("-min")) {
						currentDivergenceMethod = DivergenceType.Min;
					} else if (currentArg.equalsIgnoreCase("-rev")) {
						currentDivergenceMethod = DivergenceType.Reverse;
					} else if (currentArg.equalsIgnoreCase("-ad")) {
						commandDriver.setAuthorDistance(true);
					} else if (currentArg.equalsIgnoreCase("-l")) {
						String csvFilePath = currentTagSet.remove(0);
						while (!currentTagSet.isEmpty()) {
							csvFilePath = csvFilePath + " "
									+ currentTagSet.remove(0);
						}
						documentMatrix = CSVIO.readCSV(csvFilePath);
					} else if (currentArg.equalsIgnoreCase("-s")) {
						saveResults = true;
						saveFilePath = currentTagSet.remove(0);
						while (!currentTagSet.isEmpty()) {
							saveFilePath = saveFilePath + " "
									+ currentTagSet.remove(0);
						}
					} else if (currentArg.equalsIgnoreCase("-ee")) {
						String loadEngineFilePath = currentTagSet.remove(0);
						while (!currentTagSet.isEmpty()) {
							loadEngineFilePath = loadEngineFilePath + " "
									+ currentTagSet.remove(0);
						}
						experimentEngine.runExperiment(loadEngineFilePath);
						System.exit(0);
					} else if (currentArg.equalsIgnoreCase("-lang")) {
						String language = currentTagSet.remove(0);
						while (!currentTagSet.isEmpty()) {
							language = language + " " + currentTagSet.remove(0);
						}
						selectedLanguage = (commandDriver
								.selectedLanguage(language));
						selectedLanguage.apply();
					} else if (currentArg.equalsIgnoreCase("-ws")) {
						String wSize = currentTagSet.remove(0);
						while (!currentTagSet.isEmpty()) {
							wSize = wSize + " " + currentTagSet.remove(0);
						}
						System.out.println(wSize + " "
								+ Integer.parseInt(wSize));
						jgaapConstants.globalParams.setParameter("windowSize",
								Integer.parseInt(wSize));
					} else {
						malformedError = true;
						System.out.println("The following command"
								+ (currentTagSet.size() > 1 ? "s" : "") + " "
								+ (currentTagSet.size() > 1 ? "were" : "was")
								+ " not recognized.");
						System.out.println(currentArg);
						for (int i = 0; i < currentTagSet.size(); i++) {
							System.out.println(currentTagSet.remove(0));
						}
					}

				}
			}
			if (malformedError) {
				System.exit(1);
			}
			while (!documentMatrix.isEmpty()) {
				try {
					commandDriver.addDocument((documentMatrix.elementAt(0)
							.elementAt(1)), documentMatrix.elementAt(0)
							.elementAt(0));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				documentMatrix.remove(0);
			}
			for(String c : canonicizers){
				commandDriver.canonicizerProcessEngine(c);
			}

			try {
				commandDriver.addEventDriver(eventSelected);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//commandDriver.createEventSet();
			// MVR 9/7/2008 - The following code was adapted from John's to tell
			// the user
			// about the results they just received.
			StringBuffer finalResults = new StringBuffer();
			finalResults.append("\nCanonicizers: ");
			finalResults.append(commandDriver.canonicizersUsed() + " ");
			finalResults.append("\nEvent Set: ");
			if (mostCommonEvents) {
				finalResults.append("Most Common ");
			}
			finalResults.append(eventSelected);
			finalResults.append("\nAnalysis Method: ");
			finalResults.append(analyzerSelected);

			switch (currentDivergenceMethod.ordinal()) {
			case 1:
				finalResults.append(" (Average)");
				break;
			case 2:
				finalResults.append(" (Max) ");
				break;
			case 3:
				finalResults.append(" (Min)");
				break;
			case 4:
				finalResults.append(" (Reverse)");
			default:
			}
			jgaapConstants.globalParams.setParameter("divergenceOption", currentDivergenceMethod.ordinal());
			try {
				commandDriver.addAnalysis(analyzerSelected);
				finalResults.append("\n"+ commandDriver.execute());
				String results = finalResults.toString();
				System.out.println(results);
				if (saveResults) {
					commandDriver.saveResults(saveFilePath, results);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			/*
			 * MVR 9/7/2008 The second version of the command line has been
			 * depricated due to changes in how action events are handled they
			 * are no longer all one use svn checkout[114] to view depricated
			 * code
			 */
		}
	}
}
