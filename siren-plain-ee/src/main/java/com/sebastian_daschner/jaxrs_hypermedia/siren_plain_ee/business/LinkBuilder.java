package com.sebastian_daschner.jaxrs_hypermedia.siren_plain_ee.business;

import com.sebastian_daschner.jaxrs_hypermedia.siren_plain_ee.business.books.boundary.BooksResource;
import com.sebastian_daschner.jaxrs_hypermedia.siren_plain_ee.business.books.entity.Book;
import com.sebastian_daschner.jaxrs_hypermedia.siren_plain_ee.business.cart.boundary.CartResource;
import com.sebastian_daschner.jaxrs_hypermedia.siren_plain_ee.business.cart.entity.BookSelection;
import com.sebastian_daschner.jaxrs_hypermedia.siren_plain_ee.business.orders.boundary.OrdersResource;
import com.sebastian_daschner.jaxrs_hypermedia.siren_plain_ee.business.orders.entity.Order;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class LinkBuilder {

    public URI forBook(Book book, UriInfo uriInfo) {
        return createResourceUri(BooksResource.class, "getBook", book.getId(), uriInfo);
    }

    public URI forBookSelection(BookSelection selection, UriInfo uriInfo) {
        return createResourceUri(CartResource.class, "updateSelection", selection.getId(), uriInfo);
    }

    public URI forOrder(Order order, UriInfo uriInfo) {
        return createResourceUri(OrdersResource.class, "getOrder", order.getId(), uriInfo);
    }

    public URI forBooks(UriInfo uriInfo) {
        return createResourceUri(BooksResource.class, uriInfo);
    }

    public URI forOrders(UriInfo uriInfo) {
        return createResourceUri(OrdersResource.class, uriInfo);
    }

    public URI forShoppingCart(UriInfo uriInfo) {
        return createResourceUri(CartResource.class, uriInfo);
    }

    private URI createResourceUri(Class<?> resourceClass, String method, long id, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path(resourceClass).path(resourceClass, method).build(id);
    }

    private URI createResourceUri(Class<?> resourceClass, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path(resourceClass).build();
    }

}
