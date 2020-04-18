package com.jgaap.experiments;

import com.jgaap.generics.Experiment;
import com.jgaap.util.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class LeaveOneOutCrossValidation extends Experiment {
    @Override
    public String displayName() {
        return "Leave One Out Cross Validation";
    }

    @Override
    public String tooltipText() {
        return "Test the experiment settings by removing every document once and testing against a model built with the others.";
    }

    @Override
    public boolean showInGUI() {
        return false;
    }

    @Override
    protected Iterator<TrainTestSplit> getTestingPlan(List<Document> documents) {
        List<TrainTestSplit> plan = new ArrayList<>(documents.size());
        for (int i = 0; i < documents.size(); i++) {
            List<Document> test = Collections.singletonList(documents.get(i));
            List<Document> train;
            if (i == 0){
                train = documents.subList(1, documents.size());
            } else if (i == documents.size() - 1){
                train = documents.subList(0, i);
            } else {
                train = documents.subList(0, i);
                train.addAll(documents.subList(i+1, documents.size()));
            }
            TrainTestSplit trainTestSplit = new TrainTestSplit();
            trainTestSplit.addTrainDocuments(train);
            trainTestSplit.addTestDocuments(test);
            plan.add(trainTestSplit);
        }
        return plan.iterator();
    }
}
