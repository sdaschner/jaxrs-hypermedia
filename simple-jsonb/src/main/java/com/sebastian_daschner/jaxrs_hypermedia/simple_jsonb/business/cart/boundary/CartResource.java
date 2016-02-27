package com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.cart.boundary;

import com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.ResourceUriBuilder;
import com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.cart.entity.CartInsertion;
import com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.cart.entity.CartUpdate;
import com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.cart.entity.ShoppingCart;

import javax.ejb.Stateless;
import javax.inject.Inject;
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
    ResourceUriBuilder resourceUriBuilder;

    @Context
    UriInfo uriInfo;

    @GET
    public ShoppingCart getShoppingCart() {
        final ShoppingCart cart = shoppingCart.getShoppingCart();

        cart.getSelections().forEach(s -> {
            s.getLinks().put("modify-selection", resourceUriBuilder.forBookSelection(s.getId(), uriInfo));
            s.getBook().getLinks().put("self", resourceUriBuilder.forBook(s.getBook().getId(), uriInfo));
        });
        cart.getLinks().put("checkout", resourceUriBuilder.forCheckout(uriInfo));

        return cart;
    }

    @POST
    public void addItem(@Valid @NotNull CartInsertion insertion) {
        shoppingCart.addBookSelection(insertion);
    }

    @PUT
    @Path("{id}")
    public void updateSelection(@PathParam("id") long selectionId, @Valid @NotNull CartUpdate selectionUpdate) {
        shoppingCart.updateBookSelection(selectionId, selectionUpdate.getQuantity());
    }

    @DELETE
    @Path("{id}")
    public void deleteSelection(@PathParam("id") long selectionId) {
        shoppingCart.updateBookSelection(selectionId, 0);
    }

}
