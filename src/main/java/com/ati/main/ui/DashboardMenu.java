package com.ati.main.ui;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by alex on 13/03/16.
 */
public class DashboardMenu extends CustomComponent {

    public DashboardMenu() {
        setPrimaryStyleName("valo-menu");
        setSizeUndefined();

        setCompositionRoot(buildContent());
    }

    private Component buildContent() {
        final CssLayout menuContent = new CssLayout();
        menuContent.addStyleName("sidebar");
        menuContent.addStyleName(ValoTheme.MENU_PART);
        menuContent.addStyleName("no-vertical-drag-hints");
        menuContent.addStyleName("no-horizontal-drag-hints");
        menuContent.setWidth(null);
        menuContent.setHeight("100%");

        menuContent.addComponent(buildTitle());
        //  menuContent.addComponent(buildUserMenu());
        //  menuContent.addComponent(buildToggleButton());
        menuContent.addComponent(buildMenuItems());

        return menuContent;
    }

    private Component buildTitle() {
        Label logo = new Label(" <strong>My Dashboard</strong>",
                ContentMode.HTML);
        logo.setSizeUndefined();
        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoWrapper.addStyleName("valo-menu-title");
        return logoWrapper;
    }


    private Component buildMenuItems() {
        CssLayout menuItemsLayout = new CssLayout();
        menuItemsLayout.setPrimaryStyleName("valo-menuitems");
        for (final DashboardViewType view : DashboardViewType.values()) {
            Button menuItemComponent = new Button(view.getViewName());

            menuItemComponent.setHtmlContentAllowed(true);
            menuItemComponent.setPrimaryStyleName("valo-menu-item");
            menuItemComponent.addClickListener(event -> {
                UI.getCurrent().getNavigator().navigateTo(view.getViewName());
            });
            menuItemsLayout.addComponent(menuItemComponent);
        }
        return menuItemsLayout;

    }
}
