package com.sebastian_daschner.jaxrs_hypermedia.siren_plain_ee.business;

import com.sebastian_daschner.jaxrs_hypermedia.siren_plain_ee.business.books.entity.Book;
import com.sebastian_daschner.jaxrs_hypermedia.siren_plain_ee.business.cart.entity.BookSelection;
import com.sebastian_daschner.jaxrs_hypermedia.siren_plain_ee.business.cart.entity.ShoppingCartSelection;
import com.sebastian_daschner.jaxrs_hypermedia.siren_plain_ee.business.orders.entity.Order;

import javax.inject.Inject;
import javax.json.*;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

import static javax.json.Json.createArrayBuilder;
import static javax.json.Json.createObjectBuilder;

public class EntityBuilder {

    @Inject
    LinkBuilder linkBuilder;

    public JsonObject buildRootDocument(UriInfo uriInfo) {
        return createObjectBuilder()
                .add("links", createArrayBuilder()
                        .add(createLinkObject("books", linkBuilder.forBooks(uriInfo)))
                        .add(createLinkObject("shopping-cart", linkBuilder.forShoppingCart(uriInfo)))
                        .add(createLinkObject("orders", linkBuilder.forOrders(uriInfo)))
                ).build();
    }

    public JsonObject buildBooks(List<Book> books, UriInfo uriInfo) {
        final JsonArray bookEntities = books.stream().map(b -> buildBookTeaser(b, uriInfo)).collect(Json::createArrayBuilder, JsonArrayBuilder::add, JsonArrayBuilder::add).build();

        return createObjectBuilder()
                .add("class", createArrayBuilder().add("books"))
                .add("entities", bookEntities)
                .add("links", createArrayBuilder().add(createLinkObject("self", linkBuilder.forBooks(uriInfo))))
                .build();
    }

    private JsonObject buildBookTeaser(Book book, UriInfo uriInfo) {
        return createObjectBuilder()
                .add("rel", createArrayBuilder().add("item"))
                .add("properties", createObjectBuilder()
                        .add("name", book.getName())
                        .add("author", book.getAuthor()))
                .add("links", createArrayBuilder().add(createLinkObject("self", linkBuilder.forBook(book, uriInfo)))).build();
    }

    public JsonObject buildBook(Book book, boolean addToCartActionIncluded, UriInfo uriInfo) {
        final JsonObjectBuilder entityBuilder = createObjectBuilder()
                .add("class", createArrayBuilder().add("book"))
                .add("properties", createObjectBuilder()
                        .add("isbn", book.getIsbn())
                        .add("name", book.getName())
                        .add("author", book.getAuthor())
                        .add("availability", book.getAvailability().toString())
                        .add("price", book.getPrice()))
                .add("links", createArrayBuilder().add(createLinkObject("self", linkBuilder.forBook(book, uriInfo))));

        if (addToCartActionIncluded)
            entityBuilder.add("actions", createArrayBuilder().add(createObjectBuilder()
                    .add("name", "add-to-cart")
                    .add("title", "Add book to cart")
                    .add("method", HttpMethod.POST)
                    .add("href", linkBuilder.forShoppingCart(uriInfo).toString())
                    .add("type", MediaType.APPLICATION_JSON)
                    .add("fields", createArrayBuilder()
                            .add(createObjectBuilder()
                                    .add("name", "isbn")
                                    .add("type", "TEXT")
                                    .add("required", true))
                            .add(createObjectBuilder()
                                    .add("name", "quantity")
                                    .add("type", "NUMBER")
                                    .add("required", true)))));

        return entityBuilder.build();
    }

    public JsonObject buildShoppingCart(ShoppingCartSelection cart, UriInfo uriInfo) {
        final JsonArray selectionEntities = cart.getSelections().stream().map(s -> buildBookSelection(s, uriInfo, true, true)).collect(Json::createArrayBuilder, JsonArrayBuilder::add, JsonArrayBuilder::add).build();

        return createObjectBuilder()
                .add("class", createArrayBuilder().add("shopping-cart"))
                .add("properties", createObjectBuilder().add("price", cart.getPrice()))
                .add("entities", selectionEntities)
                .add("actions", createArrayBuilder().add(createObjectBuilder()
                        .add("name", "checkout")
                        .add("title", "Checkout shopping cart")
                        .add("method", HttpMethod.POST)
                        .add("href", linkBuilder.forOrders(uriInfo).toString())))
                .add("links", createArrayBuilder().add(createLinkObject("self", linkBuilder.forShoppingCart(uriInfo)))).build();
    }

