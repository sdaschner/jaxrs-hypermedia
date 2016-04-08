package com.sebastian_daschner.jaxrs_hypermedia.siren_plain_ee.business.books.entity;

public class Book {

    private long id;
    private String isbn;
    private String name;
    private String author;
    private Availability availability;
    // just an example; money calculation with floating point numbers is a bad idea in practice
    private double price;

    public Book() {
    }

    public Book(final Book book) {
        this.id = book.id;
        this.isbn = book.isbn;
        this.name = book.name;
        this.author = book.author;
        this.availability = book.availability;
        this.price = book.price;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != book.id) return false;
        if (Double.compare(book.price, price) != 0) return false;
        if (isbn != null ? !isbn.equals(book.isbn) : book.isbn != null) return false;
        if (name != null ? !name.equals(book.name) : book.name != null) return false;
        if (author != null ? !author.equals(book.author) : book.author != null) return false;
        return availability == book.availability;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (availability != null ? availability.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

}
