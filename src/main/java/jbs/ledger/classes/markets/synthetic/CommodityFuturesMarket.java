package jbs.ledger.classes.markets.synthetic;

import jbs.ledger.classes.markets.AbstractMarket;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.basic.Commodity;
import jbs.ledger.types.assets.synthetic.StackableNote;

import java.util.UUID;

public final class CommodityFuturesMarket extends AbstractMarket<StackableNote<Commodity>> {
    public CommodityFuturesMarket(
            UUID uniqueId,
            String symbol,
            Economic exchange,
            String currency,
            StackableNote<Commodity> unitAsset,
            double tickSize
    ) {
        super(uniqueId, symbol, exchange, currency, unitAsset, tickSize);
    }

    public CommodityFuturesMarket(AbstractMarket<StackableNote<Commodity>> copy) {
        super(copy);
    }
}
