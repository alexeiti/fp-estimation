package com.ati.fpestimation.domain.estimation;

import com.ati.fpestimation.domain.kpi.EstimationFunction;
import com.ati.fpestimation.domain.kpi.FunctionComplexityType;

import java.util.Random;

public class EstimationEntry {
    private int cost;
    private String name;
    private FunctionComplexityType complexity;
    // private EstimationFunction estimationFunction;
    private EstimationFunction estimationFunction;

    public EstimationEntry(String name) {
        //TODO remove dummy values
        cost = 5;
        this.name = name;
        estimationFunction = new EstimationFunction("External DB", null);
        complexity = FunctionComplexityType.MIN;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setComplexity(FunctionComplexityType complexity) {
        this.complexity = complexity;
    }


    public int getCost() {
        //TODO Ati return correctly calculated cos
        int costt = (int)( Math.random() *50);
        return 10;
    }

    public EstimationFunction getEstimationFunction() {
        return estimationFunction;
    }

    public void setEstimationFunction(EstimationFunction estimationFunction) {
        this.estimationFunction = estimationFunction;
    }

    public String getName() {
        return name;
    }

    public FunctionComplexityType getComplexity() {
        return complexity;
    }


    @Override
    public String toString() {
        return "EstimationEntry{" +
                "cost=" + getCost() +
                ", name='" + name + '\'' +
                ", complexity=" + complexity +
                ", estimationFunction=" + estimationFunction +
                '}';
    }
}
