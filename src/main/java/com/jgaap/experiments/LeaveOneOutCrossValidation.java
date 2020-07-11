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
        Iterator<TrainTestSplit> it = new Iterator<TrainTestSplit>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < documents.size();
            }

            @Override
            public TrainTestSplit next() {
                List<Document> test = Collections.singletonList(documents.get(i));
                List<Document> train = new ArrayList<>();
                for (int j=0; j < documents.size(); j++){
                    if (i!=j)
                        train.add(documents.get(j));
                }
                TrainTestSplit trainTestSplit = new TrainTestSplit();
                trainTestSplit.addTrainDocuments(train);
                trainTestSplit.addTestDocuments(test);
                i++;
                return trainTestSplit;
            }
        };
        return it;
    }
}
