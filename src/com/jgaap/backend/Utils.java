package com.jgaap.backend;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.jgaap.generics.Displayable;
import com.jgaap.generics.Document;

/**
 * Generic methods that will be reused throughout JGAAP
 * 
 * Any method added here is required to be static 
 * When using Utils simply reference the method you want do not instance it.
 * @author ryan
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
		Vector<Vector<String>> csvOfDocuments = new Vector<Vector<String>>();
		for(Document document : documents){
			Vector<String> current = new Vector<String>();
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
}
