package jbs.ledger.io.types.assets.synthetic.unique;

import jbs.ledger.io.types.assets.basic.CommodityData;

public final class CommodityForwardData extends UniqueNoteData {
    public CommodityForwardData() {
        super();
    }

    public CommodityData delivery;
}
