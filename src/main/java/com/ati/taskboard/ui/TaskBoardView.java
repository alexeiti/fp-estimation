package com.ati.taskboard.ui;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.declarative.Design;

@DesignRoot
public class TaskBoardView extends Panel implements View {

    Label headerLabel;

    public TaskBoardView() {
        Design.read(this);
        setSizeFull();
        headerLabel.setValue("Op Taskboard");
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
