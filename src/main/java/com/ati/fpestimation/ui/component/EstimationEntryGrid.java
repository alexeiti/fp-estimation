package com.ati.fpestimation.ui.component;

import com.ati.fpestimation.domain.estimation.EstimationEntry;
import com.ati.fpestimation.domain.kpi.EstimationFunction;
import com.ati.fpestimation.ui.main.EstimationEditView;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.Grid;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class EstimationEntryGrid extends Grid {

    private BeanItemContainer<EstimationEntry> beanEstimationContainer;
    private boolean isManualEstimationGrid;

    private class Columns {
        private static final String NAME = "name", COST = "cost", MANUAL = "manual", COMPLEXITY = "complexity", ESTIMATION_FUNCTION = "estimationFunction";
    }

    //Should not be used. Used as a WA to provide a dummy value for bounding to declarative UI.
    @Deprecated
    public EstimationEntryGrid() {
    }

    public EstimationEntryGrid(List<EstimationEntry> dataSource, boolean isManualEstimation) {
        beanEstimationContainer =
                new BeanItemContainer<EstimationEntry>(EstimationEntry.class, dataSource);

        this.setContainerDataSource(beanEstimationContainer);
        this.isManualEstimationGrid = isManualEstimation;

        this.setCaption("Double click to edit");
        this.setSizeFull();
        this.setWidth(100f, Unit.PERCENTAGE);
        this.setEditorEnabled(true);
        this.setColumnOrder(Columns.NAME, Columns.ESTIMATION_FUNCTION, Columns.COMPLEXITY, Columns.COST);
        this.getColumn(Columns.COST).setEditable(isManualEstimation);
        this.getColumn(Columns.ESTIMATION_FUNCTION)
                .setEditorField(getEditComboBox("Function is required!",
                        EstimationEditView.getFunctionProvider().getEstimationFunctions()
                ));

        this.removeColumn(Columns.MANUAL);
        this.setHeightMode(HeightMode.ROW);
        this.setEditorEnabled(true);
    }

    private Field<?> getEditComboBox(String requiredErrorMsg, Collection<EstimationFunction> items) {
        ComboBox comboBox = new ComboBox();
        comboBox.setNullSelectionAllowed(false);
        IndexedContainer container = new IndexedContainer(items);
        comboBox.setContainerDataSource(container);
        comboBox.setRequired(true);
        comboBox.setRequiredError(requiredErrorMsg);
        comboBox.setConverter(new ObjectTypeEstimationFunctionConverter());
        return comboBox;
    }

    //TODO ATI try if estimationEntry can be passed as arg. Thus returning of list and its overwriting wont be required
    public List<EstimationEntry> addEstimationEntry(String caption) {
        //TODO ATI check if it is necessary
        this.setContainerDataSource(this.getContainerDataSource());
        beanEstimationContainer.addBean(new EstimationEntry(caption, isManualEstimationGrid));
        return beanEstimationContainer.getItemIds();
    }





}
