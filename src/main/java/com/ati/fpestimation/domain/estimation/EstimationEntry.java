package com.ati.fpestimation.domain.estimation;

import com.ati.fpestimation.domain.kpi.EstimationFunction;
import com.ati.fpestimation.domain.kpi.FunctionComplexityType;

public class EstimationEntry {
    private int cost;
    private boolean manual;
    private String name;
    private FunctionComplexityType complexity;
    // private EstimationFunction estimationFunction;
    private EstimationFunction estimationFunction;

    public EstimationEntry(String name, boolean isManual) {
        //TODO remove dummy values
        cost = 5;
        this.name = name;
        this.manual = isManual;
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
        // int costt = (int) (Math.random() * 50);
        if (isManual())
            return cost;
        else
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

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isManual() {
        return manual;
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
