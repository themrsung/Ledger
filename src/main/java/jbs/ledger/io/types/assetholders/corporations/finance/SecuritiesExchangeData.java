package jbs.ledger.io.types.assetholders.corporations.finance;

import jbs.ledger.io.types.assetholders.corporations.CorporationData;
import jbs.ledger.io.types.assets.basic.StockData;
import jbs.ledger.io.types.assets.synthetic.stackable.BondData;
import jbs.ledger.io.types.markets.MarketData;
import jbs.ledger.io.types.orders.basic.BondOrderData;
import jbs.ledger.io.types.orders.basic.StockOrderData;

import java.util.ArrayList;

public final class SecuritiesExchangeData extends CorporationData {
    public SecuritiesExchangeData(CorporationData parent) {
        super(parent);
    }

    public SecuritiesExchangeData(SecuritiesExchangeData copy) {
        super(copy);

        this.stockMarkets = copy.stockMarkets;
        this.bondMarkets = copy.bondMarkets;
    }

    public SecuritiesExchangeData() {
        super();
    }

    public ArrayList<MarketData<StockData, StockOrderData>> stockMarkets;
    public ArrayList<MarketData<BondData, BondOrderData>> bondMarkets;

}
