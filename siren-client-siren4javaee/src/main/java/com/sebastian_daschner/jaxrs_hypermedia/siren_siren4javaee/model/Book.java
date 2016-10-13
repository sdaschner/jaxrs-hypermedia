package com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.model;

import java.net.URI;

public class Book {

    private URI uri;
    private String isbn;
    private String name;
    private String author;
    private String availability;
    private double price;

    public Book() {
    }

    public Book(final URI uri, final String isbn, final String name, final String author, final String availability, final double price) {
        this.uri = uri;
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.availability = availability;
        this.price = price;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(final URI uri) {
        this.uri = uri;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(final String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(final String availability) {
        this.availability = availability;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "uri='" + uri + '\'' +
                "isbn='" + isbn + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", availability='" + availability + '\'' +
                ", price=" + price +
                '}';
    }

}
