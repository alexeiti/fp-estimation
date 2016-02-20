package com.ati.fpestimation.data;

import com.ati.fpestimation.domain.estimation.FpEstimation;

public interface FpEstimationRepository {

    FpEstimation find(String id);
}
