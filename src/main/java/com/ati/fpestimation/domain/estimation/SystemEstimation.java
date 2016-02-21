package com.ati.fpestimation.domain.estimation;

import com.ati.fpestimation.domain.kpi.AppType;

import java.util.ArrayList;
import java.util.List;

public class SystemEstimation {
    private String id;
    private AppType appType;
    private List<ModuleEstimation> estimationEntryList;

    public SystemEstimation(AppType appType) {
        estimationEntryList = new ArrayList<>();
        this.appType = appType;
    }

    public ModuleEstimation addModuleEstimation(String name) {
        ModuleEstimation estimationEntry = new ModuleEstimation(name);
        estimationEntryList.add(estimationEntry);
        return estimationEntry;
    }

    public double getTotalEffortInPt() {
        //TODO implement me properly. Add the factor
        return estimationEntryList.stream().mapToDouble(value -> value.getFpCost()).sum();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AppType getAppType() {
        return appType;
    }

    public void setAppType(AppType appType) {
        this.appType = appType;
    }
}
