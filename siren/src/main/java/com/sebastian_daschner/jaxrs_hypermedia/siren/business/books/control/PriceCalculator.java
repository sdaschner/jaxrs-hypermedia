package com.sebastian_daschner.jaxrs_hypermedia.siren.business.books.control;

import com.sebastian_daschner.jaxrs_hypermedia.siren.business.cart.entity.BookSelection;

import java.util.Set;

public class PriceCalculator {

    public double calculatePrice(BookSelection selection) {
        return selection.getBook().getPrice() * selection.getQuantity();
    }

    public double calculateTotal(Set<BookSelection> selections) {
        return selections.stream().mapToDouble(s -> {
            final double price = s.getBook().getPrice() * s.getQuantity();
            s.setPrice(price);
            return price;
        }).sum();
    }

}
