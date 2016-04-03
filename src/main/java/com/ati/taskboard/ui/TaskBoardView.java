package com.ati.taskboard.ui;

import com.jit.ati.opeproject.api.OpenProjectClient;
import com.jit.ati.opeproject.api.bo.WorkPackage;
import com.jit.ati.opeproject.api.impl.OpenProjectHttpsClientImpl;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.themes.ValoTheme;

@DesignRoot
public class TaskBoardView extends Panel implements View {
    private final BeanItemContainer beanItemContainer;
    Label headerLabel;


    Table openTasksGrid;
    Table inProgressTasksGrid;


    private static final String publicAuth = "117f1a9bcb868cc5709585654efaf7533b03fca3";
    private static final String COMMUNITY_TEST_HTTPS = "https://atitest.openproject.com";
    OpenProjectClient opApiClient = new OpenProjectHttpsClientImpl(COMMUNITY_TEST_HTTPS, publicAuth);


    public TaskBoardView() {
        Design.read(this);
        setSizeFull();


        headerLabel.setValue("OP - Taskboard");
        headerLabel.setSizeUndefined();
        headerLabel.addStyleName(ValoTheme.LABEL_H1);
        headerLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        headerLabel.setSizeUndefined();
        beanItemContainer = loadTasks();
        openTasksGrid.setContainerDataSource(beanItemContainer);
        //   openTasksGrid.getColumn("subject").setRenderer(new HtmlRenderer());


        openTasksGrid.addGeneratedColumn("Open tasks", new Table.ColumnGenerator() {
            public Component generateCell(Table source, Object itemId,
                                          Object columnId) {
                WorkPackage wp = (WorkPackage) itemId;
                //String html = ((WorkPackage) itemId).getSubject();
                String html = "<p><a href=\"http://www.w3schools.com\">#4717</a></p><p><b>" + wp.getSubject() + "</b></p>";
                Label label = new Label(html, ContentMode.HTML);
                label.setSizeUndefined();
                return label;


            }
        });
        openTasksGrid.setSizeFull();
        openTasksGrid.setVisibleColumns(new Object[]{"Open tasks"});
        //  openTasksGrid.setSizeFull();


        inProgressTasksGrid.setContainerDataSource(beanItemContainer);
        inProgressTasksGrid.setSizeFull();
    }

    private BeanItemContainer<WorkPackage> loadTasks() {
        //  opApiClient.getWorkPackagesForStatus(WorkpackageStatus.NEW);
        return new BeanItemContainer(WorkPackage.class, opApiClient.getAllWorkPackagesForProject("demo_project"));


    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
