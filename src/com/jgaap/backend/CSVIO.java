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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/*
 * MVR 8/1/2008 This class will handle all IO for CSV files. readCSV- Input:
 * file name reads the CSV and return a Vector of Vectors of Strings writeCSV-
 * Input: Vector of Vectors of Strings, file name writes the data to a csv file
 * altWriteCSV- Input: Vector of Vectors of Strings, file name same as writeCSV
 * except is destructive
 */
/**
 * 
 * This class is the prettiest in all of jgaap. This class handles the parsing
 * and creation of CSV files.
 * 
 * @author Michael Ryan
 */
public class CSVIO {

	private static Logger logger = Logger.getLogger(CSVIO.class);
	
	/**
	 * This method prints the the current matrix to the terminal in a similar
	 * way to how it will appear in the csv. This is for testing purposes.
	 * 
	 * @param csvMatrix
	 *            contains the information you want in each cell and where you
	 *            want the cells
	 */
	public static void printCSV(List<List<String>> csvMatrix) {
		for (List<String> line : csvMatrix) {
			System.out.println(line);
		}
	}

	/**
	 * parses a csv document and places it in a representation of columns and
	 * rows using vectors
	 * 
	 * @param is
	 *            inputstream to the csv file
	 * @return a vector of vectors of strings this gives a representation of
	 *         information in string within the vectors
	 * @throws IOException 
	 */
	public static List<List<String>> readCSV(InputStream is) throws IOException {
		List<List<String>> rows = new ArrayList<List<String>>();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String rowText = "";
		while ((rowText = br.readLine()) != null) {
			List<String> column = new ArrayList<String>();
			StringBuilder buffer = new StringBuilder();
			int state = 1;
			// loop through the row of test using a finite state machine to
			// parse the input
			for (char c : rowText.toCharArray()) {
				switch (state) {
				case 1:
					if (c == ',') {
						column.add(buffer.toString());
						buffer = new StringBuilder();
					} else if (c == '"') {
						state = 2;
					} else {
						buffer.append(c);
					}
					break;
				case 2:
					if (c == '"') {
						state = 3;
					} else {
						buffer.append(c);
					}
					break;
				case 3:
					if (c == ',') {
						column.add(buffer.toString());
						buffer = new StringBuilder();
						state = 1;
					} else if (c == '"') {
						buffer.append('"');
						state = 2;
					} else {
						logger.error("Failed to read CSV file "+is.toString());
					}
					break;
				}
			}
			column.add(buffer.toString());
			if (column.size() > 0) {
				rows.add(column);
			}
		}
		br.close();
		return rows;
	}

	public static List<List<String>> readCSV(String fileName) throws IOException , FileNotFoundException {
			return readCSV(new FileInputStream(fileName));
	}

	/**
	 * writes to the csv without releasing the Vector to memory
	 * 
	 * @param csvMatrix
	 *            contains the information you want in each cell and where you want the cells
	 * @param file
	 *            the csv file you want to create
	 * @throws IOException 
	 */
	public static void writeCSV(List<List<String>> csvMatrix, File file) throws IOException {
			Writer output = new BufferedWriter(new FileWriter(file));
			for (List<String> row : csvMatrix) {
				StringBuilder rowBuffer = new StringBuilder();
				boolean first = true;
				for (int j = 0; j < row.size(); j++) {
					StringBuilder thisEntry = new StringBuilder();
					String entry = row.get(j);
					boolean quoteFlag = false;
					for (int k = 0; k < entry.length(); k++) {
						if (entry.charAt(k) == '"') {
							quoteFlag = true;
							thisEntry.append("\"\"");
						} else if (entry.charAt(k) == ',') {
							quoteFlag = true;
							thisEntry.append(',');
						} else {
							thisEntry.append(entry.charAt(k));
						}
					}
					if (quoteFlag) {
						if (first) {
							rowBuffer.append('"');
						} else {
							rowBuffer.append(",\"");
						}
						rowBuffer.append(thisEntry);
						rowBuffer.append('"');
					} else {
						if (!first) {
							rowBuffer.append(',');
						}
						rowBuffer.append(thisEntry);
					}
					if ((row.size() - 1) == j) {
						rowBuffer.append("\n");
					}
					first = false;
				}
				output.write(rowBuffer.toString());
			}
			output.close();
	}

	/**
	 * Creates and writes to a file in the csv format
	 * 
	 * @param csvMatrix
	 *            information to be writen to file
	 * @param fileName
	 *            name of the file to be creatd and writen
	 * @throws IOException 
	 */
	public static void writeCSV(List<List<String>> csvMatrix, String fileName) throws IOException {
		File file = new File(fileName);
		writeCSV(csvMatrix, file);
	}
}
