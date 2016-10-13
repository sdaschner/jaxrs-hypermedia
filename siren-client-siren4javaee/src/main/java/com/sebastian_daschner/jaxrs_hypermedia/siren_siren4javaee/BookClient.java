package com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee;

import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.model.Book;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.model.CartItem;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.model.Order;
import com.sebastian_daschner.siren4javaee.Action;
import com.sebastian_daschner.siren4javaee.Entity;
import com.sebastian_daschner.siren4javaee.Siren;
import com.sebastian_daschner.siren4javaee.SirenClient;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Client for Siren API.
 * Encapsulates the Siren client calls and the JSON transformations.
 */
public class BookClient {

    private final EntityMapper entityMapper;
    private final SirenClient client;
    private final URI baseUri;

    public BookClient() {
        entityMapper = new EntityMapper();
        baseUri = URI.create("http://localhost:8080/siren-plain-ee/resources/");
        client = Siren.createClient(ClientBuilder.newClient());
    }

    public List<URI> retrieveBooks() {
        final Entity books = retrieveResourceEntity("books");
        return books.getEntities().stream().map(e -> e.getLink("self")).collect(Collectors.toList());
    }

    public Book retrieveBook(final URI uri) {
        final Entity book = client.retrieveEntity(uri);
        return entityMapper.decodeBook(uri, book.getProperties());
    }

    public List<CartItem> retrieveCart() {
        final Entity cart = retrieveResourceEntity("shopping-cart");

        return cart.getEntities().stream()
                .map(entityMapper::decodeCartItem)
                .collect(Collectors.toList());
    }

    public void addToCart(final Book book, final int quantity) {
        final Entity bookEntity = client.retrieveEntity(book.getUri());

        final JsonObjectBuilder properties = Json.createObjectBuilder();
        entityMapper.encodeBook(book).forEach(properties::add);
        properties.add("quantity", quantity);

        client.performAction(bookEntity, "add-to-cart", properties.build());
    }

    public void updateCartItem(final URI uri, final int quantity) {
        final Entity cartItem = client.retrieveEntity(uri);

        final Action action = cartItem.getAction("modify-book-selection");
        final JsonObject properties = Json.createObjectBuilder().add("quantity", quantity).build();

        client.performAction(action, properties);
    }

    public void deleteCartItem(final URI uri) {
        final Entity cartItem = client.retrieveEntity(uri);

        final Action action = cartItem.getAction("delete-book-selection");
        client.performAction(action);
    }

    public URI checkout() {
        final Entity cart = retrieveResourceEntity("shopping-cart");

        final Response response = client.performAction(cart, "checkout");

        final URI location = response.getLocation();
        if (location == null)
            throw new RuntimeException("client expected a Location URI of the order when checking out");

        return location;
    }

    public Order retrieveOrder(final URI uri) {
        final Entity order = client.retrieveEntity(uri);
        return entityMapper.decodeOrder(order.getProperties(), order.getEntities());
    }

    private Entity retrieveResourceEntity(final String rel) {
        final Entity entity = client.retrieveEntity(baseUri);
        return client.followLink(entity, rel);
    }

}
