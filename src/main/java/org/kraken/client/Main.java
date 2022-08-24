package org.kraken.client;

class Main {
    public static void main(String[]args) throws InterruptedException {
        new KrakenClient().listenOrderBooks();
    }
}