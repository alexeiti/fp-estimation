package com.ati.fpestimation.domain.estimation;

import java.util.ArrayList;
import java.util.List;

public class ModuleEstimation {
    private List<EstimationEntry> estimationEntryList;
    private String name;

    public ModuleEstimation(String name) {
        this.name = name;
        this.estimationEntryList = new ArrayList<>();
    }

    public double getFpCost() {
        //TODO ATI implement properly
        return 34.4d;
    }

    public EstimationEntry addEstimationEntry() {
        EstimationEntry estimationEntry = new EstimationEntry();
        estimationEntryList.add(estimationEntry);
        return estimationEntry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EstimationEntry> getEstimationEntryList() {
        return estimationEntryList;
    }

    public void setEstimationEntryList(List<EstimationEntry> estimationEntryList) {
        this.estimationEntryList = estimationEntryList;
    }
}