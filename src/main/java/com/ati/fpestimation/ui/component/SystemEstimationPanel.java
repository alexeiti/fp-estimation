package com.ati.fpestimation.ui.component;

import com.ati.fpestimation.domain.estimation.ModuleEstimation;
import com.ati.fpestimation.domain.estimation.SystemEstimation;
import com.ati.fpestimation.ui.UiLabelHelper;
import com.ati.fpestimation.ui.callback.EstimationChangedHandler;
import com.ati.fpestimation.ui.main.EstimationEditView;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.Design;

import java.util.Collection;
import java.util.stream.Collectors;


@DesignRoot
public class SystemEstimationPanel extends Panel implements EstimationChangedHandler {

    private VerticalLayout componentContainer;
    private EstimationChangedHandler estimationChangedHandler;
    // private List<PtEstimationProvider> estimationProviders = new ArrayList<>();
    private Label lblTotalEffort;
    private Button btnAddModule;
    private TextField txtModuleName;
    private SystemEstimation systemEstimation;
    private ComboBox txtFactor;

    public SystemEstimationPanel(SystemEstimation systemEstimation, EstimationChangedHandler estimationChangedHandler) {
        super(systemEstimation.getAppType().getName());
        this.systemEstimation = systemEstimation;
        Design.read(this);
        buildTopRow();

        updateEffortValue();
    }

    protected void buildTopRow() {
        btnAddModule.addClickListener(e -> {
            System.out.println("adding");
            if (txtModuleName.getValue() != null && txtModuleName.getValue().trim().length() != 0) {
                ModuleEstimation moduleEstimation = systemEstimation.addModuleEstimation(txtModuleName.getValue());
                ModuleEstimationPanel moduleEstimationPanel = new ModuleEstimationPanel(moduleEstimation, this);
                componentContainer.addComponent(moduleEstimationPanel);
                componentContainer.setComponentAlignment(moduleEstimationPanel, Alignment.TOP_CENTER);
                txtModuleName.clear();
            }
        });
        //FIXME ATI the text for factor should come from parent and not from self caption
        buildFactorComboBox(EstimationEditView.getAppStackProvider()
                .getFactorForApp(this.getCaption()).stream().map(estimationFactor -> estimationFactor.getName()).collect(Collectors.toList()));
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
    public void systemEstimationChanged(SystemEstimation updatedEstimation) {
        System.out.println("Wathcer" + estimationChangedHandler);
        if (estimationChangedHandler != null) {
            estimationChangedHandler.systemEstimationChanged(updatedEstimation);
        }
        updateEffortValue();
    }


}