package com.sebastian_daschner.jaxrs_hypermedia.siren_siren4j.business.cart.entity;

import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4j.business.books.boundary.BookStore;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4j.business.books.entity.Book;

import javax.enterprise.inject.spi.CDI;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class IsbnBookXmlAdapter extends XmlAdapter<String, Book> {

    private BookStore bookStore;

    public IsbnBookXmlAdapter() {
        bookStore = CDI.current().select(BookStore.class).get();
    }

    @Override
    public Book unmarshal(String isbn) throws Exception {
        return bookStore.findBook(isbn);
    }

    @Override
    public String marshal(Book book) throws Exception {
        throw new UnsupportedOperationException();
    }

}
