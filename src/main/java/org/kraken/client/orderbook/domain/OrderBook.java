package org.kraken.client.orderbook.domain;

import org.kraken.client.orderbook.Order;

import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Map.entry;

public class OrderBook {

    private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSX";
    private static final String UTC_TIME_ZONE = "UTC";

    private Map<OrderType, OrdersMap> orders;
    private Date lastUpdatedAt;
    private AssetPair assetPair;

    public OrderBook(AssetPair assetPair) {
        this(assetPair, Map.ofEntries(
                entry(OrderType.ASK, new OrdersMap()),
                entry(OrderType.BID, new OrdersMap())
        ), new Date());
    }

    public OrderBook(AssetPair assetPair, Map<OrderType, OrdersMap> orders, Date lastUpdatedAt) {
        this.assetPair = assetPair;
        this.orders = orders;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public void addOrder(final Order order) {
        orders.get(order.getType()).put(order.getPrice(), order);
        lastUpdatedAt = order.getDate();
    }

    public void printOrderBook() {
        System.out.println("<------------------------------------>");
        System.out.println("asks:");
        System.out.println(orders.get(OrderType.ASK));
        System.out.println("best bid: " + orders.get(OrderType.BID).firstOrder());
        System.out.println("best ask: " + orders.get(OrderType.ASK).lastOrder());
        System.out.println("bids:");
        System.out.println(orders.get(OrderType.BID));
        System.out.println(getLastUpdatedAt());
        System.out.println(assetPair.getPair());
        System.out.println(">-------------------------------------<");
    }

    private String getLastUpdatedAt() {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        formatter.setTimeZone(TimeZone.getTimeZone(UTC_TIME_ZONE));
        return formatter.format(lastUpdatedAt);
    }
}
