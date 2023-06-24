package jbs.ledger.io.types.orders.basic;

import jbs.ledger.io.types.assets.synthetic.unique.StockForwardData;
import jbs.ledger.io.types.orders.OrderData;

public class StockOrderData extends OrderData<StockForwardData> {
    public StockOrderData(OrderData<?> parent) {
        super(parent);
    }

    public StockOrderData() {
        super();
    }
}
