package com.ati.fpestimation.ui.component;

import com.ati.fpestimation.server.ComplexityType;
import com.ati.fpestimation.server.EstimationEntry;
import com.ati.fpestimation.server.EstimationFunction;
import com.ati.fpestimation.server.FunctionRepository;
import com.vaadin.client.widget.grid.datasources.ListDataSource;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.renderers.TextRenderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class ModuleEstimationPanel extends Panel {

    private Grid grid = new Grid();
    private FunctionRepository functionRepository;
    Collection<EstimationEntry> personList = new ArrayList<>();
    BeanItemContainer<EstimationEntry> beanEstimationContainer;
    Grid gridWithDs;

    public ModuleEstimationPanel(String caption) {
        super(caption);
        //TODO use spring beans instead
        functionRepository = new FunctionRepository();

        this.setHeight(100.0f, Sizeable.Unit.PERCENTAGE);

        final VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setWidth(100f, Unit.PERCENTAGE);
        contentLayout.setMargin(true);


        gridWithDs = buildGridWithDs();
        gridWithDs.setEditorEnabled(true);
        contentLayout.addComponent(gridWithDs);


        Button btnAddModule = new Button("Add row");
        btnAddModule.addClickListener(e -> {
            addRow();
        });

        final HorizontalLayout btmRow = new HorizontalLayout();
        btmRow.setWidth(100f, Unit.PERCENTAGE);
        btmRow.setMargin(true);
        btmRow.addComponent(btnAddModule);

        btmRow.addComponents(new Label("Factor: Connector(4,95)"), new Label("Total: 123 PT"));
        contentLayout.addComponent(btmRow);


        this.setContent(contentLayout);
    }

    private Grid buildGridWithDs() {

        personList.add(new EstimationEntry());
        personList.add(new EstimationEntry());

        beanEstimationContainer =
                new BeanItemContainer<EstimationEntry>(EstimationEntry.class, personList);


        return new Grid("Estimations", beanEstimationContainer);
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
                        functionRepository.getEstimationFunctions()
                                .stream()
                                .map(EstimationFunction::getName).collect(Collectors.toList())));

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
        gridWithDs.setContainerDataSource(gridWithDs.getContainerDataSource());
        //beanEstimationContainer.refreshItems();
        beanEstimationContainer.addBean(new EstimationEntry());
        personList= beanEstimationContainer.getItemIds();
        System.out.println(personList);

    }


    private Field<?> getEditComboBox(String requiredErrorMsg, Collection<?> items) {
        ComboBox comboBox = new ComboBox();
        comboBox.setNullSelectionAllowed(false);
        IndexedContainer container = new IndexedContainer(items);
        comboBox.setContainerDataSource(container);
        comboBox.setRequired(true);
        comboBox.setRequiredError(requiredErrorMsg);
        return comboBox;
    }


    public class RefreshableBeanItemContainer<BEANTYPE> extends BeanItemContainer<BEANTYPE> {
        public RefreshableBeanItemContainer(Collection<? extends BEANTYPE> collection) throws IllegalArgumentException {
            super(collection);
        }

        public RefreshableBeanItemContainer(Class<? super BEANTYPE> type) throws IllegalArgumentException {
            super(type);
        }

        public RefreshableBeanItemContainer(Class<? super BEANTYPE> type, Collection<? extends BEANTYPE> collection) throws IllegalArgumentException {
            super(type, collection);
        }

        public void refreshItems() {
            fireItemSetChange();
        }
    }
}
