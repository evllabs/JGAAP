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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jgaap.generics.Displayable;
import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;

/**
 * Generic methods that will be reused throughout JGAAP
 * 
 * Any method added here is required to be static 
 * When using Utils simply reference the method you want do not instance it.
 * @author Michael Ryan
 *
 */
public class Utils {

	public static boolean saveFile(String filePath, String data){
		return writeToFile(filePath, data, false);
	}
	
	private static boolean writeToFile(String filePath, String data, boolean append){
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(filePath, append));
			writer.write(data);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean appendToFile(String filePath, String data){
		return writeToFile(filePath, data, true);
	}
	
	public static void saveDocumentsToCSV(List<Document> documents, File file){
		List<List<String>> csvOfDocuments = new ArrayList<List<String>>();
		for(Document document : documents){
			List<String> current = new ArrayList<String>();
			current.add(document.getAuthor());
			current.add(document.getFilePath());
			current.add(document.getTitle());
			csvOfDocuments.add(current);
		}
		CSVIO.writeCSV(csvOfDocuments, file);
	}
	
	public static List<Document> getDocumentsFromCSV(List<List<String>> documentCSV) throws Exception{
		List<Document> documents = new ArrayList<Document>();
		for(List<String> documentRow : documentCSV){
			Document document = new Document(documentRow.get(1),documentRow.get(0),(documentRow.size()>2?documentRow.get(2):null));
			documents.add(document);
		}
		return documents;
	}
	/**
	 * This method edits the list of Displayable objects you pass it so that it only contains ones with the showInGUI flag set to true
	 * @param displayElements  List of Displayables to be edited 
	 * @return Only showInGUI true Displayables
	 */
	public static List<Displayable> sanatizeShowInGUI(List<Displayable> displayElements){
		Iterator<Displayable> iterator = displayElements.iterator();
		while(iterator.hasNext()){
			if(!iterator.next().showInGUI()){
				iterator.remove();
			}
		}
		return displayElements;
	}
	
	public static List<String[]> getParameters(String input){
		List<String[]> parameters = new ArrayList<String[]>();
		String[] sets = input.split("\\|");
		for(int i = 0; i<sets.length;i++){
			parameters.add(sets[i].split(":"));
		}
		return parameters;
	}
	
	/** Calculate sample deviation */
	public static double stddev(List<Double> observations) {
		
		// Catch single observation case
		if(observations.size() == 1 ) {
			return 0.0;
		}
		
		double mean = 0.0;
		
		// Calculate the mean of the observations
		for(Double d : observations) {
			mean += d;
		}
		mean = mean / observations.size();
		
		double stddev = 0.0;
		for(Double d : observations) {
			stddev += (d - mean) * (d-mean);
		}
		
		stddev = stddev / (observations.size() - 1); // Get the sample standard deviation
		return Math.sqrt(stddev);
	}
	
	/**
	 * 
	 */
	public static EventHistogram makeHistogram(EventSet eventSet){
		EventHistogram histogram = new EventHistogram();
		for(Event evet : eventSet){
			histogram.add(evet);
		}
		return histogram;
	}
	/**
	 * 
	 */
	public static Map<Event, Double> makeCentroid(List<EventHistogram> histograms){
		Map<Event, Double> centroid = new HashMap<Event, Double>();
		double size = histograms.size();
		for(EventHistogram histogram : histograms){
			for(Event event : histogram){
				Double value = centroid.get(event);
				if(value != null){
					centroid.put(event, (value+histogram.getRelativeFrequency(event))/size);
				} else {
					centroid.put(event, histogram.getRelativeFrequency(event)/size);
				}
			}
		}
		return centroid;
	}
}
