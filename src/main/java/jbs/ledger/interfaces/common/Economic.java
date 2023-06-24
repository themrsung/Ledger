package jbs.ledger.interfaces.common;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Commodity;
import jbs.ledger.types.assets.basic.Stock;
import jbs.ledger.types.assets.synthetic.StackableNote;
import jbs.ledger.types.portfolios.basic.CashPortfolio;
import jbs.ledger.types.portfolios.basic.CommodityPortfolio;
import jbs.ledger.types.portfolios.basic.StockPortfolio;
import jbs.ledger.types.portfolios.synthetic.StackableNotePortfolio;
import jbs.ledger.types.portfolios.synthetic.UniqueNotePortfolio;

import javax.annotation.Nullable;
import java.util.ArrayList;

public interface Economic extends Unique {
    // Basic assets
    CashPortfolio getCash();
    CommodityPortfolio getCommodities();
    StockPortfolio getStocks();
    StackableNotePortfolio<Cash> getBonds();

    // Forwards
    UniqueNotePortfolio<Cash> getNotes();
    UniqueNotePortfolio<Commodity> getCommodityForwards();
    UniqueNotePortfolio<Stock> getStockForwards();
    UniqueNotePortfolio<StackableNote<Cash>> getBondForwards();

    // Futures
    StackableNotePortfolio<Commodity> getCommodityFutures();
    StackableNotePortfolio<Stock> getStockFutures();

    // Generic getters
    default ArrayList<Asset> getAssets() {
        ArrayList<Asset> assets = new ArrayList<>();

        assets.addAll(getCash().get());
        assets.addAll(getCommodities().get());
        assets.addAll(getStocks().get());

        assets.addAll(getNotes().get());
        assets.addAll(getCommodityForwards().get());
        assets.addAll(getStockForwards().get());

        assets.addAll(getBonds().get());
        assets.addAll(getCommodityFutures().get());
        assets.addAll(getStockFutures().get());

        return assets;
    }

    @Nullable
    default Asset getAsset(String symbol) {
        for (Asset asset : getAssets()) {
            if (asset.getSymbol().equalsIgnoreCase(symbol)) {
                return asset;
            }
        }

        return null;
    }
}
