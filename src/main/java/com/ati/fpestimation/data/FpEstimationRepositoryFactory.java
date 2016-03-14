package com.ati.fpestimation.data;

import com.ati.fpestimation.data.impl.DummyFpEstimationRepository;
import com.ati.fpestimation.data.impl.FileAppStackRepository;

public class FpEstimationRepositoryFactory {

    private static AppStackRepository getAppStackRepository = new FileAppStackRepository();
    private static FpEstimationRepository fpEstimationRepository = new DummyFpEstimationRepository();

    public static final AppStackRepository getAppStackRepository() {
        return getAppStackRepository;
    }

    public static final FpEstimationRepository getFpEstimationRepository() {
        return fpEstimationRepository;
    }
}
