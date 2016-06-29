package com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.books.control;

import com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.books.boundary.MockBookStore;
import com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.books.entity.Book;

import javax.inject.Inject;
import javax.json.bind.adapter.JsonbAdapter;

public class IsbnBookJsonbAdapter implements JsonbAdapter<Book, String> {

    @Inject
    MockBookStore bookStore;

    @Override
    public String adaptToJson(Book author) throws Exception {
        return author.getName();
    }

    @Override
    public Book adaptFromJson(String string) throws Exception {
        return bookStore.findBook(string);
    }
}

