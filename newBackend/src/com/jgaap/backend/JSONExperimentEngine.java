package com.jgaap.backend;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.*;

import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.Canonicizer;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Document;
import com.jgaap.generics.EventCuller;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.NeighborAnalysisDriver;
import com.jgaap.generics.Pair;

public class JSONExperimentEngine {

	public static void runExperiments(String file) throws IOException {
		runExperiments(new FileInputStream(file));
	}

	public static void runExperiments(FileInputStream file) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(file));
		JSONObject json = (JSONObject) JSONValue.parse(reader);
		final String experimentName = (String) json.get("name");
		JSONArray experiments = (JSONArray) json.get("experiments");
		List<Thread> threads = new ArrayList<Thread>();
		for (int i = 0; i < experiments.size(); i++) {
			final JSONObject experiment = (JSONObject) experiments.get(i);
			Thread thread = new Thread(new Runnable() {
				@SuppressWarnings("rawtypes")
				@Override
				public void run() {
					API api = new API();
					String number = (String) experiment.get("number");
					String language = (String) experiment.get("language");
					JSONArray canonicizers = (JSONArray) experiment.get("canonicizers");
					JSONArray eventDrivers = (JSONArray) experiment.get("eventDrivers");
					JSONArray eventCullers = (JSONArray) experiment.get("eventCullers");
					JSONArray analysisDrivers = (JSONArray) experiment.get("analysisDrivers");
					JSONArray documents = (JSONArray) experiment.get("documents");
					try {
						api.setLanguage(language);
						for (int j = 0; j < canonicizers.size(); j++) {
							api.addCanonicizer((String) canonicizers.get(j));
						}
						for (int j = 0; j < eventDrivers.size(); j++) {
							JSONObject current = (JSONObject) eventDrivers
									.get(j);
							EventDriver eventDriver = api
									.addEventDriver((String) current
											.get("name"));
							Set parameters = current.keySet();
							for (Object obj : parameters) {
								if (!"name".equalsIgnoreCase((String) obj)) {
									eventDriver.setParameter((String) obj,
											(String) current.get(obj));
								}
							}

						}
						for (int j = 0; j < eventCullers.size(); j++) {
							JSONObject current = (JSONObject) eventCullers
									.get(j);

							EventCuller eventCuller = api
									.addEventCuller((String) current
											.get("name"));
							Set parameters = current.keySet();
							for (Object obj : parameters) {
								if (!"name".equalsIgnoreCase((String) obj)) {
									eventCuller.setParameter((String) obj,
											(String) current.get(obj));
								}
							}

						}
						for (int j = 0; j < analysisDrivers.size(); j++) {
							JSONObject current = (JSONObject) analysisDrivers
									.get(j);
							AnalysisDriver analysisDriver = api
									.addAnalysisDriver((String) current
											.get("name"));
							if (analysisDriver instanceof NeighborAnalysisDriver) {
								JSONObject currentDistance = (JSONObject) current
										.get("distanceFunction");
								DistanceFunction distanceFunction = api
										.addDistanceFunction(
												(String) currentDistance
														.get("name"),
												analysisDriver);
								Set parameters = currentDistance.keySet();
								for (Object obj : parameters) {
									if (!"name".equalsIgnoreCase((String) obj)) {
										distanceFunction.setParameter(
												(String) obj,
												(String) currentDistance
														.get(obj));
									}
								}
							}
							Set parameters = current.keySet();
							for (Object obj : parameters) {
								if (!"name".equalsIgnoreCase((String) obj)
										&& !"distanceFunction"
												.equalsIgnoreCase((String) obj)) {
									analysisDriver.setParameter((String) obj,
											(String) current.get(obj));
								}
							}

						}
						for (int j = 0; j < documents.size(); j++) {
							JSONObject current = (JSONObject) documents.get(j);
							if (current.containsKey("title")) {
								if (current.containsKey("author")) {
									api.addDocument(
											(String) current.get("file"),
											(String) current.get("author"),
											(String) current.get("title"));
								} else {
									api.addDocument(
											(String) current.get("file"), "",
											(String) current.get("title"));
								}
							} else {
								if (current.containsKey("author")) {
									api.addDocument(
											(String) current.get("file"),
											(String) current.get("author"));
								} else {
									api.addDocument(
											(String) current.get("file"), "");
								}
							}

						}

						api.execute();
						List<Document> unknownDocuments = api
								.getUnknownDocuments();
						for (Document document : unknownDocuments) {
							List<String> canons = new ArrayList<String>();
							for(Canonicizer canonicizer : document.getCanonicizers()){
								canons.add(canonicizer.displayName());
							}
							Map<AnalysisDriver , Map< EventDriver, List<Pair<String,Double>>>> results = document.getResults();
							for(AnalysisDriver analysisDriver : results.keySet()){
								for(EventDriver eventDriver : results.get(analysisDriver).keySet()){
									StringBuffer buffer = new StringBuffer();
									buffer.append("\n-----------------------------------------------------------------------------");
									buffer.append("\nTitle: "+document.getTitle());
									buffer.append("\nLocation: "+document.getFilePath());
									buffer.append("\nCanonicizers: ");
									for(Canonicizer canonicizer : document.getCanonicizers()){
										buffer.append(canonicizer.displayName()+" ,");
									}
									buffer.deleteCharAt(buffer.length()-1);
									buffer.append("\n");
									buffer.append("Event Driver: "+eventDriver.displayName()+"\n");
									buffer.append("Event Cullers: ");
									for(EventCuller eventCuller : api.getEventCullers()){
										buffer.append(eventCuller.displayName()+" ,");
									}
									buffer.deleteCharAt(buffer.length()-1);
									buffer.append("\n");
									buffer.append("Analysis Method:"+analysisDriver.displayName()+"\n");
									for(Pair<String, Double> result : results.get(analysisDriver).get(eventDriver)){
										buffer.append(result.getFirst()+"\t| "+result.getSecond()+"\n");
									}
									String filePath = ExperimentEngine.fileNameGen(canons, eventDriver.displayName(), analysisDriver.displayName(), experimentName, number);
									Utils.appendToFile(filePath, buffer.toString());
									
								}
							}
							
						}
					} catch (Exception e) {

					}
				}
			});
			threads.add(thread);
		}
		for (int i = 0; i < threads.size() / 2; i++) {
			try {
				System.out.println(i / (threads.size()) * 100 + "%");
				Thread thread1 = threads.get(i * 2);
				thread1.start();
				if (i * 2 + 1 < threads.size()) {
					Thread thread2 = threads.get(i * 2 + 1);
					thread2.start();
					thread2.join();
				}
				thread1.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
