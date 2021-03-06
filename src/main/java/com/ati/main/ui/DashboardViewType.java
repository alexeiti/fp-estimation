package com.ati.main.ui;

import com.ati.booklibrary.ui.BookLibraryView;
import com.ati.fpestimation.ui.main.EstimationEditView;
import com.ati.taskboard.ui.TaskBoardView;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

public enum DashboardViewType {
    BOOK_LIBRARY("bookLibrary", BookLibraryView.class, FontAwesome.BAR_CHART_O, false),
    TASKBOARD("taskBoard", TaskBoardView.class, FontAwesome.BAR_CHART_O, false),
    ESTIMATIONS("estimation", EstimationEditView.class, FontAwesome.HOME, true);

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    private DashboardViewType(final String viewName,
                              final Class<? extends View> viewClass, final Resource icon,
                              final boolean stateful) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
    }

    public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public static DashboardViewType getByViewName(final String viewName) {
        DashboardViewType result = null;
        for (DashboardViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }

}
