package jbs.ledger.interfaces.common;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.types.assets.Cash;
import jbs.ledger.types.assets.Commodity;
import jbs.ledger.types.assets.Stock;
import jbs.ledger.types.portfolios.*;

import javax.annotation.Nullable;
import java.util.ArrayList;

public interface Economic extends Unique {
    // Basic assets
    CashPortfolio getCash();
    CommodityPortfolio getCommodities();
    StockPortfolio getStocks();

    // Forwards
    UniqueNotePortfolio<Cash> getNotes();
    UniqueNotePortfolio<Commodity> getCommodityForwards();
    UniqueNotePortfolio<Stock> getStockForwards();

    // Futures
    StackableNotePortfolio<Cash> getBonds();
    StackableNotePortfolio<Commodity> getCommodityFutures();
    StackableNotePortfolio<Stock> getStockFutures();

    // Options
    ConditionalNotePortfolio<Cash> getForexOptions();
    ConditionalNotePortfolio<Commodity> getCommodityOptions();
    ConditionalNotePortfolio<Stock> getStockOptions();

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

        assets.addAll(getForexOptions().get());
        assets.addAll(getCommodityOptions().get());
        assets.addAll(getStockOptions().get());

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
