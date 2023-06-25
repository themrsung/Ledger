package jbs.ledger.io.types.orders.synthetic;

import jbs.ledger.io.types.assets.synthetic.stackable.StockOptionData;
import jbs.ledger.io.types.assets.synthetic.unique.StockForwardData;
import jbs.ledger.io.types.orders.OrderData;

/**
 * Collateral is meaningless in futures orders
 */
public class StockOptionOrderData extends OrderData<StockOptionData> {
    public StockOptionOrderData(OrderData<?> parent) {
        super(parent);
    }

    public StockOptionOrderData() {
        super();
    }
}
