package com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.orders.boundary;

import com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.ResourceUriBuilder;
import com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.orders.entity.Order;
import com.sebastian_daschner.jaxrs_hypermedia.simple_jsonb.business.orders.entity.OrderTeaser;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Stateless
@Path("orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrdersResource {

    @Inject
    MockOrderStore orderStore;

    @Inject
    ResourceUriBuilder resourceUriBuilder;

    @Context
    UriInfo uriInfo;

    // JAX-RS 2.0 obviously isn't integrated with JSONB yet
    private Jsonb jsonb;

    @PostConstruct
    public void initJsonb() {
        jsonb = JsonbBuilder.create();
    }

    @GET
    public StreamingOutput getOrders() {
        final List<OrderTeaser> orderTeasers = orderStore.getOrderTeasers();
        orderTeasers.forEach(t -> t.getLinks().put("self", resourceUriBuilder.forOrder(t.getId(), uriInfo)));
        return output -> jsonb.toJson(orderTeasers, output);
    }

    @GET
    @Path("{id}")
    public StreamingOutput getOrder(@PathParam("id") long id) {
        final Order order = orderStore.getOrder(id);
        order.getLinks().put("self", resourceUriBuilder.forOrder(order.getId(), uriInfo));
        order.getSelections().forEach(s -> s.getBook().getLinks().put("self", resourceUriBuilder.forBook(s.getBook().getId(), uriInfo)));
        return output -> jsonb.toJson(order, output);
    }

}
