package com.jgaap.generics;

import com.jgaap.util.ConfusionMatrix;
import com.jgaap.util.Document;
import com.jgaap.util.EventSet;
import com.jgaap.util.Pair;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class Experiment extends Parameterizable implements Displayable, Comparable<Experiment>{
    private List<EventDriver> eventDrivers;
    private AnalysisDriver analysisDriver;

    public Experiment() {
        this.eventDrivers = new ArrayList<>();
    }

    public List<EventDriver> getEventDrivers() {
        return eventDrivers;
    }

    public boolean addEventDrivers(List<EventDriver> eventDrivers) {
        return this.eventDrivers.addAll(eventDrivers);
    }

    public boolean addEventDriver(EventDriver eventDriver) {
        return this.eventDrivers.add(eventDriver);
    }

    public ConfusionMatrix run(List<Document> knownDocuments) throws EventGenerationException, CanonicizationException, EventCullingException, AnalyzeException, ExperimentException {
        applyEventDrivers(knownDocuments);
        for (Iterator<TrainTestSplit> plan = this.getTestingPlan(knownDocuments); plan.hasNext(); ){
            TrainTestSplit trainTestSplit = plan.next();
            this.getAnalysisDriver().train(trainTestSplit.getTrainDocuments());
            for (Document document : trainTestSplit.getTestDocuments()) {
                List<Pair<String, Double>> result = this.getAnalysisDriver().analyze(document);
                document.addResult(this.getAnalysisDriver(), result);
            }
        }
        return evaluate(knownDocuments);
    }

    protected abstract Iterator<TrainTestSplit> getTestingPlan(List<Document> documents) throws ExperimentException;

    private void applyEventDrivers(List<Document> documents) throws EventGenerationException, CanonicizationException, EventCullingException {
        for (EventDriver eventDriver: this.getEventDrivers()){
            List<EventSet> eventSets = eventDriver.trainApply(documents);
            for (int i = 0; i < eventSets.size(); i++) {
                Document document = documents.get(i);
                EventSet eventSet = eventSets.get(i);
                document.addEventSet(eventDriver, eventSet);
            }
        }
    }

    private ConfusionMatrix evaluate(List<Document> documents){
        ConfusionMatrix confusionMatrix = new ConfusionMatrix();
        for (Document document : documents) {
            List<Pair<String, Double>> results = document.getRawResult(this.analysisDriver);
            confusionMatrix.add(document.getAuthor(), results.get(0).getFirst());
        }
        return confusionMatrix;
    }

    public void setAnalysisDriver(AnalysisDriver analysisDriver) {
        this.analysisDriver = analysisDriver;
    }

    public AnalysisDriver getAnalysisDriver() {
        return this.analysisDriver;
    }

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
        String tmp = "";
        for(EventDriver e : getEventDrivers()){
            tmp = tmp + "\n" + e.toString();
        }
        return displayName()+" : "+getParameters() + tmp +  getAnalysisDriver().toString();
    }
}
