package com.ati.fpestimation.ui.component;

import com.ati.fpestimation.server.EstimationEntry;
import com.ati.fpestimation.server.EstimationFunction;
import com.ati.fpestimation.server.FunctionRepository;
import com.ati.fpestimation.ui.UiLabelHelper;
import com.ati.fpestimation.ui.com.ati.ui.callback.EstimationChangedHandler;
import com.ati.fpestimation.ui.com.ati.ui.callback.PtEstimationProvider;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;

public class ModuleEstimationPanel extends Panel implements PtEstimationProvider {

    private static final String TOTAL_STRING_TEMPLATE = "Total: %d FP";
    private FunctionRepository functionRepository;
    private Collection<EstimationEntry> estimationEntriesList = new ArrayList<>();
    private BeanItemContainer<EstimationEntry> beanEstimationContainer;
    private Grid gridWithDs;
    private Label totalFpLabel;
    private EstimationChangedHandler moduleEstimationChangedHandler;
    private double ptSum;


    //TODO read factor for project
    private double factor = 4.9d;

    public ModuleEstimationPanel(String caption, EstimationChangedHandler estimationChangedHandler) {
        this(caption);
        moduleEstimationChangedHandler = estimationChangedHandler;
    }

    public ModuleEstimationPanel(String caption) {
        super(caption);
        //TODO use spring beans instead
        functionRepository = new FunctionRepository();

        //this.setHeight(100.0f, Sizeable.Unit.PERCENTAGE);

        final VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setWidth(100f, Unit.PERCENTAGE);
        contentLayout.setMargin(true);


        contentLayout.addComponent(buildGridWithDs());
        contentLayout.addComponent(buildBottomRow());

        this.setContent(contentLayout);
        updateCalculations();
    }

    private HorizontalLayout buildBottomRow() {
        final HorizontalLayout btmRow = new HorizontalLayout();
        btmRow.setWidth(100f, Unit.PERCENTAGE);
        btmRow.setMargin(true);

        Button btnAddModule = new Button("Add row");
        btnAddModule.addClickListener(e -> {
            addRow();
        });
        btmRow.addComponent(btnAddModule);

        Label factorLabel = new Label("Factor: Connector(4,95)");
        totalFpLabel = new Label();
        btmRow.addComponents(factorLabel, totalFpLabel);
        return btmRow;
    }

    private Grid buildGridWithDs() {
        beanEstimationContainer =
                new BeanItemContainer<EstimationEntry>(EstimationEntry.class, estimationEntriesList);
        gridWithDs = new Grid("Estimations", beanEstimationContainer);
        gridWithDs.setCaption("Double click to edit");
        gridWithDs.setSizeFull();
        gridWithDs.setEditorEnabled(true);
        gridWithDs.setColumnOrder("name", "estimationFunction", "complexity", "cost");

        gridWithDs.getColumn("estimationFunction")
                .setEditorField(getEditComboBox("Function is required!",
                        functionRepository.getEstimationFunctions()
                                .stream()
                                .map(EstimationFunction::getName).collect(Collectors.toList())))
                .setConverter(new EstimationFunctionToStringConverter());

        System.out.println(gridWithDs.getColumn("estimationFunction").getConverter());
        System.out.println(gridWithDs.getColumns());
        return gridWithDs;
    }

    private void updateCalculations() {
        int fpSum = estimationEntriesList.stream().mapToInt(EstimationEntry::getCost).sum();
        totalFpLabel.setValue(UiLabelHelper.formatFpEffort(fpSum));
        ptSum = fpSum * factor / 8;

        if (moduleEstimationChangedHandler != null) {
            moduleEstimationChangedHandler.moduleEstimationChanged(ptSum);
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
                        Arrays.asList(ComplexityType.values())
                                .stream()
                                .map(ComplexityType::getCaption).collect(Collectors.toList())));

        grid.addColumn("Value", Integer.class)
                .setRenderer(new NumberRenderer("%02d"))
                .setExpandRatio(5)
                .setEditable(false);

*/
        return null;
    }

    private void addRow() {
        gridWithDs.setContainerDataSource(gridWithDs.getContainerDataSource());
        beanEstimationContainer.addBean(new EstimationEntry());
        estimationEntriesList = beanEstimationContainer.getItemIds();
        updateCalculations();
        System.out.println(estimationEntriesList);
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

    @Override
    public double getPtEffort() {
        return ptSum;
    }


    class EstimationFunctionToStringConverter implements Converter<String, EstimationFunction> {


        @Override
        public EstimationFunction convertToModel(String s, Class<? extends EstimationFunction> aClass, Locale locale) throws ConversionException {
            System.out.println("Converting " + s);
            return new EstimationFunction("External DB", null);

        }

        @Override
        public String convertToPresentation(EstimationFunction estimationFunction, Class<? extends String> aClass, Locale locale) throws ConversionException {
            if (estimationFunction != null)
                return estimationFunction.getName();
            else
                return null;
        }

        @Override
        public Class<EstimationFunction> getModelType() {
            return EstimationFunction.class;
        }

        @Override
        public Class<String> getPresentationType() {
            return String.class;
        }
    }


}