package com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.business;

import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.business.books.entity.Book;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.business.cart.entity.BookSelection;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.business.cart.entity.ShoppingCartSelection;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.business.orders.entity.Order;
import com.sebastian_daschner.siren4javaee.FieldType;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;

import static com.sebastian_daschner.siren4javaee.Siren.createActionBuilder;
import static com.sebastian_daschner.siren4javaee.Siren.createEntityBuilder;

public class EntityBuilder {

    @Inject
    LinkBuilder linkBuilder;

    public JsonObject buildRootDocument(UriInfo uriInfo) {
        return createEntityBuilder()
                .addLink(linkBuilder.forBooks(uriInfo), "books")
                .addLink(linkBuilder.forShoppingCart(uriInfo), "shopping-cart")
                .addLink(linkBuilder.forOrders(uriInfo), "orders").build();
    }

    public JsonObject buildBooks(List<Book> books, UriInfo uriInfo) {
        final com.sebastian_daschner.siren4javaee.EntityBuilder entityBuilder = createEntityBuilder()
                .addClass("books")
                .addLink(linkBuilder.forBooks(uriInfo), "self");

        books.stream().map(b -> buildBookTeaser(b, uriInfo)).forEach(entityBuilder::addEntity);

        return entityBuilder.build();
    }

    private JsonObject buildBookTeaser(Book book, UriInfo uriInfo) {
        return createEntityBuilder()
                .addSubEntityRel("item")
                .addProperty("name", book.getName())
                .addProperty("author", book.getAuthor())
                .addLink(linkBuilder.forBook(book, uriInfo), "self")
                .build();
    }

    public JsonObject buildBook(Book book, boolean addToCartActionIncluded, UriInfo uriInfo) {
        final com.sebastian_daschner.siren4javaee.EntityBuilder entityBuilder = createEntityBuilder()
                .addClass("book")
                .addProperty("isbn", book.getIsbn())
                .addProperty("name", book.getName())
                .addProperty("author", book.getAuthor())
                .addProperty("availability", book.getAvailability().toString())
                .addProperty("price", book.getPrice())
                .addLink(linkBuilder.forBook(book, uriInfo), "self");

        if (addToCartActionIncluded)
            entityBuilder.addAction(createActionBuilder()
                    .setName("add-to-cart")
                    .setTitle("Add book to cart")
                    .setMethod(HttpMethod.POST)
                    .setHref(linkBuilder.forShoppingCart(uriInfo))
                    .setType(MediaType.APPLICATION_JSON)
                    .addField("isbn", FieldType.TEXT)
                    .addField("quantity", FieldType.NUMBER));

        return entityBuilder.build();
    }

    public JsonObject buildShoppingCart(ShoppingCartSelection cart, UriInfo uriInfo) {
        final com.sebastian_daschner.siren4javaee.EntityBuilder entityBuilder = createEntityBuilder()
                .addClass("shopping-cart")
                .addProperty("price", cart.getPrice())
                .addAction(createActionBuilder()
                        .setName("checkout")
                        .setTitle("Checkout shopping cart")
                        .setMethod(HttpMethod.POST)
                        .setHref(linkBuilder.forShoppingCart(uriInfo)))
                .addLink(linkBuilder.forShoppingCart(uriInfo), "self");

        cart.getSelections().stream().map(s -> buildBookSelection(s, uriInfo, true)).forEach(entityBuilder::addEntity);

        return entityBuilder.build();
    }

    public JsonObject buildOrders(List<Order> orders, UriInfo uriInfo) {
        final com.sebastian_daschner.siren4javaee.EntityBuilder entityBuilder = createEntityBuilder()
                .addClass("orders")
                .addLink(linkBuilder.forOrders(uriInfo), "self");

        orders.stream().map(o -> buildOrderTeaser(o, uriInfo)).forEach(entityBuilder::addEntity);

        return entityBuilder.build();
    }

    private JsonObject buildOrderTeaser(Order order, UriInfo uriInfo) {
        return createPreparedOrderBuilder(order, uriInfo).addSubEntityRel("item").build();
    }

    public JsonObject buildOrder(Order order, UriInfo uriInfo) {
        final com.sebastian_daschner.siren4javaee.EntityBuilder entityBuilder = createPreparedOrderBuilder(order, uriInfo).addClass("order");

        order.getSelections().stream().map(s -> buildBookSelection(s, uriInfo, false)).forEach(entityBuilder::addEntity);

        return entityBuilder.build();
    }

    private com.sebastian_daschner.siren4javaee.EntityBuilder createPreparedOrderBuilder(final Order order, final UriInfo uriInfo) {
        return createEntityBuilder()
                .addProperty("date", order.getDate().toString())
                .addProperty("price", order.getPrice())
                .addProperty("status", order.getStatus().toString())
                .addLink(linkBuilder.forOrder(order, uriInfo), "self");
    }

    private JsonObject buildBookSelection(BookSelection selection, UriInfo uriInfo, boolean actionsIncluded) {
        final Book book = selection.getBook();
        final com.sebastian_daschner.siren4javaee.EntityBuilder entityBuilder = createEntityBuilder()
                .addClass("book-selection")
                .addSubEntityRel("item")
                .addProperty("quantity", selection.getQuantity())
                .addProperty("price", selection.getPrice())
                .addEntity(createEntityBuilder()
                        .addClass("book")
                        .addSubEntityRel("item")
                        .addProperty("isbn", book.getIsbn())
                        .addProperty("name", book.getName())
                        .addProperty("price", book.getPrice())
                        .addLink(linkBuilder.forBook(book, uriInfo), "self"));

        if (actionsIncluded) {
            entityBuilder
                    .addAction(createActionBuilder()
                            .setName("modify-book-selection")
                            .setTitle("Modify book cart selection")
                            .setMethod(HttpMethod.PUT)
                            .setHref(linkBuilder.forBookSelection(selection, uriInfo))
                            .setType(MediaType.APPLICATION_JSON)
                            .addField("quantity", FieldType.NUMBER))
                    .addAction(createActionBuilder()
                            .setName("delete-book-selection")
                            .setTitle("Delete book cart selection")
                            .setMethod(HttpMethod.DELETE)
                            .setHref(linkBuilder.forBookSelection(selection, uriInfo)));
        }
        return entityBuilder.build();
    }

}
