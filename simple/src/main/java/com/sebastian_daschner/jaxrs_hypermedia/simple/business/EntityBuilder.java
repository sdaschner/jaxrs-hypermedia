package com.sebastian_daschner.jaxrs_hypermedia.simple.business;

import com.sebastian_daschner.jaxrs_hypermedia.simple.business.books.entity.Book;
import com.sebastian_daschner.jaxrs_hypermedia.simple.business.cart.entity.BookSelection;
import com.sebastian_daschner.jaxrs_hypermedia.simple.business.cart.entity.ShoppingCart;
import com.sebastian_daschner.jaxrs_hypermedia.simple.business.orders.entity.Order;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.net.URI;

import static javax.json.Json.createObjectBuilder;

public class EntityBuilder {

    public JsonObject buildBookTeaser(Book book, URI self) {
        final JsonObjectBuilder builder = createObjectBuilder();

        builder.add("name", book.getName());
        builder.add("author", book.getAuthor());
        builder.add("_links", createObjectBuilder().add("self", self.toString()));

        return builder.build();
    }

    public JsonObject buildBook(Book book, URI self, URI addToCart) {
        final JsonObjectBuilder builder = createObjectBuilder();

        builder.add("isbn", book.getIsbn());
        builder.add("name", book.getName());
        builder.add("author", book.getAuthor());
        builder.add("availability", book.getAvailability().name());
        builder.add("price", book.getPrice());

        final JsonObjectBuilder linkBuilder = createObjectBuilder();
        linkBuilder.add("self", self.toString());
        if (addToCart != null)
            linkBuilder.add("add-to-cart", addToCart.toString());

        builder.add("_links", linkBuilder);

        return builder.build();
    }

    public JsonObject buildShoppingCart(ShoppingCart cart, JsonArray selections, URI checkout) {

        return Json.createObjectBuilder()
                .add("price", cart.getPrice())
                .add("selections", selections)
                .add("_links", createObjectBuilder().add("checkout", checkout.toString()))
                .build();
    }

    public JsonObject buildBookSelection(BookSelection bookSelection, URI selection, URI book) {
        return Json.createObjectBuilder()
                .add("quantity", bookSelection.getQuantity())
                .add("price", bookSelection.getPrice())
                .add("book", buildBookSelectionBook(bookSelection.getBook(), book))
                .add("_links", createObjectBuilder().add("modify-selection", selection.toString()))
                .build();
    }

    public JsonObject buildBookSelection(BookSelection bookSelection, URI book) {
        return Json.createObjectBuilder()
                .add("quantity", bookSelection.getQuantity())
                .add("price", bookSelection.getPrice())
                .add("book", buildBookSelectionBook(bookSelection.getBook(), book))
                .build();
    }

    private static JsonObject buildBookSelectionBook(Book book, URI self) {
        final JsonObjectBuilder builder = createObjectBuilder();

        builder.add("isbn", book.getIsbn());
        builder.add("name", book.getName());
        builder.add("price", book.getPrice());
        builder.add("_links", createObjectBuilder().add("self", self.toString()));

        return builder.build();
    }

    public JsonObject buildOrderTeaser(Order order, URI self) {
        final JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("date", order.getDate().toString());
        builder.add("price", order.getPrice());
        builder.add("status", order.getStatus().name());
        builder.add("_links", Json.createObjectBuilder().add("self", self.toString()));

        return builder.build();
    }

    public JsonObject buildOrder(Order order, JsonArray selections) {
        return Json.createObjectBuilder()
                .add("date", order.getDate().toString())
                .add("price", order.getPrice())
                .add("status", order.getStatus().name())
                .add("selections", selections)
                .build();
    }
}
