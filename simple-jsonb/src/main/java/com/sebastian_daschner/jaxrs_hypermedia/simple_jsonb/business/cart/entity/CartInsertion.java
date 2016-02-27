package com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.cart.entity;

import com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.books.control.IsbnBookJsonbAdapter;
import com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.books.entity.Book;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.validation.constraints.NotNull;

public class CartInsertion extends CartUpdate {

    @NotNull
    @JsonbTypeAdapter(IsbnBookJsonbAdapter.class)
    @JsonbProperty("isbn")
    private Book book;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

}
