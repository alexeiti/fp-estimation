package com.ati.fpestimation.ui.main.booksample;


import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class BookLibraryUi extends VerticalLayout {
    IndexedContainer itemContainer;
    BeanItemContainer beanItemContainer;
    Grid grid;

    public BookLibraryUi() {
        itemContainer = new IndexedContainer(BooksRepository.getAllBooks());
        beanItemContainer = new BeanItemContainer(BookInfo.class, BooksRepository.getAllBooks());
        grid = new Grid();

        itemContainer.addContainerProperty("titel", String.class, "sd");
        grid.setContainerDataSource(beanItemContainer);
        setColumnFiltering();
        grid.setHeaderVisible(true);
        grid.setFooterVisible(true);
        grid.setSizeFull();
        this.setWidth(100f, Unit.PERCENTAGE);
        this.setHeight(100f, Unit.PERCENTAGE);

        this.addComponent(grid);
    }

    private void setColumnFiltering() {
        Grid.HeaderRow filterRow = grid.appendHeaderRow();
        for (Object pid : grid.getContainerDataSource()
                .getContainerPropertyIds()) {
            Grid.HeaderCell cell = filterRow.getCell(pid);

            // Have an input field to use for filter
            TextField filterField = new TextField();
            filterField.setColumns(8);

            // Update filter When the filter input is changed
            filterField.addTextChangeListener(change -> {
                // Can't modify filters so need to replace
                beanItemContainer.removeContainerFilters(pid);

                // (Re)create the filter if necessary
                if (!change.getText().isEmpty())
                    beanItemContainer.addContainerFilter(
                            new SimpleStringFilter(pid,
                                    change.getText(), true, false));
            });
            cell.setComponent(filterField);
        }
    }
}
