package com.ati.booklibrary.ui;


import com.ati.booklibrary.data.BooksRepository;
import com.ati.booklibrary.domain.BookInfo;
import com.ati.common.ui.BooleanToFontIconConverter;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Responsive;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;

@DesignRoot
public class BookLibraryView extends Panel implements View {
    IndexedContainer itemContainer;
    BeanItemContainer beanItemContainer;
    Grid booksGrid;
    Grid.HeaderRow filterRow;
    Label headerLabel;


    public BookLibraryView() {
        Design.read(this);
        setSizeFull();
        headerLabel.setValue("Book library");
        headerLabel.setSizeUndefined();
        headerLabel.addStyleName(ValoTheme.LABEL_H1);
        headerLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        itemContainer = new IndexedContainer(BooksRepository.getAllBooks());
        beanItemContainer = new BeanItemContainer(BookInfo.class, BooksRepository.getAllBooks());


        booksGrid.setContainerDataSource(beanItemContainer);
        //   setColumnFiltering();
        booksGrid.setHeaderVisible(true);
        booksGrid.setFooterVisible(true);


        //Remove the columns that we dont want to have in result booksGrid
        booksGrid.removeColumn("internalId");
        booksGrid.removeColumn("purchaseDate");
        booksGrid.removeColumn("numberOfPages");
        booksGrid.removeColumn("currentLender");
        booksGrid.removeColumn("isbn");
        booksGrid.setColumnOrder("title", "author", "available");
        booksGrid.getColumn("available")
                .setMaximumWidth(100)
                .setConverter(new BooleanToFontIconConverter())
                .setRenderer(new HtmlRenderer())
        ;
        booksGrid.getColumn("title").setExpandRatio(4);
        booksGrid.getColumn("author").setExpandRatio(2);

        addTextRendererToColumn("title");
        addTextRendererToColumn("author");
        enableInlineBookDetails();
        booksGrid.setSizeFull();

    }

    private void enableInlineBookDetails() {
        //Show the details of book
        booksGrid.setEditorEnabled(false);
        booksGrid.setDetailsGenerator(new Grid.DetailsGenerator() {
            @Override
            public Component getDetails(Grid.RowReference rowReference) {
                BookInfo bookInfo = (BookInfo) rowReference.getItemId();
                return new BookDetailsInfoGenerator(bookInfo);
            }
        });
        booksGrid.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                if (event.isDoubleClick()) {
                    Object itemId = event.getItemId();
                    booksGrid.setDetailsVisible(itemId, !booksGrid.isDetailsVisible(itemId));
                }
            }
        });
    }


    private void addTextRendererToColumn(String propertyId) {
        if (filterRow == null)
            filterRow = booksGrid.appendHeaderRow();

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
