package com.sebastian_daschner.jaxrs_hypermedia.siren.business.cart.boundary;

import com.sebastian_daschner.jaxrs_hypermedia.siren.business.books.boundary.MockBookStore;
import com.sebastian_daschner.jaxrs_hypermedia.siren.business.books.entity.Book;
import com.sebastian_daschner.jaxrs_hypermedia.siren.business.cart.entity.BookSelection;
import com.sebastian_daschner.jaxrs_hypermedia.siren.business.cart.entity.ShoppingCart;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.stream.Collectors;

@Stateless
public class MockShoppingCart {

    @Inject
    MockBookStore bookStore;

    public void addBookSelection(BookSelection selection) {
        // book selection would be stored in session for user etc.
    }

    public void updateBookSelection(long selectionId, int quantity) {
        // book selection retrieved from id and quantity updated for session
    }

    public ShoppingCart getShoppingCart() {
        final ShoppingCart cart = new ShoppingCart();
        cart.setSelections(bookStore.getBooks().stream().map(this::createBookSelection).collect(Collectors.toSet()));
        cart.setPrice(cart.getSelections().stream().mapToDouble(BookSelection::getPrice).sum());
        return cart;
    }

    private BookSelection createBookSelection(Book book) {
        final BookSelection selection = new BookSelection();
        selection.setId(123L);
        selection.setBook(book);
        selection.setQuantity(2);
        selection.setPrice(book.getPrice() * selection.getQuantity());
        return selection;
    }

}
