package jbs.ledger.classes.orders.synthetic;

import jbs.ledger.classes.orders.AbstractOrder;
import jbs.ledger.classes.orders.OrderType;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.markets.Market;
import jbs.ledger.io.types.orders.synthetic.StockFuturesOrderData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Stock;
import jbs.ledger.types.assets.synthetic.StackableNote;

import java.util.Date;
import java.util.UUID;

public final class StockFuturesOrder extends AbstractOrder<StackableNote<Stock>> {
    public StockFuturesOrder(
            UUID uniqueId,
            OrderType type,
            Economic sender,
            Date date,
            double price,
            long quantity
    ) {
        super(uniqueId, type, sender, date, price, quantity, null, null);
    }

    public StockFuturesOrder(AbstractOrder<StackableNote<Stock>> copy) {
        super(copy);
    }

    /**
     * Does nothing; Futures orders have no collateral.
     * @param market Does nothing.
     */
    @Override
    public void unregisterCashCollateral(Market<StackableNote<Stock>> market) {

    }

    /**
     * Does nothing; Futures orders have no collateral.
     * @param market Does nothing.
     */
    @Override
    public void unregisterAssetCollateral(Market<StackableNote<Stock>> market) {

    }

    // IO
    public StockFuturesOrderData toData() {
        return new StockFuturesOrderData(super.toData());
    }

    public static StockFuturesOrder fromData(StockFuturesOrderData data, LedgerState state) {
        return new StockFuturesOrder(
                data.uniqueId,
                data.type,
                state.getAssetholder(data.sender),
                data.date,
                data.price,
                data.quantity
        );
    }
}