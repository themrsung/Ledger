package jbs.ledger.types.portfolios.basic;

import jbs.ledger.io.types.assets.basic.CommodityData;
import jbs.ledger.types.assets.basic.Commodity;
import jbs.ledger.types.portfolios.AbstractPortfolio;

import javax.annotation.Nullable;
import java.util.ArrayList;

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

        super.add(asset.copy());

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

    // IO
    public static CommodityPortfolio fromData(ArrayList<CommodityData> data) {
        CommodityPortfolio portfolio = new CommodityPortfolio();

        for (CommodityData cd : data) {
            portfolio.add(Commodity.fromData(cd));
        }

        return portfolio;
    }

    public ArrayList<CommodityData> toData() {
        ArrayList<CommodityData> data = new ArrayList<>();

        for (Commodity c : get()) {
            data.add(c.toData());
        }

        return data;
    }
}
