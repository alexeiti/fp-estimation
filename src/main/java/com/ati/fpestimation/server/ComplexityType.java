package com.ati.fpestimation.server;

public enum ComplexityType {
    MAX("maximum"), MIN("minimum"), MID("middle");

    private String caption;

    ComplexityType(String caption) {
        this.caption = caption;
    }

    public String getCaption() {
        return caption;
    }
}
