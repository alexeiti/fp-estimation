package com.ati.fpestimation.data.impl;

import com.ati.fpestimation.domain.kpi.AppStackType;
import com.ati.fpestimation.domain.kpi.AppType;
import com.ati.fpestimation.domain.kpi.EstimationFactor;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonFileLoaderTest {

    @Test
    public void checkStacksConfig() throws IOException {
        List<AppStackType> appStackTypeList = JsonFileLoader.loadAllStackTypes();
        assertThat(appStackTypeList).isNotNull();
        assertThat(appStackTypeList.size()).isEqualTo(2);
        assertThat(appStackTypeList.get(0)).isInstanceOf(AppStackType.class);
    }


    @Test
    public void checkAppsConfig() throws IOException {
        List<AppType> appTypeList = JsonFileLoader.loadAllAppTypes();
        assertThat(appTypeList).isNotNull();
        assertThat(appTypeList.size()).isEqualTo(2);
        assertThat(appTypeList.get(0)).isInstanceOf(AppType.class);
    }

    @Test
    public void checkFactorConfig() throws IOException {
        List<EstimationFactor> appTypeList = JsonFileLoader.loadAllEstimationFactors();
        assertThat(appTypeList).isNotNull();
        assertThat(appTypeList.size()).isEqualTo(2);
        assertThat(appTypeList.get(0)).isInstanceOf(EstimationFactor.class);
    }


}
