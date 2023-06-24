package jbs.ledger.assetholders.sovereignties.nations;

import jbs.ledger.assetholders.AssetholderType;

import java.util.UUID;

/**
 * Principalities have a monarch.
 */
public final class Principality extends Nation {
    public Principality(UUID uniqueId, String name) {
        super(uniqueId, name);
    }

    public Principality(Principality copy) {
        super(copy);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.PRINCIPALITY;
    }
}
