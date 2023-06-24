package jbs.ledger.io.types.orders.synthetic;

import jbs.ledger.io.types.assets.synthetic.unique.StockForwardData;
import jbs.ledger.io.types.orders.OrderData;

/**
 * Collateral is meaningless in futures orders
 */
public class StockFuturesOrderData extends OrderData<StockForwardData> {
    public StockFuturesOrderData(OrderData<?> parent) {
        super(parent);
    }

    public StockFuturesOrderData() {
        super();
    }
}
