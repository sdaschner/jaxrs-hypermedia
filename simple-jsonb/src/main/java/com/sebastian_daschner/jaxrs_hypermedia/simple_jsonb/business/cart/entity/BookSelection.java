package com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.cart.entity;

import com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.books.entity.BookSelectionBook;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.validation.constraints.Min;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class BookSelection {

    @JsonbTransient
    private long id;
    @Min(1)
    private int quantity;
    private BookSelectionBook book;
    private double price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonbProperty("_links")
    private Map<String, URI> links = new HashMap<>();

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BookSelectionBook getBook() {
        return book;
    }

    public void setBook(BookSelectionBook book) {
        this.book = book;
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
