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

package com.jgaap;

import java.util.*;
import com.jgaap.DivergenceType;
import com.jgaap.backend.*;
import com.jgaap.generics.*;
import com.jgaap.languages.English;

/*
 * It's become usful to be able to automate JGAAP, so this is a 
 * robustfied, public-friendly version of the internal JGAAP API.
 * 
 * Maybe - I'm not sure about the public-friendly part. It's more
 * important that it be automation-friendly. 
 *
 * (This is heavily "inspired" by Mike's jgaapCLI.)
 */

public class JgaapAPI 
{

/* --- tester --- */
	
	public static void main (String argv[]) throws Exception
	{
				
		JgaapAPI api = new JgaapAPI();
		
		String canonicizers[] = {"unify case", "normalize whitespace"};

		api.addDocumentsFromCSV("../docs/aaac/problemA/loadA.csv", canonicizers);

		api.selectEventSet("character bigrams", false);
			
		api.selectAnalysisMethod("camberra distance");

		api.execute();

		for (int i=0; i < api.analysisResults.length; i+=2) {
			System.out.println("Doc {" + api.analysisResults[i] + "} = {" + api.analysisResults[i+1] + "}");
		}
		//perfTotalTime, perfCanonicizeTime, perfEventSetTime, perfAnalysisTime;
		System.out.println("perfCanonicizeTime = " + api.perfCanonicizeTime);
		System.out.println("perfEventSetTime = " + api.perfEventSetTime);
		System.out.println("perfAnalysisTime = " + api.perfAnalysisTime);
		System.out.println("perfTotalTime = " + api.perfTotalTime);
	
	}
	/*
	M    src/com/jgaap/gui/jgaapGUI.java
M    src/com/jgaap/backend/guiDriver.java
M    src/com/jgaap/JgaapAPI.java
M    src/build.xml
*/


/* --- Public parts --- */
	
	public void addDocumentsFromCSV(String path, String canonicizers[]) throws JgaapAPIException
	{
		checkValidity();
		verifyPreExecute();

		Vector<Vector<String>> rows;
		
		// Digest the input CSV.  readCSV() indicates error by returning null.
		rows = CSVIO.readCSV(path);
		if (rows == null) {
			thrower(etypes.BAD_FILE, "readCSV() choked on that path");
		}
		
		for (int i=0; i<rows.size(); i++) {
			Vector<String> doc = rows.elementAt(i);
			
			if (doc.size() < 2) {
				thrower(etypes.BAD_FILE, "Row (index "+i+") has too few columns");
			}
			
			String myPath = doc.elementAt(1);
			String myAuthor = doc.elementAt(0);
			
			addDocument(myPath, myAuthor, canonicizers);
		}
	}
	
	public void addDocument(String path, String author, String canonicizers[]) throws JgaapAPIException
	{
		checkValidity();
		verifyPreExecute();
		
		System.out.println("Adding " + path + " (by " + author + ")");
		
		Canonicizer resolvedCanonicizers[];
		Document doc = null;
		
		// sanity check arguments
		if (path == null) {
			thrower(etypes.BAD_ARGUMENT, "path is null");
		}
		
		// null <-> {} <-> no canonicizers
		if (canonicizers == null) {
			canonicizers = new String[0];
		}
		// null <-> "" <-> unknown author
		if (author == null) {
			author = "";
		}
		
		// lookup all elements in cononicizers[], and populate resolvedCanonicizers[] with 
		// their corresponding Canonicizer-objects
		
		resolvedCanonicizers = new Canonicizer[canonicizers.length];
		for (int i=0; i<canonicizers.length; i++) {
			Canonicizer c;
			
			// resolve the canonicizer
			try {
				c = driver.lookupCanonicizerName(canonicizers[i]);
			} catch (Exception e) {
				c = null;
			}
			
			// c==null -> the canonicizer doesn't exist
			if (c==null) {
				thrower(etypes.BAD_CANONICIZER, "Canonicizer \"" + canonicizers[i] + "\" probably doesn't exist");
			}
			resolvedCanonicizers[i] = c;
		}
		
		// Create a Document
		// FIXME: none of the Document constructors properly throw an exception in the face of an error
	
		try {
			doc = new Document(path, author, path);
		} catch (Exception e) {
			rethrow(etypes.UNKNOWN, e, "new Document("+path+", "+author+") threw an exception.");
		}
		
		// evaluate chosenLanguage
		try {
			if (chosenLanguage.isParseable()) {
				doc.setProcessedText(chosenLanguage.parseLanguage(doc.stringify()));
			}
		} catch (Exception e) {
			rethrow(etypes.UNKNOWN, e, "Jgaap encountered an interal error related to Language");
		}
		
		// Add all the canonicizers to our document (I don't think this can fail)
		for (int i=0; i < resolvedCanonicizers.length; i++) {
			doc.addCanonicizer(resolvedCanonicizers[i]);
		}
			
		// Add our document to the driver
		driver.addDocument(doc);
	}
	
