package jbs.ledger.io.types.assets.synthetic;

import jbs.ledger.io.types.assets.AssetData;

import java.util.Date;
import java.util.UUID;

public abstract class AbstractNoteData extends AssetData {
    public AbstractNoteData() {
        super();
    }
    public UUID deliverer;
    public Date expiration;
    public long quantity;
}
