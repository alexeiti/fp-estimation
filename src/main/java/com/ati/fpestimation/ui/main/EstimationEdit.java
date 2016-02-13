package com.ati.fpestimation.ui.main;

import com.ati.fpestimation.ui.component.SystemEstimationPanel;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.servlet.annotation.WebServlet;


@Theme("mytheme")
@Widgetset("com.ati.vaadin.ui.main.FpEstimationWidgetSet")
public class EstimationEdit extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Add System");
        button.addClickListener(e -> {
            layout.addComponent(new SystemEstimationPanel("SEA024 Tibco"));
        });

        layout.addComponents(name, button);
        layout.addComponents(name, button);
        layout.setMargin(true);
        layout.setSpacing(true);

        setContent(layout);
    }


    @WebServlet(urlPatterns = "/*", name = "FpEstimationServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = EstimationEdit.class, productionMode = false)
    public static class FpEstimationServlet extends VaadinServlet {
    }
}
