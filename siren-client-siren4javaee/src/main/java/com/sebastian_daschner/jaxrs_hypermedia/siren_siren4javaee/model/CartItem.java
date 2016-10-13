package com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.model;

import java.net.URI;

public class CartItem {

    private URI uri;
    private URI bookUri;
    private Book book;
    private int quantity;

    public CartItem() {
    }

    public CartItem(final URI uri, final URI bookUri, final int quantity) {
        this.uri = uri;
        this.bookUri = bookUri;
        this.quantity = quantity;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(final URI uri) {
        this.uri = uri;
    }

    public URI getBookUri() {
        return bookUri;
    }

    public void setBookUri(final URI bookUri) {
        this.bookUri = bookUri;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(final Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "uri=" + uri +
                ", bookUri=" + bookUri +
                ", book=" + book +
                ", quantity=" + quantity +
                '}';
    }

}
