package com.ati.fpestimation.data.impl;

import com.ati.common.data.impl.JsonFileLoader;
import com.ati.fpestimation.data.AppStackRepository;
import com.ati.fpestimation.domain.kpi.AppStackType;
import com.ati.fpestimation.domain.kpi.AppType;
import com.ati.fpestimation.domain.kpi.EstimationFactor;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FileAppStackRepository implements AppStackRepository {

    private List<AppStackType> appStackTypes;
    private List<AppType> appTypes;
    private List<EstimationFactor> estimationFactors;

    public FileAppStackRepository() {
        try {
            this.appTypes = JsonFileLoader.loadAllAppTypes();
            this.appStackTypes = JsonFileLoader.loadAllStackTypes();
            this.estimationFactors = JsonFileLoader.loadAllEstimationFactors();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<AppStackType> getAllStackTypes() {
        return appStackTypes;
    }

    @Override
    public List<AppType> getAllAppsForStack(int stackTypeId) {
        return appTypes.stream().filter(app -> app.getStackTypeId() == stackTypeId).collect(Collectors.toList());
    }

    @Override
    public Collection<EstimationFactor> getFactorForApp(AppType appType) {
        List<Integer> factorsForApp = appType.getEstimationFactors();

        return estimationFactors.stream().filter(estimationFactor -> factorsForApp.contains(new Integer(estimationFactor.getId()))).collect(Collectors.toList());
    }
}