    public JsonObject buildShoppingCartSelection(BookSelection selection, UriInfo uriInfo) {
        return buildBookSelection(selection, uriInfo, true, false);
    }

    public JsonObject buildOrders(List<Order> orders, UriInfo uriInfo) {
        final JsonArray orderEntities = orders.stream().map(o -> buildOrderTeaser(o, uriInfo)).collect(Json::createArrayBuilder, JsonArrayBuilder::add, JsonArrayBuilder::add).build();

        return createObjectBuilder()
                .add("class", createArrayBuilder().add("orders"))
                .add("entities", orderEntities)
                .add("links", createLinkObject("self", linkBuilder.forOrders(uriInfo)))
                .build();
    }

    private JsonObject buildOrderTeaser(Order order, UriInfo uriInfo) {
        return createObjectBuilder()
                .add("rel", createArrayBuilder().add("item"))
                .add("properties", createObjectBuilder()
                        .add("date", order.getDate().toString())
                        .add("price", order.getPrice())
                        .add("status", order.getStatus().toString()))
                .add("links", createArrayBuilder().add(createLinkObject("self", linkBuilder.forOrder(order, uriInfo)))).build();
    }

    public JsonObject buildOrder(Order order, UriInfo uriInfo) {
        final JsonArray selectionEntities = order.getSelections().stream().map(s -> buildBookSelection(s, uriInfo, false, true)).collect(Json::createArrayBuilder, JsonArrayBuilder::add, JsonArrayBuilder::add).build();

        return createObjectBuilder()
                .add("class", createArrayBuilder().add("order"))
                .add("properties", createObjectBuilder()
                        .add("date", order.getDate().toString())
                        .add("price", order.getPrice())
                        .add("status", order.getStatus().toString()))
                .add("entities", selectionEntities)
                .add("links", createArrayBuilder().add(createLinkObject("self", linkBuilder.forOrder(order, uriInfo)))).build();
    }

    private JsonObject buildBookSelection(BookSelection selection, UriInfo uriInfo, boolean hypertextIncluded, boolean asSubEntity) {
        final Book book = selection.getBook();
        final JsonObject bookJsonObject = createObjectBuilder()
                .add("class", createArrayBuilder().add("book"))
                .add("rel", createArrayBuilder().add("item"))
                .add("properties", createObjectBuilder()
                        .add("isbn", book.getIsbn())
                        .add("name", book.getName())
                        .add("price", book.getPrice()))
                .add("links", createArrayBuilder().add(createLinkObject("self", linkBuilder.forBook(book, uriInfo))))
                .build();

        final JsonObjectBuilder selectionBuilder = createObjectBuilder()
                .add("class", createArrayBuilder().add("book-selection"));

        if (asSubEntity)
            selectionBuilder.add("rel", createArrayBuilder().add("item"));

        selectionBuilder.add("properties", createObjectBuilder().add("quantity", selection.getQuantity()).add("price", selection.getPrice()))
                .add("entities", createArrayBuilder().add(bookJsonObject));

        if (hypertextIncluded) {
            selectionBuilder.add("actions", createArrayBuilder()
                    .add(createObjectBuilder()
                            .add("name", "modify-book-selection")
                            .add("title", "Modify book cart selection")
                            .add("method", HttpMethod.PUT)
                            .add("href", linkBuilder.forBookSelection(selection, uriInfo).toString())
                            .add("type", MediaType.APPLICATION_JSON)
                            .add("fields", createArrayBuilder()
                                    .add(createObjectBuilder()
                                            .add("name", "quantity")
                                            .add("type", "NUMBER")
                                            .add("required", true))))
                    .add(createObjectBuilder()
                            .add("name", "delete-book-selection")
                            .add("title", "Delete book cart selection")
                            .add("method", HttpMethod.DELETE)
                            .add("href", linkBuilder.forBookSelection(selection, uriInfo).toString())))
                    .add("links", createArrayBuilder().add(createLinkObject("self", linkBuilder.forBookSelection(selection, uriInfo))));
        }

        return selectionBuilder.build();
    }

    private JsonObject createLinkObject(String relation, URI uri) {
        return createObjectBuilder().add("rel", createArrayBuilder().add(relation)).add("href", uri.toString()).build();
    }
}
