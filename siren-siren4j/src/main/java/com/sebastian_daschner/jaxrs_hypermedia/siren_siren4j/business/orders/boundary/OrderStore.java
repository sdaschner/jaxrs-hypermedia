package com.sebastian_daschner.jaxrs_hypermedia.siren_siren4j.business.orders.boundary;

import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4j.business.books.control.PriceCalculator;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4j.business.cart.control.ShoppingCart;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4j.business.orders.entity.Order;
import com.sebastian_daschner.jaxrs_hypermedia.siren_siren4j.business.orders.entity.OrderStatus;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class OrderStore {

    @Inject
    ShoppingCart shoppingCart;

    @Inject
    PriceCalculator priceCalculator;

    private Map<Long, Order> orders;

    @PostConstruct
    public void initOrders() {
        orders = new ConcurrentHashMap<>();
    }

    @Lock(LockType.READ)
    public List<Order> getOrders() {
        final ArrayList<Order> orders = new ArrayList<>(this.orders.values());
        orders.sort(Comparator.comparing(Order::getId));

        return orders;
    }

    @Lock(LockType.READ)
    public Order getOrder(long id) {
        return orders.get(id);
    }

    @Lock(LockType.WRITE)
    public Order checkout() {
        final Order order = new Order();
        order.getSelections().addAll(shoppingCart.getSelections());
        order.setPrice(priceCalculator.calculateTotal(order.getSelections()));

        final long id = orders.size() + 1;
        order.setId(id);
        order.setDate(LocalDateTime.now());
        order.setStatus(OrderStatus.CONFIRMED);

        shoppingCart.clear();

        orders.put(id, order);
        return order;
    }

}
