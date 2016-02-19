package com.ati.fpestimation.ui.main;

import com.ati.fpestimation.data.AppStackRepository;
import com.ati.fpestimation.data.FunctionRepository;
import com.ati.fpestimation.data.impl.FileAppStackRepository;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Theme("mytheme")
@DesignRoot
@Widgetset("com.ati.vaadin.ui.main.FpEstimationWidgetSet")
public class EstimationEditView extends UI implements EstimationChangedHandler {

    private ComboBox txtSystemType;
    private Label lblEfortTotal;
    private List<PtEstimationProvider> estimationProviders = new ArrayList<>();
    private double totalPtEffort = 0;
    private Button btnAddSystem;
    private Layout systemsContainer, addSystemContainer, contentContainer;
    private VerticalLayout mainContainer;

    private FunctionRepository estimationFunctionRepository;
    private AppStackRepository appStackRepository;

    public EstimationEditView() throws IOException {
        Design.read(this);
        estimationFunctionRepository = new FunctionRepository();
        appStackRepository = new FileAppStackRepository();
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        buildTopControlRow();
        updateEffortValue();
        mainContainer.setComponentAlignment(contentContainer, Alignment.MIDDLE_CENTER);

    }


    private void buildTopControlRow() {
        //FIXME ATI use correct stack and not hardcoded
        buildSystemComboBox(EstimationEditView.getAppStackProvider().getAllAppsForStack(1).stream().map(appType -> appType.getName()).collect(Collectors.toList()));
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
    @VaadinServletConfiguration(ui = EstimationEditView.class, productionMode = false)
    public static class FpEstimationServlet extends VaadinServlet {

    }

    public static FunctionRepository getFunctionProvider() {
        return ((EstimationEditView) getCurrent()).estimationFunctionRepository;
    }

    public static AppStackRepository getAppStackProvider() {
        return ((EstimationEditView) getCurrent()).appStackRepository;
    }
}
