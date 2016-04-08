package com.sebastian_daschner.jaxrs_hypermedia.siren_plain_ee.business.books.boundary;

import com.sebastian_daschner.jaxrs_hypermedia.siren_plain_ee.business.EntityBuilder;
import com.sebastian_daschner.jaxrs_hypermedia.siren_plain_ee.business.books.entity.Book;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
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
    BookStore bookStore;

    @Inject
    EntityBuilder entityBuilder;

    @GET
    public JsonObject getBooks() {
        final List<Book> books = bookStore.getBooks();
        return entityBuilder.buildBooks(books, uriInfo);
    }

    @GET
    @Path("{id}")
    public JsonObject getBook(@PathParam("id") long id) {
        final Book book = bookStore.getBook(id);

        if (book == null)
            throw new NotFoundException();

        final boolean addToCartActionIncluded = bookStore.isAddToCartAllowed(book);

        return entityBuilder.buildBook(book, addToCartActionIncluded, uriInfo);
    }

}
