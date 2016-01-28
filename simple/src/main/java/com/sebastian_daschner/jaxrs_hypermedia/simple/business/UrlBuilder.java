package com.sebastian_daschner.jaxrs_hypermedia.simple.business;

import com.sebastian_daschner.jaxrs_hypermedia.simple.business.books.boundary.BooksResource;
import com.sebastian_daschner.jaxrs_hypermedia.simple.business.books.entity.Book;
import com.sebastian_daschner.jaxrs_hypermedia.simple.business.cart.boundary.CartResource;
import com.sebastian_daschner.jaxrs_hypermedia.simple.business.cart.entity.BookSelection;
import com.sebastian_daschner.jaxrs_hypermedia.simple.business.orders.boundary.OrdersResource;
import com.sebastian_daschner.jaxrs_hypermedia.simple.business.orders.entity.Order;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.UriInfo;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlBuilder {

    public URL forBook(Book book, UriInfo uriInfo) {
        return createResourceUrl(BooksResource.class, "getBook", book.getId(), uriInfo);
    }

    public URL forBookSelection(BookSelection selection, UriInfo uriInfo) {
        return createResourceUrl(CartResource.class, "updateSelection", selection.getId(), uriInfo);
    }

    public URL forOrder(Order order, UriInfo uriInfo) {
        return createResourceUrl(OrdersResource.class, "getOrder", order.getId(), uriInfo);
    }

    public URL forShoppingCart(UriInfo uriInfo) {
        return createResourceUrl(CartResource.class, uriInfo);
    }

    public URL forCheckout(UriInfo uriInfo) {
        return createResourceUrl(OrdersResource.class, uriInfo);
    }

    private URL createResourceUrl(Class<?> resourceClass, String method, long id, UriInfo uriInfo) {
        try {
            return uriInfo.getBaseUriBuilder().path(resourceClass).path(resourceClass, method).build(id).toURL();
        } catch (MalformedURLException e) {
            throw new WebApplicationException(e);
        }
    }

    private URL createResourceUrl(Class<?> resourceClass, UriInfo uriInfo) {
        try {
            return uriInfo.getBaseUriBuilder().path(resourceClass).build().toURL();
        } catch (MalformedURLException e) {
            throw new WebApplicationException(e);
        }
    }

}
