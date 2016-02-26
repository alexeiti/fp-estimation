package com.ati.fpestimation.ui.main;

import com.ati.fpestimation.data.AppStackRepository;
import com.ati.fpestimation.data.FpEstimationRepository;
import com.ati.fpestimation.data.FunctionRepository;
import com.ati.fpestimation.data.impl.DummyFpEstimationRepository;
import com.ati.fpestimation.data.impl.FileAppStackRepository;
import com.ati.fpestimation.domain.estimation.FpEstimation;
import com.ati.fpestimation.domain.estimation.SystemEstimation;
import com.ati.fpestimation.exception.ValidationException;
import com.ati.fpestimation.ui.UiLabelHelper;
import com.ati.fpestimation.ui.callback.SystemEstimationChangedHandler;
import com.ati.fpestimation.ui.component.SystemEstimationPanel;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.themes.ValoTheme;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Theme("mytheme")
@DesignRoot
@Widgetset("com.ati.vaadin.ui.main.FpEstimationWidgetSet")
public class EstimationEditView extends UI implements SystemEstimationChangedHandler {

    private ComboBox txtSystemType;
    private Label lblEfortTotal, lblStatus, lblName, lblStack;
//    private List<PtEstimationProvider> estimationProviders = new ArrayList<>();

    private Button btnAddSystem;
    private Layout systemsContainer, addSystemContainer, contentContainer;
    private VerticalLayout mainContainer;

    private AppStackRepository appStackRepository;
    private FpEstimation currentEstimation;
    private FpEstimationRepository estimationRepository;

    public EstimationEditView() throws IOException {
        Design.read(this);
        appStackRepository = new FileAppStackRepository();
        //TODO ATI use real estimation provider
        estimationRepository = new DummyFpEstimationRepository();
        //TODO ATI use correct Id
        currentEstimation = estimationRepository.find("some id");

    }
//*********************

    private void test() {
        List<sample> sapleList = new ArrayList();
        sapleList.add(new sample(20, "alex", "male"));
        sapleList.add(new sample(20, "vika", "female"));
        Table table = new Table("Sample table");
        BeanItemContainer beanEstimationContainer =
                new BeanItemContainer<sample>(sample.class, sapleList);
        table.setContainerDataSource(beanEstimationContainer);
        table.addStyleName(ValoTheme.TABLE_BORDERLESS);
        table.addStyleName(ValoTheme.TABLE_NO_STRIPES);
        table.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
        table.addStyleName(ValoTheme.TABLE_SMALL);

        table.setEditable(true);
        systemsContainer.addComponent(table);
    }

    //*********************
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        buildTopControlRow();
        loadSystemEstimations();
        updateEffortValue();
        mainContainer.setComponentAlignment(contentContainer, Alignment.MIDDLE_CENTER);
        //test();
    }

    private void loadSystemEstimations() {
        for (SystemEstimation systemEstimation : currentEstimation.getSystemEstimationList()) {
            SystemEstimationPanel systemEstimationPanel = new SystemEstimationPanel(systemEstimation, this, this.currentEstimation.getStackType());
            //      estimationProviders.add(systemEstimationPanel);
            systemsContainer.addComponent(systemEstimationPanel);
        }
    }


    private void buildTopControlRow() {
        lblName.setValue(currentEstimation.getName());
        lblStack.setValue(currentEstimation.getStackType().getName());
        buildSystemComboBox(EstimationEditView.getAppStackProvider().getAllAppsForStack(currentEstimation.getStackType().getId())
                .stream().map(appType -> appType.getName()).collect(Collectors.toList()));
        btnAddSystem.addClickListener(e -> {
            addSystemEstimation();
        });

    }

    private void addSystemEstimation() {
        //TODO ATI read app type from combobox
        try {
            SystemEstimation systemEstimation = currentEstimation.addNewSystemEstimation(appStackRepository.getAllAppsForStack(currentEstimation.getStackType().getId()).get(0));
            SystemEstimationPanel systemEstimationPanel = new SystemEstimationPanel(systemEstimation, this, currentEstimation.getStackType());
            //  estimationProviders.add(systemEstimationPanel);
            systemsContainer.addComponent(systemEstimationPanel);
        } catch (ValidationException e1) {
            Notification.show("", e1.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
    }

    private void buildSystemComboBox(Collection<?> items) {
        IndexedContainer container = new IndexedContainer(items);
        txtSystemType.setContainerDataSource(container);
        txtSystemType.setRequired(true);
        txtSystemType.setRequiredError("Pick a system");
    }


    private void updateEffortValue() {
        lblEfortTotal.setValue(UiLabelHelper.formatPtEffort(currentEstimation.getTotalEffortInPt()));
    }

    @Override
    public void estimationChanged(SystemEstimation updatedEstimation) {
        updateEffortValue();
    }

    @WebServlet(urlPatterns = "/*", name = "FpEstimationServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = EstimationEditView.class, productionMode = false)
    public static class FpEstimationServlet extends VaadinServlet {

    }


    public static AppStackRepository getAppStackProvider() {
        return ((EstimationEditView) getCurrent()).appStackRepository;
    }

    public static FpEstimation getCurrentEstimation() {
        return ((EstimationEditView) getCurrent()).currentEstimation;
    }

    //TODO ATI move method to AppStackRepository
    private FunctionRepository estimationFunctionRepository = new FunctionRepository();

    public static FunctionRepository getFunctionProvider() {
        return ((EstimationEditView) getCurrent()).estimationFunctionRepository;
    }
}
