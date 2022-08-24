package org.kraken.client.orderbook.listener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.kraken.client.orderbook.domain.AssetPair;
import org.kraken.client.orderbook.Order;
import org.kraken.client.orderbook.domain.OrderBook;
import org.kraken.client.orderbook.domain.OrderType;

import java.net.http.WebSocket;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Map.entry;

public class OrderBookListener implements WebSocket.Listener {

    private final static Logger LOGGER = Logger.getLogger(OrderBookListener.class.getName());
    private static final int ORDER_OBJECT_INDEX = 1;
    private static final String IS_JSON_ARRAY_KEY = "[";

    private final CountDownLatch latch;
    private Map<AssetPair, OrderBook> orderBooks;

    public OrderBookListener(CountDownLatch latch) {
        this(latch, Map.ofEntries(
                entry(AssetPair.ETH_USD, new OrderBook(AssetPair.ETH_USD)),
                entry(AssetPair.XBT_USD, new OrderBook(AssetPair.XBT_USD))
        ));
    }

    private OrderBookListener(CountDownLatch latch, Map<AssetPair, OrderBook> orderBooks) {
        this.latch = latch;
        this.orderBooks = orderBooks;
    }

    public void onOpen(WebSocket webSocket) {
        WebSocket.Listener.super.onOpen(webSocket);
        final String orderBookEvent = new OrderBookEvent().toJson().toString();
        LOGGER.log(Level.INFO, "Subscribe: " + orderBookEvent);
        webSocket.sendText(orderBookEvent, true);
    }

    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        if (!isOrderData(data)) {
            return WebSocket.Listener.super.onText(webSocket, data, last);
        }

        final JSONArray orderData = new JSONArray(data.toString());
        final AssetPair assetPair = AssetPair.parse(orderData);
        final Order order = Order.parseOrder(orderData);

        orderBooks.get(assetPair).addOrder(order);

        printOrderBooks();

        latch.countDown();
        return WebSocket.Listener.super.onText(webSocket, data, last);
    }

    public void onError(WebSocket webSocket, Throwable error) {
        LOGGER.log(Level.WARNING, error.toString());
        WebSocket.Listener.super.onError(webSocket, error);
    }

    private boolean isOrderData(CharSequence data) {
        if (data.toString().startsWith(IS_JSON_ARRAY_KEY)) {
            JSONObject orderData = new JSONArray(data.toString())
                    .getJSONObject(ORDER_OBJECT_INDEX);
            return orderData.has(OrderType.ASK.getType()) ||
                    orderData.has(OrderType.BID.getType());
        }
        return false;
    }

    private void printOrderBooks() {
        orderBooks.values().forEach(OrderBook::printOrderBook);
    }
}

