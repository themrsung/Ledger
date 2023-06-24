package jbs.ledger.classes.markets.basic;

import jbs.ledger.classes.markets.AbstractMarket;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.synthetic.StackableNote;

import java.util.UUID;

public final class BondMarket extends AbstractMarket<StackableNote<Cash>> {
    public BondMarket(
            UUID uniqueId,
            String symbol,
            Economic exchange,
            String currency,
            StackableNote<Cash> unitAsset,
            double tickSize
    ) {
        super(uniqueId, symbol, exchange, currency, unitAsset, tickSize);
    }

    public BondMarket(AbstractMarket<StackableNote<Cash>> copy) {
        super(copy);
    }
}
