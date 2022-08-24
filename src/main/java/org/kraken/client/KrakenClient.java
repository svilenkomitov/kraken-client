package org.kraken.client;

import org.kraken.client.orderbook.listener.OrderBookListener;

import java.net.URI;
import java.net.http.HttpClient;
import java.util.concurrent.CountDownLatch;

public class KrakenClient {

    private static final String KRAKEN_WS_URI = "wss://ws.kraken.com";
    private static final int MAX_LATCH = 100;

    public void listenOrderBooks() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(MAX_LATCH);

        HttpClient.newHttpClient()
                .newWebSocketBuilder()
                .buildAsync(URI.create(KRAKEN_WS_URI), new OrderBookListener(latch))
                .join();

        latch.await();
    }
}