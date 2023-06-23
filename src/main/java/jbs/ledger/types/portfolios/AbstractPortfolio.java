package jbs.ledger.types.portfolios;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.portfolios.Portfolio;

import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * A basic portfolio.
 * @param <A> Type of asset to hold.
 */
public abstract class AbstractPortfolio<A extends Asset> implements Portfolio<A> {
    protected AbstractPortfolio(AbstractPortfolio<A> copy) {
        this.entries = copy.entries;
    }
    protected AbstractPortfolio() {
        this.entries = new ArrayList<>();
    }

    private final ArrayList<A> entries;

    @Override
    public ArrayList<A> get() {
        return new ArrayList<>(entries);
    }

    @Nullable
    @Override
    public A get(String symbol) {
        for (A a : get()) {
            if (a.getSymbol().equalsIgnoreCase(symbol)) {
                return a;
            }
        }

        return null;
    }

    protected ArrayList<A> getRaw() {
        return entries;
    }
    @Nullable
    protected A getRaw(String symbol) {
        for (A a : getRaw()) {
            if (a.getSymbol().equalsIgnoreCase(symbol)) {
                return a;
            }
        }
        
        return null;
    }

    @Override
    public void add(A asset) {
        entries.add(asset);
    }
}
