package jbs.ledger.classes.orders.basic;

import jbs.ledger.classes.orders.AbstractOrder;
import jbs.ledger.classes.orders.OrderType;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.markets.Market;
import jbs.ledger.io.types.assets.synthetic.stackable.BondData;
import jbs.ledger.io.types.orders.basic.BondOrderData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.synthetic.StackableNote;

import java.util.Date;
import java.util.UUID;

public final class BondOrder extends AbstractOrder<StackableNote<Cash>> {
    public BondOrder(
            UUID uniqueId,
            OrderType type,
            Economic sender,
            Date date,
            double price,
            long quantity
    ) {
        super(uniqueId, type, sender, date, price, quantity, null, null);
    }

    public BondOrder(AbstractOrder<StackableNote<Cash>> copy) {
        super(copy);
    }

    @Override
    public void onFulfilled(Market<StackableNote<Cash>> market, double price, long quantity) {
        super.onFulfilled(market, price, quantity);

        // FIXME NO SETTLEMENT IN ALL ORDER TYPEs
    }

    /**
     * Does nothing; Bond orders have no collateral.
     * @param market Does nothing.
     */
    @Override
    public void unregisterCashCollateral(Market<StackableNote<Cash>> market) {

    }

    /**
     * Does nothing; Bond orders have no collateral.
     * @param market Does nothing.
     */
    @Override
    public void unregisterAssetCollateral(Market<StackableNote<Cash>> market) {

    }

    // IO
    public BondOrderData toData() {
        return new BondOrderData(super.toData());
    }

    public static BondOrder fromData(BondOrderData data, LedgerState state) {
        return new BondOrder(
                data.uniqueId,
                data.type,
                state.getAssetholder(data.sender),
                data.date,
                data.price,
                data.quantity
        );
    }
}
