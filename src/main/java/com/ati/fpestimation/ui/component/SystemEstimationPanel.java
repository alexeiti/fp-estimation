package com.ati.fpestimation.ui.component;

import com.ati.fpestimation.ui.UiLabelHelper;
import com.ati.fpestimation.ui.com.ati.ui.callback.EstimationChangedHandler;
import com.ati.fpestimation.ui.com.ati.ui.callback.PtEstimationProvider;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;


public class SystemEstimationPanel extends Panel implements EstimationChangedHandler, PtEstimationProvider {

    final VerticalLayout contentLayout = new VerticalLayout();
    private EstimationChangedHandler estimationChangedHandler;
    private double totalPtEffort = 0;
    private List<PtEstimationProvider> estimationProviders = new ArrayList<>();
    private Label effortLabel;

    public SystemEstimationPanel(String caption, EstimationChangedHandler estimationChangedHandler) {
        this(caption);
        this.estimationChangedHandler = estimationChangedHandler;
    }

    public SystemEstimationPanel(String caption) {
        super(caption);
        this.setHeight(95f, Sizeable.Unit.PERCENTAGE);
        contentLayout.addComponent(buildTopRow());
        contentLayout.setWidth(95f, Unit.PERCENTAGE);
        contentLayout.setMargin(true);
        this.setContent(contentLayout);
        updateEffortValue();
    }

    protected AbstractLayout buildTopRow() {
        final HorizontalLayout topRow = new HorizontalLayout();
        final TextField txtModuleName = new TextField();
        topRow.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
        topRow.addComponent(new Label("Module:"));
        Button btnAddModule = new Button("Add module");
        btnAddModule.addClickListener(e -> {
            if (txtModuleName.getValue() != null && txtModuleName.getValue().trim().length() != 0) {
                ModuleEstimationPanel moduleEstimationProvider = new ModuleEstimationPanel(txtModuleName.getValue(), this);
                estimationProviders.add(moduleEstimationProvider);
                contentLayout.addComponent(moduleEstimationProvider);
                txtModuleName.clear();
            }
        });
        effortLabel = new Label();

        topRow.addComponents(txtModuleName, btnAddModule, effortLabel);
        return topRow;
    }

    private void updateEffortValue() {
        effortLabel.setValue(UiLabelHelper.formatPtEffort(totalPtEffort));
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