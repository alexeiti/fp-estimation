package com.ati.fpestimation.domain.estimation;

import com.ati.fpestimation.domain.kpi.AppStackType;

public class FpEstimation {

    private String id;
    private String name;
    private AppStackType stackType;

    public double getTotalEffortInPt() {
        return 300.5d;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AppStackType getStackType() {
        return stackType;
    }

    public void setStackType(AppStackType stackType) {
        this.stackType = stackType;
    }
}
