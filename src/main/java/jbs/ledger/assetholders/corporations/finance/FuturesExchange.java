package jbs.ledger.assetholders.corporations.finance;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.classes.markets.synthetic.CashOptionMarket;
import jbs.ledger.classes.markets.synthetic.CommodityFuturesMarket;
import jbs.ledger.classes.markets.synthetic.StockFuturesMarket;
import jbs.ledger.classes.markets.synthetic.StockOptionMarket;
import jbs.ledger.io.types.assetholders.corporations.finance.FuturesExchangeData;
import jbs.ledger.io.types.assets.synthetic.stackable.CashOptionData;
import jbs.ledger.io.types.assets.synthetic.stackable.CommodityFuturesData;
import jbs.ledger.io.types.assets.synthetic.stackable.StockFuturesData;
import jbs.ledger.io.types.assets.synthetic.stackable.StockOptionData;
import jbs.ledger.io.types.markets.MarketData;
import jbs.ledger.io.types.orders.synthetic.CashOptionOrderData;
import jbs.ledger.io.types.orders.synthetic.CommodityFuturesOrderData;
import jbs.ledger.io.types.orders.synthetic.StockFuturesOrderData;
import jbs.ledger.io.types.orders.synthetic.StockOptionOrderData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Futures exchanges can process futures orders
 */
public final class FuturesExchange extends Corporation {
    public FuturesExchange(
            UUID uniqueId,
            String name,
            String symbol,
            String currency,
            Cash capital,
            long shareCount
    ) {
        super(uniqueId, name, symbol, currency, capital, shareCount);

        this.commodityFuturesMarkets = new ArrayList<>();
        this.stockFuturesMarkets = new ArrayList<>();
        this.stockOptionMarkets = new ArrayList<>();
        this.cashOptionMarkets = new ArrayList<>();
    }

    public FuturesExchange(FuturesExchange copy) {
        super(copy);

        this.commodityFuturesMarkets = copy.commodityFuturesMarkets;
        this.stockFuturesMarkets = copy.stockFuturesMarkets;
        this.stockOptionMarkets = copy.stockOptionMarkets;
        this.cashOptionMarkets = copy.cashOptionMarkets;
    }

    private final ArrayList<CommodityFuturesMarket> commodityFuturesMarkets;
    private final ArrayList<StockFuturesMarket> stockFuturesMarkets;
    private final ArrayList<StockOptionMarket> stockOptionMarkets;
    private final ArrayList<CashOptionMarket> cashOptionMarkets;

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

    public ArrayList<StockOptionMarket> getStockOptionMarkets() {
        return new ArrayList<>(stockOptionMarkets);
    }

    public void addStockOptionMarket(StockOptionMarket market) {
        stockOptionMarkets.add(market);
    }

    public boolean removeStockOptionMarket(StockOptionMarket market) {
        return stockOptionMarkets.remove(market);
    }

    public ArrayList<CashOptionMarket> getCashOptionMarkets() {
        return new ArrayList<>(cashOptionMarkets);
    }

    public void addCashOptionMarket(CashOptionMarket market) {
        cashOptionMarkets.add(market);
    }

    public boolean removeCashOptionMarket(CashOptionMarket market) {
        return cashOptionMarkets.remove(market);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.FUTURES_EXCHANGE;
    }


    // IO
    @Override
    public FuturesExchangeData toData() {
        FuturesExchangeData data = new FuturesExchangeData(super.toData());

        for (CommodityFuturesMarket market : commodityFuturesMarkets) {
            data.commodityFuturesMarkets.add(market.toData());
        }

        for (StockFuturesMarket market : stockFuturesMarkets) {
            data.stockFuturesMarkets.add(market.toData());
        }

        for (StockOptionMarket market : stockOptionMarkets) {
            data.stockOptionMarkets.add(market.toData());
        }

        for (CashOptionMarket market : cashOptionMarkets) {
            data.cashOptionMarkets.add(market.toData());
        }

        return data;
    }

    public static FuturesExchange getEmptyInstance(UUID uniqueId) {
        return new FuturesExchange(uniqueId);
    }

    private FuturesExchange(UUID uniqueId) {
        super(uniqueId);

        this.commodityFuturesMarkets = new ArrayList<>();
        this.stockFuturesMarkets = new ArrayList<>();
        this.stockOptionMarkets = new ArrayList<>();
        this.cashOptionMarkets = new ArrayList<>();
    }

    public void load(FuturesExchangeData data, LedgerState state) {
        super.load(data, state);

        this.commodityFuturesMarkets.clear();
        this.stockFuturesMarkets.clear();
        this.stockOptionMarkets.clear();
        this.cashOptionMarkets.clear();

        for (MarketData<CommodityFuturesData, CommodityFuturesOrderData> market : data.commodityFuturesMarkets) {
            this.commodityFuturesMarkets.add(CommodityFuturesMarket.fromData(market, state));
        }

        for (MarketData<StockFuturesData, StockFuturesOrderData> market : data.stockFuturesMarkets) {
            this.stockFuturesMarkets.add(StockFuturesMarket.fromData(market, state));
        }

        for (MarketData<StockOptionData, StockOptionOrderData> market : data.stockOptionMarkets) {
            this.stockOptionMarkets.add(StockOptionMarket.fromData(market, state));
        }

        for (MarketData<CashOptionData, CashOptionOrderData> market : data.cashOptionMarkets) {
            this.cashOptionMarkets.add(CashOptionMarket.fromData(market, state));
        }


    }
}
