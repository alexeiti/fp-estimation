package com.ati.booklibrary.data;


import com.ati.booklibrary.domain.BookInfo;

import java.util.*;

public class BooksRepository {

    public static List<BookInfo> getAllBooks() {
        List<BookInfo> result = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            BookInfo book = new BookInfo(i);

            book.setTitle("Title" + i * 11);
            book.setAuthor("Author " + i * 10 + " Junior");
            book.setNumberOfPages(rand.nextInt(300));

            book.setPurchaseDate(getRandomDate());
            result.add(book);
        }
        return result;
    }

    public static Date getRandomDate() {

        GregorianCalendar gc = new GregorianCalendar();

        int year = randBetween(1900, 2010);

        gc.set(gc.YEAR, year);

        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));

        gc.set(gc.DAY_OF_YEAR, dayOfYear);

        return gc.getTime();

    }

    public static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }
}
