package com.ati.fpestimation.data.impl;

import com.ati.fpestimation.data.FpEstimationRepository;
import com.ati.fpestimation.domain.estimation.FpEstimation;

import java.io.IOException;

public class DummyFpEstimationRepository implements FpEstimationRepository {
    @Override
    public FpEstimation find(String id) {
        FpEstimation estimation = new FpEstimation();
        estimation.setName("Loyalty points");
        try {
            estimation.setStackType(new FileAppStackRepository().getAllStackTypes().get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return estimation;
    }
}
