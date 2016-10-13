package com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.model;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Order {

    private LocalDateTime date;
    private String status;
    private double price;
    private Map<URI, Integer> bookSelections = new HashMap<>();

    public Order() {
    }

    public Order(final LocalDateTime date, final String status, final double price, final Map<URI, Integer> bookSelections) {
        this.date = date;
        this.status = status;
        this.price = price;
        this.bookSelections = bookSelections;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(final LocalDateTime date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public Map<URI, Integer> getBookSelections() {
        return bookSelections;
    }

    public void setBookSelections(final Map<URI, Integer> bookSelections) {
        this.bookSelections = bookSelections;
    }

    @Override
    public String toString() {
        return "Order{" +
                "date=" + date +
                ", status='" + status + '\'' +
                ", price=" + price +
                ", bookSelections=" + bookSelections +
                '}';
    }

}
