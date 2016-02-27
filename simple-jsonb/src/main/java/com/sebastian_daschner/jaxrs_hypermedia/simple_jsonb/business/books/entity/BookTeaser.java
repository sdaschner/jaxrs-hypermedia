package com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.books.entity;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class BookTeaser {

    @JsonbTransient
    private long id;
    private String name;
    private String author;
    @JsonbProperty("_links")
    private Map<String, URI> links = new HashMap<>();

    public BookTeaser() {
    }

    public BookTeaser(Book book) {
        id = book.getId();
        name = book.getName();
        author = book.getAuthor();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Map<String, URI> getLinks() {
        return links;
    }

    public void setLinks(Map<String, URI> links) {
        this.links = links;
    }

}
