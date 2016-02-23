package com.ati.fpestimation.ui.component;

import com.ati.fpestimation.domain.estimation.ModuleEstimation;
import com.ati.fpestimation.domain.estimation.SystemEstimation;
import com.ati.fpestimation.domain.kpi.AppStackType;
import com.ati.fpestimation.domain.kpi.EstimationFactor;
import com.ati.fpestimation.ui.UiLabelHelper;
import com.ati.fpestimation.ui.callback.ModuleEstimationChangedHandler;
import com.ati.fpestimation.ui.callback.SystemEstimationChangedHandler;
import com.ati.fpestimation.ui.main.EstimationEditView;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Collection;


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
    //TODO check if stack is needed here
    private AppStackType appStackType;

    public SystemEstimationPanel(SystemEstimation systemEstimation, SystemEstimationChangedHandler systemEstimationChangedHandler, AppStackType appStack) {
        super(systemEstimation.getAppType().getName());
        this.systemEstimation = systemEstimation;
        this.systemEstimationChangedHandler = systemEstimationChangedHandler;
        this.appStackType = appStack;
        Design.read(this);
        initTopRow();
        preloadEstimationData();
        this.addStyleName(ValoTheme.PANEL_WELL);

    }

    private void preloadEstimationData() {
        systemEstimation.getEstimationEntryList().stream().forEach(this::addModuleEstimationPanel);
        updateEffortValue();
    }

    protected void initTopRow() {
        btnAddModule.addClickListener(e -> addModuleEventHandler());
        buildFactorComboBox(EstimationEditView.getAppStackProvider()
                .getFactorForApp(systemEstimation.getAppType()));
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
        BeanItemContainer container = new BeanItemContainer(EstimationFactor.class, items);
        txtFactor.setContainerDataSource(container);
        txtFactor.setNullSelectionAllowed(false);
        txtFactor.setRequired(true);
        txtFactor.setRequiredError("Pick a system");
        txtFactor.setItemCaptionPropertyId("name");
        if (container.firstItemId() != null && systemEstimation.getEstimationFactor() == null) {
            txtFactor.select(container.firstItemId());
            systemEstimation.setEstimationFactor((EstimationFactor) txtFactor.getValue());
            estimationChanged();
        }
        txtFactor.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                systemEstimation.setEstimationFactor((EstimationFactor) txtFactor.getValue());
                estimationChanged();
            }
        });
    }

    private void updateEffortValue() {
        lblTotalEffort.setValue(UiLabelHelper.formatPtEffort(systemEstimation.getTotalEffortInPt()));
    }


    @Override
    public void estimationChanged() {

        if (systemEstimationChangedHandler != null) {
            systemEstimationChangedHandler.estimationChanged(systemEstimation);
        }
        updateEffortValue();
    }
}