package com.sebastian_daschner.jaxrs_hypermedia.siren.business;

import com.google.code.siren4j.component.Entity;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import static com.google.code.siren4j.component.builder.EntityBuilder.createEntityBuilder;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class RootResource {

    @Context
    UriInfo uriInfo;

    @Inject
    LinkBuilder linkBuilder;

    @GET
    public Entity getRoot() {
        return createEntityBuilder()
                .addLink(linkBuilder.forBooks(uriInfo, "books"))
                .addLink(linkBuilder.forShoppingCart(uriInfo, "shopping-cart"))
                .addLink(linkBuilder.forOrders(uriInfo, "orders")).build();
    }

}
