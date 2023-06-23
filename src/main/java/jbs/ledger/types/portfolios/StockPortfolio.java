package jbs.ledger.types.portfolios;

import jbs.ledger.types.assets.Cash;
import jbs.ledger.types.assets.Stock;

import javax.annotation.Nullable;

public final class StockPortfolio extends AbstractPortfolio<Stock> {
    public StockPortfolio(StockPortfolio copy) {
        super();
    }
    public StockPortfolio() {
        super();
    }

    @Nullable
    @Override
    public Stock get(String symbol) {
        for (Stock c : get()) {
            if (c.getSymbol().equalsIgnoreCase(symbol)) {
                return c;
            }
        }

        return null;
    }

    @Override
    public void add(Stock asset) {
        Stock existing = get(asset.getSymbol());

        if (existing != null && existing.isStackable(asset)) {
            existing.addQuantity(asset.getQuantity());
            return;
        }

        super.add(asset);

        clean();
    }

    @Override
    public void remove(Stock asset) {
        add(asset.negate());
    }

    @Override
    public boolean contains(Stock asset) {
        Stock exiting = get(asset.getSymbol());

        if (exiting != null) {
            return exiting.getQuantity() >= asset.getQuantity();
        }

        return super.contains(asset);
    }

    @Override
    public void clean() {
        getRaw().removeIf(e -> e.getQuantity() == 0L);
    }
}
