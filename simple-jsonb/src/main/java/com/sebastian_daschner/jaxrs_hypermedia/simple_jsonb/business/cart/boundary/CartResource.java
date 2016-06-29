package com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.cart.boundary;

import com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.ResourceUriBuilder;
import com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.cart.entity.CartInsertion;
import com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.cart.entity.CartUpdate;
import com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.cart.entity.ShoppingCart;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.InputStream;
import java.util.Set;

@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("shopping_cart")
public class CartResource {

    @Inject
    MockShoppingCart shoppingCart;

    @Inject
    ResourceUriBuilder resourceUriBuilder;

    @Context
    UriInfo uriInfo;

    // JAX-RS 2.0 obviously isn't integrated with JSONB yet
    private Jsonb jsonb;

    // as deserialization is done manually, validation also isn't integrated by JAX-RS
    @Inject
    Validator validator;

    @PostConstruct
    public void initJsonb() {
        jsonb = JsonbBuilder.create();
    }

    @GET
    public StreamingOutput getShoppingCart() {
        final ShoppingCart cart = shoppingCart.getShoppingCart();

        cart.getSelections().forEach(s -> {
            s.getLinks().put("modify-selection", resourceUriBuilder.forBookSelection(s.getId(), uriInfo));
            s.getBook().getLinks().put("self", resourceUriBuilder.forBook(s.getBook().getId(), uriInfo));
        });
        cart.getLinks().put("checkout", resourceUriBuilder.forCheckout(uriInfo));

        return output -> jsonb.toJson(cart, output);
    }

    @POST
    public void addItem(InputStream input) {
        final CartInsertion insertion = jsonb.fromJson(input, CartInsertion.class);

        final Set<ConstraintViolation<CartUpdate>> violations = validator.validate(insertion);
        if (!violations.isEmpty())
            throwBadRequest(violations);

        shoppingCart.addBookSelection(insertion);
    }

    @PUT
    @Path("{id}")
    public void updateSelection(@PathParam("id") long selectionId, InputStream input) {
        final CartUpdate selectionUpdate = jsonb.fromJson(input, CartUpdate.class);

        final Set<ConstraintViolation<CartUpdate>> violations = validator.validate(selectionUpdate);
        if (!violations.isEmpty())
            throwBadRequest(violations);

        shoppingCart.updateBookSelection(selectionId, selectionUpdate.getQuantity());
    }

    private <T> void throwBadRequest(final Set<ConstraintViolation<T>> violations) {
        final Response.ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
        violations.stream().map(v -> v.getPropertyPath() + " " + v.getMessage()).forEach(h -> builder.header("X-Error", h));
        throw new BadRequestException(builder.build());
    }

    @DELETE
    @Path("{id}")
    public void deleteSelection(@PathParam("id") long selectionId) {
        shoppingCart.updateBookSelection(selectionId, 0);
    }

}
