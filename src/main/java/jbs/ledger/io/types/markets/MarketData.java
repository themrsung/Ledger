package jbs.ledger.io.types.markets;

import jbs.ledger.io.types.assets.AssetData;
import jbs.ledger.io.types.orders.OrderData;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Data class for Market
 * @param <A> Asset traded in this market
 * @param <O> Type of order data
 */
public class MarketData<A extends AssetData, O extends OrderData<?>> {
    public MarketData() {}

    public UUID uniqueId;
    public String symbol;
    public UUID exchange;
    public String currency;
    public A unitAsset;
    public ArrayList<O> orders;

    public double price;
    public double tickSize;
}
