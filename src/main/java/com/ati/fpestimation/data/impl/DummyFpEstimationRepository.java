package com.ati.fpestimation.data.impl;

import com.ati.fpestimation.data.FpEstimationRepository;
import com.ati.fpestimation.domain.estimation.FpEstimation;
import com.ati.fpestimation.domain.estimation.ModuleEstimation;
import com.ati.fpestimation.domain.estimation.SystemEstimation;

public class DummyFpEstimationRepository implements FpEstimationRepository {
    @Override
    public FpEstimation find(String id) {
        FpEstimation estimation = new FpEstimation();
        estimation.setName("Loyalty points");

        estimation.setStackType(new FileAppStackRepository().getAllStackTypes().get(0));
        estimation.addSystemEstimation(new SystemEstimation(new FileAppStackRepository().getAllAppsForStack(1).get(0)));
        SystemEstimation systemEstimation = new SystemEstimation(new FileAppStackRepository().getAllAppsForStack(1).get(0));
        ModuleEstimation moduleEstimation1 = systemEstimation.addModuleEstimation("some module1");
        moduleEstimation1.addEstimationEntry("Create process", false);
        moduleEstimation1.addEstimationEntry("Delete ws", false);
        systemEstimation.addModuleEstimation("some module2");
        estimation.addSystemEstimation(systemEstimation);
        estimation.addSystemEstimation(new SystemEstimation(new FileAppStackRepository().getAllAppsForStack(1).get(0)));

        return estimation;
    }
}
