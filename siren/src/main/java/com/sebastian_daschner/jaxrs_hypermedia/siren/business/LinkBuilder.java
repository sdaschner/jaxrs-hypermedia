package com.sebastian_daschner.jaxrs_hypermedia.siren.business;

import com.google.code.siren4j.component.Link;
import com.sebastian_daschner.jaxrs_hypermedia.siren.business.books.boundary.BooksResource;
import com.sebastian_daschner.jaxrs_hypermedia.siren.business.books.entity.Book;
import com.sebastian_daschner.jaxrs_hypermedia.siren.business.cart.boundary.CartResource;
import com.sebastian_daschner.jaxrs_hypermedia.siren.business.cart.entity.BookSelection;
import com.sebastian_daschner.jaxrs_hypermedia.siren.business.orders.boundary.OrdersResource;
import com.sebastian_daschner.jaxrs_hypermedia.siren.business.orders.entity.Order;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

import static com.google.code.siren4j.component.builder.LinkBuilder.createLinkBuilder;

public class LinkBuilder {

    private static final String SELF_REL = "self";

    public Link forBook(Book book, UriInfo uriInfo) {
        return createResourceUri(BooksResource.class, "getBook", book.getId(), uriInfo);
    }

    public Link forBookSelection(BookSelection selection, UriInfo uriInfo) {
        return createResourceUri(CartResource.class, "updateSelection", selection.getId(), uriInfo);
    }

    public Link forOrder(Order order, UriInfo uriInfo) {
        return createResourceUri(OrdersResource.class, "getOrder", order.getId(), uriInfo);
    }

    public Link forBooks(UriInfo uriInfo) {
        return forBooks(uriInfo, SELF_REL);
    }

    public Link forBooks(UriInfo uriInfo, String relation) {
        return createResourceUri(BooksResource.class, relation, uriInfo);
    }

    public Link forOrders(UriInfo uriInfo) {
        return forOrders(uriInfo, SELF_REL);
    }

    public Link forOrders(UriInfo uriInfo, String relation) {
        return createResourceUri(OrdersResource.class, relation, uriInfo);
    }

    public Link forShoppingCart(UriInfo uriInfo) {
        return forShoppingCart(uriInfo, SELF_REL);
    }

    public Link forShoppingCart(UriInfo uriInfo, String relation) {
        return createResourceUri(CartResource.class, relation, uriInfo);
    }

    private Link createResourceUri(Class<?> resourceClass, String method, long id, UriInfo uriInfo) {
        final URI uri = uriInfo.getBaseUriBuilder().path(resourceClass).path(resourceClass, method).build(id);
        return createLinkBuilder().setHref(uri.toString()).setRelationship("self").build();
    }

    private Link createResourceUri(Class<?> resourceClass, String relation, UriInfo uriInfo) {
        final URI uri = uriInfo.getBaseUriBuilder().path(resourceClass).build();
        return createLinkBuilder().setHref(uri.toString()).setRelationship(relation).build();
    }

}
