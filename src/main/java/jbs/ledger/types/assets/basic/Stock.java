package jbs.ledger.types.assets.basic;

import jbs.ledger.interfaces.assets.IntegralAsset;
import jbs.ledger.io.types.assets.basic.StockData;
import jbs.ledger.types.assets.AssetType;

/**
 * Stock
 * Has a symbol(string) and quantity(long).
 */
public final class Stock implements IntegralAsset {
    /**
     * Creates a new stock instance
     * @param symbol Unique symbol of this stock
     * @param quantity Quantity of this stock
     */
    public Stock(
            String symbol,
            long quantity
    ) {
        this.symbol = symbol;
        this.quantity = quantity;
    }

    public Stock(final Stock copy) {
        this.symbol = copy.symbol;
        this.quantity = copy.quantity;
    }
    public Stock() {
        this.symbol = null;
        this.quantity = 0L;
    }

    private final String symbol;
    private long quantity;

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public long getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override
    public Stock copy() {
        return new Stock(this);
    }

    @Override
    public Stock negate() {
        return (Stock) IntegralAsset.super.negate();
    }

    @Override
    public AssetType getType() {
        return AssetType.STOCK;
    }

    // IO
    public static Stock fromData(StockData data) {
        return new Stock(data);
    }

    private Stock(StockData data) {
        this.symbol = data.symbol;
        this.quantity = data.quantity;
    }

    public StockData toData() {
        StockData data = new StockData();

        data.type = AssetType.STOCK;
        data.symbol = symbol;
        data.quantity = quantity;

        return data;
    }
}
