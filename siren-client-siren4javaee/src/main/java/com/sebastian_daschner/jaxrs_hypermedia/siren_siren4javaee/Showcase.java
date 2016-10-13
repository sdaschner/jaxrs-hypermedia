package com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee;

import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.model.Book;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.model.CartItem;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.model.Order;

import java.util.List;

/**
 * Showcase for Siren API usage.
 * Encapsulates the business logic.
 */
public class Showcase {

    private BookShopping shopping = new BookShopping();

    public void run() {
        final String isbn1 = "1-2345-3456-1";
        Book book = shopping.findBook(isbn1);
        System.out.println("found book = " + book);

        shopping.addToCart(book);
        System.out.println("added book to cart");

        final String isbn2 = "1-2345-3456-2";
        book = shopping.findBook(isbn2);
        System.out.println("found book = " + book);

        shopping.addToCart(book);
        System.out.println("added book to cart");

        List<CartItem> cartItems = shopping.getCartItems();
        CartItem cartItem = cartItems.stream().filter(b -> isbn1.equals(b.getBook().getIsbn())).findAny().get();
        cartItem.setQuantity(2);
        shopping.updateCartItem(cartItem);
        System.out.println("updated cartItem = " + cartItem + " to contain 2");

        cartItems = shopping.getCartItems();
        cartItem = cartItems.stream().filter(b -> isbn2.equals(b.getBook().getIsbn())).findAny().get();
        shopping.deleteCartItem(cartItem);
        System.out.println("removed cartItem = " + cartItem);

        final Order order = shopping.checkoutCart();
        System.out.println("checked out; order = " + order);
    }

    public static void main(String[] args) {
        new Showcase().run();
    }

}
