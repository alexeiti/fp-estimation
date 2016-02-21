package com.ati.fpestimation.ui.component;

import com.ati.fpestimation.domain.estimation.EstimationEntry;
import com.ati.fpestimation.domain.estimation.ModuleEstimation;
import com.ati.fpestimation.domain.kpi.EstimationFunction;
import com.ati.fpestimation.ui.UiLabelHelper;
import com.ati.fpestimation.ui.callback.EstimationChangedHandler;
import com.ati.fpestimation.ui.callback.PtEstimationProvider;
import com.ati.fpestimation.ui.main.EstimationEditView;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.Design;

import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;

@DesignRoot
public class ModuleEstimationPanel extends Panel implements PtEstimationProvider {


    //  private Collection<EstimationEntry> estimationEntriesList = new ArrayList<>();
    private BeanItemContainer<EstimationEntry> beanEstimationContainer;
    private Grid gridWithDs = new Grid();
    private Label lblFpTotal;
    private EstimationChangedHandler moduleEstimationChangedHandler;
    private double ptSum;
    private Layout gridContainer, contentLayout;
    private Button btnAddModule;
    private VerticalLayout rootContainer;
    private ComboBox txtFactor;

    private ModuleEstimation moduleEstimation;

    //TODO read factor for project
    private double factor = 4.9d;


    public ModuleEstimationPanel(ModuleEstimation moduleEstimation, EstimationChangedHandler estimationChangedHandler) {
        super(moduleEstimation.getName());
        this.moduleEstimation = moduleEstimation;
        moduleEstimationChangedHandler = estimationChangedHandler;‚
        Design.read(this);

        gridContainer.addComponent(buildGridWithDs());
        //  this.setPrimaryStyleName("v-panel-color3 color2");
        buildBottomRow();
        updateCalculations();
        rootContainer.setComponentAlignment(contentLayout, Alignment.TOP_CENTER);
        //FIXME ATI the text for factor should come from parent and not from self caption
        buildFactorComboBox(EstimationEditView.getAppStackProvider()
                .getFactorForApp(this.getCaption()).stream().map(estimationFactor -> estimationFactor.getName()).collect(Collectors.toList()));
    }


    private void buildBottomRow() {
        btnAddModule.addClickListener(e -> {
            addRow();

        });

    }

    private Grid buildGridWithDs() {
        System.out.println("Initializing module estimation:" + moduleEstimation);
        beanEstimationContainer =
                new BeanItemContainer<EstimationEntry>(EstimationEntry.class, moduleEstimation.getEstimationEntryList());
        gridWithDs = new Grid("Estimations", beanEstimationContainer);
        gridWithDs.setCaption("Double click to edit");
        // gridWithDs.setSizeFull();
        gridWithDs.setWidth(100f, Unit.PERCENTAGE);
        gridWithDs.setEditorEnabled(true);
        gridWithDs.setColumnOrder("name", "estimationFunction", "complexity", "cost");

        gridWithDs.getColumn("estimationFunction")
                .setEditorField(getEditComboBox("Function is required!",
                        EstimationEditView.getFunctionProvider().getEstimationFunctions()
                                .stream()
                                .map(EstimationFunction::getName).collect(Collectors.toList())))
                .setConverter(new EstimationFunctionToStringConverter());

        System.out.println(gridWithDs.getColumn("estimationFunction").getConverter());
        System.out.println(gridWithDs.getColumns());
        return gridWithDs;
    }

    private void updateCalculations() {
        int fpSum = moduleEstimation.getEstimationEntryList().stream().mapToInt(EstimationEntry::getCost).sum();
        lblFpTotal.setValue(UiLabelHelper.formatFpEffort(fpSum));
        ptSum = fpSum * factor / 8;

        if (moduleEstimationChangedHandler != null) {
            //TODO ATI fix call and interface with correct arguments
            //  moduleEstimationChangedHandler.systemEstimationChanged(ptSum);
        }
        gridWithDs.setHeightMode(HeightMode.ROW);

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
        gridWithDs.setContainerDataSource(gridWithDs.getContainerDataSource());
        beanEstimationContainer.addBean(new EstimationEntry());
        moduleEstimation.setEstimationEntryList(beanEstimationContainer.getItemIds());
        updateCalculations();
        System.out.println(moduleEstimation.getEstimationEntryList());
    }

    private void buildFactorComboBox(Collection<?> items) {
        IndexedContainer container = new IndexedContainer(items);
        txtFactor.setContainerDataSource(container);
        txtFactor.setRequired(true);
        txtFactor.setRequiredError("Pick a system");
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