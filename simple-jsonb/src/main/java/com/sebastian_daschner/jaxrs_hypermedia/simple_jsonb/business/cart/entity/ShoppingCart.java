package com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.cart.entity;

import javax.json.bind.annotation.JsonbProperty;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ShoppingCart {

    private double price;
    private Set<BookSelection> selections;
    @JsonbProperty("_links")
    private Map<String, URI> links = new HashMap<>();

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

    public Map<String, URI> getLinks() {
        return links;
    }

    public void setLinks(Map<String, URI> links) {
        this.links = links;
    }

}
