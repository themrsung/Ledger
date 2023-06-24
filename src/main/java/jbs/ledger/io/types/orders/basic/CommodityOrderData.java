package jbs.ledger.io.types.orders.basic;

import jbs.ledger.io.types.assets.synthetic.unique.CommodityForwardData;
import jbs.ledger.io.types.orders.OrderData;

public class CommodityOrderData extends OrderData<CommodityForwardData> {
    public CommodityOrderData(OrderData<?> parent) {
        super(parent);
    }

    public CommodityOrderData() {
        super();
    }
}
