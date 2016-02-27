package com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.books.control;

import com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.books.boundary.MockBookStore;
import com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.books.entity.Book;

import javax.enterprise.inject.spi.CDI;
import javax.json.bind.adapter.JsonbAdapter;

public class IsbnBookJsonbAdapter implements JsonbAdapter<Book, String> {

    // TODO test CDI injection
    private MockBookStore bookStore;

    public IsbnBookJsonbAdapter() {
        bookStore = CDI.current().select(MockBookStore.class).get();
    }

    @Override
    public Book adaptTo(String string) throws Exception {
        return bookStore.findBook(string);
    }

    @Override
    public String adaptFrom(Book author) throws Exception {
        return author.getName();
    }
}
