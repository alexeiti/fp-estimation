package com.ati.fpestimation.domain.kpi;

import java.util.List;

public class AppType {
    private String name;
    private int stackTypeId;
    private List<Integer> estimationFactors;

    public AppType() {
    }

    public AppType(String name, int stackTypeId) {
        this.name = name;
        this.stackTypeId = stackTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStackTypeId() {
        return stackTypeId;
    }

    public void setStackTypeId(int stackTypeId) {
        this.stackTypeId = stackTypeId;
    }

    public List<Integer> getEstimationFactors() {
        return estimationFactors;
    }

    public void setEstimationFactors(List<Integer> estimationFactors) {
        this.estimationFactors = estimationFactors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppType appType = (AppType) o;

        if (stackTypeId != appType.stackTypeId) return false;
        return !(name != null ? !name.equals(appType.name) : appType.name != null);

    }


    @Override
    public String toString() {
        return "AppType{" +
                "name='" + name + '\'' +
                ", stackTypeId=" + stackTypeId +
                ", estimationFactors=" + estimationFactors +
                '}';
    }
}
