package jbs.ledger.types.assets;

import jbs.ledger.interfaces.assets.FractionalAsset;
import org.bukkit.Bukkit;

/**
 * Cash
 * Has a currency code(string) and balance(double).
 */
public final class Cash implements FractionalAsset {
    public Cash(
            String currency,
            double balance
    ) {
        this.symbol = currency;
        this.balance = balance;
    }

    public Cash(Cash copy) {
        this.symbol = copy.symbol;
        this.balance = copy.balance;
    }
    public Cash() {
        this.symbol = null;
        this.balance = 0d;
    }

    private final String symbol;
    private double balance;

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
        Bukkit.getLogger().info("balance change" + getBalance() + " -> " + balance);
        this.balance = balance;
    }

    @Override
    public Cash copy() {
        return new Cash(this);
    }

    @Override
    public Cash negate() {
        return new Cash(symbol, -balance);
    }
}
