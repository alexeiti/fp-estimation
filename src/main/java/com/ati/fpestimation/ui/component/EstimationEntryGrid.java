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
import com.vaadin.ui.themes.ValoTheme;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class EstimationEntryGrid extends Grid {

    private BeanItemContainer<EstimationEntry> beanEstimationContainer;

    //Should not be used. Used as a WA to provide a dummy value for bounding to declarative UI.
    @Deprecated
    public EstimationEntryGrid() {
    }

    public EstimationEntryGrid(List<EstimationEntry> dataSource) {
        beanEstimationContainer =
                new BeanItemContainer<EstimationEntry>(EstimationEntry.class, dataSource);
        this.setContainerDataSource(beanEstimationContainer);

        this.setCaption("Double click to edit");
        this.setSizeFull();
        this.setWidth(100f, Unit.PERCENTAGE);
        this.setEditorEnabled(true);
        this.setColumnOrder("name", "estimationFunction", "complexity", "cost");

        this.getColumn("estimationFunction")
                .setEditorField(getEditComboBox("Function is required!",
                        EstimationEditView.getFunctionProvider().getEstimationFunctions()
                ));
        //    .setConverter(new EstimationFunctionToStringConverter());


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
        beanEstimationContainer.addBean(new EstimationEntry(caption));
        return beanEstimationContainer.getItemIds();
    }


    class ObjectTypeEstimationFunctionConverter implements Converter<Object, Object> {
        @Override
        public Object convertToModel(Object value, Class<?> targetType, Locale locale) throws
                Converter.ConversionException {
            //TODO ATI use function from repository
            if (value != null)
                return new EstimationFunction(value.toString(), null);
            else
                return null;
        }

        @Override
        public Object convertToPresentation(Object value, Class<?> targetType, Locale locale) throws
                Converter.ConversionException {
            if (value != null)
                return value.toString();
            else
                return "";
        }

        @Override
        public Class<Object> getModelType() {
            return Object.class;
        }

        @Override
        public Class<Object> getPresentationType() {
            return Object.class;
        }


    }


}
