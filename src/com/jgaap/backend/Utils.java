package com.jgaap.backend;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import com.jgaap.generics.Document;

public class Utils {

	public static boolean saveFile(String filePath, String data){
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(filePath));
			writer.write(data);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
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
}
