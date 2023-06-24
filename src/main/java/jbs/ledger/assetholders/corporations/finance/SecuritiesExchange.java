package jbs.ledger.assetholders.corporations.finance;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.classes.markets.basic.BondMarket;
import jbs.ledger.classes.markets.basic.StockMarket;
import jbs.ledger.classes.markets.synthetic.CommodityFuturesMarket;
import jbs.ledger.classes.markets.synthetic.StockFuturesMarket;
import jbs.ledger.io.types.assetholders.corporations.finance.FuturesExchangeData;
import jbs.ledger.io.types.assetholders.corporations.finance.SecuritiesExchangeData;
import jbs.ledger.io.types.assets.basic.StockData;
import jbs.ledger.io.types.assets.synthetic.stackable.BondData;
import jbs.ledger.io.types.assets.synthetic.stackable.CommodityFuturesData;
import jbs.ledger.io.types.assets.synthetic.stackable.StockFuturesData;
import jbs.ledger.io.types.markets.MarketData;
import jbs.ledger.io.types.orders.basic.BondOrderData;
import jbs.ledger.io.types.orders.basic.StockOrderData;
import jbs.ledger.io.types.orders.synthetic.CommodityFuturesOrderData;
import jbs.ledger.io.types.orders.synthetic.StockFuturesOrderData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Securities exchanges can process stock/bond orders.
 */
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




    // IO
    @Override
    public SecuritiesExchangeData toData() {
        SecuritiesExchangeData data = new SecuritiesExchangeData(super.toData());

        for (StockMarket market : stockMarkets) {
            data.stockMarkets.add(market.toData());
        }

        for (BondMarket market : bondMarkets) {
            data.bondMarkets.add(market.toData());
        }

        return data;
    }

    public static SecuritiesExchange getEmptyInstance(UUID uniqueId) {
        return new SecuritiesExchange(uniqueId);
    }

    private SecuritiesExchange(UUID uniqueId) {
        super(uniqueId);

        this.stockMarkets = new ArrayList<>();
        this.bondMarkets = new ArrayList<>();
    }

    public void load(SecuritiesExchangeData data, LedgerState state) {
        super.load(data, state);

        for (MarketData<StockData, StockOrderData> market : data.stockMarkets) {
            stockMarkets.add(StockMarket.fromData(market, state));
        }

        for (MarketData<BondData, BondOrderData> market : data.bondMarkets) {
            bondMarkets.add(BondMarket.fromData(market, state));
        }
    }
}
