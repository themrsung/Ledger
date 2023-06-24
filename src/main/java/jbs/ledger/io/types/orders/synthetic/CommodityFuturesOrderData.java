package jbs.ledger.io.types.orders.synthetic;

import jbs.ledger.io.types.assets.synthetic.unique.CommodityForwardData;
import jbs.ledger.io.types.orders.OrderData;

/**
 * Collateral is meaningless in futures orders
 */
public class CommodityFuturesOrderData extends OrderData<CommodityForwardData> {
    public CommodityFuturesOrderData(OrderData<?> parent) {
        super(parent);
    }

    public CommodityFuturesOrderData() {
        super();
    }
}
