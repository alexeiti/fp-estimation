package com.ati.fpestimation.ui.component;

import com.ati.fpestimation.domain.estimation.EstimationEntry;
import com.ati.fpestimation.domain.estimation.ModuleEstimation;
import com.ati.fpestimation.ui.UiLabelHelper;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.Property;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.themes.ValoTheme;

@DesignRoot
public class ModuleEstimationPanel extends Panel {


    private EstimationEntryGrid estimationEntryGrid = new EstimationEntryGrid();
    private Label lblFpTotal;
    private ModuleEstimationChangedListener moduleEstimationChangedListener;
    private Layout gridContainer, contentLayout;
    private Button btnAddRow;
    private VerticalLayout rootContainer;
    private TextField txtEntryName, txtModuleName;
    private CssLayout componentNameGroup;


    private ModuleEstimation moduleEstimation;

    public ModuleEstimationPanel(ModuleEstimation moduleEstimation) {
        this.moduleEstimation = moduleEstimation;

        Design.read(this);
        //TODO change the color of the tetbox

        txtModuleName.setValue(moduleEstimation.getName());
        txtModuleName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        txtModuleName.addValueChangeListener(event -> {
            //TODO move to method and show error if value is not set
            if (txtModuleName.getValue() != null && txtModuleName.getValue().trim().length() != 0)
                moduleEstimation.setName(txtModuleName.getValue());
        });

        //TODO check how to set the estimation type? Probably from the factor combobox
        estimationEntryGrid = new EstimationEntryGrid(moduleEstimation.getEstimationEntryList(), false);
        estimationEntryGrid.addDeleteEstimatioEntryListener(() -> updateCalculations());
        gridContainer.addComponent(estimationEntryGrid);
        initBottomRow();
        updateCalculations();
        rootContainer.setComponentAlignment(contentLayout, Alignment.TOP_CENTER);
        btnAddRow.addStyleName(ValoTheme.BUTTON_PRIMARY);
        lblFpTotal.addStyleName(ValoTheme.LABEL_BOLD);
        lblFpTotal.addStyleName(ValoTheme.LABEL_COLORED);
        componentNameGroup.addStyleName("v-component-group");
        txtEntryName.setInputPrompt("Component name");
    }

    private void initBottomRow() {
        btnAddRow.addClickListener(e -> addRow());
    }

    private void updateCalculations() {
        int fpSum = moduleEstimation.getEstimationEntryList().stream().mapToInt(EstimationEntry::getCost).sum();
        lblFpTotal.setValue(UiLabelHelper.formatFpEffort(fpSum));
        if (moduleEstimationChangedListener != null) {
            moduleEstimationChangedListener.estimationChanged();
        }

    }



    private void addRow() {
        if (UiLabelHelper.isValueSet(txtEntryName)) {
            moduleEstimation.setEstimationEntryList(estimationEntryGrid.addEstimationEntry(txtEntryName.getValue()));
            updateCalculations();
        }
    }

    public interface ModuleEstimationChangedListener {
        void estimationChanged();
    }
    public void addModuleEstimationChangedListener(ModuleEstimationChangedListener moduleEstimationChangedListener) {
        this.moduleEstimationChangedListener = moduleEstimationChangedListener;
    }
}