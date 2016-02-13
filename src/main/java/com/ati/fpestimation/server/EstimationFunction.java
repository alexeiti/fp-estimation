package com.ati.fpestimation.server;

import java.util.Map;

public class EstimationFunction {

    private String name;
    private Map<ComplexityType, Short> weightMap;

    public EstimationFunction(String name, Map<ComplexityType, Short> weightMap) {
        this.name = name;
        this.weightMap = weightMap;
    }

    public String getName() {
        return name;
    }

    public Map<ComplexityType, Short> getWeightMap() {
        return weightMap;
    }
}
