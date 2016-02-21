package com.ati.fpestimation.domain.estimation;

import com.ati.fpestimation.domain.kpi.AppType;

import java.util.ArrayList;
import java.util.List;

public class SystemEstimation {
    private String id;
    private AppType appType;
    private List<ModuleEstimation> estimationEntryList;

    //TODO ATI update the factor with real value
    //TODO update factor on gui changes
    private double factor = 4.9d;


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
        return estimationEntryList.stream().mapToDouble(ModuleEstimation::getFpCost).sum() * factor / 8;
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

    public List<ModuleEstimation> getEstimationEntryList() {
        return estimationEntryList;
    }

    @Override
    public String toString() {
        return "SystemEstimation{" +
                "id='" + id + '\'' +
                ", appType=" + appType +
                ", estimationEntryList=" + estimationEntryList +
                '}';
    }
}
