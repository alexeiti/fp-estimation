package com.ati.fpestimation.ui;

import com.vaadin.ui.TextField;

public class UiLabelHelper {

    public static final String formatFpEffort(int effort) {
        return String.format("Total: %d FP", effort);
    }

    public static final String formatPtEffort(double effort) {
        return String.format("Total: %.2f PT", effort);
    }


    public static boolean isValueSet(TextField tf) {
        return (tf.getValue() != null && tf.getValue().trim().length() != 0);
    }
}
