package com.sebastian_daschner.jaxrs_hypermedia.siren_siren4j.business.cart.entity;

import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4j.business.books.entity.Book;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class BookSelection extends Selection {

    @XmlTransient
    private long id;

    @NotNull
    @XmlJavaTypeAdapter(IsbnBookXmlAdapter.class)
    @XmlElement(name = "isbn")
    private Book book;

    @XmlTransient
    private double price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
