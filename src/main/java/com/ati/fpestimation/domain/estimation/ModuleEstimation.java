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
        //TODO ATI implement properly.
        return estimationEntryList.stream().mapToDouble(EstimationEntry::getCost).sum();
    }

    public EstimationEntry addEstimationEntry(String name, boolean isManual) {
        EstimationEntry estimationEntry = new EstimationEntry(name, isManual);
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


    @Override
    public String toString() {
        return "ModuleEstimation{" +
                "estimationEntryList=" + estimationEntryList +
                ", name='" + name + '\'' +
                '}';
    }
}
