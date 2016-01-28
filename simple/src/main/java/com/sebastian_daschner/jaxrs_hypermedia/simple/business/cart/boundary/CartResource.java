package com.sebastian_daschner.jaxrs_hypermedia.simple.business.cart.boundary;

import com.sebastian_daschner.jaxrs_hypermedia.simple.business.EntityBuilder;
import com.sebastian_daschner.jaxrs_hypermedia.simple.business.UrlBuilder;
import com.sebastian_daschner.jaxrs_hypermedia.simple.business.cart.entity.BookSelection;
import com.sebastian_daschner.jaxrs_hypermedia.simple.business.cart.entity.Selection;
import com.sebastian_daschner.jaxrs_hypermedia.simple.business.cart.entity.ShoppingCart;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Path("shopping_cart")
public class CartResource {

    @Inject
    MockShoppingCart shoppingCart;

    @Inject
    EntityBuilder entityBuilder;

    @Inject
    UrlBuilder urlBuilder;

    @Context
    UriInfo uriInfo;

    @GET
    public JsonObject getShoppingCart() {
        final ShoppingCart cart = shoppingCart.getShoppingCart();

        JsonArray bookSelections = cart.getSelections().stream()
                .map(b -> entityBuilder.buildBookSelection(b, urlBuilder.forBookSelection(b, uriInfo), urlBuilder.forBook(b.getBook(), uriInfo)))
                .collect(Json::createArrayBuilder, JsonArrayBuilder::add, JsonArrayBuilder::add)
                .build();

        return entityBuilder.buildShoppingCart(cart, bookSelections, urlBuilder.forCheckout(uriInfo));
    }

    @POST
    public void addItem(@Valid @NotNull BookSelection selection) {
        shoppingCart.addBookSelection(selection);
    }

    @PUT
    @Path("{id}")
    public void updateSelection(@PathParam("id") long selectionId, @Valid @NotNull Selection selectionUpdate) {
        shoppingCart.updateBookSelection(selectionId, selectionUpdate.getQuantity());
    }

    @DELETE
    @Path("{id}")
    public void deleteSelection(@PathParam("id") long selectionId) {
        shoppingCart.updateBookSelection(selectionId, 0);
    }

}
