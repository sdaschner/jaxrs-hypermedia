package com.sebastian_daschner.jaxrs_hypermedia.siren.business.orders.boundary;

import com.google.code.siren4j.component.Entity;
import com.google.code.siren4j.error.Siren4JException;
import com.sebastian_daschner.jaxrs_hypermedia.siren.business.EntityBuilder;
import com.sebastian_daschner.jaxrs_hypermedia.siren.business.LinkBuilder;
import com.sebastian_daschner.jaxrs_hypermedia.siren.business.orders.entity.Order;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
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
    public Entity getOrders() {
        final List<Order> orders = orderStore.getOrders();
        return entityBuilder.buildOrders(orders, uriInfo);
    }

    @GET
    @Path("{id}")
    public Entity getOrder(@PathParam("id") long id) throws Siren4JException {
        final Order order = orderStore.getOrder(id);
        if (order == null)
            throw new NotFoundException();
        return entityBuilder.buildOrder(order, uriInfo);
    }

    @POST
    public Response checkout() {
        final Order order = orderStore.checkout();
        return Response.created(URI.create(linkBuilder.forOrder(order, uriInfo).getHref())).build();
    }

}