	public void selectEventSet(String eventSet, boolean mostCommon) throws JgaapAPIException
	{
		checkValidity();
		verifyPreExecute();

		Set possibleESets = driver.getAllEventSets();

		if (!possibleESets.contains(eventSet.toLowerCase())) {
			thrower(etypes.BAD_EVENT_SET, eventSet+" is an invalid event set.");
		}

		chosenEventSet = eventSet.toLowerCase();
		chosenEventSet_mostCommon = mostCommon;
	}
	
	public void selectAnalysisMethod(String analysisMethod) throws JgaapAPIException
	{
		checkValidity();
		verifyPreExecute();
		
		if (!(anaMethods.containsKey(analysisMethod.toLowerCase())
			|| distMethods.containsKey(analysisMethod.toLowerCase()))) {
			thrower(etypes.BAD_ANALYSIS_METHOD, analysisMethod+" is an invalid analysis method.");
		}

		chosenAnalysisMethod = analysisMethod.toLowerCase();
	}
	
	//perfTotalTime, perfCanonicizeTime, perfEventSetTime, perfAnalysisTime;
	public void execute() throws JgaapAPIException
	{
		checkValidity();
		verifyPreExecute();
	
		// Remember the current state of the driver's error members 
		boolean processError = driver.getProcessError();
		String processErrorMessage = driver.getProcessErrorMessage();

		// Frustratingly, Java doesn't offer any simple way to measure cpu time.
		// So we'll settle for wall time (for now). 
		long startTime = System.currentTimeMillis(); // Performance timing starts now!
		
		// Process canonicizers
		try {
			driver.canonicize();
		} catch (Exception e) {
			rethrow(etypes.UNKNOWN, e, "An exception was thrown within JGAAP while in driver.canonicize()");
		}
		
		perfCanonicizeTime = System.currentTimeMillis();
		
		// Apply the event set
		try {
			driver.createEventSet(chosenEventSet, chosenEventSet_mostCommon);
		} catch (Exception e) {
			rethrow(etypes.UNKNOWN, e, "An exception was thrown within JGAAP while in createEventSet("+chosenEventSet+", "+chosenEventSet_mostCommon+")");
		}
		
		perfEventSetTime = System.currentTimeMillis();
		
		if ((processError != driver.getProcessError()) ||
			(processErrorMessage != driver.getProcessErrorMessage()))
		{
			thrower(etypes.UNKNOWN, "An internal error occured within JGAAP while in createEventSet("+chosenEventSet+", "+chosenEventSet_mostCommon+")");
		}

		// Set divergence method
		try {
			jgaapConstants.globalParams.setParameter("divergenceOption", chosenDivergenceMethod.ordinal());
		} catch (Exception e) {
			rethrow(etypes.UNKNOWN, e, "Weird internal error (setParameter(divergenceOption) failed (???))");
		}
		
		long tmpTime = System.currentTimeMillis();
		
		// Apply the analysis method
		try {
			analysisResults = driver.runStatisticalAnalysisRaw(chosenAnalysisMethod);
		} catch (Exception e) {
			rethrow(etypes.UNKNOWN, e, "An internal error ccured within JGAAP while performing statistical analysis ("+chosenAnalysisMethod+")");
		}
		
		long endTime = System.currentTimeMillis();
		
		// calculate execution times
		// (this looks a little convoluted, but it simply measures the length of time (according to currentTimeMillis())
		//	for running each particular stage of execution.)
		perfEventSetTime -= perfCanonicizeTime;
		perfCanonicizeTime -= startTime;
		perfAnalysisTime = endTime - tmpTime;
		perfTotalTime = endTime - startTime;
		
		didExecute = true;
	}
	
	
	/* Constructor */
	
