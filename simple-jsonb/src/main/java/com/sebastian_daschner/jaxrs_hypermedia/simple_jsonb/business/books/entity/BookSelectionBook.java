package com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.books.entity;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class BookSelectionBook {

    @JsonbTransient
    private long id;
    private String isbn;
    private String name;
    private double price;
    @JsonbProperty("_links")
    private Map<String, URI> links = new HashMap<>();

    public BookSelectionBook() {
    }

    public BookSelectionBook(Book book) {
        isbn = book.getIsbn();
        name = book.getName();
        price = book.getPrice();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Map<String, URI> getLinks() {
        return links;
    }

    public void setLinks(Map<String, URI> links) {
        this.links = links;
    }

}
