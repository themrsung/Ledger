package jbs.ledger.types.portfolios.basic;

import jbs.ledger.io.types.assets.basic.StockData;
import jbs.ledger.types.assets.basic.Stock;
import jbs.ledger.types.portfolios.AbstractPortfolio;

import javax.annotation.Nullable;
import java.util.ArrayList;

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
        Stock existing = getRaw(asset.getSymbol());

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

    // IO
    public static StockPortfolio fromData(ArrayList<StockData> data) {
        StockPortfolio portfolio = new StockPortfolio();

        for (StockData sd : data) {
            portfolio.add(Stock.fromData(sd));
        }

        return portfolio;
    }

    public ArrayList<StockData> toData() {
        ArrayList<StockData> data = new ArrayList<>();

        for (Stock c : get()) {
            data.add(c.toData());
        }

        return data;
    }
}
