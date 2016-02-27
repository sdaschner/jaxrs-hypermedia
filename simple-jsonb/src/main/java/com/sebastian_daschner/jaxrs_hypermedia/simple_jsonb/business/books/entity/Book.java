package com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.books.entity;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class Book {

    @JsonbTransient
    private long id;
    private String isbn;
    private String name;
    private String author;
    private Availability availability;
    // just an example; money calculation with floating point numbers is a bad idea in practice
    private double price;
    @JsonbProperty("_links")
    private Map<String, URI> links = new HashMap<>();

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
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
