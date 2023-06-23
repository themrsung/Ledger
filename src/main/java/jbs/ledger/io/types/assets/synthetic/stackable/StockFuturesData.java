package jbs.ledger.io.types.assets.synthetic.stackable;

import jbs.ledger.io.types.assets.basic.StockData;

public final class StockFuturesData extends StackableNoteData {
    public StockFuturesData() {
        super();
    }

    public StockData delivery;
}
