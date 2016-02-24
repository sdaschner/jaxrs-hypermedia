package com.sebastian_daschner.jaxrs_hypermedia.siren.business.orders.boundary;

import com.sebastian_daschner.jaxrs_hypermedia.siren.business.cart.boundary.MockShoppingCart;
import com.sebastian_daschner.jaxrs_hypermedia.siren.business.cart.entity.ShoppingCart;
import com.sebastian_daschner.jaxrs_hypermedia.siren.business.orders.entity.Order;
import com.sebastian_daschner.jaxrs_hypermedia.siren.business.orders.entity.OrderStatus;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Collections.singletonList;

@Stateless
public class MockOrderStore {

    @Inject
    MockShoppingCart mockShoppingCart;

    public List<Order> getOrders() {
        return singletonList(newOrder());
    }

    public Order getOrder(long id) {
        final Order order = newOrder();
        order.setId(id);
        return order;
    }

    private Order newOrder() {
        final Order order = new Order();
        order.setId(1L);
        order.setDate(LocalDateTime.now().minusHours(2));
        order.setStatus(OrderStatus.SHIPPED);

        final ShoppingCart cart = mockShoppingCart.getShoppingCart();
        order.setSelections(cart.getSelections());
        order.setPrice(cart.getPrice());

        return order;
    }

}
