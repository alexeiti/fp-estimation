package com.ati.fpestimation.ui.main;

import com.ati.fpestimation.ui.component.SystemEstimationPanel;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;
import java.util.Arrays;
import java.util.Collection;


@Theme("mytheme")
@Widgetset("com.ati.vaadin.ui.main.FpEstimationWidgetSet")
public class EstimationEdit extends UI {

    final VerticalLayout verticalLayout = new VerticalLayout();
    ComboBox systemComboBox;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        verticalLayout.addComponent(buildTopControlRow());
        verticalLayout.setWidth(60,Unit.PERCENTAGE);

        verticalLayout.setMargin(true);
        verticalLayout.setSpacing(true);

        setContent(verticalLayout);
    }

    private AbstractLayout buildTopControlRow() {
        HorizontalLayout topRow = new HorizontalLayout();
        topRow.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
        topRow.addComponents(new Label("System:"),buildSystemComboBox(Arrays.asList(new String[]{"Tibco", "OfflineOE"})));
        Button button = new Button("Add System");
        button.addClickListener(e -> {
            verticalLayout.addComponent(new SystemEstimationPanel(systemComboBox.getValue().toString()));

        });

        topRow.addComponents(button);
        return topRow;
    }

    private Field<?> buildSystemComboBox(Collection<?> items) {
        systemComboBox = new ComboBox();

        systemComboBox.setWidth(200, Unit.PIXELS);
        systemComboBox.setNullSelectionAllowed(false);
        IndexedContainer container = new IndexedContainer(items);
        systemComboBox.setContainerDataSource(container);
        systemComboBox.setRequired(true);
        systemComboBox.setRequiredError("Pick a system");
        return systemComboBox;
    }

    @WebServlet(urlPatterns = "/*", name = "FpEstimationServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = EstimationEdit.class, productionMode = false)
    public static class FpEstimationServlet extends VaadinServlet {
    }
}
