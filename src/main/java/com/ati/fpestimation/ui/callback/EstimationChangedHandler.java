package com.ati.fpestimation.ui.callback;

import com.ati.fpestimation.domain.estimation.SystemEstimation;

public interface EstimationChangedHandler {
    void systemEstimationChanged(SystemEstimation updatedEstimation);
    //TODO ATI add method for delete of the estimation
}
