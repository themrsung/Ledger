package jbs.ledger.classes.markets.basic;

import jbs.ledger.classes.markets.AbstractMarket;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.basic.Cash;

import java.util.UUID;

public final class ForexMarket extends AbstractMarket<Cash> {
    public ForexMarket(
            UUID uniqueId,
            String symbol,
            Economic exchange,
            String currency,
            Cash unitAsset,
            double tickSize
    ) {
        super(uniqueId, symbol, exchange, currency, unitAsset, tickSize);
    }

    public ForexMarket(AbstractMarket<Cash> copy) {
        super(copy);
    }
}