	public JgaapAPI()
	{
		driver = new guiDriver();
		anaMethods = AutoPopulate.getAnalysisDrivers();
		distMethods = AutoPopulate.getDistanceFunctions();
	}
	
	
	/* Exceptions */
	
	// JgaapAPI only throws JgaapAPIExceptions - which may simply be internal exceptions recast as JgaapAPIExceptions.
	// exception_types define a few general areas to which this exception may be referring.
	//
	// In all cases, if a JgaapAPIException is thrown, then the JgaapAPI object is invalid and needs to be reinstantiated from scratch.
	public enum exception_types {
		BAD_ARGUMENT,       // invalid arguments to a JgaapAPI method
		BAD_FILE,            // a non-existent/unreadable file was specified
		BAD_CANONICIZER,     // an invalid canonicizer was used
		BAD_EVENT_SET,       // an invalid event set was used
		BAD_ANALYSIS_METHOD, // an invalid analysis method was used
		OBJECT_INVALID,	     // this instance is invalid
		ALREADY_EXECUTED,    // this instance has already execute()ed
		UNKNOWN          // There was an internal error (or "bug" (or "undocument feature"))
	};
	public exception_types etypes;
	public class JgaapAPIException extends Exception {
		public exception_types type;
		public JgaapAPIException (exception_types type, String m) {
			super(m);
			this.type = type;
		}
		public JgaapAPIException (exception_types type, String m, Exception cause) {
			super(m, cause);
			this.type = type;
		}

	}
	
	
/* --- Internal parts --- */
	
	
	// internal backend to JgaapAPI
	private guiDriver driver;
	
	// private copies of all valid analysis/distance methods
	private Map<String, AnalysisDriver> anaMethods;
	private Map<String, DistanceFunction> distMethods;
	
	// If we ever throw an exception, then this object is invalid. The user needs to instantiate a new JgaapAPI object.
	private boolean isInvalid = false;

	// has execute() completed successfully? If so, then only the results-methods may be used. Otherwise, only the setup-methods may.
	private boolean didExecute = false;
	
	// which event set have we chosen? And is mostCommon set?
	private String chosenEventSet = null; // chosenEventSet==null <-> there is no event set selected yet
	private boolean chosenEventSet_mostCommon = false;

	// which analysis method have we chosen?
	private String chosenAnalysisMethod = null; // chosenAnalysisMethod==null <-> there is no analysis method selected yet
	
	// which divergence method?
	private DivergenceType chosenDivergenceMethod = DivergenceType.Standard; // FIXME: this can't be user-selected yet
	
	// which language?
	private Language chosenLanguage = new English(); // FIXME: this can't be user-selected yet
	
	// Analysis results!
	public String analysisResults[];
	
	// Performance results (in milliseconds):
	public long perfTotalTime, perfCanonicizeTime, perfEventSetTime, perfAnalysisTime;
	
	// Set this.isInvalid, and throw a JgaapAPIException.
	private void thrower (exception_types type, String message) throws JgaapAPIException
	{
		isInvalid = true; // something bad happened -> this instance of JgaapAPI is no longer usable.
				  // this is for consistency's sake. 
		JgaapAPIException e = new JgaapAPIException(type, message);
		throw e;
	}

	// Set this.isInvalid, and throw a JgaapAPIException-wrapper for another exception
	private void rethrow (exception_types type, Exception cause, String message) throws JgaapAPIException
	{
		isInvalid = true; // something bad happened -> this instance of JgaapAPI is no longer usable.
		                  // this is for consistency's sake. 
		JgaapAPIException e = new JgaapAPIException(type, message, cause);
		throw e;
	}

	// This method simply checks whether this.isInvalid is true, and throws an OBJECT_INVALID exception.
	private void checkValidity() throws JgaapAPIException
	{
		if (isInvalid) {
			thrower(etypes.OBJECT_INVALID, "This instance is invalid (because it previously threw an exception).");
		}
	}
	
	// This method verifies that didExecute is false, and throws ALREADY_EXECUTED otherwise.
	private void verifyPreExecute() throws JgaapAPIException
	{
		if (didExecute) {
			thrower(etypes.ALREADY_EXECUTED, "This instance already had execute() called. Only its results may be accessed.");
		}
	}





}
