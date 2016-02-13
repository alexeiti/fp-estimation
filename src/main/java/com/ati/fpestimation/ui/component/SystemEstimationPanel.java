package com.ati.fpestimation.ui.component;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;


public class SystemEstimationPanel extends Panel {

    public SystemEstimationPanel(String caption) {
        super(caption);
        this.setHeight(100.0f, Sizeable.Unit.PERCENTAGE);

        final VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setWidth(85f, Unit.PERCENTAGE);
        contentLayout.setMargin(true);


        Button btnAddModule = new Button("Add module");
        btnAddModule.addClickListener(e -> {
            contentLayout.addComponent(new ModuleEstimationPanel("order.activation"));
        });
        contentLayout.addComponent(btnAddModule);
        this.setContent(contentLayout);
    }
}