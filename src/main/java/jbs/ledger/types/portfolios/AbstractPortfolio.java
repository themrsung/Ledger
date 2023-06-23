package jbs.ledger.types.portfolios;

import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.portfolios.Portfolio;

import java.util.ArrayList;

public abstract class AbstractPortfolio<A> implements Portfolio<A> {
    protected AbstractPortfolio(AbstractPortfolio<A> copy) {
        this.entries = copy.entries;
        this.holder = copy.holder;
    }
    protected AbstractPortfolio() {
        this.entries = new ArrayList<>();
        this.holder = null;
    }

    private Economic holder;
    private final ArrayList<A> entries;

    @Override
    public Economic getHolder() {
        return holder;
    }

    @Override
    public void setHolder(Economic holder) {
        this.holder = holder;
    }

    @Override
    public ArrayList<A> get() {
        return new ArrayList<>(entries);
    }

    protected ArrayList<A> getRaw() {
        return entries;
    }

    @Override
    public void add(A asset) {
        entries.add(asset);
    }
}
