package jbs.ledger.assetholders.sovereignties.nations;

import jbs.ledger.assetholders.AssetholderType;

import java.util.UUID;

/**
 * Parliamentary Republics have a prime minister.
 */
public final class ParliamentaryRepublic extends Nation {
    public ParliamentaryRepublic(UUID uniqueId, String name) {
        super(uniqueId, name);
    }

    public ParliamentaryRepublic(ParliamentaryRepublic copy) {
        super(copy);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.PARLIAMENTARY_REPUBLIC;
    }
}
