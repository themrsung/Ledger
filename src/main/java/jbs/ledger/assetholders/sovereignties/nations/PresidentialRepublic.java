package jbs.ledger.assetholders.sovereignties.nations;

import jbs.ledger.assetholders.AssetholderType;

import java.util.UUID;

/**
 * Presidential Republics have a president and vice president.
 */
public final class PresidentialRepublic extends Nation {
    public PresidentialRepublic(UUID uniqueId, String name) {
        super(uniqueId, name);
    }

    public PresidentialRepublic(PresidentialRepublic copy) {
        super(copy);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.PRESIDENTIAL_REPUBLIC;
    }
}
