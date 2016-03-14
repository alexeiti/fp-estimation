package com.ati.booklibrary.ui;

import com.ati.booklibrary.domain.BookInfo;
import com.ati.common.data.CommonRepositoryFactory;
import com.ati.common.domain.EmployeeInfo;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Date;

public class BookDetailsInfoGenerator extends VerticalLayout {
    private final BookInfo bookInfo;

    public BookDetailsInfoGenerator(BookInfo bookInfo) {
        this.bookInfo = bookInfo;


        //     this.setWidth(90f, Unit.PERCENTAGE);
//        this.addStyleName(ValoTheme.LAYOUT_CARD);

        Layout topInfoRow = buildTopInfoRow();
        this.addComponent(topInfoRow);
        this.setComponentAlignment(topInfoRow, Alignment.MIDDLE_LEFT);

        Layout lenderLayout = buildLenderBox();
        this.addComponent(lenderLayout);
        this.setComponentAlignment(lenderLayout, Alignment.MIDDLE_CENTER);

        Layout btnControlRow = buildBottomControlBox();
        this.addComponent(btnControlRow);
        this.setComponentAlignment(btnControlRow, Alignment.MIDDLE_RIGHT);

        this.setMargin(true);
        // this.setSpacing(true);
    }

    private Layout buildTopInfoRow() {
        Layout hLayout = new HorizontalLayout();
        hLayout.addComponent(buildBookInfoField("Title:", bookInfo.getTitle()));
        hLayout.addComponent(buildBookInfoField("Author:", "" + bookInfo.getAuthor()));
        hLayout.addComponent(buildBookInfoField("ISBN:", "" + bookInfo.getIsbn()));
        hLayout.addComponent(buildBookInfoField("Pages:", "" + bookInfo.getNumberOfPages()));

        hLayout.setWidth(95, Unit.PERCENTAGE);

        return hLayout;
    }

    private Layout buildLenderBox() {
        HorizontalLayout hLayout = new HorizontalLayout();

        ComboBox cboLender = new ComboBox("Lender:");
        cboLender.addStyleName(ValoTheme.COMBOBOX_SMALL);
        cboLender.setWidth(300, Unit.PIXELS);
        cboLender.setContainerDataSource(new BeanItemContainer<EmployeeInfo>(EmployeeInfo.class, CommonRepositoryFactory.getEmployeeRepository().getAllEmployees()));

        hLayout.addComponent(cboLender);

//TODO read real date
        Label lblBorrowDate = new Label();
        lblBorrowDate.setCaption("Borrowed on:");
        lblBorrowDate.setValue(new Date().toString());
        lblBorrowDate.setIcon(FontAwesome.CALENDAR);
        hLayout.addComponent(lblBorrowDate);

        Button btnReturn = new Button("Return");
        btnReturn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnReturn.addStyleName(ValoTheme.BUTTON_SMALL);
        hLayout.addComponent(btnReturn);
        hLayout.setComponentAlignment(btnReturn, Alignment.BOTTOM_RIGHT);
        hLayout.setSpacing(true);
        hLayout.setMargin(true);
        return hLayout;
    }

    private Layout buildBottomControlBox() {
        HorizontalLayout hLayout = new HorizontalLayout();
        //TODO add btn handler
        Button btnSave = new Button("Update");
        btnSave.addStyleName(ValoTheme.BUTTON_LINK);
        hLayout.addComponent(btnSave);
        hLayout.setComponentAlignment(btnSave, Alignment.BOTTOM_RIGHT);


        //TODO add btn handler
        Button btnCancel = new Button("Cancel");
        btnCancel.addStyleName(ValoTheme.BUTTON_LINK);

        //TODO decide if cancel button is really necessary
        //  hLayout.addComponent(btnCancel);
        //   hLayout.setComponentAlignment(btnCancel, Alignment.BOTTOM_RIGHT);

        hLayout.setWidth(20, Unit.PERCENTAGE);
        return hLayout;
    }

    private AbstractComponent buildBookInfoField(String caption, Object value) {
        TextField field = new TextField(caption);
        //  field.setCaption(caption);
        field.setValue(value != null ? value.toString() : "");
        field.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
        return field;
    }
}