package com.ati.fpestimation.domain.estimation;

import com.ati.fpestimation.domain.kpi.AppStackType;
import com.ati.fpestimation.domain.kpi.AppType;
import com.ati.fpestimation.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class FpEstimation {

    private String id;
    private String name;
    private AppStackType stackType;
    private List<SystemEstimation> systemEstimationList;

    public FpEstimation() {
        systemEstimationList = new ArrayList<>();
    }

    public double getTotalEffortInPt() {
        return systemEstimationList.stream().mapToDouble(systemEstimation -> systemEstimation.getTotalEffortInPt()).sum();
    }

    public SystemEstimation addNewSystemEstimation(AppType appType) throws ValidationException {
        //TODO validate if such appType is already existing in the list
        SystemEstimation systemEstimation = new SystemEstimation(appType);
        if (systemEstimationList.stream().map(SystemEstimation::getAppType).anyMatch(appType1 -> appType.equals(appType1))) {
            throw new ValidationException("Estimation for " + appType.getName() + " already exists");
        }
        systemEstimationList.add(systemEstimation);
        return systemEstimation;
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

    public List<SystemEstimation> getSystemEstimationList() {
        return systemEstimationList;
    }

    public void addSystemEstimation(SystemEstimation systemEstimation) {
        this.systemEstimationList.add(systemEstimation);
    }
}
