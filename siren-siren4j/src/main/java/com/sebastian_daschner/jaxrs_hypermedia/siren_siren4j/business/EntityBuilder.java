package com.sebastian_daschner.jaxrs_hypermedia.siren_siren4j.business;

import com.google.code.siren4j.component.Entity;
import com.google.code.siren4j.component.impl.ActionImpl;
import com.google.code.siren4j.meta.FieldType;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4j.business.books.entity.Book;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4j.business.cart.entity.BookSelection;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4j.business.cart.entity.ShoppingCartSelection;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4j.business.orders.entity.Order;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.code.siren4j.component.builder.ActionBuilder.createActionBuilder;
import static com.google.code.siren4j.component.builder.EntityBuilder.createEntityBuilder;
import static com.google.code.siren4j.component.builder.FieldBuilder.createFieldBuilder;

public class EntityBuilder {

    @Inject
    LinkBuilder linkBuilder;

    public Entity buildBooks(List<Book> books, UriInfo uriInfo) {
        final List<Entity> bookEntities = books.stream().map(b -> buildBookTeaser(b, uriInfo)).collect(Collectors.toList());

        return createEntityBuilder().setComponentClass("books")
                .addLink(linkBuilder.forBooks(uriInfo))
                .addSubEntities(bookEntities).build();
    }

    private Entity buildBookTeaser(Book book, UriInfo uriInfo) {
        return createEntityBuilder().setRelationship("item")
                .addLink(linkBuilder.forBook(book, uriInfo))
                .addProperty("name", book.getName())
                .addProperty("author", book.getAuthor()).build();
    }

    public Entity buildBook(Book book, boolean addToCartActionIncluded, UriInfo uriInfo) {
        final com.google.code.siren4j.component.builder.EntityBuilder entityBuilder = createEntityBuilder().setComponentClass("book")
                .addLink(linkBuilder.forBook(book, uriInfo))
                .addProperty("isbn", book.getIsbn())
                .addProperty("name", book.getName())
                .addProperty("author", book.getAuthor())
                .addProperty("availability", book.getAvailability())
                .addProperty("price", book.getPrice());


        if (addToCartActionIncluded)
            entityBuilder.addAction(createActionBuilder().setName("add-to-cart")
                    .setTitle("Add book to cart")
                    .setMethod(ActionImpl.Method.POST)
                    .setHref(linkBuilder.forShoppingCart(uriInfo).getHref())
                    .setType(MediaType.APPLICATION_JSON)
                    .addField(createFieldBuilder().setName("isbn").setType(FieldType.TEXT).setRequired(true).build())
                    .addField(createFieldBuilder().setName("quantity").setType(FieldType.NUMBER).setRequired(true).build())
                    .build());

        return entityBuilder.build();
    }

    public Entity buildShoppingCart(ShoppingCartSelection cart, UriInfo uriInfo) {
        final List<Entity> selections = cart.getSelections().stream().map(s -> buildBookSelection(s, uriInfo, true, true)).collect(Collectors.toList());

        return createEntityBuilder().setComponentClass("shopping-cart")
                .addProperty("price", cart.getPrice())
                .addSubEntities(selections)
                .addAction(createActionBuilder().setName("checkout")
                        .setTitle("Checkout shopping cart")
                        .setMethod(ActionImpl.Method.POST)
                        .setHref(linkBuilder.forOrders(uriInfo).getHref())
                        .build())
                .build();
    }

    public Entity buildShoppingCartSelection(BookSelection selection, UriInfo uriInfo) {
        return buildBookSelection(selection, uriInfo, true, false);
    }

    public Entity buildOrders(List<Order> orders, UriInfo uriInfo) {
        final List<Entity> orderEntities = orders.stream().map(o -> buildOrderTeaser(o, uriInfo)).collect(Collectors.toList());

        return createEntityBuilder().setComponentClass("orders")
                .addLink(linkBuilder.forOrders(uriInfo))
                .addSubEntities(orderEntities).build();
    }

    public Entity buildOrderTeaser(Order order, UriInfo uriInfo) {
        return createEntityBuilder().setComponentClass("order")
                .addLink(linkBuilder.forOrder(order, uriInfo))
                .setRelationship("item")
                .addProperty("date", order.getDate().toString())
                .addProperty("price", order.getPrice())
                .addProperty("status", order.getStatus().name()).build();
    }

    public Entity buildOrder(Order order, UriInfo uriInfo) {
        final List<Entity> selections = order.getSelections().stream().map(s -> buildBookSelection(s, uriInfo, false, true)).collect(Collectors.toList());

        return createEntityBuilder().setComponentClass("order")
                .addLink(linkBuilder.forOrder(order, uriInfo))
                .addSubEntities(selections)
                .addProperty("date", order.getDate().toString())
                .addProperty("price", order.getPrice())
                .addProperty("status", order.getStatus())
                .build();
    }

    private Entity buildBookSelection(BookSelection selection, UriInfo uriInfo, boolean hypertextIncluded, boolean asSubEntity) {
        final Book book = selection.getBook();
        final Entity bookEntity = createEntityBuilder().setComponentClass("book")
                .setRelationship("item")
                .addLink(linkBuilder.forBook(book, uriInfo))
                .addProperty("isbn", book.getIsbn())
                .addProperty("name", book.getName())
                .addProperty("price", book.getPrice())
                .build();

        final com.google.code.siren4j.component.builder.EntityBuilder selectionBuilder = createEntityBuilder().setComponentClass("book-selection")
                .addProperty("quantity", selection.getQuantity())
                .addProperty("price", selection.getPrice())
                .addSubEntity(bookEntity);

        if (asSubEntity)
            selectionBuilder.setRelationship("item");

        if (hypertextIncluded) {
            selectionBuilder.addAction(createActionBuilder().setName("modify-book-selection")
                    .setTitle("Modify book cart selection")
                    .setMethod(ActionImpl.Method.PUT)
                    .setHref(linkBuilder.forBookSelection(selection, uriInfo).getHref())
                    .setType(MediaType.APPLICATION_JSON)
                    .addField(createFieldBuilder().setName("quantity").setType(FieldType.NUMBER).setRequired(true).build())
                    .build());
            selectionBuilder.addAction(createActionBuilder().setName("delete-book-selection")
                    .setTitle("Delete book cart selection")
                    .setMethod(ActionImpl.Method.DELETE)
                    .setHref(linkBuilder.forBookSelection(selection, uriInfo).getHref())
                    .build());
            selectionBuilder.addLink(linkBuilder.forBookSelection(selection, uriInfo));
        }

        return selectionBuilder.build();
    }
}
