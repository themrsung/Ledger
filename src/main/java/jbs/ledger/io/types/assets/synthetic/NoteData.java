package jbs.ledger.io.types.assets.synthetic;

import jbs.ledger.io.types.assets.AssetData;

import java.util.Date;
import java.util.UUID;

public abstract class NoteData<D extends AssetData> extends AssetData {
    public NoteData() {
        super();
    }

    public D delivery;
    public UUID deliverer;
    public Date expiration;
    public long quantity;
}
