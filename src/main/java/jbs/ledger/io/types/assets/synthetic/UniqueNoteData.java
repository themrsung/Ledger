package jbs.ledger.io.types.assets.synthetic;

import jbs.ledger.io.types.assets.AssetData;

import java.util.UUID;

public final class UniqueNoteData<D extends AssetData> extends NoteData<D> {
    public UniqueNoteData() {}

    public UUID uniqueId;
}
