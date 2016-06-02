package com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.business;

import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.business.books.entity.Availability;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.business.books.entity.Book;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.util.Arrays;
import java.util.List;

public class MockObjectExposer {

    @Produces
    @ApplicationScoped
    public List<Book> exposeBooks() {
        return Arrays.asList(newBook(1L), newBook(2L), newBook(3L));
    }

    private static Book newBook(final long id) {
        Book book = new Book();
        book.setId(id);
        book.setIsbn("1-2345-3456-" + id);
        book.setName("Hello World");
        book.setAuthor("Duke");
        book.setAvailability(Availability.IN_STOCK);
        book.setPrice(9.99d);
        return book;
    }

}
