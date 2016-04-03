package com.ati.main.ui;

import com.ati.fpestimation.data.AppStackRepository;
import com.ati.fpestimation.data.FunctionRepository;
import com.ati.fpestimation.domain.estimation.FpEstimation;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import javax.servlet.annotation.WebServlet;


@Theme("mytheme")
@DesignRoot
@Widgetset("com.ati.main.ui.DashboardUIWidgetSet")
public class DashboardUI extends UI {
    @Override
    protected void init(VaadinRequest request) {
        Responsive.makeResponsive(this);
        updateContent();
        System.out.println("Running app");

    }

    private void updateContent() {
        //TODO ATI add authentication
        // Authenticated user
        setContent(new MainView());
        //   removeStyleName("loginview");#
        final String f = Page.getCurrent().getUriFragment();
     /*   if (f == null || f.equals("")) {
            getNavigator().navigateTo("common");
        }*/

        //TODO ATI make it dinamic
        getNavigator().navigateTo(DashboardViewType.BOOK_LIBRARY.getViewName());
        //  getNavigator().navigateTo(getNavigator().getState());

    }

    @WebServlet(urlPatterns = "/*", name = "DashboardUIServlet", asyncSupported = true)
    @VaadinServletConfiguration( ui = DashboardUI.class, productionMode = false)
    public static class DashboardUIServlet extends VaadinServlet {

    }


    public static AppStackRepository getAppStackProvider() {
        //TODO ATI fix usage
        //   return ((DashboardUI) getCurrent()).appStackRepository;
        return null;
    }

    public static FpEstimation getCurrentEstimation() {
        //TODO ATI fix usage
        //    return ((EstimationEditView) getCurrent()).currentEstimation;
        return null;
    }

    //TODO ATI move method to AppStackRepository
    private FunctionRepository estimationFunctionRepository = new FunctionRepository();

    public static FunctionRepository getFunctionProvider() {
        return ((DashboardUI) getCurrent()).estimationFunctionRepository;
    }
}
