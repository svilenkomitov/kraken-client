package org.kraken.client.orderbook.listener;

import org.json.JSONObject;

public class OrderBookSubscription {

    private static final String SUBSCRIPTION_KEY = "book";
    private static final String NAME_JSON_KEY = "name";

    private final String name;

    public OrderBookSubscription() {
        this(SUBSCRIPTION_KEY);
    }

    private OrderBookSubscription(final String name) {
        this.name = name;
    }

    public JSONObject toJson() {
        JSONObject json =  new JSONObject();
        json.put(NAME_JSON_KEY, name);
        return json;
    }
}