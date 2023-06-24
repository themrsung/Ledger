package jbs.ledger.io.types.assets.synthetic.unique;

import jbs.ledger.io.types.assets.synthetic.stackable.BondData;

public final class BondForwardData extends UniqueNoteData {
    public BondForwardData() {
        super();
    }

    public BondData delivery;
}
