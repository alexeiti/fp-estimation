package com.ati.fpestimation.domain.kpi;

import java.util.Map;

public class EstimationFunction {

    private String name;
    private Map<FunctionComplexityType, Short> weightMap;

    public EstimationFunction(String name, Map<FunctionComplexityType, Short> weightMap) {
        this.name = name;
        this.weightMap = weightMap;
    }

    public String getName() {
        return name;
    }

    public Map<FunctionComplexityType, Short> getWeightMap() {
        return weightMap;
    }
}
