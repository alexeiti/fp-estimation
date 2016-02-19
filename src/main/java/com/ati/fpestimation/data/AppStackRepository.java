package com.ati.fpestimation.data;

import com.ati.fpestimation.domain.AppStackType;
import com.ati.fpestimation.domain.AppType;
import com.ati.fpestimation.domain.EstimationFactor;

import java.util.Collection;
import java.util.List;

public interface AppStackRepository {

    List<AppStackType> getAllStackTypes();

    List<AppType> getAllAppsForStack(int stackId);

    Collection<EstimationFactor> getFactorForApp(String appName);
    
}
