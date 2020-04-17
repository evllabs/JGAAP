package com.jgaap.backend;

import com.jgaap.JGAAPConstants;
import com.jgaap.generics.Experiment;
import com.jgaap.util.ConfusionMatrix;
import com.jgaap.util.Document;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class ExperimentJSONTest {

    @Test
    public void readExperiment() throws Exception {
        String json = new String(
                Files.readAllBytes(
                        Paths.get(
                                getClass().getResource(
                                        JGAAPConstants.JGAAP_RESOURCE_PACKAGE+"experiment.json"
                                ).toURI())));
        Experiment e = ExperimentJSON.readExperiment(json);
//        assertTrue(e.displayName().equalsIgnoreCase("Leave One Out Cross Validation"));
        List<Document> documents = Utils.getDocumentsFromCSV(
                CSVIO.readCSV(
                        getClass().getResourceAsStream(
                                JGAAPConstants.JGAAP_RESOURCE_PACKAGE+"aaac/problemA/loadA.csv")));
        List<Document> knownDocuments = new ArrayList<Document>();
        List<Document> unknownDocuments = new ArrayList<Document>();
        Set<String> authors = new HashSet<String>();
        for (Document document : documents) {
            if (document.isAuthorKnown()) {
                knownDocuments.add(document);
                document.load();
                authors.add(document.getAuthor());
            } else {
                unknownDocuments.add(document);
            }
        }
        ConfusionMatrix result = e.run(knownDocuments);
        System.out.println("F1 score "+result.getAverageF1Score());
        System.out.println("Precision "+result.getAveragePrecision());
        System.out.println("Recall "+result.getAverageRecall());
        System.out.println(e.toString());
    }
}