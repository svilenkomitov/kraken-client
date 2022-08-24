package org.kraken.client.orderbook.domain;

import org.json.JSONObject;

public enum OrderType {
    ASK("a"),
    BID("b");

    private final String type;

    OrderType(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static OrderType parse(final JSONObject orderDetails) {
        if(isAskOrder(orderDetails)) {
            return OrderType.ASK;
        } else if(isBidOrder(orderDetails)) {
            return OrderType.BID;
        }
        throw new RuntimeException("invalid order type");
    }

    private static boolean isAskOrder(JSONObject orderData) {
        return orderData.optJSONArray(ASK.type) != null;
    }

    private static boolean isBidOrder(JSONObject orderData) {
        return orderData.optJSONArray(BID.type) != null;
    }

    public static OrderType getByType(String type) {
        for(OrderType v : values())
            if(v.getType().equalsIgnoreCase(type)) return v;
        throw new IllegalArgumentException();
    }
}