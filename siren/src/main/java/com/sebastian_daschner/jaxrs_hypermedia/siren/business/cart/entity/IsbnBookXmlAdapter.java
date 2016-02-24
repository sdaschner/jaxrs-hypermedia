package com.sebastian_daschner.jaxrs_hypermedia.siren.business.cart.entity;

import com.sebastian_daschner.jaxrs_hypermedia.siren.business.books.boundary.MockBookStore;
import com.sebastian_daschner.jaxrs_hypermedia.siren.business.books.entity.Book;

import javax.enterprise.inject.spi.CDI;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class IsbnBookXmlAdapter extends XmlAdapter<String, Book> {

    private MockBookStore bookStore;

    public IsbnBookXmlAdapter() {
        bookStore = CDI.current().select(MockBookStore.class).get();
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
