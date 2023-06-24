package jbs.ledger.io.types.assetholders.corporations.finance;

import jbs.ledger.io.types.assetholders.corporations.CorporationData;
import jbs.ledger.io.types.assets.synthetic.stackable.CommodityFuturesData;
import jbs.ledger.io.types.assets.synthetic.stackable.StockFuturesData;
import jbs.ledger.io.types.markets.MarketData;
import jbs.ledger.io.types.orders.synthetic.CommodityFuturesOrderData;
import jbs.ledger.io.types.orders.synthetic.StockFuturesOrderData;

import java.util.ArrayList;

public final class FuturesExchangeData extends CorporationData {
    public FuturesExchangeData(CorporationData parent) {
        super(parent);
    }

    public FuturesExchangeData(FuturesExchangeData copy) {
        super(copy);

        this.commodityFuturesMarkets = copy.commodityFuturesMarkets;
        this.stockFuturesMarkets = copy.stockFuturesMarkets;
    }

    public FuturesExchangeData() {
        super();
    }

    public ArrayList<MarketData<CommodityFuturesData, CommodityFuturesOrderData>> commodityFuturesMarkets;
    public ArrayList<MarketData<StockFuturesData, StockFuturesOrderData>> stockFuturesMarkets;

}
