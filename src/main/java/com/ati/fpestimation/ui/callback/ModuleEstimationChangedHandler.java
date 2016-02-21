package com.ati.fpestimation.ui.callback;

import com.ati.fpestimation.domain.estimation.ModuleEstimation;

public interface ModuleEstimationChangedHandler {
    void estimationChanged(ModuleEstimation updatedEstimation);
    //TODO ATI add method for delete of the estimation
}
