package com.ati.main.ui;

import com.ati.booklibrary.ui.BookLibraryView;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;

/**
 * Created by alex on 13/03/16.
 */
public class DashboardUiNavigator extends Navigator {
    public DashboardUiNavigator(final ComponentContainer container) {
        super(UI.getCurrent(), container);

        this.addView("common", BookLibraryView.class);
    }

}
