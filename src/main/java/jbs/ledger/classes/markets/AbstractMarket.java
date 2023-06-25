package jbs.ledger.classes.markets;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.markets.Market;
import jbs.ledger.interfaces.orders.Order;
import jbs.ledger.io.types.markets.MarketData;

import java.util.ArrayList;
import java.util.UUID;

public abstract class AbstractMarket<A extends Asset> implements Market<A> {
    public AbstractMarket(
            UUID uniqueId,
            String symbol,
            Economic exchange,
            String currency,
            A unitAsset,
            double tickSize
    ) {
        this.uniqueId = uniqueId;
        this.symbol = symbol;
        this.exchange = exchange;
        this.currency = currency;
        this.unitAsset = unitAsset;
        this.orders = new ArrayList<>();

        this.price = 0d;
        this.volume = 0L;
        this.tickSize = tickSize;
    }

    public AbstractMarket(AbstractMarket<A> copy) {
        this.uniqueId = copy.uniqueId;
        this.symbol = copy.symbol;
        this.exchange = copy.exchange;
        this.currency = copy.currency;
        this.unitAsset = copy.unitAsset;
        this.orders = copy.orders;
        this.price = copy.price;
        this.volume = copy.volume;
        this.tickSize = copy.tickSize;
    }

    private final UUID uniqueId;
    private final String symbol;

    private final Economic exchange;
    private final String currency;
    protected final A unitAsset;
    private final ArrayList<Order<A>> orders;

    private double price;
    private transient long volume;
    private double tickSize;

    @Override
    public void placeOrder(Order<A> order) {
        double adjustedPrice = (order.getType().isBuy() ? Math.floor(order.getPrice() / getTickSize()) : Math.ceil(order.getPrice()) / getTickSize()) * getTickSize();
        order.setPrice(adjustedPrice);

        orders.add(order);
    }

    @Override
    public boolean removeOrder(Order<A> order) {
        return orders.remove(order);
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public Economic getExchange() {
        return exchange;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public ArrayList<Order<A>> getOrders() {
        return new ArrayList<>(orders);
    }

    @Override
    public double getHighestSellPrice() {
        return Market.super.getHighestSellPrice();
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public long getVolume() {
        return volume;
    }

    @Override
    public void addVolume(long delta) {
        this.volume += delta;
    }

    @Override
    public void setVolume(long volume) {
        this.volume = volume;
    }

    @Override
    public double getTickSize() {
        return tickSize;
    }

    @Override
    public void setTickSize(double tickSize) {
        this.tickSize = tickSize;
    }
}
