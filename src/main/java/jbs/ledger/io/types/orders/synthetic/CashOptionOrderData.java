package jbs.ledger.io.types.orders.synthetic;

import jbs.ledger.io.types.assets.synthetic.stackable.CashOptionData;
import jbs.ledger.io.types.orders.OrderData;

/**
 * Collateral is meaningless in futures orders
 */
public class CashOptionOrderData extends OrderData<CashOptionData> {
    public CashOptionOrderData(OrderData<?> parent) {
        super(parent);
    }

    public CashOptionOrderData() {
        super();
    }
}
