package com.sebastian_daschner.jaxrs_hypermedia.siren.business.orders.entity;

import com.sebastian_daschner.jaxrs_hypermedia.siren.business.cart.entity.BookSelection;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Order {

    private long id;
    private LocalDateTime date;
    private OrderStatus status;
    private double price;

    private Set<BookSelection> selections = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<BookSelection> getSelections() {
        return selections;
    }

    public void setSelections(Set<BookSelection> selections) {
        this.selections = selections;
    }

}
