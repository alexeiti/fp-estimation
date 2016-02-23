package com.ati.fpestimation.domain.estimation;

import com.ati.fpestimation.domain.kpi.AppType;
import com.ati.fpestimation.domain.kpi.EstimationFactor;

import java.util.ArrayList;
import java.util.List;

public class SystemEstimation {
    private String id;
    private AppType appType;
    private List<ModuleEstimation> estimationEntryList;

    private EstimationFactor estimationFactor;

    //TODO ATI update the factor with real value
    //TODO update factor on gui changes


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
        //TODO ATI check if factor is mandatory and always set
        if (estimationFactor != null)
            return estimationEntryList.stream().mapToDouble(ModuleEstimation::getFpCost).sum() * estimationFactor.getFactor() / 8;
        return 0;
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

    public EstimationFactor getEstimationFactor() {
        return estimationFactor;
    }

    public void setEstimationFactor(EstimationFactor estimationFactor) {
        this.estimationFactor = estimationFactor;
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
