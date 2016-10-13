package com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee;

import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.model.Book;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.model.CartItem;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.model.Order;
import com.sebastian_daschner.siren4javaee.SubEntity;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.Serializable;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EntityMapper {

    public Book decodeBook(final URI uri, final Map<String, Serializable> properties) {
        final String isbn = (String) properties.get("isbn");
        final String name = (String) properties.get("name");
        final String author = (String) properties.get("author");
        final String availability = (String) properties.get("availability");
        final double price = (double) properties.get("price");

        return new Book(uri, isbn, name, author, availability, price);
    }

    public JsonObject encodeBook(final Book book) {
        return Json.createObjectBuilder()
                .add("isbn", book.getIsbn())
                .add("name", book.getName())
                .add("author", book.getAuthor())
                .add("availability", book.getAvailability())
                .add("price", book.getPrice()).build();
    }

    public CartItem decodeCartItem(final SubEntity itemEntity) {
        // FEATURE check Siren class and avoid blindly taking the first
        final URI bookUri = itemEntity.getEntities().get(0).getLink("self");

        return new CartItem(itemEntity.getLink("self"), bookUri, ((Long) itemEntity.getProperties().get("quantity")).intValue());
    }

    public Order decodeOrder(final Map<String, Serializable> properties, final Collection<SubEntity> selections) {
        final LocalDateTime date = LocalDateTime.parse((String) properties.get("date"));
        final String status = (String) properties.get("status");
        final double price = (double) properties.get("price");
        final Map<URI, Integer> bookSelections = new HashMap<>();

        selections.forEach(s -> {
            // FEATURE check Siren class and avoid blindly taking the first
            final URI book = s.getEntities().get(0).getLink("self");
            bookSelections.put(book, ((Long) s.getProperties().get("quantity")).intValue());
        });

        return new Order(date, status, price, bookSelections);
    }

}
