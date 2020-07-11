package com.jgaap.generics;

import com.jgaap.util.*;
import lombok.Data;

import java.util.*;

public abstract class Experiment extends Parameterizable implements Displayable, Comparable<Experiment>{
    private List<Model> models;

    public void addModel(Model model){
        if (models == null){
            this.models = new ArrayList<>();
        }
        this.models.add(model);
    }

    public List<Model> getModels() {
        return this.models;
    }


    public Map<Model, ConfusionMatrix> run(List<Document> knownDocuments) throws EventGenerationException, CanonicizationException, EventCullingException, AnalyzeException, ExperimentException, LanguageParsingException, DocumentException, ModelException {
        Map<Model, ConfusionMatrix> results = new HashMap<>();
        for (Model model : this.getModels()) {
            ConfusionMatrix confusionMatrix = new ConfusionMatrix();
            for (Iterator<TrainTestSplit> plan = this.getTestingPlan(knownDocuments); plan.hasNext(); ) {
                TrainTestSplit trainTestSplit = plan.next();
                model.train(trainTestSplit.getTrainDocuments());
                for (Document document : trainTestSplit.getTestDocuments()) {
                    String result = model.apply(document);
                    confusionMatrix.add(document.getAuthor(), result);
                }
            }
            model.train(knownDocuments);
            results.put(model, confusionMatrix);
        }
        return results;
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
        return displayName()+" : "+getParameters();
    }
}
