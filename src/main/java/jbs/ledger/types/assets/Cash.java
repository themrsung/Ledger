package jbs.ledger.types.assets;

import jbs.ledger.interfaces.assets.FractionalAsset;
import jbs.ledger.interfaces.common.Economic;

/**
 * Cash
 * Has a currency code(string) and balance(double).
 */
public final class Cash implements FractionalAsset {
    public Cash(Cash copy) {
        this.holder = copy.holder;
        this.symbol = copy.symbol;
        this.balance = copy.balance;
    }
    public Cash() {
        this.holder = null;
        this.symbol = null;
        this.balance = 0d;
    }

    private Economic holder;
    private final String symbol;
    private double balance;

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
    public double getBalance() {
        return balance;
    }

    @Override
    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public Cash copy() {
        return new Cash(this);
    }

    @Override
    public Cash negate() {
        return (Cash) FractionalAsset.super.negate();
    }
}
