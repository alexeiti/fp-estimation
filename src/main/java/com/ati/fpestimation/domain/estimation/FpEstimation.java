package com.ati.fpestimation.domain.estimation;

import com.ati.fpestimation.domain.kpi.AppStackType;

import java.util.ArrayList;
import java.util.List;

public class FpEstimation {

    private String id;
    private String name;
    private AppStackType stackType;
    private List<SystemEstimationGroup> systemEstimationList;

    public FpEstimation() {
        systemEstimationList = new ArrayList<>();
    }

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

    public List<SystemEstimationGroup> getSystemEstimationList() {
        return systemEstimationList;
    }

    public void addSystemEstimation(SystemEstimationGroup systemEstimation) {
        this.systemEstimationList.add(systemEstimation);
    }
}
