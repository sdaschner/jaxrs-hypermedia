package com.sebastian_daschner.jaxrs_hypermedia.simple.business.orders.boundary;

import com.sebastian_daschner.jaxrs_hypermedia.simple.business.EntityBuilder;
import com.sebastian_daschner.jaxrs_hypermedia.simple.business.ResourceUriBuilder;
import com.sebastian_daschner.jaxrs_hypermedia.simple.business.orders.entity.Order;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrdersResource {

    @Inject
    MockOrderStore orderStore;

    @Inject
    EntityBuilder entityBuilder;

    @Inject
    ResourceUriBuilder resourceUriBuilder;

    @GET
    public JsonArray getOrders() {
        return orderStore.getOrders().stream()
                .map(o -> entityBuilder.buildOrderTeaser(o))
                .collect(Json::createArrayBuilder, JsonArrayBuilder::add, JsonArrayBuilder::add)
                .build();
    }

    @GET
    @Path("{id}")
    public JsonObject getOrder(@PathParam("id") long id) {
        final Order order = orderStore.getOrder(id);

        JsonArray bookSelections = order.getSelections().stream()
                .map(b -> entityBuilder.buildBookSelection(b))
                .collect(Json::createArrayBuilder, JsonArrayBuilder::add, JsonArrayBuilder::add)
                .build();

        return entityBuilder.buildOrder(order, bookSelections);
    }

}
