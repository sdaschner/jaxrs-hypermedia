package com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.business.cart.boundary;

import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.business.EntityBuilder;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.business.cart.entity.BookSelection;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.business.cart.entity.Selection;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.business.cart.entity.ShoppingCartSelection;

import javax.ejb.Stateless;
import javax.inject.Inject;
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
    ShoppingStore shoppingStore;

    @Inject
    EntityBuilder entityBuilder;

    @Context
    UriInfo uriInfo;

    @GET
    public JsonObject getShoppingCart() {
        final ShoppingCartSelection cart = shoppingStore.getShoppingCartSelection();
        return entityBuilder.buildShoppingCart(cart, uriInfo);
    }

    @POST
    public void addItem(@Valid @NotNull BookSelection selection) {
        shoppingStore.addBookSelection(selection);
    }

    @GET
    @Path("{id}")
    public JsonObject getSelection(@PathParam("id") long selectionId) {
        final BookSelection selection = shoppingStore.getSelection(selectionId);
        return entityBuilder.buildShoppingCartSelection(selection, uriInfo);
    }

    @PUT
    @Path("{id}")
    public void updateSelection(@PathParam("id") long selectionId, @Valid @NotNull Selection selectionUpdate) {
        shoppingStore.updateBookSelection(selectionId, selectionUpdate.getQuantity());
    }

    @DELETE
    @Path("{id}")
    public void deleteSelection(@PathParam("id") long selectionId) {
        shoppingStore.updateBookSelection(selectionId, 0);
    }

}
