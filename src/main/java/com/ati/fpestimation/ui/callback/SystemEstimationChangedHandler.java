package com.ati.fpestimation.ui.callback;

import com.ati.fpestimation.domain.estimation.SystemEstimation;

public interface SystemEstimationChangedHandler {
    void estimationChanged(SystemEstimation updatedEstimation);
    //TODO ATI add method for delete of the estimation
}
