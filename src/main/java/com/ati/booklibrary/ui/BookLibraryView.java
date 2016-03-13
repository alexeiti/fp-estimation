package com.ati.booklibrary.ui;


import com.ati.booklibrary.data.BooksRepository;
import com.ati.booklibrary.domain.BookInfo;
import com.ati.common.ui.BooleanToFontIconConverter;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;

public class BookLibraryView extends VerticalLayout implements View {
    IndexedContainer itemContainer;
    BeanItemContainer beanItemContainer;
    Grid grid;
    Grid.HeaderRow filterRow;

    //TODO ATI move design to html template file
    public BookLibraryView() {
        itemContainer = new IndexedContainer(BooksRepository.getAllBooks());
        beanItemContainer = new BeanItemContainer(BookInfo.class, BooksRepository.getAllBooks());
        grid = new Grid();


        grid.setContainerDataSource(beanItemContainer);
        //   setColumnFiltering();
        grid.setHeaderVisible(true);
        grid.setFooterVisible(true);
        grid.setSizeFull();
        this.setWidth(100f, Unit.PERCENTAGE);
        this.setHeight(100f, Unit.PERCENTAGE);

        //Remove the columns that we dont want to have in result grid
        grid.removeColumn("internalId");
        grid.removeColumn("purchaseDate");
        grid.removeColumn("numberOfPages");
        grid.removeColumn("currentLender");
        grid.removeColumn("isbn");
        grid.setColumnOrder("title", "author", "available");
        grid.getColumn("available")
                .setMaximumWidth(100)
                .setConverter(new BooleanToFontIconConverter())
                .setRenderer(new HtmlRenderer())
        ;
        grid.getColumn("title").setExpandRatio(4);
        grid.getColumn("author").setExpandRatio(2);

        addTextRendererToColumn("title");
        addTextRendererToColumn("author");
        enableInlineBookDetails();


        this.addComponent(grid);
        this.setMargin(true);
        this.setWidth(90, Unit.PERCENTAGE);
    }

    private void enableInlineBookDetails() {
        //Show the details of book
        grid.setEditorEnabled(false);
        grid.setDetailsGenerator(new Grid.DetailsGenerator() {
            @Override
            public Component getDetails(Grid.RowReference rowReference) {
                BookInfo bookInfo = (BookInfo) rowReference.getItemId();
                return new BookDetailsInfoGenerator(bookInfo);
            }
        });
        grid.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                if (event.isDoubleClick()) {
                    Object itemId = event.getItemId();
                    grid.setDetailsVisible(itemId, !grid.isDetailsVisible(itemId));
                }
            }
        });
    }


    private void addTextRendererToColumn(String propertyId) {
        if (filterRow == null)
            filterRow = grid.appendHeaderRow();

        Grid.HeaderCell cell = filterRow.getCell(propertyId);

        // Have an input field to use for filter
        TextField filterField = new TextField();
        filterField.setWidth(100f, Unit.PERCENTAGE);
        //  filterField.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        filterField.addStyleName(ValoTheme.TEXTFIELD_SMALL);

        // Update filter When the filter input is changed
        filterField.addTextChangeListener(change -> {
            // Can't modify filters so need to replace
            beanItemContainer.removeContainerFilters(propertyId);

            // (Re)create the filter if necessary
            if (!change.getText().isEmpty())
                beanItemContainer.addContainerFilter(
                        new SimpleStringFilter(propertyId,
                                change.getText(), true, false));
        });
        cell.setComponent(filterField);
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //TODO implement
    }
}
