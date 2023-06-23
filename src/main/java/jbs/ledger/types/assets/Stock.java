package jbs.ledger.types.assets;

import jbs.ledger.interfaces.assets.IntegralAsset;

/**
 * Stock
 * Has a symbol(string) and quantity(long).
 */
public final class Stock implements IntegralAsset {
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
}
