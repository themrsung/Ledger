package jbs.ledger.io.types.assets.synthetic.stackable;

import jbs.ledger.io.types.assets.basic.CashData;

public final class BondData extends StackableNoteData {
    public BondData() {
        super();
    }

    public CashData delivery;
}
