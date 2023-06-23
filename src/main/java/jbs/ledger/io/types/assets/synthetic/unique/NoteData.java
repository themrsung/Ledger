package jbs.ledger.io.types.assets.synthetic.unique;

import jbs.ledger.io.types.assets.basic.CashData;

public final class NoteData extends UniqueNoteData {
    public NoteData() {
        super();
    }

    public CashData delivery;
}
