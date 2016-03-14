package com.ati.fpestimation.ui.component;

import com.ati.fpestimation.data.FpEstimationRepositoryFactory;
import com.ati.fpestimation.domain.estimation.ModuleEstimation;
import com.ati.fpestimation.domain.estimation.SystemEstimation;
import com.ati.fpestimation.domain.kpi.AppStackType;
import com.ati.fpestimation.domain.kpi.EstimationFactor;
import com.ati.fpestimation.ui.UiLabelHelper;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.Design;

import java.util.Collection;


@DesignRoot
public class SystemEstimationPanel extends Panel {

    private VerticalLayout componentContainer;
    private SystemEstimationChangedListener systemEstimationChangedListener;
    // private List<PtEstimationProvider> estimationProviders = new ArrayList<>();
    private Label lblTotalEffort;
    private Button btnAddModule;
    private TextField txtModuleName;
    private SystemEstimation systemEstimation;
    private ComboBox txtFactor;
    //TODO check if stack is needed here
    private AppStackType appStackType;
    private CssLayout moduleNameGroup;

    public SystemEstimationPanel(SystemEstimation systemEstimation, AppStackType appStack) {
        super(systemEstimation.getAppType().getName());
        this.systemEstimation = systemEstimation;
        this.appStackType = appStack;
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
        moduleNameGroup.addStyleName("v-component-group");
        txtModuleName.setInputPrompt("Module name:");
        buildFactorComboBox(FpEstimationRepositoryFactory.getAppStackRepository()
                .getFactorForApp(systemEstimation.getAppType()));
    }

    private void addModuleEventHandler() {
        if (UiLabelHelper.isValueSet(txtModuleName)) {
            ModuleEstimation moduleEstimation = systemEstimation.addModuleEstimation(txtModuleName.getValue());
            addModuleEstimationPanel(moduleEstimation);
            txtModuleName.clear();
        }
    }

    private void addModuleEstimationPanel(ModuleEstimation moduleEstimation) {
        ModuleEstimationPanel moduleEstimationPanel = new ModuleEstimationPanel(moduleEstimation);
        moduleEstimationPanel.addModuleEstimationChangedListener(() -> estimationChanged());
        componentContainer.addComponent(moduleEstimationPanel);
        componentContainer.setComponentAlignment(moduleEstimationPanel, Alignment.TOP_CENTER);
    }

    private void buildFactorComboBox(Collection<?> items) {
        BeanItemContainer container = new BeanItemContainer(EstimationFactor.class, items);
        txtFactor.setContainerDataSource(container);
        txtFactor.setNullSelectionAllowed(false);
        txtFactor.setRequired(true);
        txtFactor.setRequiredError("Pick a system");
        txtFactor.setItemCaptionPropertyId("uiName");

        if (container.firstItemId() != null && systemEstimation.getEstimationFactor() == null) {
            txtFactor.select(container.firstItemId());
            systemEstimation.setEstimationFactor((EstimationFactor) txtFactor.getValue());
            estimationChanged();
        }
        txtFactor.addValueChangeListener(event -> {
            systemEstimation.setEstimationFactor((EstimationFactor) txtFactor.getValue());
            estimationChanged();
        });
    }

    private void updateEffortValue() {
        lblTotalEffort.setValue(UiLabelHelper.formatPtEffort(systemEstimation.getTotalEffortInPt()));
    }

    public void estimationChanged() {
        if (systemEstimationChangedListener != null) {
            systemEstimationChangedListener.estimationChanged(systemEstimation);
        }
        updateEffortValue();
    }


    public interface SystemEstimationChangedListener {
        void estimationChanged(SystemEstimation updatedEstimation);

    }

    public void addSystemEstimationChangedHandler(SystemEstimationChangedListener systemEstimationChangedHandler) {
        this.systemEstimationChangedListener = systemEstimationChangedHandler;
    }

}