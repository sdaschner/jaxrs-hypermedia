package com.sebastian_daschner.jaxrs_hypermedia.siren_siren4j.business.cart.boundary;

import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4j.business.books.boundary.BookStore;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4j.business.books.control.PriceCalculator;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4j.business.cart.control.ShoppingCart;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4j.business.cart.entity.BookSelection;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4j.business.cart.entity.ShoppingCartSelection;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Set;

@Stateless
public class ShoppingStore {

    @Inject
    BookStore bookStore;

    @Inject
    ShoppingCart shoppingCart;

    @Inject
    PriceCalculator priceCalculator;

    public ShoppingCartSelection getShoppingCartSelection() {
        final Set<BookSelection> selections = shoppingCart.getSelections();

        final ShoppingCartSelection cartSelection = new ShoppingCartSelection();
        cartSelection.setSelections(selections);
        cartSelection.setPrice(priceCalculator.calculateTotal(selections));

        return cartSelection;
    }

    public BookSelection getSelection(long selectionId) {
        return shoppingCart.getSelection(selectionId);
    }

    public void addBookSelection(BookSelection selection) {
        shoppingCart.addBookSelection(selection);
    }

    public void updateBookSelection(long selectionId, int quantity) {
        shoppingCart.updateBookSelection(selectionId, quantity);
    }
}
