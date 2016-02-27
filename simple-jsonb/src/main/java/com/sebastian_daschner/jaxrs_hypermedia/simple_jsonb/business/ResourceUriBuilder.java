package com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business;

import com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.books.boundary.BooksResource;
import com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.cart.boundary.CartResource;
import com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.orders.boundary.OrdersResource;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class ResourceUriBuilder {

    public URI forBook(long id, UriInfo uriInfo) {
        return createResourceUri(BooksResource.class, "getBook", id, uriInfo);
    }

    public URI forBookSelection(long id, UriInfo uriInfo) {
        return createResourceUri(CartResource.class, "updateSelection", id, uriInfo);
    }

    public URI forOrder(long id, UriInfo uriInfo) {
        return createResourceUri(OrdersResource.class, "getOrder", id, uriInfo);
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
