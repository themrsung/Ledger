package jbs.ledger.types.assets;

import jbs.ledger.interfaces.assets.IntegralAsset;
import jbs.ledger.interfaces.common.Economic;

/**
 * Stock
 * Has a symbol(string) and quantity(long).
 */
public final class Stock implements IntegralAsset {
    public Stock(final Stock copy) {
        this.holder = copy.holder;
        this.symbol = copy.symbol;
        this.quantity = copy.quantity;
    }
    public Stock() {
        this.holder = null;
        this.symbol = null;
        this.quantity = 0L;
    }

    private Economic holder;
    private final String symbol;
    private long quantity;

    @Override
    public Economic getHolder() {
        return holder;
    }

    @Override
    public void setHolder(Economic holder) {
        this.holder = holder;
    }

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
