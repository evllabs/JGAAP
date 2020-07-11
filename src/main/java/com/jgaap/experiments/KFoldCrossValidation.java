package com.jgaap.experiments;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.jgaap.generics.Experiment;
import com.jgaap.generics.ExperimentException;
import com.jgaap.util.Document;

import java.util.*;

public class KFoldCrossValidation  extends Experiment {

    public KFoldCrossValidation() {
        super();
        addParams(
                "K",
                "K",
                "5",
                new String[] {
                        "2", "3", "4", "5", "6", "7", "8", "9", "10", "11",
                        "12", "13", "14", "15", "16", "17", "18", "19", "20",
                        "21", "22", "23", "24", "25", "26", "27", "28", "29",
                        "30", "31", "32", "33", "34", "35", "36", "37", "38",
                        "39", "40", "41", "42", "43", "44", "45", "46", "47",
                        "48", "49", "50" },
                false);
    }

    @Override
    public String displayName() {
        return "k Fold Cross Validation";
    }

    @Override
    public String tooltipText() {
        return "Test the experiment settings by breaking each author into k folds of test and training data.";
    }

    @Override
    public boolean showInGUI() {
        return false;
    }

    @Override
    protected Iterator<TrainTestSplit> getTestingPlan(List<Document> documents) throws ExperimentException {
        int k = this.getParameter("k", 5);
        Multimap<String, Document> authorToDocuments = ArrayListMultimap.create();
        for (Document document : documents) {
            authorToDocuments.put(document.getAuthor(), document);
        }
        // check that all authors have enough documents for k folds.
        for (Map.Entry<String, Collection<Document>> authorDocumentsEntry : authorToDocuments.asMap().entrySet()) {
            if (authorDocumentsEntry.getValue().size() < k)
                throw new ExperimentException(
                        "Author " + authorDocumentsEntry.getKey() +
                        " does not have enough documents to test with K=" + k);
        }
        return new Iterator<TrainTestSplit>() {
            int i = 0;
            @Override
            public boolean hasNext() {
                return i < k;
            }

            @Override
            public TrainTestSplit next() {
                TrainTestSplit trainTestSplit = new TrainTestSplit();
                for (Map.Entry<String, Collection<Document>> authorDocumentsEntry : authorToDocuments.asMap().entrySet()){
                    List<Document> authorsDocuments = new ArrayList<>(authorDocumentsEntry.getValue());
                    int count = authorsDocuments.size()/k;
                    int start = i * count;
                    int end = start + count;
                    if (i == k-1){
                        end = authorsDocuments.size();
                    }
                    List<Document> test = authorsDocuments.subList(start, end);
                    List<Document> train = new ArrayList<>(authorsDocuments.size()-k);
                    for (int j = 0; j < authorsDocuments.size(); j++) {
                        if (j<start || j>=end) {
                            train.add(authorsDocuments.get(j));
                        }
                    }
                    trainTestSplit.addTestDocuments(test);
                    trainTestSplit.addTrainDocuments(train);
                }
                i++;
                return trainTestSplit;
            }
        };
    }


}
