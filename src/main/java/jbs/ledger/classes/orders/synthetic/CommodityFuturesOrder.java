package jbs.ledger.classes.orders.synthetic;

import jbs.ledger.classes.orders.AbstractOrder;
import jbs.ledger.classes.orders.OrderType;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.markets.Market;
import jbs.ledger.types.assets.basic.Commodity;
import jbs.ledger.types.assets.synthetic.StackableNote;

import java.util.Date;
import java.util.UUID;

public final class CommodityFuturesOrder extends AbstractOrder<StackableNote<Commodity>> {
    public CommodityFuturesOrder(
            UUID uniqueId,
            OrderType type,
            Economic sender,
            Date date,
            double price,
            long quantity
    ) {
        super(uniqueId, type, sender, date, price, quantity, null, null);
    }

    public CommodityFuturesOrder(AbstractOrder<StackableNote<Commodity>> copy) {
        super(copy);
    }

    /**
     * Does nothing; Futures orders have no collateral.
     * @param market Does nothing.
     */
    @Override
    public void unregisterCashCollateral(Market<StackableNote<Commodity>> market) {

    }

    /**
     * Does nothing; Futures orders have no collateral.
     * @param market Does nothing.
     */
    @Override
    public void unregisterAssetCollateral(Market<StackableNote<Commodity>> market) {

    }
}
