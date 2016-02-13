package com.ati.fpestimation.server;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FunctionRepository {
    Map<String, EstimationFunction> estimationFunctionsMap;


    public FunctionRepository() {
        this.estimationFunctionsMap = new HashMap<>();
        initFunctionsWithTestdata();
    }

    private void initFunctionsWithTestdata() {
        Map<ComplexityType, Short> dummyWeightMap = new HashMap<>();
        EstimationFunction ef = new EstimationFunction("Internal DB", dummyWeightMap);
        estimationFunctionsMap.put(ef.getName(),ef);

        ef = new EstimationFunction("External DB", dummyWeightMap);
        estimationFunctionsMap.put(ef.getName(),ef);

        ef = new EstimationFunction("Ausgabe", dummyWeightMap);
        estimationFunctionsMap.put(ef.getName(),ef);

    }

    public FunctionRepository(Map<String, EstimationFunction> estimationFunctionsMap) {
        this.estimationFunctionsMap = estimationFunctionsMap;
    }

    public Collection<EstimationFunction> getEstimationFunctions() {
        return estimationFunctionsMap.values();

    }

}
