package jbs.ledger.assetholders.corporations.finance;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.classes.markets.synthetic.CommodityFuturesMarket;
import jbs.ledger.classes.markets.synthetic.StockFuturesMarket;
import jbs.ledger.types.assets.basic.Cash;

import java.util.ArrayList;
import java.util.UUID;

public final class FuturesExchange extends Corporation {
    public FuturesExchange(
            UUID uniqueId,
            String name,
            String symbol,
            String currency,
            Cash capital
    ) {
        super(uniqueId, name, symbol, currency, capital);

        this.commodityFuturesMarkets = new ArrayList<>();
        this.stockFuturesMarkets = new ArrayList<>();
    }

    public FuturesExchange(FuturesExchange copy) {
        super(copy);

        this.commodityFuturesMarkets = copy.commodityFuturesMarkets;
        this.stockFuturesMarkets = copy.stockFuturesMarkets;
    }

    private final ArrayList<CommodityFuturesMarket> commodityFuturesMarkets;
    private final ArrayList<StockFuturesMarket> stockFuturesMarkets;

    public ArrayList<CommodityFuturesMarket> getCommodityFuturesMarkets() {
        return new ArrayList<>(commodityFuturesMarkets);
    }

    public void addCommodityFuturesMarket(CommodityFuturesMarket market) {
        commodityFuturesMarkets.add(market);
    }

    public boolean removeCommodityFuturesMarket(CommodityFuturesMarket market) {
        return commodityFuturesMarkets.remove(market);
    }

    public ArrayList<StockFuturesMarket> getStockFuturesMarkets() {
        return new ArrayList<>(stockFuturesMarkets);
    }

    public void addStockFuturesMarket(StockFuturesMarket market) {
        stockFuturesMarkets.add(market);
    }

    public boolean removeStockFuturesMarket(StockFuturesMarket market) {
        return stockFuturesMarkets.remove(market);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.FUTURES_EXCHANGE;
    }
}
