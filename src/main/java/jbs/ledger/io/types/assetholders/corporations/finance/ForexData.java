package jbs.ledger.io.types.assetholders.corporations.finance;

import jbs.ledger.io.types.assetholders.corporations.CorporationData;
import jbs.ledger.io.types.assets.basic.CashData;
import jbs.ledger.io.types.markets.MarketData;
import jbs.ledger.io.types.orders.basic.ForexOrderData;

import java.util.ArrayList;

public final class ForexData extends CorporationData {
    public ForexData(CorporationData parent) {
        super(parent);
    }

    public ForexData(ForexData copy) {
        super(copy);

        this.markets = copy.markets;
    }

    public ForexData() {
        super();
    }

    public ArrayList<MarketData<CashData, ForexOrderData>> markets;

}
