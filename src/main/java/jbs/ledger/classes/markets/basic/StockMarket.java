package jbs.ledger.classes.markets.basic;

import jbs.ledger.classes.markets.AbstractMarket;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.basic.Stock;

import java.util.UUID;

public final class StockMarket extends AbstractMarket<Stock> {
    public StockMarket(
            UUID uniqueId,
            String symbol,
            Economic exchange,
            String currency,
            Stock unitAsset,
            double tickSize
    ) {
        super(uniqueId, symbol, exchange, currency, unitAsset, tickSize);
    }

    public StockMarket(AbstractMarket<Stock> copy) {
        super(copy);
    }
}
