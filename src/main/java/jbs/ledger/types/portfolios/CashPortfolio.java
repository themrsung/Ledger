package jbs.ledger.types.portfolios;

import jbs.ledger.types.assets.Cash;

import javax.annotation.Nullable;

public final class CashPortfolio extends AbstractPortfolio<Cash> {
    public CashPortfolio(CashPortfolio copy) {
        super();
    }
    public CashPortfolio() {
        super();
    }

    @Nullable
    @Override
    public Cash get(String symbol) {
        for (Cash c : get()) {
            if (c.getSymbol().equalsIgnoreCase(symbol)) {
                return c;
            }
        }

        return null;
    }

    @Override
    public void add(Cash asset) {
        Cash existing = get(asset.getSymbol());

        if (existing != null && existing.isStackable(asset)) {
            existing.addBalance(asset.getBalance());
            return;
        }

        super.add(asset);

        clean();
    }

    @Override
    public void remove(Cash asset) {
        add(asset.negate());
    }

    @Override
    public boolean contains(Cash asset) {
        Cash exiting = get(asset.getSymbol());

        if (exiting != null) {
            return exiting.getBalance() >= asset.getBalance();
        }

        return super.contains(asset);
    }

    @Override
    public void clean() {
        getRaw().removeIf(e -> e.getBalance() == 0d);
    }
}
