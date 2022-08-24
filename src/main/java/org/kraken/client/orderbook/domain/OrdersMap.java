package org.kraken.client.orderbook.domain;

import org.kraken.client.orderbook.Order;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class OrdersMap extends TreeMap<BigDecimal, Order> {

    private final static String ORDERS_DELIMITER = ", \n";

    public OrdersMap() {
        super(new TreeMap<>(Collections.reverseOrder()));
    }

    public Order lastOrder() {
        if(lastEntry() != null) {
            return lastEntry().getValue();
        }
        return new Order();
    }

    public Order firstOrder() {
        if(firstEntry() != null) {
            return firstEntry().getValue();
        }
        return new Order();
    }

    @Override
    public String toString() {
        return  "[ " +
                this.values()
                        .stream()
                        .map(Order::toString)
                        .collect(Collectors.joining(ORDERS_DELIMITER)) +
                " ]";
    }
}