package com.ati.fpestimation.domain.kpi;

public enum FunctionComplexityType {
    MAX("maximum"), MIN("minimum"), MID("middle");

    private String caption;

    FunctionComplexityType(String caption) {
        this.caption = caption;
    }

    public String getCaption() {
        return caption;
    }
}
