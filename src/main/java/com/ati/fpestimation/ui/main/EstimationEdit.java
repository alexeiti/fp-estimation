package com.ati.fpestimation.ui.main;

import com.ati.fpestimation.ui.UiLabelHelper;
import com.ati.fpestimation.ui.com.ati.ui.callback.EstimationChangedHandler;
import com.ati.fpestimation.ui.com.ati.ui.callback.PtEstimationProvider;
import com.ati.fpestimation.ui.component.SystemEstimationPanel;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.Design;

import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@Theme("mytheme")
@DesignRoot
@Widgetset("com.ati.vaadin.ui.main.FpEstimationWidgetSet")
public class EstimationEdit extends UI implements EstimationChangedHandler {

    private ComboBox txtSystemType;
    private Label lblEfortTotal;
    private List<PtEstimationProvider> estimationProviders = new ArrayList<>();
    private double totalPtEffort = 0;
    private Button btnAddSystem;
    private Layout systemsContainer;

    public EstimationEdit() {
        Design.read(this);
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        buildTopControlRow();
        updateEffortValue();

    }


    private void buildTopControlRow() {
        buildSystemComboBox(Arrays.asList(new String[]{"Tibco", "OfflineOE"}));
        btnAddSystem.addClickListener(e -> {
            SystemEstimationPanel systemEstimationPanel = new SystemEstimationPanel(txtSystemType.getValue().toString(), this);
            estimationProviders.add(systemEstimationPanel);
            systemsContainer.addComponent(systemEstimationPanel);

        });

    }

    private void buildSystemComboBox(Collection<?> items) {

        IndexedContainer container = new IndexedContainer(items);
        txtSystemType.setContainerDataSource(container);
        txtSystemType.setRequired(true);
        txtSystemType.setRequiredError("Pick a system");
    }

    @Override
    public void moduleEstimationChanged(double newValue) {
        totalPtEffort = estimationProviders.stream().mapToDouble(PtEstimationProvider::getPtEffort).sum();
        updateEffortValue();
    }

    private void updateEffortValue() {
        lblEfortTotal.setValue(UiLabelHelper.formatPtEffort(totalPtEffort));
    }

    @WebServlet(urlPatterns = "/*", name = "FpEstimationServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = EstimationEdit.class, productionMode = false)
    public static class FpEstimationServlet extends VaadinServlet {

    }
}
