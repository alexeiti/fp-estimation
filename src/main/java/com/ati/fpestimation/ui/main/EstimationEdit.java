package com.ati.fpestimation.ui.main;

import com.ati.fpestimation.ui.UiLabelHelper;
import com.ati.fpestimation.ui.com.ati.ui.callback.EstimationChangedHandler;
import com.ati.fpestimation.ui.com.ati.ui.callback.PtEstimationProvider;
import com.ati.fpestimation.ui.component.SystemEstimationPanel;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@Theme("mytheme")
@Widgetset("com.ati.vaadin.ui.main.FpEstimationWidgetSet")
public class EstimationEdit extends UI implements EstimationChangedHandler {

    private final VerticalLayout verticalLayout = new VerticalLayout();
    private ComboBox systemComboBox;
    private Label effortLabel;
    private List<PtEstimationProvider> estimationProviders = new ArrayList<>();
    private double totalPtEffort = 0;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        verticalLayout.addComponent(buildTopControlRow());
        verticalLayout.setWidth(60, Unit.PERCENTAGE);
        verticalLayout.setMargin(true);
        verticalLayout.setSpacing(true);

        setContent(verticalLayout);
        updateEffortValue();
    }

    private AbstractLayout buildTopControlRow() {
        HorizontalLayout topRow = new HorizontalLayout();
        topRow.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
        topRow.addComponents(new Label("System:"), buildSystemComboBox(Arrays.asList(new String[]{"Tibco", "OfflineOE"})));
        Button button = new Button("Add System");
        button.addClickListener(e -> {
            SystemEstimationPanel systemEstimationPanel = new SystemEstimationPanel(systemComboBox.getValue().toString(), this);
            estimationProviders.add(systemEstimationPanel);
            verticalLayout.addComponent(systemEstimationPanel);

        });

        effortLabel = new Label();
        topRow.addComponents(button, effortLabel);
        return topRow;
    }

    private Field<?> buildSystemComboBox(Collection<?> items) {
        systemComboBox = new ComboBox();

        systemComboBox.setWidth(200, Unit.PIXELS);
        systemComboBox.setNullSelectionAllowed(false);
        IndexedContainer container = new IndexedContainer(items);
        systemComboBox.setContainerDataSource(container);
        systemComboBox.setRequired(true);
        systemComboBox.setRequiredError("Pick a system");
        return systemComboBox;
    }

    @Override
    public void moduleEstimationChanged(double newValue) {
        totalPtEffort = estimationProviders.stream().mapToDouble(PtEstimationProvider::getPtEffort).sum();
        updateEffortValue();
    }

    private void updateEffortValue() {
        effortLabel.setValue(UiLabelHelper.formatPtEffort(totalPtEffort));
    }

    @WebServlet(urlPatterns = "/*", name = "FpEstimationServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = EstimationEdit.class, productionMode = false)
    public static class FpEstimationServlet extends VaadinServlet {

    }
}
