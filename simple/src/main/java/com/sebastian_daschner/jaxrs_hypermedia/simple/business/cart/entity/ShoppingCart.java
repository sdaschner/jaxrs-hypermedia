package com.sebastian_daschner.jaxrs_hypermedia.simple.business.cart.entity;

import java.util.Set;

public class ShoppingCart {

    private double price;
    private Set<BookSelection> selections;

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
