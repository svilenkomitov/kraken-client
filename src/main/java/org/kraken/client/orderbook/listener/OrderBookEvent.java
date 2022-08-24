package org.kraken.client.orderbook.listener;

import org.json.JSONObject;
import org.kraken.client.orderbook.domain.AssetPair;

import java.util.Arrays;
import java.util.List;

public class OrderBookEvent {

    private static final String EVENT_KEY = "subscribe";
    private static final String EVENT_JSON_KEY = "event";
    private static final String PAIR_JSON_KEY = "pair";
    private static final String SUBSCRIPTION_JSON_KEY = "subscription";
    private static final List<AssetPair> SUPPORTED_ASSET_PAIRS =
            Arrays.asList(AssetPair.XBT_USD, AssetPair.ETH_USD);

    private final String event;
    private final List<AssetPair> pairs;
    private final OrderBookSubscription subscription;

    public OrderBookEvent() {
        this(EVENT_KEY, SUPPORTED_ASSET_PAIRS, new OrderBookSubscription());
    }
    private OrderBookEvent(final String event, final List<AssetPair> pairs,
                          final OrderBookSubscription subscription) {
        this.event = event;
        this.pairs = pairs;
        this.subscription = subscription;
    }

    public JSONObject toJson() {
        JSONObject json =  new JSONObject();
        json.put(EVENT_JSON_KEY, event);
        json.put(PAIR_JSON_KEY, pairs.stream().map(AssetPair::getPair).toArray());
        json.put(SUBSCRIPTION_JSON_KEY, subscription.toJson());
        return json;
    }
}
