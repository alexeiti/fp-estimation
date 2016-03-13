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
import com.ati.fpestimation.ui.component.SystemEstimationPanel;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.themes.ValoTheme;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

@DesignRoot
public class EstimationEditView extends Panel implements View {

    private ComboBox txtSystemType;
    private Label lblEfortTotal, lblStatus, lblName, lblStack;
//    private List<PtEstimationProvider> estimationProviders = new ArrayList<>();

    private Button btnAddSystem;
    private Layout systemsContainer, addSystemContainer, contentContainer;
    private VerticalLayout mainContainer;

    //TODO ATI weave the repositories as beans
    private static AppStackRepository appStackRepository;

    private static FpEstimation currentEstimation;
    private static FpEstimationRepository estimationRepository;

    static {
        try {
            appStackRepository = new FileAppStackRepository();
            //TODO ATI use real estimation provider
            estimationRepository = new DummyFpEstimationRepository();
            //TODO ATI use correct Id
            currentEstimation = estimationRepository.find("some id");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public EstimationEditView() throws IOException {
        Design.read("EstimationEditView.html", this);
        buildTopControlRow();
        loadSystemEstimations();
        updateEffortValue();
        //this.setComponentAlignment(mainContainer, Alignment.MIDDLE_CENTER);

    }

    private void loadSystemEstimations() {
        for (SystemEstimation systemEstimation : currentEstimation.getSystemEstimationList()) {
            SystemEstimationPanel systemEstimationPanel = new SystemEstimationPanel(systemEstimation, currentEstimation.getStackType());
            systemEstimationPanel.addSystemEstimationChangedHandler(updatedEstimation -> updateEffortValue());
            systemsContainer.addComponent(systemEstimationPanel);
        }
    }


    private void buildTopControlRow() {
        lblName.setValue(currentEstimation.getName());
        lblStack.setValue(currentEstimation.getStackType().getName());
        buildSystemComboBox(appStackRepository.getAllAppsForStack(currentEstimation.getStackType().getId())
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
        return appStackRepository;
    }

    //TODO ATI move method to AppStackRepository
    private static FunctionRepository estimationFunctionRepository = new FunctionRepository();

    public static FunctionRepository getFunctionProvider() {
        return estimationFunctionRepository;
    }
}
