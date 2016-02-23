package com.ati.fpestimation.data;

import com.ati.fpestimation.domain.kpi.AppStackType;
import com.ati.fpestimation.domain.kpi.AppType;
import com.ati.fpestimation.domain.kpi.EstimationFactor;

import java.util.Collection;
import java.util.List;

public interface AppStackRepository {

    List<AppStackType> getAllStackTypes();

    List<AppType> getAllAppsForStack(int stackId);

    Collection<EstimationFactor> getFactorForApp(AppType appType);

}
