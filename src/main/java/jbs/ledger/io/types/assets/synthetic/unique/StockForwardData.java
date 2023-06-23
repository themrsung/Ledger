package jbs.ledger.io.types.assets.synthetic.unique;

import jbs.ledger.io.types.assets.basic.StockData;

public final class StockForwardData extends UniqueNoteData {
    public StockForwardData() {
        super();
    }

    public StockData delivery;
}
