package jbs.ledger.io.types.orders.basic;

import jbs.ledger.io.types.assets.synthetic.unique.NoteData;
import jbs.ledger.io.types.orders.OrderData;

public class ForexOrderData extends OrderData<NoteData> {
    public ForexOrderData(OrderData<?> parent) {
        super(parent);
    }

    public ForexOrderData() {
        super();
    }
}
