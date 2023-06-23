package jbs.ledger.types.portfolios.basic;

import jbs.ledger.types.assets.basic.Commodity;
import jbs.ledger.types.portfolios.AbstractPortfolio;

import javax.annotation.Nullable;

public final class CommodityPortfolio extends AbstractPortfolio<Commodity> {
    public CommodityPortfolio(CommodityPortfolio copy) {
        super();
    }
    public CommodityPortfolio() {
        super();
    }

    @Nullable
    @Override
    public Commodity get(String symbol) {
        for (Commodity c : get()) {
            if (c.getSymbol().equalsIgnoreCase(symbol)) {
                return c;
            }
        }

        return null;
    }

    @Override
    public void add(Commodity asset) {
        Commodity existing = getRaw(asset.getSymbol());

        if (existing != null && existing.isStackable(asset)) {
            existing.addQuantity(asset.getQuantity());
            return;
        }

        super.add(asset);

        clean();
    }

    @Override
    public void remove(Commodity asset) {
        add(asset.negate());
    }

    @Override
    public boolean contains(Commodity asset) {
        Commodity exiting = get(asset.getSymbol());

        if (exiting != null) {
            return exiting.getQuantity() >= asset.getQuantity();
        }

        return super.contains(asset);
    }

    @Override
    public void clean() {
        getRaw().removeIf(e ->  e.getQuantity() == 0L);
    }
}
