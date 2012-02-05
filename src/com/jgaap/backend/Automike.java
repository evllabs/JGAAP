package com.jgaap.backend;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.DocType;
import com.jgaap.generics.Document;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.Pair;

public class Automike {

	public static void automate(InputStream settings, InputStream tagging)
			throws Exception {
		List<List<String>> experiments = CSVIO.readCSV(settings);
		List<List<String>> tags = CSVIO.readCSV(tagging);
		List<String> accessSettings = tags.remove(0);
		Connection connection = getConnection(accessSettings);
		for(List<String> tag : tags){
			String table = tag.remove(0);
			Statement statement = connection.createStatement(); 
			List<Document> documents = selectDocuments(table, tag, statement);
			for(List<String> experiment : experiments){
				API api = API.getPrivateInstance();
				api.setLanguage(experiment.get(0));
				for(Document document : documents){
					api.addDocument(new Document(document));
				}
				if (!"".equalsIgnoreCase(experiment.get(1).trim())) {
					String[] canonicizers = experiment.get(1).split("\\s*\\|\\s*");
					for (String current : canonicizers) {
						api.addCanonicizer(current);
					}
				}
				String event = experiment.get(2);
				String[] eventCullers = experiment.get(3).split("\\|");
				String analysis = experiment.get(4);
				String distance = experiment.get(5);
				EventDriver eventDriver = api.addEventDriver(event);
				for(String eventCuller : eventCullers){
					api.addEventCuller(eventCuller);
				}
				AnalysisDriver analysisDriver = api.addAnalysisDriver(analysis);
				if(!distance.isEmpty()){
					api.addDistanceFunction(distance, analysisDriver);
				}
				
				StringBuilder resultTableBuilder = new StringBuilder();
				
				resultTableBuilder.append(table).append("-");
				boolean first = true;
				for(String element :tag){
					if(first){
						first=false;
					}else {
						resultTableBuilder.append("+");
					}
					resultTableBuilder.append(element);
				}
				resultTableBuilder.append("-");
				
				for(Document document : documents){
					Statement storeResultStatement = connection.createStatement();
					List<Pair<String, Double>> results = document.getRawResult(analysisDriver, eventDriver);
					boolean correct = results.get(0).getFirst().equalsIgnoreCase(document.getAuthor());
					String title = document.getTitle();
					
					
				}
			}
		}
	}
	
	private static boolean createResultsTable(String tableName){
		return false;
	}
	
	private static boolean tableExists(String table){
		
		return false;
	}
	
	private static boolean addResultToTable(String table, String title, boolean correct, List<Pair<String,Double>> results, Statement statement){
		
		return false;
	}

	private static List<Document> selectDocuments(String table, List<String> columnNames, Statement statement) throws SQLException{
		List<Document> documents = new ArrayList<Document>();
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT ");
		for(String columnName : columnNames){
			queryBuilder.append(columnName).append(", ");
		}
		queryBuilder.deleteCharAt(queryBuilder.lastIndexOf(","));
		queryBuilder.append(" FROM ").append(table);
		String query = queryBuilder.toString();
		ResultSet resultSet = statement.executeQuery(query);
		while(resultSet.next()){
			Document document = new Document();
			document.setDocType(DocType.DATABASE);
			String id = Integer.toString(resultSet.getInt("id"));
			document.setTitle(table+"-"+id);
			document.setFilePath(table+"-"+id);
			document.readStringText(resultSet.getString("text"));
			StringBuilder tagBuilder = new StringBuilder();
			boolean first = true;
			for(String columnName : columnNames){
				if(!first){
					tagBuilder.append("+");
				}
				tagBuilder.append(resultSet.getString(columnName));
			}
			document.setAuthor(tagBuilder.toString());
			documents.add(document);
		}
		return documents;
	}
	
	private static Connection getConnection(List<String> accessSettings) throws Exception {
		Connection conn = null;
		String url = accessSettings.get(0);
		String userName = accessSettings.get(1);
		String password = accessSettings.get(2);
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection(url, userName, password);
		return conn;
	}

}
