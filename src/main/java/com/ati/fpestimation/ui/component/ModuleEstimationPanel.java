package com.ati.fpestimation.ui.component;

import com.ati.fpestimation.server.ComplexityType;
import com.ati.fpestimation.server.EstimationFunction;
import com.ati.fpestimation.server.FunctionRepository;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.renderers.TextRenderer;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class ModuleEstimationPanel extends Panel {

    private Grid grid = new Grid();
    private FunctionRepository functionRepository;

    public ModuleEstimationPanel(String caption) {

        super(caption);
        //TODO use spring beans instead
        functionRepository = new FunctionRepository();

        this.setHeight(100.0f, Sizeable.Unit.PERCENTAGE);

        final VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setWidth(100f, Unit.PERCENTAGE);
        contentLayout.setMargin(true);
        contentLayout.addComponent(buildEstimationGrid());

        Button btnAddModule = new Button("Add row");
        btnAddModule.addClickListener(e -> {
            addRow();
        });
        contentLayout.addComponent(btnAddModule);

        contentLayout.addComponents(new Label("Total:"), new Label("O PT"));
        this.setContent(contentLayout);
    }

    private Grid buildEstimationGrid() {
        grid = new Grid();
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
                        Arrays.asList(functionRepository.getEstimationFunctions()
                                .stream()
                                .map(EstimationFunction::getName).collect(Collectors.toList()))));

        grid.addColumn("Complexity", String.class)
                .setRenderer(new TextRenderer())
                .setExpandRatio(4)
                .setEditorField(getEditComboBox("Complexity is required!",
                        Arrays.asList(ComplexityType.values())
                                .stream()
                                .map(ComplexityType::getCaption).collect(Collectors.toList())));

        grid.addColumn("Value", Integer.class)
                .setRenderer(new NumberRenderer("%02d"))
                .setExpandRatio(5)
                .setEditable(false);


        return grid;
    }

    private void addRow() {
        grid.addRow("Ws Starter", "External DB", ComplexityType.MIN.getCaption(), new Integer(20));
    }


    private Field<?> getEditComboBox(String requiredErrorMsg, Collection<?> items) {
        ComboBox comboBox = new ComboBox();
        comboBox.setNullSelectionAllowed(true);
        IndexedContainer container = new IndexedContainer(items);
        comboBox.setContainerDataSource(container);
        comboBox.setRequired(true);
        comboBox.setRequiredError(requiredErrorMsg);
        return comboBox;
    }
}
