package com.ati.fpestimation.ui;

/**
 * Created by alex on 14/02/16.
 */
public class UiLabelHelper {

    public static final String formatFpEffort(int effort) {
        return String.format("Total: %d FP", effort);
    }

    public static final String formatPtEffort(double effort) {
        return String.format("Total: %.2f PT", effort);
    }


}
