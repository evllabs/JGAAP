package com.jgaap.generics;

import com.jgaap.util.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class Experiment extends Parameterizable implements Displayable, Comparable<Experiment>{
    private Model model;

    public void setModel(Model model){
        this.model = model;
    }

    public Model getModel() {
        return this.model;
    }

    public ConfusionMatrix run(List<Document> knownDocuments) throws EventGenerationException, CanonicizationException, EventCullingException, AnalyzeException, ExperimentException, LanguageParsingException, DocumentException, ModelException {
        ConfusionMatrix confusionMatrix = new ConfusionMatrix();
        for (Iterator<TrainTestSplit> plan = this.getTestingPlan(knownDocuments); plan.hasNext(); ){
            TrainTestSplit trainTestSplit = plan.next();
            this.model.train(trainTestSplit.getTrainDocuments());
            for (Document document : trainTestSplit.getTestDocuments()) {
                String result = this.model.apply(document);
                confusionMatrix.add(document.getAuthor(), result);
            }
        }
        return confusionMatrix;
    }

    protected abstract Iterator<TrainTestSplit> getTestingPlan(List<Document> documents) throws ExperimentException;

    public int compareTo(Experiment experiment){
        return this.displayName().compareTo(experiment.displayName());
    }

    @Data
    protected class TrainTestSplit {
        List<Document> trainDocuments;
        List<Document> testDocuments;

        public void addTrainDocuments(Collection<Document> documents) {
            if (trainDocuments == null) {
                trainDocuments = new ArrayList<>();
            }
            trainDocuments.addAll(documents);
        }

        public void addTestDocuments(Collection<Document> documents) {
            if (testDocuments == null) {
                testDocuments = new ArrayList<>();
            }
            testDocuments.addAll(documents);
        }
    }

    public String toString() {
        return displayName()+" : "+getParameters() + getModel().toString();
    }
}
