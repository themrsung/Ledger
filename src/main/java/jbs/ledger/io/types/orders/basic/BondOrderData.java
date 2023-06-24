package jbs.ledger.io.types.orders.basic;

import jbs.ledger.io.types.assets.synthetic.stackable.BondData;
import jbs.ledger.io.types.orders.OrderData;

/**
 * Collateral is meaningless in bond orders
 */
public class BondOrderData extends OrderData<BondData> {
    public BondOrderData(OrderData<?> parent) {
        super(parent);
    }

    public BondOrderData() {
        super();
    }
}
