package com.ati.fpestimation.ui.component;

import com.ati.fpestimation.ui.UiLabelHelper;
import com.ati.fpestimation.ui.callback.EstimationChangedHandler;
import com.ati.fpestimation.ui.callback.PtEstimationProvider;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.Design;

import java.util.ArrayList;
import java.util.List;


@DesignRoot
public class SystemEstimationPanel extends Panel implements EstimationChangedHandler, PtEstimationProvider {


    private VerticalLayout componentContainer;
    private EstimationChangedHandler estimationChangedHandler;
    private double totalPtEffort = 0;
    private List<PtEstimationProvider> estimationProviders = new ArrayList<>();
    private Label lblTotalEffort;
    private Button btnAddModule;
    private TextField txtModuleName;

    public SystemEstimationPanel(String caption, EstimationChangedHandler estimationChangedHandler) {
        this(caption);
        this.estimationChangedHandler = estimationChangedHandler;
    }

    public SystemEstimationPanel(String caption) {
        super(caption);
        Design.read(this);
        buildTopRow();
        updateEffortValue();
    }

    protected void buildTopRow() {
        btnAddModule.addClickListener(e -> {
            System.out.println("adding");
            if (txtModuleName.getValue() != null && txtModuleName.getValue().trim().length() != 0) {
                ModuleEstimationPanel moduleEstimationPanel = new ModuleEstimationPanel(txtModuleName.getValue(), this);
                estimationProviders.add(moduleEstimationPanel);
                componentContainer.addComponent(moduleEstimationPanel);
                componentContainer.setComponentAlignment(moduleEstimationPanel,Alignment.TOP_CENTER);
                txtModuleName.clear();
            }
        });
    }

    private void updateEffortValue() {
        lblTotalEffort.setValue(UiLabelHelper.formatPtEffort(totalPtEffort));
    }

    @Override
    public void moduleEstimationChanged(double newValue) {
        totalPtEffort = estimationProviders.stream().mapToDouble(PtEstimationProvider::getPtEffort).sum();
        System.out.println("Wathcer" + estimationChangedHandler);
        if (estimationChangedHandler != null) {
            estimationChangedHandler.moduleEstimationChanged(totalPtEffort);
        }
        updateEffortValue();
    }

    @Override
    public double getPtEffort() {
        return totalPtEffort;
    }
}