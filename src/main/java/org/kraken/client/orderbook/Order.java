package org.kraken.client.orderbook;

import org.json.JSONArray;
import org.json.JSONObject;
import org.kraken.client.orderbook.domain.OrderType;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Order {

    private static final int ORDER_OBJECT_INDEX = 1;
    private static final int PRICE_INDEX = 0;
    private static final int VOLUME_INDEX = 1;
    private static final int DATE_INDEX = 2;

    private BigDecimal price;
    private BigDecimal volume;
    private Date date;

    private OrderType type;

    public Order() {}
    public Order(BigDecimal price, BigDecimal volume, Date date, OrderType type) {
        this.price = price;
        this.volume = volume;
        this.date = date;
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public Date getDate() {
        return date;
    }

    public OrderType getType() {
        return type;
    }

    public static Order parseOrder(final JSONArray orderData) {
        final JSONObject orderObj = orderData.getJSONObject(ORDER_OBJECT_INDEX);
        final OrderType type = OrderType.parse(orderObj);
        final JSONArray orderDetails = orderObj.getJSONArray(type.getType());
        final JSONArray updatedOrderDetails = orderDetails.getJSONArray(orderDetails.length() - 1);

        final BigDecimal price = parsePrice(updatedOrderDetails);
        final BigDecimal volume = parseVolume(updatedOrderDetails);
        final Date date = parseDate(updatedOrderDetails);

        return new Order(price, volume, date, type);
    }

    private static BigDecimal parsePrice(final JSONArray updatedOrderDetails) {
        return updatedOrderDetails.getBigDecimal(PRICE_INDEX);
    }

    private static BigDecimal parseVolume(final JSONArray updatedOrderDetails) {
        return updatedOrderDetails.getBigDecimal(VOLUME_INDEX);
    }

    private static Date parseDate(final JSONArray updatedOrderDetails) {
        BigDecimal date = updatedOrderDetails.getBigDecimal(DATE_INDEX);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(TimeUnit.SECONDS.toMillis(date.longValue()));
        return calendar.getTime();
    }

    @Override
    public String toString() {
        return "[ " + price + ", " + volume + " ]";
    }
}
