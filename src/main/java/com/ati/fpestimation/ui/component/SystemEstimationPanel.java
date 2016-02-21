package com.ati.fpestimation.ui.component;

import com.ati.fpestimation.domain.estimation.ModuleEstimation;
import com.ati.fpestimation.domain.estimation.SystemEstimation;
import com.ati.fpestimation.domain.kpi.EstimationFactor;
import com.ati.fpestimation.ui.UiLabelHelper;
import com.ati.fpestimation.ui.callback.ModuleEstimationChangedHandler;
import com.ati.fpestimation.ui.callback.SystemEstimationChangedHandler;
import com.ati.fpestimation.ui.main.EstimationEditView;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.Design;

import java.util.Collection;
import java.util.stream.Collectors;


@DesignRoot
public class SystemEstimationPanel extends Panel implements ModuleEstimationChangedHandler {

    private VerticalLayout componentContainer;
    private SystemEstimationChangedHandler systemEstimationChangedHandler;
    // private List<PtEstimationProvider> estimationProviders = new ArrayList<>();
    private Label lblTotalEffort;
    private Button btnAddModule;
    private TextField txtModuleName;
    private SystemEstimation systemEstimation;
    private ComboBox txtFactor;

    public SystemEstimationPanel(SystemEstimation systemEstimation, SystemEstimationChangedHandler systemEstimationChangedHandler) {
        super(systemEstimation.getAppType().getName());
        this.systemEstimation = systemEstimation;
        this.systemEstimationChangedHandler = systemEstimationChangedHandler;
        Design.read(this);
        initTopRow();
        preloadEstimationData();

    }

    private void preloadEstimationData() {
        systemEstimation.getEstimationEntryList().stream().forEach(this::addModuleEstimationPanel);
        updateEffortValue();
    }

    protected void initTopRow() {
        btnAddModule.addClickListener(e -> addModuleEventHandler());
        //FIXME ATI the text for factor should come from parent and not from self caption
        buildFactorComboBox(EstimationEditView.getAppStackProvider()
                .getFactorForApp(this.getCaption()).stream().map(EstimationFactor::getName).collect(Collectors.toList()));
    }

    private void addModuleEventHandler() {
        if (txtModuleName.getValue() != null && txtModuleName.getValue().trim().length() != 0) {
            ModuleEstimation moduleEstimation = systemEstimation.addModuleEstimation(txtModuleName.getValue());
            addModuleEstimationPanel(moduleEstimation);
            txtModuleName.clear();
        }
    }

    private void addModuleEstimationPanel(ModuleEstimation moduleEstimation) {
        ModuleEstimationPanel moduleEstimationPanel = new ModuleEstimationPanel(moduleEstimation, this);
        componentContainer.addComponent(moduleEstimationPanel);
        componentContainer.setComponentAlignment(moduleEstimationPanel, Alignment.TOP_CENTER);
    }

    private void buildFactorComboBox(Collection<?> items) {
        IndexedContainer container = new IndexedContainer(items);
        txtFactor.setContainerDataSource(container);
        txtFactor.setRequired(true);
        txtFactor.setRequiredError("Pick a system");
    }

    private void updateEffortValue() {
        lblTotalEffort.setValue(UiLabelHelper.formatPtEffort(systemEstimation.getTotalEffortInPt()));
    }


    @Override
    public void estimationChanged(ModuleEstimation updatedEstimation) {

        if (systemEstimationChangedHandler != null) {
            systemEstimationChangedHandler.estimationChanged(systemEstimation);
        }
        updateEffortValue();
    }
}