package com.jgaap.backend;

import com.jgaap.JGAAPConstants;
import com.jgaap.generics.Experiment;
import com.jgaap.util.ConfusionMatrix;
import com.jgaap.util.Document;
import com.jgaap.util.Model;
import lombok.extern.log4j.Log4j;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

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
        assertTrue(e.displayName().equalsIgnoreCase("k Fold Cross Validation"));
        log.info("Loading Documents.");
        List<Document> documents = Utils.getDocumentsFromCSV(
            CSVIO.readCSV(
                getClass().getResourceAsStream(
                    JGAAPConstants.JGAAP_RESOURCE_PACKAGE+"aaac/problemF/loadF.csv")));
        List<Document> knownDocuments = new ArrayList<Document>();
        List<Document> unknownDocuments = new ArrayList<Document>();
        for (Document document : documents) {
            if (document.isAuthorKnown()) {
                knownDocuments.add(document);
            } else {
                unknownDocuments.add(document);
            }
        }
        log.info("Documents Loaded.");
        log.info("Running Experiment.");
        ConfusionMatrix result = e.train(knownDocuments);
        Model model = e.getModel();
        log.info("Experiment Finished.");
        double f1 = result.getAverageF1Score();
        double precision = result.getAveragePrecision();
        double recall = result.getAverageRecall();
        log.info("F1 score "+f1);
        log.info("Precision "+precision);
        log.info("Recall "+recall);
        for (String label : result.getLabels()) {
            log.info(label+" F1 score "+result.getF1Score(label));
            log.info(label+" Precision "+result.getPrecision(label));
            log.info(label+" Recall "+result.getRecall(label));
        }
        assertEquals(0.8419494578569506, f1, 0.05);
        assertEquals(0.8507456140350879, precision, 0.05);
        assertEquals(0.8333333333333334, recall, 0.05);
        for (Document unknown : unknownDocuments) {
            String author = model.apply(unknown);
            String expected = unknown.getTitle().split("\\s+")[1];
            log.info("Actual: "+expected+"\t Predicted: "+author);
            // This problem should be 100% except for the None case which we don't cover in any method at this time.
            assertTrue(expected.equalsIgnoreCase(author) || expected.equalsIgnoreCase("NONE"));
        }

    }
}