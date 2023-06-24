package jbs.ledger.assetholders.corporations.finance;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.classes.markets.basic.BondMarket;
import jbs.ledger.classes.markets.basic.StockMarket;
import jbs.ledger.types.assets.basic.Cash;

import java.util.ArrayList;
import java.util.UUID;

public final class SecuritiesExchange extends Corporation {
    public SecuritiesExchange(
            UUID uniqueId,
            String name,
            String symbol,
            String currency,
            Cash capital
    ) {
        super(uniqueId, name, symbol, currency, capital);

        this.stockMarkets = new ArrayList<>();
        this.bondMarkets = new ArrayList<>();
    }

    public SecuritiesExchange(SecuritiesExchange copy) {
        super(copy);

        this.stockMarkets = copy.stockMarkets;
        this.bondMarkets = copy.bondMarkets;
    }

    private final ArrayList<StockMarket> stockMarkets;
    private final ArrayList<BondMarket> bondMarkets;

    public ArrayList<StockMarket> getStockMarkets() {
        return new ArrayList<>(stockMarkets);
    }

    public void addStockMarket(StockMarket market) {
        stockMarkets.add(market);
    }

    public boolean removeStockMarket(StockMarket market) {
        return stockMarkets.remove(market);
    }

    public ArrayList<BondMarket> getBondMarkets() {
        return new ArrayList<>(bondMarkets);
    }

    public void addBondMarket(BondMarket market) {
        bondMarkets.add(market);
    }

    public boolean removeBondMarket(BondMarket market) {
        return bondMarkets.remove(market);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.SECURITIES_EXCHANGE;
    }
}
