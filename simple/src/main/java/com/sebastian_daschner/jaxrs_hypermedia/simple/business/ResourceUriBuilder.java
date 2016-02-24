package com.sebastian_daschner.jaxrs_hypermedia.simple.business;

import com.sebastian_daschner.jaxrs_hypermedia.simple.business.books.boundary.BooksResource;
import com.sebastian_daschner.jaxrs_hypermedia.simple.business.books.entity.Book;
import com.sebastian_daschner.jaxrs_hypermedia.simple.business.cart.boundary.CartResource;
import com.sebastian_daschner.jaxrs_hypermedia.simple.business.cart.entity.BookSelection;
import com.sebastian_daschner.jaxrs_hypermedia.simple.business.orders.boundary.OrdersResource;
import com.sebastian_daschner.jaxrs_hypermedia.simple.business.orders.entity.Order;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class ResourceUriBuilder {

    public URI forBook(Book book, UriInfo uriInfo) {
        return createResourceUri(BooksResource.class, "getBook", book.getId(), uriInfo);
    }

    public URI forBookSelection(BookSelection selection, UriInfo uriInfo) {
        return createResourceUri(CartResource.class, "updateSelection", selection.getId(), uriInfo);
    }

    public URI forOrder(Order order, UriInfo uriInfo) {
        return createResourceUri(OrdersResource.class, "getOrder", order.getId(), uriInfo);
    }

    public URI forShoppingCart(UriInfo uriInfo) {
        return createResourceUri(CartResource.class, uriInfo);
    }

    public URI forCheckout(UriInfo uriInfo) {
        return createResourceUri(OrdersResource.class, uriInfo);
    }

    private URI createResourceUri(Class<?> resourceClass, String method, long id, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path(resourceClass).path(resourceClass, method).build(id);
    }

    private URI createResourceUri(Class<?> resourceClass, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path(resourceClass).build();
    }

}
