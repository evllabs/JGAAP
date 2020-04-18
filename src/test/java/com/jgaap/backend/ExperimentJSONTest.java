package com.jgaap.backend;

import com.jgaap.JGAAPConstants;
import com.jgaap.generics.Experiment;
import com.jgaap.util.ConfusionMatrix;
import com.jgaap.util.Document;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@Log4j
public class ExperimentJSONTest {

    @Test
    public void readExperiment() throws Exception {
        log.info("Loading json.");
        String json = new String(
            Files.readAllBytes(
                Paths.get(
                    getClass().getResource(
                        JGAAPConstants.JGAAP_RESOURCE_PACKAGE+"experiment.json"
                    ).toURI())));
        log.info("Populating Experiment.");
        Experiment e = ExperimentJSON.readExperiment(json);
        log.info("Experiment Loaded");
        assertTrue(e.displayName().equalsIgnoreCase("K Fold Cross Validation"));
        log.info(e.toString());
        log.info("Loading Documents.");
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
        log.info("Documents Loaded.");
        log.info("Running Experiment.");
        ConfusionMatrix result = e.run(knownDocuments);
        log.info("Experiment Finished.");
        double f1 = result.getAverageF1Score();
        double precision = result.getAveragePrecision();
        double recall = result.getAverageRecall();
        log.info("F1 score "+f1);
        log.info("Precision "+precision);
        log.info("Recall "+recall);
        assertEquals(f1, 0.22391625538335796, 0.05);
        assertEquals(precision, 0.23021978021978023, 0.05);
        assertEquals(recall, 0.23021978021978023, 0.05);
    }
}