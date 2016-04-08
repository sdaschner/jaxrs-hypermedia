package com.sebastian_daschner.jaxrs_hypermedia.siren_plain_ee.business.cart.entity;

import java.util.HashSet;
import java.util.Set;

public class ShoppingCartSelection {

    private double price;
    private Set<BookSelection> selections = new HashSet<>();

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
