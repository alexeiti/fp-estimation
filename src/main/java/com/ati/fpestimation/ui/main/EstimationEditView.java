package com.ati.fpestimation.ui.main;

import com.ati.booklibrary.ui.BookLibraryView;
import com.ati.fpestimation.data.AppStackRepository;
import com.ati.fpestimation.data.FpEstimationRepository;
import com.ati.fpestimation.data.FunctionRepository;
import com.ati.fpestimation.data.impl.DummyFpEstimationRepository;
import com.ati.fpestimation.data.impl.FileAppStackRepository;
import com.ati.fpestimation.domain.estimation.FpEstimation;
import com.ati.fpestimation.domain.estimation.SystemEstimation;
import com.ati.fpestimation.exception.ValidationException;
import com.ati.fpestimation.ui.UiLabelHelper;
import com.ati.fpestimation.ui.component.SystemEstimationPanel;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.themes.ValoTheme;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

@DesignRoot
public class EstimationEditView extends CustomComponent implements View {

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
        //TODO ATI fix design
        Design.read(this);
        appStackRepository = new FileAppStackRepository();
        //TODO ATI use real estimation provider
        estimationRepository = new DummyFpEstimationRepository();
        //TODO ATI use correct Id
        currentEstimation = estimationRepository.find("some id");

    }
//*********************

    private void test() {

        systemsContainer.addComponent(new BookLibraryView());
        systemsContainer.setHeight(1000f, Sizeable.Unit.PIXELS);
    }

    //*********************
    private boolean testMode = true;

    //TODO ATI use in constructor
    protected void init(VaadinRequest vaadinRequest) {
        if (!testMode) {
            buildTopControlRow();
            loadSystemEstimations();
            updateEffortValue();
            mainContainer.setComponentAlignment(contentContainer, Alignment.MIDDLE_CENTER);
        } else
            test();
    }

    private void loadSystemEstimations() {
        for (SystemEstimation systemEstimation : currentEstimation.getSystemEstimationList()) {
            SystemEstimationPanel systemEstimationPanel = new SystemEstimationPanel(systemEstimation, this.currentEstimation.getStackType());
            systemEstimationPanel.addSystemEstimationChangedHandler(updatedEstimation -> updateEffortValue());
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
        lblEfortTotal.addStyleName(ValoTheme.LABEL_H3);
        lblEfortTotal.addStyleName(ValoTheme.LABEL_COLORED);
        lblEfortTotal.addStyleName(ValoTheme.LABEL_BOLD);
    }

    private void addSystemEstimation() {
        //TODO ATI read app type from combobox
        try {
            SystemEstimation systemEstimation = currentEstimation.addNewSystemEstimation(appStackRepository.getAllAppsForStack(currentEstimation.getStackType().getId()).get(0));
            SystemEstimationPanel systemEstimationPanel = new SystemEstimationPanel(systemEstimation, currentEstimation.getStackType());
            systemEstimationPanel.addSystemEstimationChangedHandler(updatedEstimation -> updateEffortValue());
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
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }


    public static AppStackRepository getAppStackProvider() {
        //TODO ATI fix usage
        return null;
        //return ((EstimationEditView) getCurrent()).appStackRepository;
    }

    public static FpEstimation getCurrentEstimation() {
        //TODO ATI fix usage
        return null;
        //return ((EstimationEditView) getCurrent()).currentEstimation;
    }

    //TODO ATI move method to AppStackRepository
    private FunctionRepository estimationFunctionRepository = new FunctionRepository();

    public static FunctionRepository getFunctionProvider() {
        //TODO ATI fix usage
        return null;
        //return ((EstimationEditView) getCurrent()).estimationFunctionRepository;
    }

}
