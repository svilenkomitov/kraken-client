package org.kraken.client.orderbook.domain;

import org.json.JSONArray;

public enum AssetPair {
    XBT_USD("XBT/USD"),
    ETH_USD("ETH/USD");

    private static final int ASSET_PAIR_INDEX = 3;

    private final String pair;

    AssetPair(final String pair) {
        this.pair = pair;
    }

    public String getPair() {
        return pair;
    }

    public static AssetPair parse(final JSONArray orderData) {
        return getByPair(orderData.getString(ASSET_PAIR_INDEX));
    }

    public static AssetPair getByPair(String pair) {
        for(AssetPair v : values())
            if(v.getPair().equalsIgnoreCase(pair)) return v;
        throw new IllegalArgumentException();
    }
}