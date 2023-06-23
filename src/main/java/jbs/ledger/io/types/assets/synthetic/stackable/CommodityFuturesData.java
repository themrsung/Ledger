package jbs.ledger.io.types.assets.synthetic.stackable;

import jbs.ledger.io.types.assets.basic.CommodityData;

public final class CommodityFuturesData extends StackableNoteData {
    public CommodityFuturesData() {
        super();
    }

    public CommodityData delivery;
}
