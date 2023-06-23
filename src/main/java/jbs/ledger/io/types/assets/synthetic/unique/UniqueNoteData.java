package jbs.ledger.io.types.assets.synthetic.unique;

import jbs.ledger.io.types.assets.synthetic.AbstractNoteData;

import java.util.UUID;

public abstract class UniqueNoteData extends AbstractNoteData {
    public UniqueNoteData() {
        super();
    }

    public UUID uniqueId;
}
