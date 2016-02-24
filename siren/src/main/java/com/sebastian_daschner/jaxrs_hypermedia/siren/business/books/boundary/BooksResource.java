package com.sebastian_daschner.jaxrs_hypermedia.siren.business.books.boundary;

import com.google.code.siren4j.component.Entity;
import com.google.code.siren4j.error.Siren4JException;
import com.sebastian_daschner.jaxrs_hypermedia.siren.business.books.entity.Book;
import com.sebastian_daschner.jaxrs_hypermedia.siren.business.orders.boundary.EntityBuilder;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Stateless
@Path("books")
@Produces(MediaType.APPLICATION_JSON)
public class BooksResource {

    @Context
    UriInfo uriInfo;

    @Inject
    MockBookStore bookStore;

    @Inject
    EntityBuilder entityBuilder;

    @GET
    public Entity getBooks() throws Siren4JException {
        final List<Book> books = bookStore.getBooks();
        return entityBuilder.buildBooks(books, uriInfo);
    }

    @GET
    @Path("{id}")
    public Entity getBook(@PathParam("id") long id) throws Siren4JException {
        final Book book = bookStore.getBook(id);

        final boolean addToCartActionIncluded = bookStore.isAddToCartAllowed(book);

        return entityBuilder.buildBook(book, addToCartActionIncluded, uriInfo);
    }

}
