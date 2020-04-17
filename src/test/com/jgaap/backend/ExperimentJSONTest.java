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
        assertTrue(e.displayName().equalsIgnoreCase("K Fold Cross Validation"));
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
        double f1 = result.getAverageF1Score();
        double precision = result.getAveragePrecision();
        double recall = result.getAverageRecall();
        System.out.println("F1 score "+f1);
        System.out.println("Precision "+precision);
        System.out.println("Recall "+recall);
        assertEquals(f1, 0.22391625538335796, 0.05);
        assertEquals(precision, 0.23021978021978023, 0.05);
        assertEquals(recall, 0.23021978021978023, 0.05);
    }
}