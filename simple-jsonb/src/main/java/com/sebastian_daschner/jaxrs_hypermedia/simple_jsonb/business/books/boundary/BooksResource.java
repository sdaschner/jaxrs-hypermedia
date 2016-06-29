package com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.books.boundary;

import com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.ResourceUriBuilder;
import com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.books.entity.Book;
import com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.books.entity.BookTeaser;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Stateless
@Path("books")
@Produces(MediaType.APPLICATION_JSON)
public class BooksResource {

    @Inject
    MockBookStore bookStore;

    @Inject
    ResourceUriBuilder resourceUriBuilder;

    @Context
    UriInfo uriInfo;

    // JAX-RS 2.0 obviously isn't integrated with JSONB yet
    private Jsonb jsonb;

    @PostConstruct
    public void initJsonb() {
        jsonb = JsonbBuilder.create();
    }

    @GET
    public StreamingOutput getBooks() {
        final List<BookTeaser> books = bookStore.getBookTeasers();
        books.forEach(b -> b.getLinks().put("self", resourceUriBuilder.forBook(b.getId(), uriInfo)));

        return output -> jsonb.toJson(books, output);
    }

    @GET
    @Path("{id}")
    public StreamingOutput getBook(@PathParam("id") long id) {
        final Book book = bookStore.getBook(id);
        book.getLinks().put("self", resourceUriBuilder.forBook(book.getId(), uriInfo));

        if (bookStore.isAddToCartAllowed(book))
            book.getLinks().put("add-to-cart", resourceUriBuilder.forShoppingCart(uriInfo));

        return output -> jsonb.toJson(book, output);
    }

}
