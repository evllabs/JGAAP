package com.jgaap.util;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.Map;
import java.util.Set;

public class ConfusionMatrix {
    private Table<String, String, Integer> matrix;

    public ConfusionMatrix() {
        this.matrix = HashBasedTable.create();
    }

    public void add(String actual, String predicted) {
        this.add(actual, predicted, 1);
    }

    public void add(String actual, String predicted, int count) {
        matrix.put(actual, predicted, this.getCount(actual,predicted) + count);
    }

    public int getCount(String actual, String predicted) {
        Integer count = matrix.get(actual, predicted);
        if (count == null) {
            return 0;
        }
        return count;
    }

    public int getPredictedTotal(String predicted) {
        int total = 0;
        for (Map.Entry<String, Integer> entry : this.matrix.column(predicted).entrySet()){
            total += entry.getValue();
        }
        return total;
    }

    public int getActualTotal(String actual) {
        int total = 0;
        for (Map.Entry<String, Integer> entry : this.matrix.row(actual).entrySet()){
            total += entry.getValue();
        }
        return total;
    }

    public double getPrecision(String label) {
        double truePositive = this.matrix.get(label, label);
        return truePositive / this.getPredictedTotal(label);
    }

    public double getAveragePrecision() {
        Set<String> labels = this.matrix.rowKeySet();
        double sum = 0;
        for (String label : labels){
            sum += getPrecision(label);
        }
        return sum / labels.size();
    }

    public double getRecall(String label) {
        double truePositive = this.matrix.get(label, label);
        return truePositive / this.getActualTotal(label);
    }

    public double getAverageRecall() {
        Set<String> labels = this.matrix.rowKeySet();
        double sum = 0;
        for (String label : labels) {
            sum += getRecall(label);
        }
        return sum / labels.size();
    }

    public double getF1Score(String label) {
        double precision = this.getPrecision(label);
        double recall = this.getRecall(label);
        return 2 * (precision * recall) / (precision + recall);
    }

    public double getAverageF1Score() {
        Set<String> labels = this.matrix.rowKeySet();
        double sum = 0;
        for (String label : labels){
            sum += getF1Score(label);
        }
        return sum / labels.size();
    }
}
