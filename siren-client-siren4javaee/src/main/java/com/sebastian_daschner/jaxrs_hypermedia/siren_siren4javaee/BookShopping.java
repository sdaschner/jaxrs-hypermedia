package com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee;

import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.model.Book;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.model.CartItem;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.model.Order;

import java.net.URI;
import java.util.List;

/**
 * Business facade for book shopping.
 * Encapsulates multiple client calls within one use-case.
 */
public class BookShopping {

    private BookClient client = new BookClient();

    public Book findBook(final String isbn) {
        final List<URI> books = client.retrieveBooks();
        return books.stream()
                .map(client::retrieveBook)
                .filter(b -> isbn.equals(b.getIsbn()))
                .findAny().orElse(null);
    }

    public void addToCart(final Book book) {
        addToCart(book, 1);
    }

    public void addToCart(final Book book, final int quantity) {
        client.addToCart(book, quantity);
    }

    public List<CartItem> getCartItems() {
        final List<CartItem> cartItemTeasers = client.retrieveCart();
        cartItemTeasers.forEach(t -> {
            final Book book = client.retrieveBook(t.getBookUri());
            t.setBook(book);
        });
        return cartItemTeasers;
    }

    public void updateCartItem(final CartItem cartItem) {
        client.updateCartItem(cartItem.getUri(), cartItem.getQuantity());
    }

    public void deleteCartItem(final CartItem cartItem) {
        client.deleteCartItem(cartItem.getUri());
    }

    public Order checkoutCart() {
        final URI orderId = client.checkout();
        return client.retrieveOrder(orderId);
    }

}
