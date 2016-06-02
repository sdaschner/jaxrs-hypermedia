package com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.business.orders.boundary;

import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.business.EntityBuilder;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.business.LinkBuilder;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.business.orders.entity.Order;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Stateless
@Path("orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrdersResource {

    @Inject
    OrderStore orderStore;

    @Inject
    EntityBuilder entityBuilder;

    @Inject
    LinkBuilder linkBuilder;

    @Context
    UriInfo uriInfo;

    @GET
    public JsonObject getOrders() {
        final List<Order> orders = orderStore.getOrders();
        return entityBuilder.buildOrders(orders, uriInfo);
    }

    @GET
    @Path("{id}")
    public JsonObject getOrder(@PathParam("id") long id) {
        final Order order = orderStore.getOrder(id);
        if (order == null)
            throw new NotFoundException();
        return entityBuilder.buildOrder(order, uriInfo);
    }

    @POST
    public Response checkout() {
        final Order order = orderStore.checkout();
        return Response.created(linkBuilder.forOrder(order, uriInfo)).build();
    }

}
