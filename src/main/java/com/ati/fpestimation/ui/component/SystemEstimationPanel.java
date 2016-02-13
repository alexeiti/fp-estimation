package com.ati.fpestimation.ui.component;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;


public class SystemEstimationPanel extends Panel {

    final VerticalLayout contentLayout = new VerticalLayout();

    public SystemEstimationPanel(String caption) {
        super(caption);
        this.setHeight(95f, Sizeable.Unit.PERCENTAGE);
        contentLayout.addComponent(buildTopRow());
        contentLayout.setWidth(95f, Unit.PERCENTAGE);
        contentLayout.setMargin(true);
        this.setContent(contentLayout);
    }

    protected AbstractLayout buildTopRow() {
        final HorizontalLayout topRow = new HorizontalLayout();
        final TextField txtModuleName = new TextField();
        topRow.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
        topRow.addComponent(new Label("Module:"));
        Button btnAddModule = new Button("Add module");
        btnAddModule.addClickListener(e -> {
            if (txtModuleName.getValue() != null && txtModuleName.getValue().trim().length() != 0) {
                contentLayout.addComponent(new ModuleEstimationPanel(txtModuleName.getValue()));
                txtModuleName.clear();
            }
        });
        topRow.addComponent(txtModuleName);
        topRow.addComponent(btnAddModule);
        return topRow;
    }
}