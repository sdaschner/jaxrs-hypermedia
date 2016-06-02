package com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.business.books.boundary;

import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.business.books.entity.Availability;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.business.books.entity.Book;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Stateless
public class BookStore {

    @Inject
    List<Book> books;

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public Book getBook(final long id) {
        return findMatchingBook(b -> b.getId() == id);
    }

    public Book findBook(String isbn) {
        return findMatchingBook(b -> b.getIsbn().equals(isbn));
    }

    private Book findMatchingBook(Predicate<Book> predicate) {
        return books.stream().filter(predicate).findFirst().orElse(null);
    }

    public boolean isAddToCartAllowed(Book book) {
        // could contain business logic about customer, book, etc.
        return book.getAvailability() == Availability.IN_STOCK;
    }

}
