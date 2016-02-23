package com.ati.fpestimation.ui.component;

import com.ati.fpestimation.domain.estimation.EstimationEntry;
import com.ati.fpestimation.domain.estimation.ModuleEstimation;
import com.ati.fpestimation.ui.UiLabelHelper;
import com.ati.fpestimation.ui.callback.ModuleEstimationChangedHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.themes.ValoTheme;

@DesignRoot
public class ModuleEstimationPanel extends Panel {


    private EstimationEntryGrid estimationEntryGrid = new EstimationEntryGrid();
    private Label lblFpTotal;
    private ModuleEstimationChangedHandler moduleEstimationChangedHandler;
    private Layout gridContainer, contentLayout;
    private Button btnAddModule;
    private VerticalLayout rootContainer;
    private TextField txtComponentName;


    private ModuleEstimation moduleEstimation;

    public ModuleEstimationPanel(ModuleEstimation moduleEstimation, ModuleEstimationChangedHandler moduleEstimationChangedHandler) {
        super(moduleEstimation.getName());
        this.moduleEstimation = moduleEstimation;
        this.moduleEstimationChangedHandler = moduleEstimationChangedHandler;
        Design.read(this);
        estimationEntryGrid = new EstimationEntryGrid(moduleEstimation.getEstimationEntryList());
        gridContainer.addComponent(estimationEntryGrid);
        //  this.setPrimaryStyleName("v-panel-color3 color2");
        initBottomRow();
        updateCalculations();
        rootContainer.setComponentAlignment(contentLayout, Alignment.TOP_CENTER);
        btnAddModule.addStyleName(ValoTheme.BUTTON_PRIMARY);

    }

    private void initBottomRow() {
        btnAddModule.addClickListener(e -> addRow());
    }

    private void updateCalculations() {
        int fpSum = moduleEstimation.getEstimationEntryList().stream().mapToInt(EstimationEntry::getCost).sum();
        lblFpTotal.setValue(UiLabelHelper.formatFpEffort(fpSum));
        //System.out.println("updating estimation " + moduleEstimationChangedHandler);
        if (moduleEstimationChangedHandler != null) {
            moduleEstimationChangedHandler.estimationChanged();
        }

    }

    private Grid buildEstimationGrid() {
      /*  grid = new Grid();
        grid.setCaption("Double click to edit");
        grid.setSizeFull();
        grid.setEditorEnabled(true);
        grid.setSelectionMode(Grid.SelectionMode.NONE);


        grid.addColumn("Step", String.class)
                .setRenderer(new TextRenderer()).
                setExpandRatio(7)
        ;

        grid.addColumn("Function", String.class)
                .setRenderer(new TextRenderer())
                .setExpandRatio(4)
                .setEditorField(getEditComboBox("Function is required!",
                        functionRepository.getEstimationFunctions()
                                .stream()
                                .map(EstimationFunction::getName).collect(Collectors.toList())));

        grid.addColumn("Complexity", String.class)
                .setRenderer(new TextRenderer())
                .setExpandRatio(4)
                .setEditorField(getEditComboBox("Complexity is required!",
                        Arrays.asList(FunctionComplexityType.values())
                                .stream()
                                .map(FunctionComplexityType::getCaption).collect(Collectors.toList())));

        grid.addColumn("Value", Integer.class)
                .setRenderer(new NumberRenderer("%02d"))
                .setExpandRatio(5)
                .setEditable(false);

*/
        return null;
    }

    private void addRow() {
        moduleEstimation.setEstimationEntryList(estimationEntryGrid.addEstimationEntry(txtComponentName.getValue()));
        updateCalculations();
        System.out.println(moduleEstimation.getEstimationEntryList());
    }


}