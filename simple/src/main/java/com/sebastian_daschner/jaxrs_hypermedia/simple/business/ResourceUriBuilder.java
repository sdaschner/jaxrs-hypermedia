package com.sebastian_daschner.jaxrs_hypermedia.simple.business;

import com.sebastian_daschner.jaxrs_hypermedia.simple.business.books.boundary.BooksResource;
import com.sebastian_daschner.jaxrs_hypermedia.simple.business.books.entity.Book;
import com.sebastian_daschner.jaxrs_hypermedia.simple.business.cart.boundary.CartResource;
import com.sebastian_daschner.jaxrs_hypermedia.simple.business.cart.entity.BookSelection;
import com.sebastian_daschner.jaxrs_hypermedia.simple.business.orders.boundary.OrdersResource;
import com.sebastian_daschner.jaxrs_hypermedia.simple.business.orders.entity.Order;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class ResourceUriBuilder {

    public URI forBook(Book book) {
        return createResourceUrl(BooksResource.class, "getBook", book.getId());
    }

    public URI forBookSelection(BookSelection selection) {
        return createResourceUrl(CartResource.class, "updateSelection", selection.getId());
    }

    public URI forOrder(Order order) {
        return createResourceUrl(OrdersResource.class, "getOrder", order.getId());
    }

    public URI forShoppingCart() {
        return UriBuilder.fromResource(CartResource.class).build();
    }

    public URI forCheckout() {
        return UriBuilder.fromResource(OrdersResource.class).build();
    }

    private static URI createResourceUrl(Class<?> resourceClass, String method, long id) {
        return UriBuilder.fromResource(resourceClass).path(resourceClass, method).build(id);
    }

}
