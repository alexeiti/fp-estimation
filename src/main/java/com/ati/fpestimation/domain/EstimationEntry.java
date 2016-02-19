package com.ati.fpestimation.domain;

public class EstimationEntry {
    private int cost;
    private String name;
    private FunctionComplexityType complexity;
    // private EstimationFunction estimationFunction;
    private EstimationFunction estimationFunction;

    public EstimationEntry() {
        cost= 5;
        name = "SOmething";
        complexity = FunctionComplexityType.MIN;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComplexity(FunctionComplexityType complexity) {
        this.complexity = complexity;
    }


    public int getCost() {
        return cost;
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
                "cost=" + cost +
                ", name='" + name + '\'' +
                ", complexity=" + complexity +
                ", estimationFunction=" + estimationFunction +
                '}';
    }
}
