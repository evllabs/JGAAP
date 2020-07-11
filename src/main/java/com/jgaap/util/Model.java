package com.jgaap.util;

import com.jgaap.generics.*;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<EventDriver> eventDrivers;
    private AnalysisDriver analysisDriver;
    private boolean trained;

    public Model() {
        this.eventDrivers = new ArrayList<>();
        this.trained = false;
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

    private void checkTrained() throws ModelException {
        if (!this.trained) {
            throw new ModelException("Model has not been trained yet!");
        }
    }

    public Model train(List<Document> knowns) throws EventGenerationException, CanonicizationException, EventCullingException, AnalyzeException, LanguageParsingException, DocumentException {
        List<Document> processed_documents = trainEventDrivers(knowns);
        getAnalysisDriver().train(processed_documents);
        this.trained = true;
        return this;
    }

    public String apply(Document text) throws ModelException, AnalyzeException, EventGenerationException, CanonicizationException, EventCullingException {
        checkTrained();
//        AbsoluteHistogram histogram = applyEventDrivers(Collections.singletonList(text)).get(0);
        return  getAnalysisDriver().analyze(text).get(0).getFirst();
    }

    public List<String> apply(List<Document> texts) throws ModelException, AnalyzeException, EventGenerationException, CanonicizationException, EventCullingException, LanguageParsingException, DocumentException {
        checkTrained();
        List<Document> documents = applyEventDrivers(texts);
        return  getAnalysisDriver().analyze(documents);
    }

    private List<Document> trainApplyEventDrivers(List<Document> texts, boolean train) throws EventGenerationException, CanonicizationException, EventCullingException, LanguageParsingException, DocumentException {
        List<Document> processedDocuments = new ArrayList<>(texts.size());
        for (EventDriver eventDriver: this.getEventDrivers()){
            List<EventSet> currentEventSets;
            if (train) {
                currentEventSets = eventDriver.trainApply(texts);
            } else {
                currentEventSets = eventDriver.apply(texts);
            }
            for (int i = 0; i < currentEventSets.size(); i++) {
                EventSet eventSet = currentEventSets.get(i);
                if (processedDocuments.size() == i){
                    Document document = new Document();
                    document.setAuthor(texts.get(i).getAuthor());
                    processedDocuments.add(document);
                }
                processedDocuments.get(i).addEventSet(eventDriver, eventSet);
            }
        }
        return processedDocuments;
    }

    private List<Document> trainEventDrivers(List<Document> texts) throws EventGenerationException, CanonicizationException, EventCullingException, LanguageParsingException, DocumentException {
        return trainApplyEventDrivers(texts, true);
    }

    private List<Document> applyEventDrivers(List<Document> texts) throws EventGenerationException, CanonicizationException, EventCullingException, LanguageParsingException, DocumentException {
        return trainApplyEventDrivers(texts, false);
    }

    public void setAnalysisDriver(AnalysisDriver analysisDriver) {
        this.analysisDriver = analysisDriver;
    }

    public AnalysisDriver getAnalysisDriver() {
        return this.analysisDriver;
    }
}
