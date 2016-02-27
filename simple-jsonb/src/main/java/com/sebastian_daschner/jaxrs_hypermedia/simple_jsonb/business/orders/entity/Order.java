package com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.orders.entity;

import com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.cart.entity.BookSelection;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Order {

    @JsonbTransient
    private long id;
    private LocalDateTime date;
    private OrderStatus status;
    private double price;
    private Set<BookSelection> selections;
    @JsonbProperty("_links")
    private Map<String, URI> links = new HashMap<>();

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

    public Map<String, URI> getLinks() {
        return links;
    }

    public void setLinks(Map<String, URI> links) {
        this.links = links;
    }

}
