package com.ati.vaadin.ui.component;

import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.renderers.TextRenderer;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by alex on 13/02/16.
 */
public class ModuleEstimationPanel extends Panel {

    private Grid grid = new Grid();

    public ModuleEstimationPanel(String caption) {
        super(caption);
        this.setHeight(100.0f, Sizeable.Unit.PERCENTAGE);

        final VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setWidth(100f, Unit.PERCENTAGE);
        contentLayout.setMargin(true);
        contentLayout.addComponent(buildEstimationGrid());

        Button btnAddModule = new Button("Add row");
        btnAddModule.addClickListener(e -> {
            addRow();
        });

        contentLayout.addComponents(new Label("Total:"), new Label("O PT"));
        contentLayout.addComponent(btnAddModule);
        this.setContent(contentLayout);
    }

    private Grid buildEstimationGrid() {
        grid = new Grid();
        grid.setCaption("Double click to edit");
        grid.setSizeFull();
        grid.setEditorEnabled(true);
        grid.setSelectionMode(Grid.SelectionMode.NONE);


        grid.addColumn("Step", String.class)
                .setRenderer(new TextRenderer()).setExpandRatio(4)
        ;

        grid.addColumn("Art", String.class)
                .setRenderer(new TextRenderer()).setExpandRatio(4);

        grid.addColumn("Complexity", String.class)
                .setRenderer(new TextRenderer()).setExpandRatio(4)
                .setEditorField(getComboBox("Complexity is required!",
                        Arrays.asList(new String[]{"maximum", "medium", "min"})));

        grid.addColumn("Value", Integer.class)
                .setRenderer(new NumberRenderer("%02d"))
                .setExpandRatio(5).setEditable(false) ;



        return grid;
    }

    private void addRow() {
        grid.addRow("Ws Starter", "External DB", "Min", new Integer(20));
    }


    private Field<?> getComboBox(String requiredErrorMsg, Collection<?> items) {
        ComboBox comboBox = new ComboBox();
        comboBox.setNullSelectionAllowed(true);
        IndexedContainer container = new IndexedContainer(items);
        comboBox.setContainerDataSource(container);
        comboBox.setRequired(true);
        comboBox.setRequiredError(requiredErrorMsg);
        return comboBox;
    }
}
