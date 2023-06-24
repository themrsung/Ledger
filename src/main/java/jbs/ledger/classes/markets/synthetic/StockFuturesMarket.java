package jbs.ledger.classes.markets.synthetic;

import jbs.ledger.classes.markets.AbstractMarket;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.basic.Stock;
import jbs.ledger.types.assets.synthetic.StackableNote;

import java.util.UUID;

public final class StockFuturesMarket extends AbstractMarket<StackableNote<Stock>> {
    public StockFuturesMarket(
            UUID uniqueId,
            String symbol,
            Economic exchange,
            String currency,
            StackableNote<Stock> unitAsset,
            double tickSize
    ) {
        super(uniqueId, symbol, exchange, currency, unitAsset, tickSize);
    }

    public StockFuturesMarket(AbstractMarket<StackableNote<Stock>> copy) {
        super(copy);
    }
}
